package jmlabs.com.freehourscodingchallenge.meetings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jmlabs.com.freehourscodingchallenge.R
import jmlabs.com.freehourscodingchallenge.model.MeetingsViewModel
import kotlinx.android.synthetic.main.fragment_meetings.view.*
import kotlinx.serialization.ImplicitReflectionSerializer


@ImplicitReflectionSerializer
class MeetingsFragment : Fragment() {

    companion object {
        fun newInstance() = MeetingsFragment()
    }

    private lateinit var viewModel: MeetingsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var meetingsAdapter: MeetingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_meetings, container, false)
        recyclerView = rootView.rvMeetings
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(context)
        meetingsAdapter = MeetingsAdapter()

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = meetingsAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MeetingsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.getMeetings().observe(this, Observer {
            meetingsAdapter.setDataSet(it ?: listOf())
        })
    }
}
