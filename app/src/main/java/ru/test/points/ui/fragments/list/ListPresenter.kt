package ru.test.points.ui.fragments.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.test.points.base.dagger.scope.FragmentScope
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.repository.points.PointsRepository
import javax.inject.Inject

@FragmentScope
@InjectViewState
class ListPresenter @Inject constructor(
    private val pointsRepository: PointsRepository
) : MvpPresenter<ListFragmentView>() {

    private var pointsDisposable: Disposable? = null
    private var pointDisposable: Disposable? = null
    private var points: List<DepositionPointFullInfo>? = null

    init {
        observePointChange()
        observeRecentPoints()
    }

    fun observeRecentPoints() {
        pointsDisposable?.dispose()
        pointsDisposable =
            pointsRepository
                .observeRecentPoints()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    points = it
                    viewState.renderPoints(it)
                }
    }

    fun observePointChange() {
        pointDisposable?.dispose()
        pointDisposable = pointsRepository
            .observePointChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { newPoint ->
                points?.let {
                    val index = it.indexOfFirst {
                        it.depositionPoint.externalId == newPoint.depositionPoint.externalId
                    }

                    it.getOrNull(index)?.let {
                        it.depositionPointInfo = newPoint.depositionPointInfo

                        viewState.updatePoint(index, newPoint)
                    }
                }
            }

    }
/*
    fun updatePointInfo(pointId: String) {
        pointDisposable?.dispose()
        pointDisposable = pointsRepository
            .getPointFullInfo(pointId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { newPoint ->
                points?.let {
                    val index = it.indexOfFirst {
                        it.depositionPoint.externalId == pointId
                    }
                    it[index].depositionPointInfo = newPoint.depositionPointInfo

                    //viewState.renderPoints(it)
                    viewState.updatePoint(index, newPoint)
                }
            }
    }*/


    override fun onDestroy() {
        pointDisposable?.dispose()
        pointsDisposable?.dispose()
        super.onDestroy()
    }

}