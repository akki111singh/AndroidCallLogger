package com.assignment.inncircles

import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.inncircles.helper.CallReceiver
import com.assignment.inncircles.ui.callLifeCycleDetection.CallLifecycleDetection
import com.assignment.inncircles.ui.callLifeCycleDetection.viewmodel.CallViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var callReceiver: CallReceiver

    companion object {
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: " + "Receiver registered" )
        setContent {
            val viewModel: CallViewModel = viewModel()
            callReceiver = CallReceiver(viewModel)
            val intentFilter = IntentFilter()
            intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            registerReceiver(callReceiver, intentFilter)
            CallLifecycleDetection(viewModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callReceiver)
    }
}




