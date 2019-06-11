package ru.test.points.ui.fragments.map

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.model.points.DepositionPointInfo

interface MapView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun renderPoints(depositionPointsFullInfo: List<DepositionPointFullInfo>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun renderPointInfo(depositionPointFullInfo: DepositionPointFullInfo?)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToDetails(depositionPointFullInfo: DepositionPointFullInfo)

}