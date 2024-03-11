package com.assignment.inncircles.helper

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHelper(
    permissions: List<String>,
    onGranted: () -> Unit,
    onDenied: () -> Unit
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)
    var permissionRequested by remember { mutableStateOf(false) }

    LaunchedEffect(multiplePermissionsState) {
        if (!multiplePermissionsState.allPermissionsGranted && !permissionRequested) {
            multiplePermissionsState.launchMultiplePermissionRequest()
            permissionRequested = true
        }
    }

    if (multiplePermissionsState.allPermissionsGranted) {
        onGranted.invoke()
    } else {
        onDenied.invoke()
    }
}