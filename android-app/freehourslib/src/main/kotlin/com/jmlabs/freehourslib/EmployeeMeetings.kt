package com.jmlabs.freehourslib

import kotlinx.serialization.Serializable
import java.util.*

/**
 * Represents a list of time meetings of an employee
 * This class get serialized to Json:
 * {
 *   "name": "Kyle",
 *   "meetings": ["8AM", "1:30PM", "2PM"]
 * }
 */
@Serializable
data class EmployeeMeetings(val name: String, val meetings: List<String>) {
    fun isFreeTheNextHalfHour(time: Calendar): Boolean {
        for (index in meetings.indices) {
            val meetingLocalTime = getTime(index)
            if (meetingLocalTime.compareTo(time) == 0) {
                return false
            }
        }
        return true
    }

    fun getTime(index: Int): Calendar {
        val hour = meetings[index]
        val parts = hour.split(":")
        var hr = (if (parts.size > 1) parts[0] else parts[0].dropLast(2))
            .toIntOrNull() ?: throw WrongTimeException()
        val minutes = if (parts.size > 1)
            parts[1].dropLast(2).toIntOrNull() ?: throw WrongTimeException()
        else 0

        val time = when (hour.takeLast(2).toLowerCase()) {
            "pm" -> Calendar.PM
            "am" -> Calendar.AM
            else -> throw WrongTimeException()
        }

        if (hr == 12) hr = 0
        if (hr > 12) throw WrongTimeException()

        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(Calendar.AM_PM, time)
        calendar.set(Calendar.HOUR, hr)
        calendar.set(Calendar.MINUTE, minutes)
        return calendar
    }
}

class WrongTimeException : Exception()