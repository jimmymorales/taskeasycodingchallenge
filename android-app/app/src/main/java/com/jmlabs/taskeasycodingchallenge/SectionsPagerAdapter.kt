package com.jmlabs.taskeasycodingchallenge

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jmlabs.taskeasycodingchallenge.freetime.FreeTimeFragment
import com.jmlabs.taskeasycodingchallenge.meetings.MeetingsFragment
import kotlinx.serialization.ImplicitReflectionSerializer

@ImplicitReflectionSerializer
class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments =
        listOf(FreeTimeFragment.newInstance(), MeetingsFragment.newInstance())

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}