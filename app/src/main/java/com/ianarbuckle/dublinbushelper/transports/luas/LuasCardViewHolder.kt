package com.ianarbuckle.dublinbushelper.transports.luas

import android.support.v7.widget.RecyclerView
import android.view.View

import kotlinx.android.synthetic.main.layout_card.view.*



/**
 * Created by Ian Arbuckle on 28/07/2017.

 */

class LuasCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LuasCardRowView {

    override fun setStopId(stopId: String) {
        itemView.tvStopid.text = stopId
    }

    override fun setName(name: String) {
        itemView.tvName.text = name
    }

    override fun setRoute(route: String) {
        itemView.tvRoute.text = route
    }

    override fun setRedColorText(color: Int) {
        itemView.tvRoute.setTextColor(color)
    }

    override fun setGreenColorText(color: Int) {
        itemView.tvRoute.setTextColor(color)
    }

    override fun setRedText(redText: String) {
        itemView.tvRoute.text = redText
    }

    override fun setGreenText(greenText: String) {
        itemView.tvRoute.text = greenText
    }
}
