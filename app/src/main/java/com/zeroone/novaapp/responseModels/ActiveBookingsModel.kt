package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class ActiveBookingsModel(
    @SerializedName("propertyId")
    val propertyId: String? = null,

    @SerializedName("propertyName")
    val propertyName: String? = null,

    @SerializedName("serviceType")
    val serviceType: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("timestamp")
    val timestamp: String? = null
) {


}