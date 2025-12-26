package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class JobStage {
    @SerializedName("stage")
    val stage: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("description")
    val description: String? = null
}