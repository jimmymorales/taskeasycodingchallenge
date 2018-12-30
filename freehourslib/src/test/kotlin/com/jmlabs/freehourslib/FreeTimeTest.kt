package com.jmlabs.freehourslib

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.time.LocalTime
import kotlin.test.assertEquals

@RunWith(BlockJUnit4ClassRunner::class)
class FreeTimeTest {
    @Test
    fun `should create a free time object from local time`() {
        assertEquals(
            FreeTime("9AM"),
            FreeTime.fromLocalTime(LocalTime.of(9, 0))
        )

        assertEquals(
            FreeTime("12:30PM"),
            FreeTime.fromLocalTime(LocalTime.of(12, 30))
        )

        assertEquals(
            FreeTime("3PM"),
            FreeTime.fromLocalTime(LocalTime.of(15, 0))
        )
    }
}