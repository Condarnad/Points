package ru.test.points.ui.fragments.map

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.base.dagger.scope.FragmentScope
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.repository.partners.PartnersRepository
import ru.test.points.repository.points.PointsRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@FragmentScope
@InjectViewState
class MapPresenter @Inject constructor(
    private val pointsRepository: PointsRepository,
    private val partnersRepository: PartnersRepository
) : MvpPresenter<MapView>() {

    var selectedPoint: DepositionPointFullInfo? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        reloadPoints()
    }

    fun reloadPoints() {
    }


    var pointsDisposable: Disposable? = null
    fun updatePoints(bounds: GeoPointBounds) {

        pointDisposable?.dispose()
        pointsDisposable = pointsRepository
            .getPointsWithFullInfo(bounds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                viewState.renderPoints(it)
            }
    }

    fun navigateToDetails() {
        selectedPoint?.let(viewState::navigateToDetails)
    }

    var pointDisposable: Disposable? = null
    fun showPointInfo(depositionPointFullInfo: DepositionPointFullInfo?) {

        pointDisposable?.dispose()
        selectedPoint = depositionPointFullInfo

        depositionPointFullInfo?.let {

            pointDisposable = pointsRepository
                .getPointFullInfo(depositionPointFullInfo.depositionPoint.externalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    viewState.renderPointInfo(it)
                }
        }

        viewState.renderPointInfo(depositionPointFullInfo)

    }
}