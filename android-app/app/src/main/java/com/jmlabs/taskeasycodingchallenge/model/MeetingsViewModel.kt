package com.jmlabs.taskeasycodingchallenge.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.jmlabs.freehourslib.EmployeeMeetings
import com.jmlabs.freehourslib.FreeTime
import com.jmlabs.freehourslib.MeetingsSchedules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer

/**
 * Meetings model is in charge of providing to the UI the employees meeting
 * and free times, also adding meetings.
 * Meetings are only saved on memory and not on storage.
 * This uses the freehourslib for calculating the employees that are free in work hours.
 */
@ImplicitReflectionSerializer
class MeetingsViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val meetingsLiveData = MutableLiveData<List<EmployeeMeetings>>()

    // Every time the employee meetings list change will calculate
    // free times again.
    val freeTimes: LiveData<List<FreeTime>> = Transformations.switchMap(meetingsLiveData) {
        val freeTimes = MutableLiveData<List<FreeTime>>()
        freeTimes.value = MeetingsSchedules(it).freeTimeList
        freeTimes
    }

    init {
        // reads and parse json on io thread
        ioScope.launch {
            val json = String(
                application.applicationContext.assets.open("input.json").readBytes())
            Log.d("TAG", json)
            meetingsLiveData.postValue(
                MeetingsSchedules.fromJson(json).meetings.toMutableList())
        }
    }

    /**
     * Returns list of employee meetings
     */
    fun getMeetings() : LiveData<List<EmployeeMeetings>> {
        return meetingsLiveData
    }

    /**
     * Return list of employees
     */
    fun getEmployees(): List<String> {
        return meetingsLiveData.value?.map { it.name } ?: listOf()
    }

    /**
     * Adds meeting live data object
     * @param employee name of employee
     * @param time time of meeting
     */
    fun addMeeting(employee: String, time: String) {
        val employeeMeetings = meetingsLiveData.value?.find { it.name == employee }

        val meetingExists =
            employeeMeetings?.meetings?.any { it == time } ?: false
        if (meetingExists) {
            throw Exception("Meeting already exists")
        }

        val meetings = employeeMeetings?.meetings?.toMutableList()
        meetings?.add(time)
        meetingsLiveData.value = meetingsLiveData.value
            ?.map {
                if(it.name == employee)
                    it.copy(meetings = meetings!!.toList())
                else
                    it
            }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel() // when view model gets clear cancel job
    }
}
