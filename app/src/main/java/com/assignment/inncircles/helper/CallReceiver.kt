package com.assignment.inncircles.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.assignment.inncircles.ui.callLifeCycleDetection.viewmodel.CallViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CallReceiver(private val callViewModel: CallViewModel) : BroadcastReceiver() {
    private var coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    companion object{
        private var previousState: String? = null
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) ?: return

            when (state) {

                TelephonyManager.EXTRA_STATE_IDLE -> {
                    coroutineScope.launch {
                        delay(2000)
                        callViewModel.onCallStateChanged("Call Ended")
                        val lastCallLogs = CallLogHelper.retrieveCallDetails(context)
                        lastCallLogs
                            ?.let {
                                callViewModel.updateLastCallLogs(it)
                                callViewModel.sendCallLogsToApi(context,it)
                            }

                    }
                }

                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    if(previousState ==  TelephonyManager.EXTRA_STATE_RINGING){
                        callViewModel.onCallStateChanged("Outgoing Call in Progress")
                    } else{
                        callViewModel.onCallStateChanged("Incoming Call in Progress")
                    }

                }

                TelephonyManager.EXTRA_STATE_RINGING -> {
                    callViewModel.onCallStateChanged("RINGING")
                }
            }
            previousState = state

        }
    }
}





