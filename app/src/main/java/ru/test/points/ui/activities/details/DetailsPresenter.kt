package ru.test.points.ui.activities.details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.repository.points.PointsRepository
import javax.inject.Inject

@ActivityScope
@InjectViewState
class DetailsPresenter @Inject constructor(
    private val depositionPointFullInfo: DepositionPointFullInfo,
    private val pointsRepository: PointsRepository
): MvpPresenter<DetailsView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        pointsRepository.setPointVisitedStatus(depositionPointFullInfo,true)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        viewState.renderPointInfo(depositionPointFullInfo)
    }

}