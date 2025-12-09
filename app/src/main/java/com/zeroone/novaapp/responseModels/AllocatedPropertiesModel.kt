package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AllocatedPropertiesModel(
    @SerializedName("propertyPicture")
    val propertyPicture: String? = null,

    @SerializedName("propertyName")
    val propertyName: String? = null,

    @SerializedName("propertyAddress")
    val propertyAddress: String? = null,

    @SerializedName("unitNumber")
    val unitNumber: String? = null
) {


}