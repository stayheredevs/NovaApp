package com.zeroone.novaapp.appApi

import com.zeroone.novaapp.responseModels.ServerCallResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImplementation @Inject constructor(var apiService: ApiService) : ApiHelper {


    override suspend fun auth_registration(serviceRequest: String?): Response<ServerCallResponse> {
        return apiService.auth_registration(serviceRequest)


    }

}