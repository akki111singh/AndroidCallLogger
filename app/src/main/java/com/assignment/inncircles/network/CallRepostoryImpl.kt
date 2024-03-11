package com.assignment.inncircles.network

import android.net.Network
import com.assignment.inncircles.model.CallInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CallRepositoryImpl @Inject constructor(
    private val callService: CallService,
    private val networkCall: NetworkCall
): CallRepository {
    override suspend fun sendCallData(callInfo: CallInfo): Flow<Resource<SuccessResponse>> {
        // return success for now since api is not available
        return flow {
            emit(Resource.Success(SuccessResponse(true)))
        }
//        return networkCall.sendRequest {
//            callService.sendCallLogs(callInfo)
//        }
    }

}