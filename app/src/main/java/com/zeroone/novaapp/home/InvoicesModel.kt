package com.zeroone.novaapp.home

import com.google.gson.annotations.SerializedName


class InvoicesModel(
    @SerializedName("invoiceNumber")
    val invoiceNumber: String? = null,

    @SerializedName("invoiceStatus")
    val invoiceStatus: String? = null
)