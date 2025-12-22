package com.zeroone.novaapp.responseModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PropertyDetailsModel(
    var propertyName: String,
    var unitNumber: String,
    var location: String,
    var propertySqrFts: String,
    var propertySize: String?,
    var propertyType: String?
): Parcelable