package com.jmlabs.freehourslib

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.time.LocalTime
import kotlin.test.assertEquals

@RunWith(BlockJUnit4ClassRunner::class)
class MeetingsSchedulesTest {

    private val employee1 = EmployeeMeetings("John", listOf("9AM", "11:30AM"))
    private val employee2 = EmployeeMeetings("Mary", listOf("9AM", "2PM", "3:30PM"))
    private val employee3 = EmployeeMeetings("Frank", listOf("9AM", "11:30AM"))

    private val meetingsSchedules = MeetingsSchedules(listOf(employee1, employee2, employee3))

    @Test
    fun `should get employees with free time`() {
        val freeTime9AM = meetingsSchedules.getFreeEmployeesAt(LocalTime.of(9, 0))
        assertEquals(freeTime9AM.time, "9AM")
        assertEquals(freeTime9AM.employees.size, 0)

        val freeTime1130AM = meetingsSchedules.getFreeEmployeesAt(LocalTime.of(11, 30))
        assertEquals(freeTime1130AM.time, "11:30AM")
        assertEquals(freeTime1130AM.employees.size, 1)
        assertEquals(freeTime1130AM.employees[0], "Mary")

        val freeTime2PM = meetingsSchedules.getFreeEmployeesAt(LocalTime.of(14, 0))
        assertEquals(freeTime2PM.time, "2PM")
        assertEquals(freeTime2PM.employees.size, 2)
        assertEquals(freeTime2PM.employees, listOf("John", "Frank"))

        val freeTime430PM = meetingsSchedules.getFreeEmployeesAt(LocalTime.of(16, 30))
        assertEquals(freeTime430PM.time, "4:30PM")
        assertEquals(freeTime430PM.employees.size, 3)
        assertEquals(freeTime430PM.employees, listOf("John", "Mary", "Frank"))
    }

    @Test
    fun `should have a list of free times`() {
        assertEquals(
            meetingsSchedules.freeTimeList.size,
            MeetingsSchedules.WORK_HOURS.size
        )

        assertEquals(meetingsSchedules.freeTimeList[0].time, "8AM")
        assertEquals(meetingsSchedules.freeTimeList[0].employees.size, 3)

        assertEquals(meetingsSchedules.freeTimeList[2].time, "9AM")
        assertEquals(meetingsSchedules.freeTimeList[2].employees.size, 0)

        assertEquals(meetingsSchedules.freeTimeList[7].time, "11:30AM")
        assertEquals(meetingsSchedules.freeTimeList[7].employees.size, 1)
        assertEquals(meetingsSchedules.freeTimeList[7].employees[0], "Mary")

        assertEquals(meetingsSchedules.freeTimeList.last().time, "4:30PM")
        assertEquals(meetingsSchedules.freeTimeList.last().employees.size, 3)
    }
}