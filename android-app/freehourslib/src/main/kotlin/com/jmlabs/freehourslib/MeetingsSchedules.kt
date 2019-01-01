package com.jmlabs.freehourslib

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parseList
import kotlinx.serialization.stringify
import java.util.*

@ImplicitReflectionSerializer
fun List<FreeTime>.toJson(): String {
    return JSON.stringify(this)
}

/**
 * Represents "company" employee schedules, has access to employees meetings and
 * employees free time
 */
class MeetingsSchedules(val meetings: List<EmployeeMeetings>) {

    val freeTimeList = getFreeTimesInWorkHours()

    fun getFreeTimesInWorkHours(): List<FreeTime> {
        val freeTimes = mutableListOf<FreeTime>()
        for (time in WORK_HOURS) {
            freeTimes.add(getFreeEmployeesAt(time))
        }
        return freeTimes
    }

    fun getFreeEmployeesAt(time: Calendar): FreeTime {
        val freeTime = FreeTime.fromCalendar(time)
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
            getCalendar(8, 0),     // 8AM
            getCalendar(8, 30),    // 8:30AM
            getCalendar(9, 0),     // 9AM
            getCalendar(9, 30),    // 9:30AM
            getCalendar(10, 0),    // 10AM
            getCalendar(10, 30),   // 10:30AM
            getCalendar(11, 0),    // 11AM
            getCalendar(11, 30),   // 11:30AM
            getCalendar(13, 0),    // 1PM
            getCalendar(13, 30),   // 1:30PM
            getCalendar(14, 0),    // 2PM
            getCalendar(14, 30),   // 2:30PM
            getCalendar(15, 0),    // 3PM
            getCalendar(15, 30),   // 3:30PM
            getCalendar(16, 0),    // 4PM
            getCalendar(16, 30)    // 4:30PM
        )

        fun getCalendar(hour: Int, minutes: Int): Calendar {
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar.add(Calendar.HOUR_OF_DAY, hour)
            calendar.add(Calendar.MINUTE, minutes)
            return calendar
        }

        @ImplicitReflectionSerializer
        fun fromJson(json: String): MeetingsSchedules {
            val schedules = JSON.parseList<EmployeeMeetings>(json)
            return MeetingsSchedules(schedules)
        }
    }
}