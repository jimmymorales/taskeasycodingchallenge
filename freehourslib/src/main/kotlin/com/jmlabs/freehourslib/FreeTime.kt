package com.jmlabs.freehourslib

import kotlinx.serialization.Serializable
import java.util.*

/**
 * Represents a list of employees that are free at a specific time.
 * This class gets serialized to Json:
 * {
 *   "time": "3:30PM",
 *   "employees": ["Kyle", "Luis", "Alex"]
 * }
 */
@Serializable
data class FreeTime(val time: String, val employees: MutableList<String> = mutableListOf()) {

    companion object {
        fun fromCalendar(calendar: Calendar): FreeTime {
            val hour = calendar.get(Calendar.HOUR)
            val minutes = calendar.get(Calendar.MINUTE)
            val time =
                (if (hour != 0) hour else 12).toString() +
                (if (minutes != 0) ":"+minutes.toString() else "") +
                (when (calendar.get(Calendar.AM_PM)) {
                    Calendar.AM -> "AM"
                    Calendar.PM -> "PM"
                    else -> throw Exception("Wrong time")
                })

            return FreeTime(time)
        }
    }
}