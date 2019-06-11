package ru.test.points.ui.fragments.map

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.test.points.model.points.DepositionPoint

interface MapView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun renderPoints(depositionPoints: List<DepositionPoint>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun renderPointInfo(depositionPoint: DepositionPoint?)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToDetails(depositionPoint: DepositionPoint)

}