package com.zeroone.novaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.zeroone.novaapp.responseModels.JobsStagesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(): ViewModel() {

    var jobStageLiveData: MutableLiveData<MutableList<JobsStagesModel>>? = null
        get() {
            if (field == null) {
                jobStageLiveData = MutableLiveData()
            }
            return field
        }

    fun processJobStages(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val jobStages = gson.fromJson(jobStages, Array<JobsStagesModel>::class.java).toList()
        jobStageLiveData?.postValue(jobStages as MutableList<JobsStagesModel>?)


    }


    val jobStages = """[
  {
    "StageID": 1,
    "StageName": "Request Received",
    "StageDescription": "Your service request has been successfully submitted.",
    "StageStatus": "passed",
    "Timestamp": "2025-02-10 08:15 AM"
  },
  {
    "StageID": 2,
    "StageName": "Accepted",
    "StageDescription": "Your request was accepted by our team.",
    "StageStatus": "passed",
    "Timestamp": "2025-02-10 08:25 AM"
  },
  {
    "StageID": 3,
    "StageName": "Scheduled",
    "StageDescription": "Your cleaning session has been scheduled.",
    "StageStatus": "passed",
    "Timestamp": "2025-02-10 09:00 AM"
  },
  {
    "StageID": 4,
    "StageName": "On The Way",
    "StageDescription": "Our cleaning team is en route to your location.",
    "StageStatus": "passed",
    "Timestamp": "2025-02-10 10:05 AM"
  },
  {
    "StageID": 5,
    "StageName": "In Progress",
    "StageDescription": "Cleaning is currently in progress.",
    "StageStatus": "active",
    "Timestamp": "2025-02-10 10:45 AM"
  },
  {
    "StageID": 6,
    "StageName": "Completed",
    "StageDescription": "Your cleaning service has been successfully completed.",
    "StageStatus": "pending",
    "Timestamp": "2025-02-10 12:20 PM"
  },
  {
    "StageID": 7,
    "StageName": "Approved",
    "StageDescription": "You have approved the final cleaning results.",
    "StageStatus": "pending",
    "Timestamp": "2025-02-10 12:35 PM"
  },
  {
    "StageID": 8,
    "StageName": "Closed",
    "StageDescription": "The service request has been closed. Thank you!",
    "StageStatus": "pending",
    "Timestamp": "2025-02-10 12:40 PM"
  }
]
"""

}