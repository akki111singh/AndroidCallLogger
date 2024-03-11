package com.assignment.inncircles.network

import com.assignment.inncircles.model.CallInfo
import kotlinx.coroutines.flow.Flow

interface CallRepository {
    suspend fun sendCallData(callInfo: CallInfo): Flow<Resource<SuccessResponse>>
}