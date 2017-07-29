package com.ianarbuckle.dublinbushelper.transports.schedules

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ianarbuckle.dublinbushelper.R

/**
 * Created by Ian Arbuckle on 31/03/2017.

 */

class ScheduleAdapter(private val presenter: SchedulePresenterImpl) : RecyclerView.Adapter<ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_schedule_data, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        presenter.onBindRowViewAtPositon(position, holder)
    }

    override fun getItemCount(): Int {
        return presenter.resultsRowCount
    }

}
