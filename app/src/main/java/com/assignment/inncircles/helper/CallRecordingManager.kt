package com.assignment.inncircles.helper

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.assignment.inncircles.model.CallInfo

class CallRecordingManager {
    companion object {
       const val TAG = "CallRecordingManager"
    }

    fun getLastCallRecording(contentResolver: ContentResolver): String? {
        Log.d(TAG, "get Latest Call Recording: ")
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )

        val selection = "${MediaStore.Audio.Media.DATA} like ?"
        val selectionArgs = arrayOf("%CallRecordings%")
        var lastRecordingPath: String? = null

        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, "${MediaStore.Audio.Media.DATE_ADDED} DESC")

        cursor?.use {
            val filePathColumnIndex = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            if (it.moveToFirst()) {
                // Retrieve information about the last call recording
                lastRecordingPath = it.getString(filePathColumnIndex)
                Log.d(TAG, "getLastCallRecording: $lastRecordingPath")
            }
        }

        return lastRecordingPath
    }


    fun deleteCallRecording(context: Context, callInfo: CallInfo) {
        Log.d(TAG, "deleteCallRecording: ")
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Audio.Media.DATA} = ?"
        val selectionArgs = arrayOf(callInfo.recordingUri)

        val rowsDeleted = contentResolver.delete(uri, selection, selectionArgs)
        if (rowsDeleted > 0) {
            Log.d(TAG, "CALL RECORDING DELETED: $callInfo")
        } else {
            Log.d(TAG, "No call recording found to delete.")
        }
    }
}
