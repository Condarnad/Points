package ru.test.points.ui.activities.details

import com.arellomobile.mvp.MvpView
import ru.test.points.model.points.DepositionPointFullInfo

interface DetailsView : MvpView {
    fun renderPointInfo(depositionPointFullInfo: DepositionPointFullInfo)
}