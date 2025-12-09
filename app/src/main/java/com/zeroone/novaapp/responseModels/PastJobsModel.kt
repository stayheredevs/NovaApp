package com.zeroone.novaapp.responseModels

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PastJobsModel(
    @SerializedName("propertyId")
    val propertyId: String? = null,

    @SerializedName("propertyName")
    val propertyName: String? = null,

    @SerializedName("serviceType")
    val serviceType: String? = null,

    @SerializedName("serviceDate")
    val serviceDate: String? = null,

    @SerializedName("serviceTime")
    val serviceTime: String? = null
    
) : Parcelable