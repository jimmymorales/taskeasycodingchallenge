package com.jmlabs.taskeasycodingchallenge.freetime

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmlabs.taskeasycodingchallenge.R
import com.jmlabs.taskeasycodingchallenge.model.MeetingsViewModel
import kotlinx.android.synthetic.main.fragment_free_time.view.*
import kotlinx.serialization.ImplicitReflectionSerializer

@ImplicitReflectionSerializer
class FreeTimeFragment : Fragment() {

    companion object {
        fun newInstance() = FreeTimeFragment()
    }

    private lateinit var viewModel: MeetingsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var freeTimeAdapter: FreeTimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_free_time, container, false)
        recyclerView = rootView.recyclerViewFreeTime
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(context)
        freeTimeAdapter = FreeTimeAdapter()

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = freeTimeAdapter
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MeetingsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.freeTimes.observe(this, Observer {
            freeTimeAdapter.setDataSet(it ?: listOf())
        })
    }
}