package com.zeroone.novaapp.repositories

import com.zeroone.novaapp.network_calls.BaseApiResponse
import com.zeroone.novaapp.network_calls.NetworkResult
import com.zeroone.novaapp.remote_data_source.RemoteDataSource
import com.zeroone.novaapp.responseModels.ServerCallResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class AuthRepository @Inject constructor(val remoteDataSource: RemoteDataSource): BaseApiResponse() {

    suspend fun authRegistration(requestPayload: String?): Flow<NetworkResult<ServerCallResponse>> {
        return flow{

            emit(
                safeApiCall {
                    remoteDataSource.autRegistration(requestPayload)
                })



        }.flowOn(Dispatchers.IO)
    }



}