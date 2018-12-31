package com.jmlabs.freehourslib

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import kotlin.test.assertEquals

@RunWith(BlockJUnit4ClassRunner::class)
class FreeTimeTest {
    @Test
    fun `should create a free time object from local time`() {
        assertEquals(
            FreeTime("9AM"),
            FreeTime.fromCalendar(MeetingsSchedules.getCalendar(9, 0))
        )

        assertEquals(
            FreeTime("12:30PM"),
            FreeTime.fromCalendar(MeetingsSchedules.getCalendar(12, 30))
        )

        assertEquals(
            FreeTime("3PM"),
            FreeTime.fromCalendar(MeetingsSchedules.getCalendar(15, 0))
        )
    }
}