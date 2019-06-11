package ru.test.points.ui.widget.pointshortinfo

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_point_short_info.view.*
import ru.test.points.R
import ru.test.points.base.image.GlideWrapper
import ru.test.points.model.points.DepositionPoint

class PointShortInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    init {
        inflate(context, R.layout.layout_point_short_info, this)
    }

    fun bind(depositionPoint: DepositionPoint) {
        point_address.text = depositionPoint.fullAddress
        point_title.text = depositionPoint.addressInfo

       /*GlideWrapper
            .load(context,depositionPoint)
            .into(point_image)*/
    }
}

