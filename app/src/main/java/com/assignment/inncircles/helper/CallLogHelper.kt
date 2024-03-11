package com.assignment.inncircles.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.CallLog
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.assignment.inncircles.model.CallInfo
import com.assignment.inncircles.model.CallType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CallLogHelper {
    private const val TAG = "CALL LOG HELPER"
    fun retrieveCallDetails(context: Context?): CallInfo? {
        var lastCallInfo: CallInfo? = null
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED
        ) {


            val cursor = context.contentResolver.query(
                CallLog.Calls.CONTENT_URI.buildUpon()
                    .appendQueryParameter(CallLog.Calls.LIMIT_PARAM_KEY, "1")
                    .build(),
                null, null, null, CallLog.Calls.DATE + " DESC"
            );

            cursor?.use {
                if (it.moveToFirst()) {
                    val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)
                    val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
                    val timestampIndex = it.getColumnIndex(CallLog.Calls.DATE)
                    val callTypeIndex = it.getColumnIndex(CallLog.Calls.TYPE)


                    // Check if columns exist
                    if (durationIndex != -1 && numberIndex != -1 && timestampIndex != -1 && callTypeIndex != -1) {
                        // Retrieve call details
                        val callDuration = it.getString(durationIndex)
                        val callNumber = it.getString(numberIndex)
                        val callTimestamp = it.getLong(timestampIndex)
                        val callType = it.getInt(callTypeIndex)

                         lastCallInfo =  addCallInfo(context,callNumber, callTimestamp, callDuration, callType)
                        Log.d(TAG, "CALL LOG DATA FETCHED$lastCallInfo")
                    } else {
                        Log.e(TAG, "One or more required columns not found in call log")
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_CALL_LOG),
                PERMISSION_REQUEST_READ_PHONE_STATE
            )
        }

        return lastCallInfo
    }

    private fun addCallInfo(
        context: Context,
        phoneNumber: String,
        timestamp: Long,
        duration: String,
        type: Int
    ): CallInfo {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date(timestamp))
        val callType = CallType.fromInt(type).name
        val recordingUri = CallRecordingManager().getLastCallRecording(contentResolver = context.contentResolver)
        return CallInfo(
            duration = duration,
            date = date,
            type = callType,
            number = phoneNumber,
            recordingUri = recordingUri ?: ""
        )


    }

    private const val PERMISSION_REQUEST_READ_PHONE_STATE = 1001

}