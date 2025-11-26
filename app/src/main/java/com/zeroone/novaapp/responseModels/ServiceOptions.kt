package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class ServiceOptions(
    @SerializedName("optionName")
    val optionName: String? = null,

    @SerializedName("optionPrice")
    val optionPrice: String? = null
) {


}