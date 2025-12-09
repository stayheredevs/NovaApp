package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class ActionsModel(
    @SerializedName("action")
    var action: String? = null
)
