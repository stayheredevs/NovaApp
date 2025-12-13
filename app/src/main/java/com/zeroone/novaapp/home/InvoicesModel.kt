package com.zeroone.novaapp.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class InvoicesModel(
    @SerializedName("invoiceNumber")
    val invoiceNumber: String? = null,

    @SerializedName("invoiceStatus")
    val invoiceStatus: String? = null
): Parcelable