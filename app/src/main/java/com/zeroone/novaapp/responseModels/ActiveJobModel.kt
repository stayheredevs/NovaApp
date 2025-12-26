package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ActiveJobModel {
    @SerializedName("assigned_agent")
    val assignedAgent: AssignedAgent? = null

    @SerializedName("job_details")
    val jobDetails: JobDetails? = null

    @SerializedName("job_stages")
    val jobStages: MutableList<JobsStagesModel?>? = null
}