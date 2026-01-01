package com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.core.ui.components.CameraPermissionDialog
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormEvent
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormState
import com.yousrasdn.businesscardgenerator.ui.theme.Spacing
import java.io.File


@Composable
fun CreateProfilePhotoScreen(
    uiState: BusinessCardFormState,
    onEvent: (BusinessCardFormEvent) -> Unit
) {
    val context = LocalContext.current
    
    val photoUri = remember {
        val file = File(context.cacheDir, "temp_photo_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    var showCameraRationale by remember { mutableStateOf(false) }
    var showCameraRequestDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            onEvent(BusinessCardFormEvent.UpdatePhoto(it.toString()))
        }
    }
    
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            onEvent(BusinessCardFormEvent.UpdatePhoto(photoUri.toString()))
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(photoUri)
        } else {
            showCameraRationale =  ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.CAMERA
            )
            showCameraRequestDialog = true
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
    ) {
        StepTitle(
            title = stringResource(R.string.step3_title),
            subtitle = stringResource(R.string.step3_subtitle)
        )

        Spacer(modifier = Modifier.height(Spacing.large))

        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
                if (uiState.profilePhotoUri == null) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(R.string.photo_no_photo),
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    AsyncImage(
                        model = uiState.profilePhotoUri,
                        contentDescription = stringResource(R.string.cd_profile_photo),
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        Spacer(modifier = Modifier.height(Spacing.medium))

        Text(
            text = if (uiState.profilePhotoUri == null)
                stringResource(R.string.photo_no_photo)
            else
                stringResource(R.string.photo_selected),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(Spacing.large))

        OutlinedButton(
            onClick = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.photo_choose_gallery))
        }

        OutlinedButton(
            onClick = { 
                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.photo_take_photo))
        }

        if (uiState.profilePhotoUri != null) {
            TextButton(
                onClick = {
                    onEvent(BusinessCardFormEvent.DeletePhoto(uiState.profilePhotoUri))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.photo_remove))
            }
        }

        if (showCameraRequestDialog) {
            CameraPermissionDialog(
                shouldShowRationale = showCameraRationale,
                onDismiss = { showCameraRequestDialog = false },
                onRequestPermission = {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )
        }
    }
}