package com.jmlabs.freehourslib

import kotlinx.serialization.Serializable
import java.time.DateTimeException
import java.time.LocalTime

@Serializable
data class EmployeeMeetings(val name: String, val meetings: List<String>) {
    fun isFreeTheNextHalfHour(time: LocalTime): Boolean {
        for (index in meetings.indices) {
            val meetingLocalTime = getLocalTime(index)
            if (meetingLocalTime?.equals(time) == true) {
                return false
            }
        }
        return true
    }

    private fun getLocalTime(index: Int): LocalTime? {
        val hour = meetings[index]
        val parts = hour.split(":")
        var hr = (if (parts.size > 1) parts[0] else parts[0].dropLast(2))
            .toIntOrNull() ?: throw DateTimeException("Wrong time")
        val minutes = if (parts.size > 1)
            parts[1].dropLast(2).toIntOrNull() ?: throw DateTimeException("Wrong time")
        else 0

        val time = hour.takeLast(2)

        if (time.equals("PM", true) && hr != 12) {
            hr += 12
        } else if (hr == 12) {
            hr = 0
        }

        return LocalTime.of(hr, minutes)
    }
}