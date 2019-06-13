package ru.test.points.ui.fragments.list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo

interface ListFragmentView: MvpView{

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun renderPoints(points:List<DepositionPointFullInfo>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updatePoint(index:Int,newPoint:DepositionPointFullInfo)
}