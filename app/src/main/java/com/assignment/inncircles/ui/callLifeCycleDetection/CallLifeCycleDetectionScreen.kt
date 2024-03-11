package com.assignment.inncircles.ui.callLifeCycleDetection

import android.Manifest
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignment.inncircles.helper.CallLogHelper
import com.assignment.inncircles.helper.PermissionHelper
import com.assignment.inncircles.model.CallInfo
import com.assignment.inncircles.ui.callLifeCycleDetection.viewmodel.CallViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CallLifecycleDetection(callViewModel: CallViewModel) {
    val context = LocalContext.current
    var permissionsGranted by remember { mutableStateOf(false) }
    val callState by callViewModel.callState.collectAsState()
    val callInfo by callViewModel.lastCallLogs.collectAsState()

    PermissionHelper(
        permissions = phoneStatePermissions,
        onGranted = {
            permissionsGranted = true
        },
        onDenied = {
            permissionsGranted = false
        }
    )

    if (permissionsGranted) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                text = "Call State: $callState",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W500),
                modifier = Modifier.padding(8.dp)
            )

            if(callInfo != null) {
                Text(
                    "Call Log", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(8.dp)
                )
            }
            callInfo?.let { info ->
                CallLogInfoCard(info = info)
            }

        }
    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                text = "Phone State permissions are not granted.",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(8.dp)
            )
            // Button to request permissions could be added here
        }
    }
}

@Composable
fun CallLogInfoCard(info: CallInfo) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Phone Number: ${info.number}", style = TextStyle(fontSize = 16.sp))
            Text(text = "Date: ${info.date}", style = TextStyle(fontSize = 16.sp))
            Text(text = "Duration: ${info.duration}", style = TextStyle(fontSize = 16.sp))
            Text(text = "Type: ${info.type}", style = TextStyle(fontSize = 16.sp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val phoneStatePermissions = listOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, READ_MEDIA_AUDIO)