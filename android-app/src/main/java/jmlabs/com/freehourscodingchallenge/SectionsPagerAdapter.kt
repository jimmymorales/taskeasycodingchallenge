package jmlabs.com.freehourscodingchallenge

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jmlabs.com.freehourscodingchallenge.freetime.FreeTimeFragment
import jmlabs.com.freehourscodingchallenge.meetings.MeetingsFragment
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