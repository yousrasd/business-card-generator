package com.yousrasdn.businesscardgenerator.core.ui.components

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.yousrasdn.businesscardgenerator.R

@Composable
fun CameraPermissionDialog(
    shouldShowRationale: Boolean,
    onDismiss: () -> Unit,
    onRequestPermission: () -> Unit
) {
    val context = LocalContext.current
    
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(stringResource(R.string.camera_permission_title))
        },
        text = {
            Text(
                if (shouldShowRationale) {
                    stringResource(R.string.camera_permission_rationale)
                } else {
                    stringResource(R.string.camera_permission_settings_message)
                }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    if (shouldShowRationale) {
                        onRequestPermission()
                    } else {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        context.startActivity(intent)
                    }
                }
            ) {
                Text(
                    if (shouldShowRationale) {
                        stringResource(R.string.grant_permission)
                    } else {
                        stringResource(R.string.open_settings)
                    }
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.btn_cancel))
            }
        }
    )
}
