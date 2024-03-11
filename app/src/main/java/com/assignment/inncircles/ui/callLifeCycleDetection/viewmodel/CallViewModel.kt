package com.assignment.inncircles.ui.callLifeCycleDetection.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.inncircles.helper.CallRecordingManager
import com.assignment.inncircles.model.CallInfo
import com.assignment.inncircles.network.CallRepository
import com.assignment.inncircles.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallViewModel @Inject constructor(private val repository: CallRepository) : ViewModel() {

    companion object {
        const val TAG = "CallViewModel"
    }
    private val _callState = MutableStateFlow("NONE")
    val callState = _callState.asStateFlow()

    private val _lastCallLogs = MutableStateFlow<CallInfo?>(null)
    val lastCallLogs = _lastCallLogs.asStateFlow()


    fun updateLastCallLogs(newState: CallInfo){
        _lastCallLogs.value = newState
    }

    fun onCallStateChanged(newState: String) {
        viewModelScope.launch {
             Log.d(TAG, "CALL STATE $newState")
            _callState.emit(newState)
        }
    }

    fun sendCallLogsToApi(context:Context?,callInfo: CallInfo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.sendCallData(callInfo).collect {
                when(it){
                    is Resource.Success -> {
                        Log.d(TAG, "API CALL SUCCESS")
                        if (context != null) {
                            CallRecordingManager().deleteCallRecording(context,callInfo)
                        }
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Error -> {

                    }

                    else -> {}
                }
            }
        }
    }
}