package com.ecamarero.spacex.ui.launches.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecamarero.spacex.ui.R
import com.ecamarero.spacex.ui.launches.model.LaunchUI
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_launch.view.*

internal class LaunchAdapter(val onItemSelected: (LaunchUI) -> Unit) : ListAdapter<LaunchUI, LaunchAdapter.ViewHolder>(
    LaunchDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_launch,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: LaunchUI = getItem(position)
        with(holder.itemView) {
            Picasso.get().load(item.missionPatchUrlString).into(mission_patch_image)
            mission_name.text = item.missionName
            date_text.text = "${item.missionDate} at ${item.missionTime}"
            rocket_text.text = "${item.rocketName} - ${item.rocketType}"
            days_until_label.text =
                if (item.pastLaunch) context.getString(R.string.days_from_now_label) else context.getString(
                    R.string.days_since_now_label
                )
            days_until_text.text = "${item.daysForLaunch}"
            success_launch_image.setImageDrawable(when(item.successfulLaunch){
                true -> context.getDrawable(R.drawable.ic_check_black_24dp)
                false -> context.getDrawable(R.drawable.ic_clear_black_24dp)
                null -> null
            })
            setOnClickListener {
                onItemSelected(item)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private object LaunchDiffCallback : DiffUtil.ItemCallback<LaunchUI>() {
        override fun areItemsTheSame(oldItem: LaunchUI, newItem: LaunchUI): Boolean {
            return oldItem.flightNumber == newItem.flightNumber
        }

        override fun areContentsTheSame(oldItem: LaunchUI, newItem: LaunchUI): Boolean {
            return oldItem == newItem
        }
    }
}