package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class JobsStagesModel(
    @SerializedName("StageID")
    val stageID: Int? = null,

    @SerializedName("StageName")
    val stageName: String? = null,

    @SerializedName("StageDescription")
    val stageDescription: String? = null,

    @SerializedName("StageStatus")
    val stageStatus: String? = null,

    @SerializedName("Timestamp")
    val timestamp: String? = null
)