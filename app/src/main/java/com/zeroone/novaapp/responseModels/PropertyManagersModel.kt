package com.zeroone.novaapp.responseModels

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PropertyManagersModel(
    @SerializedName("managerId")
    val managerId: String? = null,

    @SerializedName("profilePicture")
    val profilePicture: String? = null,

    @SerializedName("managerName")
    val managerName: String? = null,

    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @SerializedName("propertiesManaging")
    val propertiesManaging: Int? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("allocationDate")
    val allocationDate: String? = null
): Parcelable