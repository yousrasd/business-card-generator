package com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yousrasdn.businesscardgenerator.R
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

        TextField(
            value = uiState.firstName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {  newText ->
                onEvent(CreateBusinessCardEvent.UpdateFirstName(newText))
            },
            label = { Text(stringResource(R.string.field_first_name)) },
            singleLine = true
        )

        TextField(
            value = uiState.lastName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {  newText ->
                onEvent(CreateBusinessCardEvent.UpdateLastName(newText))
            },
            label = { Text(stringResource(R.string.field_last_name)) },
            singleLine = true
        )

        TextField(
            value = uiState.jobTitle,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {  newText ->
                onEvent(CreateBusinessCardEvent.UpdateJobTitle(newText))
            },
            label = { Text(stringResource(R.string.field_job_title)) },
            singleLine = true
        )

        TextField(
            value = uiState.company,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {  newText ->
                onEvent(CreateBusinessCardEvent.UpdateCompany(newText))
            },
            label = { Text(stringResource(R.string.field_company)) },
            singleLine = true
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