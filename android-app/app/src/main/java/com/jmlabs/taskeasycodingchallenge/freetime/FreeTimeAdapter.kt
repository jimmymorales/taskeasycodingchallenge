package com.jmlabs.taskeasycodingchallenge.freetime

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmlabs.freehourslib.FreeTime
import com.jmlabs.taskeasycodingchallenge.R
import kotlinx.android.synthetic.main.free_time_layout.view.*

class FreeTimeAdapter
    : RecyclerView.Adapter<FreeTimeViewHolder>() {

    private val dataset: MutableList<FreeTime> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FreeTimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.free_time_layout, parent, false)
        return FreeTimeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(viewHolder: FreeTimeViewHolder, position: Int) {
        viewHolder.bind(dataset[position])
    }

    fun setDataSet(list: List<FreeTime>) {
        dataset.clear()
        dataset.addAll(list)
        notifyDataSetChanged()
    }
}

class FreeTimeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val tvTitle = itemView.tvTitle
    private val tvEmployees = itemView.tvEmployees

    fun bind(freeTime: FreeTime) {
        tvTitle.text = freeTime.time
        tvEmployees.text = freeTime.employees.joinToString(separator = " - ")
    }
}