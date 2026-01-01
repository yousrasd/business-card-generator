package com.yousrasdn.businesscardgenerator.core.ui.components

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

/**
 * Handles the complete camera permission flow:
 * 1. Request permission
 * 2. Show rationale if needed
 * 3. Direct to settings if permanently denied
 * 
 * @param onPermissionGranted Called when permission is granted
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionHandler(
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: @Composable () -> Unit = {  }
) {
    var showCameraRequestDialog by remember { mutableStateOf(false) }

    val cameraPermission = rememberPermissionState(
        Manifest.permission.CAMERA,
        onPermissionResult = { granted ->
                showCameraRequestDialog = !granted
        }
    )

    LaunchedEffect(Unit) {
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }
    }
    
    when {
        cameraPermission.status.isGranted -> {
            onPermissionGranted()
        }
        showCameraRequestDialog -> {
            CameraPermissionDialog(
                shouldShowRationale = cameraPermission.status.shouldShowRationale,
                onDismiss = { showCameraRequestDialog = false },
                onRequestPermission = {
                    showCameraRequestDialog = false
                    cameraPermission.launchPermissionRequest()
                }
            )
        }
        else -> {
            onPermissionDenied()
        }
    }
}
