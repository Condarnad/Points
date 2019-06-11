package ru.test.points.ui.fragments.map

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_map.*
import ru.test.points.R
import ru.test.points.base.androidx.MvpAppCompatFragment
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.ui.activities.details.DetailsActivity
import javax.inject.Inject

class MapFragment : MvpAppCompatFragment(), MapView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MapPresenter

    @ProvidePresenter
    fun providePresenter() = presenter


    var gMap: GoogleMap? = null
    var onMapReady: (() -> Unit)? = null
    var markerPointMapping: HashMap<Marker, DepositionPoint> = hashMapOf()


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_map, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogleMap()
        initBsb()
    }

    private fun initBsb() {
        val bsb = BottomSheetBehavior.from(map_card)
        bsb.state = BottomSheetBehavior.STATE_HIDDEN
        bsb.setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {}

                override fun onStateChanged(view: View, state: Int) {
                    if (state == BottomSheetBehavior.STATE_HIDDEN)
                        presenter.showPointInfo(null)
                }

            }
        )
        map_point.setOnClickListener {
            presenter.navigateToDetails()
        }
    }

    private fun initGoogleMap() {
        (childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment)?.getMapAsync {
            gMap = it
            onMapReady?.invoke()

            configMap()
            subscribeMapCallbacks()
        }
    }

    private fun configMap() {

    }

    private fun subscribeMapCallbacks() {
        gMap?.setOnMarkerClickListener {
            presenter.showPointInfo(markerPointMapping[it])
            true
        }

        gMap?.setOnCameraIdleListener {
            gMap?.let { gMap ->
                val projection = gMap.projection.visibleRegion.latLngBounds
                val middlePoint = GeoPoint(projection.center)

                presenter.updatePoints(
                    middlePoint,
                    GeoPointBounds(projection)
                        .northeast
                        .distanceBetween(middlePoint)
                        .toInt()
                )
            }
        }
    }

    override fun renderPoints(depositionPoints: List<DepositionPoint>) {

        gMap?.let { gMap ->


            //новые точки - N
            val newPoints = hashMapOf(*depositionPoints.map { it.externalId to it }.toTypedArray())

            // находим точки, которые еще не отображены на экране - 2*N
            val toAdd = newPoints.minus(markerPointMapping.values.map { it.externalId })

            // N
            val oldMarkers = markerPointMapping.keys.toHashSet()
            // удаляем точки, которых больше нет в поисковой выдаче - N
            oldMarkers.forEach {
                val point = markerPointMapping[it]
                if (!newPoints.contains(point?.externalId)) {
                    markerPointMapping.remove(it)
                    it.remove()
                }
            }
            // N
            // дорисовываем новые точки
            toAdd.forEach { point ->
                val marker = gMap.addMarker(
                    MarkerOptions()
                        .apply {
                            position(point.value.location?.toLatLng() ?: GeoPoint().toLatLng())
                        }
                )
                markerPointMapping[marker] = point.value
            }
        }

        if (gMap == null) {
            onMapReady = {
                renderPoints(depositionPoints)
            }
        }
    }

    override fun renderPointInfo(depositionPoint: DepositionPoint?) {
        val bsb = BottomSheetBehavior.from(map_card)
        if (depositionPoint != null) {
            map_point.bind(depositionPoint)

            bsb.apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                isFitToContents = true
            }
        } else
            bsb.state = BottomSheetBehavior.STATE_HIDDEN

    }

    override fun navigateToDetails(depositionPoint: DepositionPoint) {

        startActivity(Intent(context, DetailsActivity::class.java))
    }
}