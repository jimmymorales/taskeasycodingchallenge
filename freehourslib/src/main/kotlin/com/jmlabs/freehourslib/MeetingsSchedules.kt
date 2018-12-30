package com.jmlabs.freehourslib

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parseList
import kotlinx.serialization.stringify
import java.time.LocalTime

@ImplicitReflectionSerializer
fun List<FreeTime>.toJson(): String {
    return JSON.stringify(this)
}

/**
 * Represents "company" employee schedules, has access to employees meetings and
 * employees free time
 */
class MeetingsSchedules(private val meetings: List<EmployeeMeetings>) {

    val freeTimeList = getFreeTimesInWorkHours()

    fun getFreeTimesInWorkHours(): List<FreeTime> {
        val freeTimes = mutableListOf<FreeTime>()
        for (time in WORK_HOURS) {
            freeTimes.add(getFreeEmployeesAt(time))
        }
        return freeTimes
    }

    fun getFreeEmployeesAt(time: LocalTime): FreeTime {
        val freeTime = FreeTime.fromLocalTime(time)
        for (employeeMeeting in meetings) {
            if (employeeMeeting.isFreeTheNextHalfHour(time)) {
                freeTime.employees.add(employeeMeeting.name)
            }
        }
        return freeTime
    }

    companion object {
        /**
         * List of local times from 8AM to 5PM, excluding lunch (12PM-1PM)
         */
        val WORK_HOURS = listOf(
            LocalTime.of(8, 0),     // 8AM
            LocalTime.of(8, 30),    // 8:30AM
            LocalTime.of(9, 0),     // 9AM
            LocalTime.of(9, 30),    // 9:30AM
            LocalTime.of(10, 0),    // 10AM
            LocalTime.of(10, 30),   // 10:30AM
            LocalTime.of(11, 0),    // 11AM
            LocalTime.of(11, 30),   // 11:30AM
            LocalTime.of(13, 0),    // 1PM
            LocalTime.of(13, 30),   // 1:30PM
            LocalTime.of(14, 0),    // 2PM
            LocalTime.of(14, 30),   // 2:30PM
            LocalTime.of(15, 0),    // 3PM
            LocalTime.of(15, 30),   // 3:30PM
            LocalTime.of(16, 0),    // 4PM
            LocalTime.of(16, 30)    // 4:30PM
        )

        @ImplicitReflectionSerializer
        fun fromJson(json: String): MeetingsSchedules {
            val schedules = JSON.parseList<EmployeeMeetings>(json)
            return MeetingsSchedules(schedules)
        }
    }
}