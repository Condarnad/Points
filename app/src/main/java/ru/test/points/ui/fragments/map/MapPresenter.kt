package ru.test.points.ui.fragments.map

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.common.GeoPoint
import ru.test.points.repository.points.PointsRepository
import javax.inject.Inject

@ActivityScope
@InjectViewState
class MapPresenter @Inject constructor(
    private val pointsRepository: PointsRepository
) : MvpPresenter<MapView>() {

    var selectedPoint: DepositionPoint? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        reloadPoints()
    }

    fun reloadPoints() {
    }

    fun updatePoints(location: GeoPoint, radius: Int) {
        pointsRepository
            .getPoints(location, radius = radius)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.renderPoints(it)
            }
    }

    fun navigateToDetails() {
        selectedPoint?.let(viewState::navigateToDetails)
    }

    fun showPointInfo(depositionPoint: DepositionPoint?) {
        selectedPoint = depositionPoint
        viewState.renderPointInfo(depositionPoint)
    }
}