package com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.core.ui.components.ValidatedTextField
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.CreateBusinessCardEvent
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.CreateBusinessCardState
import com.yousrasdn.businesscardgenerator.ui.theme.Spacing

@Composable
fun CreateBasicInfoScreen(
    uiState: CreateBusinessCardState,
    onEvent: (CreateBusinessCardEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        StepTitle(
            title = stringResource(id =R.string.step1_title),
            subtitle = stringResource(id =R.string.step1_subtitle),

        )

        ValidatedTextField(
            value = uiState.firstName,
            onValueChange = { newText ->
                onEvent(CreateBusinessCardEvent.UpdateFirstName(newText))
            },
            label = stringResource(R.string.field_first_name),
            errorMessage = uiState.firstNameError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        ValidatedTextField(
            value = uiState.lastName,
            onValueChange = { newText ->
                onEvent(CreateBusinessCardEvent.UpdateLastName(newText))
            },
            label = stringResource(R.string.field_last_name),
            errorMessage = uiState.lastNameError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        ValidatedTextField(
            value = uiState.jobTitle,
            onValueChange = { newText ->
                onEvent(CreateBusinessCardEvent.UpdateJobTitle(newText))
            },
            label = stringResource(R.string.field_job_title),
            errorMessage = uiState.jobTitleError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        ValidatedTextField(
            value = uiState.company,
            onValueChange = { newText ->
                onEvent(CreateBusinessCardEvent.UpdateCompany(newText))
            },
            label = stringResource(R.string.field_company),
            errorMessage = uiState.companyError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        )




    }
}


@Composable
@Preview
fun CreateBasicInfoScreenPreview() {
    CreateBasicInfoScreen(
        uiState = CreateBusinessCardState(),
        onEvent = {}
    )
}