package ru.test.points.ui.widget.pointshortinfo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_point_short_info.view.*
import ru.test.points.R
import ru.test.points.base.image.GlideWrapper
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.model.points.DepositionPointInfo
import ru.test.points.ui.utils.setTextOrGone
import ru.test.points.ui.utils.setVisibleOrGone

class PointShortInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    init {
        inflate(context, R.layout.layout_point_short_info, this)
    }

    fun bind(depositionPoint: DepositionPointFullInfo) {

        point_address.setTextOrGone(depositionPoint.depositionPoint.fullAddress)

        //Наверное не стоит использовать id партенра для отображения во вьюхе, если нет интернета
        point_title.text = depositionPoint.partner?.name ?: depositionPoint.depositionPoint.partnerName

        bindPointInfo(depositionPoint.depositionPointInfo)
        bindPartner(depositionPoint.partner)
    }

    fun bindPointInfo(depositionPointInfo: DepositionPointInfo?) {
        point_seen_indicator.setVisibleOrGone(depositionPointInfo?.isVisited != true)
    }

    fun bindPartner(partner: Partner?) {
        GlideWrapper
            .load(context, partner?.picture.orEmpty())
            .circleCrop()
            .into(point_image)
    }
}

