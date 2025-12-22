package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PropertySize(
    @SerializedName("sizeId")
    val sizeId: String? = null,

    @SerializedName("sizeName")
    val sizeName: String? = null
)