package com.zeroone.novaapp.remote_data_source

import com.zeroone.novaapp.appApi.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(val apiHelper: ApiHelper) {

    suspend fun autRegistration(serviceRequest: String?) = apiHelper.auth_registration(serviceRequest)

}