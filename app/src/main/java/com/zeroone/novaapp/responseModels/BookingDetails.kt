package com.zeroone.novaapp.responseModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class BookingDetails(
    val propertyName: String,
    val propertyAddress: String,
    val serviceName: String,
    val serviceOptionName: String,
    val serviceOptionPrice: String,
    val serviceTime: String
): Parcelable
