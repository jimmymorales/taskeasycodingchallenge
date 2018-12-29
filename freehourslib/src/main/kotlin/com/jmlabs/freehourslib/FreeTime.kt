package com.jmlabs.freehourslib

import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
data class FreeTime(val time: String, val employees: MutableList<String> = mutableListOf()) {

    companion object {
        fun fromLocalTime(localTime: LocalTime): FreeTime {
            val time =
                (when {
                    localTime.hour > 12 -> localTime.hour - 12
                    localTime.hour == 0 -> 12
                    else -> localTime.hour
                }).toString() +
                (if (localTime.minute == 0) "" else ":"+localTime.minute.toString()) +
                if (localTime.hour > 11) "PM" else "AM"

            return FreeTime(time)
        }
    }
}