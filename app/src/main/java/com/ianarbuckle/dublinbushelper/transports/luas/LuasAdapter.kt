package com.ianarbuckle.dublinbushelper.transports.luas

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton

import com.ianarbuckle.dublinbushelper.R

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result
import com.ianarbuckle.dublinbushelper.utils.OnRecyclerItemClickListener

import java.util.ArrayList
import java.util.LinkedList

import kotlinx.android.synthetic.main.layout_card.view.*
/**
 * Created by Ian Arbuckle on 02/03/2017.

 */

class LuasAdapter(private val presenter: LuasPresenterImpl) : RecyclerView.Adapter<LuasCardViewHolder>(), Filterable {

    private var filteredResultList: MutableList<Result>? = null

    private var onRecyclerItemClickListener: OnRecyclerItemClickListener? = null

    private var resultsFilter: ResultsFilter? = null

    init {
        this.filteredResultList = ArrayList<Result>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LuasCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_card, parent, false)
        return LuasCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LuasCardViewHolder, position: Int) {
        presenter.onBindRowViewAtPositon(position, holder)
        presenter.setRouteText(position, holder)

        val btnSchedule = holder.itemView.btnSchedule
        val btnFavourites = holder.itemView.ibFavourite
        onClickListeners(holder, position, btnSchedule, btnFavourites)
    }

    private fun onClickListeners(holder: LuasCardViewHolder, position: Int, btnSchedule: Button?, btnFavourites: ImageButton?) {
        btnSchedule?.setOnClickListener { presenter.onRowClickAtPosition(holder.adapterPosition, holder) }

        btnFavourites?.setOnClickListener {
            onRecyclerItemClickListener?.onItemClick(this@LuasAdapter, presenter.getResults(position), holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return presenter.resultsRowCount
    }

    override fun getFilter(): Filter {
        if (resultsFilter == null) {
            resultsFilter = ResultsFilter(this, presenter.resultsList)
        }
        return resultsFilter as ResultsFilter
    }

    fun updateList(list: MutableList<Result>) {
        filteredResultList = list
        notifyDataSetChanged()
    }

    private inner class ResultsFilter(private val adapter: LuasAdapter, resultList: List<Result>) : Filter() {
        private val resultList: List<Result>
        private val filteredList: MutableList<Result>

        init {
            this.resultList = LinkedList(resultList)
            this.filteredList = ArrayList<Result>()
        }

        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            filteredList.clear()
            val result = Filter.FilterResults()

            if (constraint.isEmpty()) {
                filteredList.addAll(resultList)
            } else {
                val filterPattern = constraint.toString().trim { it <= ' ' }

                for (results in resultList) {
                    val isName = results.displaystopid?.contains(filterPattern)
                    val operator = results.operators?.get(0)
                    val isOperator = operator?.routes.toString().contains(filterPattern)
                    val isStopId = results.stopid?.contains(filterPattern)
                    if (isName as Boolean || isOperator || isStopId as Boolean) {
                        filteredList.add(results)
                    }
                }
            }
            result.values = filteredList
            result.count = filteredList.size
            return result
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            adapter.filteredResultList?.clear()
            adapter.filteredResultList?.addAll(results.values as ArrayList<Result>)
            adapter.notifyDataSetChanged()
        }
    }

    fun setOnRecyclerItemLongClickListener(onRecyclerItemLongClickListener: OnRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemLongClickListener
    }
}
