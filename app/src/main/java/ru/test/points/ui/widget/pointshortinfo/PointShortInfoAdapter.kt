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

class PointShortInfoAdapter(val listener: (DepositionPoint) -> Unit) :
    ListAdapter<DepositionPoint, PointShortInfoAdapter.ViewHolder>(PointShortInfoDC()) {

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

        fun bind(listener: (DepositionPoint) -> Unit, data: DepositionPoint) {
            view.root.apply {
                bind(data)
                setOnClickListener {
                    listener.invoke(data)
                }
            }

        }
    }
}

private class PointShortInfoDC : DiffUtil.ItemCallback<DepositionPoint>() {
    override fun areItemsTheSame(oldItem: DepositionPoint, newItem: DepositionPoint) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: DepositionPoint, newItem: DepositionPoint) =
        oldItem == newItem

}
