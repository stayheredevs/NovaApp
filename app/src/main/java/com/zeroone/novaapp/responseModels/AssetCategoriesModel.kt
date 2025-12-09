package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class AssetCategoriesModel(
    @SerializedName("CategoryID")
    val categoryID: String? = null,

    @SerializedName("CategoryName")
    val categoryName: String? = null
) {


}