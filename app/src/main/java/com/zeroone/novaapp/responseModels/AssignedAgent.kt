package com.zeroone.novaapp.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class AssignedAgent(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("profile_pic_url")
    val profilePicUrl: String? = null,

    @SerializedName("phone_number")
    val phoneNumber: String? = null,

    @SerializedName("jobs_completed")
    val jobsCompleted: Int? = null,

    @SerializedName("rating")
    val rating: Double? = null
) {

}