package ru.test.points.ui.widget.pointshortinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.item_point_short_info.view.*
import kotlinx.android.synthetic.main.layout_point_short_info.view.*
import ru.test.points.R
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo
import android.content.ContextWrapper
import android.app.Activity


class PointShortInfoAdapter(val listener: (DepositionPointFullInfo, Bundle?) -> Unit) :
    ListAdapter<DepositionPointFullInfo, PointShortInfoAdapter.ViewHolder>(PointShortInfoDC()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val point = getItem(position)
        holder.apply {
            bind(listener, point)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_point_short_info, parent, false)
        )

    class ViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        private fun getActivity(): Activity? {
            var context = view.context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }

        fun bind(listener: (DepositionPointFullInfo, Bundle?) -> Unit, data: DepositionPointFullInfo) {
            view.root.apply {
                bind(data)
                setOnClickListener {

                    listener.invoke(data,
                        getActivity()?.let {
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                it,
                                view.root.point_image,
                                "partner_picture"
                            ).toBundle()
                        }
                    )

                    //костыль
                    //при использовании общего элемента мы не можем прокидывать новые данные в адаптер,
                    //потому что это будет другая вьюха и картинка улетит неизвестно куда
                    //поэтому меняем данные минуя submitList

                }
            }

        }
    }
}

private class PointShortInfoDC : DiffUtil.ItemCallback<DepositionPointFullInfo>() {
    override fun areItemsTheSame(oldItem: DepositionPointFullInfo, newItem: DepositionPointFullInfo) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: DepositionPointFullInfo, newItem: DepositionPointFullInfo) =
        oldItem.depositionPoint.externalId == newItem.depositionPoint.externalId
                && oldItem.depositionPointInfo == newItem.depositionPointInfo
                && oldItem.partner == newItem.partner

}
