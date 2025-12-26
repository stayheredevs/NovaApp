package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class JobDetails {
    @SerializedName("service_name")
    val serviceName: String? = null

    @SerializedName("date")
    val date: String? = null

    @SerializedName("time")
    val time: String? = null
}