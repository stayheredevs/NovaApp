package com.zeroone.novaapp.responseModels

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class PropertyTypes(
    @SerializedName("propertyTypeId")
    val propertyTypeId: String? = null,

    @SerializedName("propertyTypeName")
    val propertyTypeName: String? = null
): Parcelable