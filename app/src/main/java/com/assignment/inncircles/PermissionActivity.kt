package com.assignment.inncircles

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionActivity : ComponentActivity() {

    private val conferencePermissions = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        // For Android version 32 or less, only request external storage permission
        arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    } else {
        // For other Android versions, request all permissions
        arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_MEDIA_AUDIO,
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val isNotGrantedAll = permissions.any { !it.value }
            if (isNotGrantedAll) {
                Toast.makeText(this, "Provide necessary permissions to proceed", Toast.LENGTH_SHORT)
                    .show()
            } else {
                launchMainActivity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isAllPermissionsGranted = conferencePermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!isAllPermissionsGranted) {
            requestPermissionLauncher.launch(conferencePermissions)
        } else {
            launchMainActivity()
        }
    }

    private fun launchMainActivity() {
        setContent {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
