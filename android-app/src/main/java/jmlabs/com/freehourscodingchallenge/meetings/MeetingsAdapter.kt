package jmlabs.com.freehourscodingchallenge.meetings

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmlabs.freehourslib.EmployeeMeetings
import jmlabs.com.freehourscodingchallenge.R
import kotlinx.android.synthetic.main.meeting_layout.view.*

class MeetingsAdapter
    : RecyclerView.Adapter<MeetingViewHolder>() {

    private val dataset: MutableList<EmployeeMeetings> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MeetingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meeting_layout, parent, false)
        return MeetingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(viewHolder: MeetingViewHolder, position: Int) {
        viewHolder.bind(dataset[position])
    }

    fun setDataSet(list: List<EmployeeMeetings>) {
        dataset.clear()
        dataset.addAll(list)
        notifyDataSetChanged()
    }
}

class MeetingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val tvTitle = itemView.tvEmployee
    private val tvEmployees = itemView.tvMeetings

    fun bind(employeeMeetings: EmployeeMeetings) {
        tvTitle.text = employeeMeetings.name
        tvEmployees.text = employeeMeetings.meetings.joinToString(separator = " - ")
    }
}