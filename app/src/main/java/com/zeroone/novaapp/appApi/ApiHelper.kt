package com.zeroone.novaapp.appApi

import com.zeroone.novaapp.responseModels.ServerCallResponse
import retrofit2.Response
import retrofit2.http.Body

interface ApiHelper {

    suspend fun auth_registration(serviceRequest: String?): Response<ServerCallResponse>

}