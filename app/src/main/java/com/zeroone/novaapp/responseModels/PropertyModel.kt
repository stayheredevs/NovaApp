package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PropertyModel (
    @SerializedName("propertyId")
    val propertyId: String? = null,

    @SerializedName("propertyName")
    val propertyName: String? = null,

    @SerializedName("bedrooms")
    val bedrooms: Int? = null,

    @SerializedName("size")
    val size: String? = null
) {

    
}