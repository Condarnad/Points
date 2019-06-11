package ru.test.points.ui.widget.pointshortinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_point_short_info.view.*
import ru.test.points.R
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo

class PointShortInfoAdapter(val listener: (DepositionPointFullInfo) -> Unit) :
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

        fun bind(listener: (DepositionPointFullInfo) -> Unit, data: DepositionPointFullInfo) {
            view.root.apply {
                bind(data)
                setOnClickListener {
                    listener.invoke(data)
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

}
