package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.core.ui.components.StepIndicator
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps.CreateBasicInfoScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps.CreateContactInfoScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps.CreateProfilePhotoScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.steps.ReviewAndSaveCardScreen
import com.yousrasdn.businesscardgenerator.ui.theme.Spacing



@Composable
fun AddBusinessCardScreen(backStack: SnapshotStateList<Any>, createBusinessCardViewModel: CreateBusinessCardViewModel = hiltViewModel()) {

    val uiState = createBusinessCardViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        createBusinessCardViewModel.sideEffect.collect { sideEffect ->
            when(sideEffect) {
                is CreateBusinessCardSideEffect.NavigateBack -> {
                    backStack.removeLastOrNull()
                }
                else -> {}
            }
        }
    }


    Scaffold(
        topBar = {
            AddBusinessCardHeader(createBusinessCardViewModel::onEvent)
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.medium),
            ) {

                Row(horizontalArrangement = Arrangement.Center) {
                    StepIndicator(
                        currentStep = uiState.value.currentStep.normalizeToNumber(),
                        totalSteps = TOTAL_CARD_CREATION_STEPS
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.large))

                when(uiState.value.currentStep) {
                    ProfileCreationStep.BasicInfo -> {
                        CreateBasicInfoScreen(uiState.value, createBusinessCardViewModel::onEvent)
                    }
                    ProfileCreationStep.ContactInfo -> {
                        CreateContactInfoScreen(
                            uiState.value, createBusinessCardViewModel::onEvent
                        )
                    }
                    ProfileCreationStep.Photo -> {
                        CreateProfilePhotoScreen(
                            uiState.value, createBusinessCardViewModel::onEvent
                        )
                    }

                    ProfileCreationStep.Review -> {
                        ReviewAndSaveCardScreen(
                            uiState.value, createBusinessCardViewModel::onEvent
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f, fill = true))

                FooterContinueButton(
                    onEvent = createBusinessCardViewModel::onEvent,
                    isDisabled = uiState.value.isNextButtonDisabled
                )

            }
        }

    }

}


@Composable
fun AddBusinessCardHeader(goBack: (CreateBusinessCardEvent) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
        horizontal = Spacing.medium,
        vertical = Spacing.medium
    ).fillMaxWidth()) {
        Button(onClick = {goBack(CreateBusinessCardEvent.PreviousStep)}) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Text(text = stringResource(id=R.string.create_card_title))
    }
}


@Composable
fun FooterContinueButton(
    onEvent: (CreateBusinessCardEvent) -> Unit,
    isDisabled: Boolean = false,
    isLastScreen: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding( Spacing.medium),
    ){
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !isDisabled,
            onClick = {onEvent(CreateBusinessCardEvent.NextStep)}
        )
        {
            Text(
                if(isLastScreen)
                stringResource(R.string.btn_finish) else
            stringResource(R.string.btn_next)
            )
        }
    }
}


@Preview
@Composable
fun AddBusinessCardScreenPreview() {
    AddBusinessCardScreen(
        backStack = remember { mutableStateListOf() },
    )
}