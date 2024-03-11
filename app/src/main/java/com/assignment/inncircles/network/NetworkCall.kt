package com.assignment.inncircles.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class NetworkCall(private val internetHelper: InternetHelper) {

    suspend fun <T> sendRequest(request: suspend () -> T): Flow<Resource<T>> {
        return flow {
            if (internetHelper.isOnline) {
                emit(Resource.Loading)
                val response = request.invoke()
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(NoInternetException))
            }
        }.catch { exception ->
            emit(Resource.Error(exception))
        }.flowOn(Dispatchers.IO)
    }
}