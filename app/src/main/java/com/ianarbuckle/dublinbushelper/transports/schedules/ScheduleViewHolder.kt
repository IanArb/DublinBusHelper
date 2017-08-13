package com.ianarbuckle.dublinbushelper.transports.schedules

import android.support.v7.widget.RecyclerView
import android.view.View

import kotlinx.android.synthetic.main.layout_item_schedule_data.view.*

/**
 * Created by Ian Arbuckle on 27/07/2017.

 */

class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ScheduleRowView {

    override fun setDestination(destination: String) {
        itemView.tvDest.text = destination
    }

    override fun setDueTime(dueTime: String) {
        itemView.tvDue.text = dueTime
    }

    override fun setDirection(direction: String) {
        itemView.tvDirection.text = direction
    }
}
