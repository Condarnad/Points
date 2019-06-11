package ru.test.points.ui.fragments.list

import com.arellomobile.mvp.MvpView
import ru.test.points.model.points.DepositionPoint

interface ListFragmentView: MvpView{

    fun renderPoints(points:List<DepositionPoint>)

    fun renderPointsInfo()

}