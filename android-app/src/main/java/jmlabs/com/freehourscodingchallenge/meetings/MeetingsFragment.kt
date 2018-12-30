package jmlabs.com.freehourscodingchallenge.meetings

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jmlabs.com.freehourscodingchallenge.R
import jmlabs.com.freehourscodingchallenge.model.MeetingsViewModel
import kotlinx.serialization.ImplicitReflectionSerializer


@ImplicitReflectionSerializer
class MeetingsFragment : Fragment() {

    companion object {
        fun newInstance() = MeetingsFragment()
    }

    private lateinit var viewModel: MeetingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.meetings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MeetingsViewModel::class.java)
    }

}
