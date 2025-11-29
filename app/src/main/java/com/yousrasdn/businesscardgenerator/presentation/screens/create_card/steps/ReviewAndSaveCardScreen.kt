package com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormEvent
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormState
import com.yousrasdn.businesscardgenerator.ui.theme.Spacing

@Composable
fun ReviewAndSaveCardScreen(
    uiState: BusinessCardFormState,
    onEvent: (BusinessCardFormEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
    ) {
        StepTitle(
            title = stringResource(R.string.step4_title),
            subtitle = stringResource(R.string.step4_subtitle)
        )

        Spacer(modifier = Modifier.height(Spacing.medium))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.profilePhotoUri != null) {
                        AsyncImage(
                            model = uiState.profilePhotoUri,
                            contentDescription = stringResource(R.string.cd_profile_photo),
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.photo_no_photo),
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.small))

                Text(
                    text = "${uiState.firstName} ${uiState.lastName}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = uiState.jobTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = uiState.company,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(Spacing.small))

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = Spacing.small),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                ) {
                    ContactInfoRow(
                        iconRes = R.drawable.ic_email,
                        label = stringResource(R.string.field_email),
                        value = uiState.email
                    )

                    if (uiState.phone.isNotBlank()) {
                        ContactInfoRow(
                            iconRes = R.drawable.ic_phone,
                            label = stringResource(R.string.field_phone),
                            value = uiState.phone
                        )
                    }

                    if (uiState.website.isNotBlank()) {
                        ContactInfoRow(
                            iconRes = R.drawable.ic_web,
                            label = stringResource(R.string.field_website),
                            value = uiState.website
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactInfoRow(
    iconRes: Int,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.size(Spacing.medium))
        
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}