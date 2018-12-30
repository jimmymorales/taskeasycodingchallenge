package com.jmlabs.freehourslib

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.time.DateTimeException
import java.time.LocalTime
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
    fun `should throw DateTimeException`() {
        for (index in wrongTimeMeetings.meetings.indices) {
            assertFailsWith(DateTimeException::class) {
                wrongTimeMeetings.getLocalTime(index)
            }
        }
    }

    @Test
    fun `should convert to LocalTime`() {
        assertEquals(timeMeetings.getLocalTime(0), LocalTime.of(9, 0))
        assertEquals(timeMeetings.getLocalTime(1), LocalTime.of(11, 30))
        assertEquals(timeMeetings.getLocalTime(2), LocalTime.of(12, 0))
        assertEquals(timeMeetings.getLocalTime(3), LocalTime.of(16, 0))
    }

    @Test
    fun `should see if user is free`() {
        assert(timeMeetings.isFreeTheNextHalfHour(LocalTime.of(14, 0)))
        assertFalse {
            timeMeetings.isFreeTheNextHalfHour(LocalTime.of(9, 0))
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