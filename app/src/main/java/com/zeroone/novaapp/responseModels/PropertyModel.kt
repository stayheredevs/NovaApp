package com.zeroone.novaapp.responseModels


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PropertyModel(
    @SerializedName("propertyId")
    val propertyId: String? = null,

    @SerializedName("propertyName")
    val propertyName: String? = null,

    @SerializedName("bedrooms")
    val bedrooms: Int? = null,

    @SerializedName("size")
    val size: String? = null
) : Parcelable
