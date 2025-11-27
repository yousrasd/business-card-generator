package com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.core.ui.components.ValidatedTextField
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.CreateBusinessCardEvent
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.CreateBusinessCardState
import com.yousrasdn.businesscardgenerator.ui.theme.Spacing

@Composable
fun CreateContactInfoScreen(
    uiState: CreateBusinessCardState,
    onEvent: (CreateBusinessCardEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        StepTitle(
            title = stringResource(R.string.step2_title),
            subtitle = stringResource(R.string.step2_subtitle)
        )

        ValidatedTextField(
            value = uiState.email,
            onValueChange = { newText ->
                onEvent(CreateBusinessCardEvent.UpdateEmail(newText))
            },
            label = stringResource(R.string.field_email),
            errorMessage = uiState.emailError,
            placeholder = stringResource(R.string.field_email_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )

        ValidatedTextField(
            value = uiState.phone,
            onValueChange = { newText ->
                onEvent(CreateBusinessCardEvent.UpdatePhone(newText))
            },
            label = stringResource(R.string.field_phone),
            errorMessage = uiState.phoneError,
            placeholder = stringResource(R.string.field_phone_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
        )

        ValidatedTextField(
            value = uiState.website,
            onValueChange = { newText ->
                onEvent(CreateBusinessCardEvent.UpdateWebsite(newText))
            },
            label = stringResource(R.string.field_website),
            errorMessage = uiState.websiteError,
            placeholder = stringResource(R.string.field_website_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done
            ),
        )
    }
}