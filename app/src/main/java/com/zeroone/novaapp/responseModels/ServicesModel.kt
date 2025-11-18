package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.SerializedName


class ServicesModel(
    @SerializedName("serviceTypeId")
    val serviceTypeId: String? = null,

    @SerializedName("serviceTypeName")
    val serviceTypeName: String? = null,

    @SerializedName("iconUrl")
    val iconUrl: String? = null
) {


}