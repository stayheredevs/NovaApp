package com.zeroone.novaapp.appApi

import com.zeroone.novaapp.responseModels.ServerCallResponse
import com.zeroone.novaapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.AUTH_REGISTER)
    suspend fun auth_registration(@Body serviceRequest: String?): Response<ServerCallResponse>

}