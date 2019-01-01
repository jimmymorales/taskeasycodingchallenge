package com.jmlabs.freehourslib

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

@ImplicitReflectionSerializer
@RunWith(BlockJUnit4ClassRunner::class)
class EmployeeMeetingsTest {

    private val wrongTimeMeetings =
        EmployeeMeetings("John",
            listOf("11:30",
                "PM",
                "11:",
                "18:30PM"))

    private val timeMeetings =
        EmployeeMeetings("John",
            listOf("9AM", "11:30AM", "12PM", "4PM"))

    @Test
    fun `should throw WrongTimeException`() {
        for (index in wrongTimeMeetings.meetings.indices) {
            assertFailsWith(WrongTimeException::class) {
                wrongTimeMeetings.getTime(index)
            }
        }
    }

    @Test
    fun `should convert to LocalTime`() {
        assertEquals(timeMeetings.getTime(0),
            MeetingsSchedules.getCalendar(9, 0))
        assertEquals(timeMeetings.getTime(1),
            MeetingsSchedules.getCalendar(11, 30))
        assertEquals(timeMeetings.getTime(2),
            MeetingsSchedules.getCalendar(12, 0))
        assertEquals(timeMeetings.getTime(3),
            MeetingsSchedules.getCalendar(16, 0))
    }

    @Test
    fun `should see if user is free`() {
        assert(timeMeetings.isFreeTheNextHalfHour(
            MeetingsSchedules.getCalendar(14, 0)))
        assertFalse {
            timeMeetings.isFreeTheNextHalfHour(MeetingsSchedules.getCalendar(9, 0))
        }
    }

    @Test
    fun `should serialize to json`() {
        assertEquals(
            JSON.stringify(timeMeetings),
            "{\"name\":\"John\",\"meetings\":[\"9AM\",\"11:30AM\",\"12PM\",\"4PM\"]}"
        )
    }

    @Test
    fun `should deserialize from json`() {
        assertEquals(
            JSON.parse("{\"name\":\"Andy\",\"meetings\":[\"10AM\",\"11:30AM\"]}"),
            EmployeeMeetings("Andy", listOf("10AM", "11:30AM"))
        )
    }
}