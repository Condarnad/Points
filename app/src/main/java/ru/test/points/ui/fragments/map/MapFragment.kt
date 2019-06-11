package ru.test.points.ui.fragments.map

import android.content.Context
import android.content.Intent
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
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.model.points.DepositionPointFullInfo
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
    var markerPointMapping: HashMap<Marker, DepositionPointFullInfo> = hashMapOf()


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

                presenter.updatePoints(GeoPointBounds(projection))
            }
        }
    }

    override fun renderPoints(depositionPointsFullInfo: List<DepositionPointFullInfo>) {

        gMap?.let { gMap ->


            //новые точки
            val newPoints = hashMapOf(*depositionPointsFullInfo.map { it.depositionPoint.externalId to it }.toTypedArray())

            // находим точки, которые еще не отображены на экране
            val toAdd = newPoints.minus(markerPointMapping.values.map { it.depositionPoint.externalId })

            val oldMarkers = markerPointMapping.keys.toHashSet()
            // удаляем точки, которых больше нет в поисковой выдаче
            oldMarkers.forEach {
                val point = markerPointMapping[it]
                if (!newPoints.contains(point?.depositionPoint?.externalId)) {
                    markerPointMapping.remove(it)
                    it.remove()
                }
            }
            // дорисовываем новые точки
            toAdd.forEach { point ->
                val marker = gMap.addMarker(
                    MarkerOptions()
                        .apply {
                            position(point.value.depositionPoint.location?.toLatLng() ?: GeoPoint().toLatLng())
                        }
                )
                markerPointMapping[marker] = point.value
            }
        }

        if (gMap == null) {
            onMapReady = {
                renderPoints(depositionPointsFullInfo)
            }
        }
    }

    override fun renderPointInfo(depositionPointFullInfo: DepositionPointFullInfo?) {
        val bsb = BottomSheetBehavior.from(map_card)

        if (depositionPointFullInfo != null) {
            map_point.bind(depositionPointFullInfo)

            bsb.apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                isFitToContents = true
            }
        } else
            bsb.state = BottomSheetBehavior.STATE_HIDDEN

    }

    override fun navigateToDetails(depositionPointFullInfo: DepositionPointFullInfo) {

        startActivity(DetailsActivity.prepareIntent(context!!,depositionPointFullInfo))
    }
}