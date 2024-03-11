package com.assignment.inncircles.network

import android.provider.CallLog
import com.assignment.inncircles.model.CallInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface CallService {

    @POST("/api/sendCallLogs")
    suspend fun sendCallLogs(@Body callInfo: CallInfo): SuccessResponse
}