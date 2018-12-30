package jmlabs.com.freehourscodingchallenge.model

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

@ImplicitReflectionSerializer
class MeetingsViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val meetingsLiveData = MutableLiveData<List<EmployeeMeetings>>()

    val freeTimes = Transformations.switchMap(meetingsLiveData) {
        val freeTimes = MutableLiveData<List<FreeTime>>()
        freeTimes.value = MeetingsSchedules(it).freeTimeList
        freeTimes
    }

    init {
        ioScope.launch {
            val json = String(
                application.applicationContext.assets.open("input.json").readBytes())
            Log.d("TAG", json)
            val meetings = MeetingsSchedules.fromJson(json).meetings
            meetingsLiveData.postValue(meetings)
        }
    }

    fun getMeetings() : LiveData<List<EmployeeMeetings>> {
        return meetingsLiveData
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
