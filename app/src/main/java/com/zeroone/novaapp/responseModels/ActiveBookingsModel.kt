package com.zeroone.novaapp.responseModels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable