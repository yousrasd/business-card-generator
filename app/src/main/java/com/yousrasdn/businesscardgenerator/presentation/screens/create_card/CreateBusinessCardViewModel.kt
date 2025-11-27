package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.ProfilePictureProcessingUseCase
import com.yousrasdn.businesscardgenerator.domain.validator.StepValidatorFactory
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TOTAL_CARD_CREATION_STEPS = 4

@HiltViewModel
class CreateBusinessCardViewModel @Inject constructor(
    private val application: Application,
    private val validateFields: ValidateCardProfileFieldsUseCase,
    private val stepValidatorFactory: StepValidatorFactory,
    private val profilePictureProcessingUseCase: ProfilePictureProcessingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateBusinessCardState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CreateBusinessCardSideEffect>(
        extraBufferCapacity = 1
    )
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: CreateBusinessCardEvent) {
        when(event) {
            is CreateBusinessCardEvent.UpdateFirstName -> handleFirstNameUpdate(event.value)
            is CreateBusinessCardEvent.UpdateLastName -> handleLastNameUpdate(event.value)
            is CreateBusinessCardEvent.UpdateJobTitle -> handleJobTitleUpdate(event.value)
            is CreateBusinessCardEvent.UpdateCompany -> handleCompanyUpdate(event.value)
            is CreateBusinessCardEvent.UpdateEmail -> handleEmailUpdate(event.value)
            is CreateBusinessCardEvent.UpdatePhone -> handlePhoneUpdate(event.value)
            is CreateBusinessCardEvent.UpdateWebsite -> handleWebsiteUpdate(event.value)
            is CreateBusinessCardEvent.NextStep -> handleNextStep()
            is CreateBusinessCardEvent.PreviousStep -> handlePreviousStep()
            is CreateBusinessCardEvent.UpdatePhoto -> handlePhotoUpdate(event.value)
            is CreateBusinessCardEvent.DeletePhoto -> handlePhotoDelete(event.value)
        }
    }

    private fun handleFirstNameUpdate(value: String) {
        val validation = validateFields.validateFirstName(value)
        _uiState.value = _uiState.value.copy(
            firstName = value,
            firstNameError = validation.errorOrNull()
        )
        validateCurrentStep()
    }

    private fun handleLastNameUpdate(value: String) {
        val validation = validateFields.validateLastName(value)
        _uiState.value = _uiState.value.copy(
            lastName = value,
            lastNameError = validation.errorOrNull()
        )
        validateCurrentStep()
    }

    private fun handleJobTitleUpdate(value: String) {
        val validation = validateFields.validateJobTitle(value)
        _uiState.value = _uiState.value.copy(
            jobTitle = value,
            jobTitleError = validation.errorOrNull()
        )
        validateCurrentStep()
    }

    private fun handleCompanyUpdate(value: String) {
        val validation = validateFields.validateCompany(value)
        _uiState.value = _uiState.value.copy(
            company = value,
            companyError = validation.errorOrNull()
        )
        validateCurrentStep()
    }

    private fun handleEmailUpdate(value: String) {
        val validation = validateFields.validateEmail(value)
        _uiState.value = _uiState.value.copy(
            email = value,
            emailError = validation.errorOrNull()
        )
        validateCurrentStep()
    }

    private fun handlePhoneUpdate(value: String) {
        val validation = validateFields.validatePhone(value)
        _uiState.value = _uiState.value.copy(
            phone = value,
            phoneError = validation.errorOrNull()
        )
        validateCurrentStep()
    }

    private fun handleWebsiteUpdate(value: String) {
        val validation = validateFields.validateWebsite(value)
        _uiState.value = _uiState.value.copy(
            website = value,
            websiteError = validation.errorOrNull()
        )
        validateCurrentStep()
    }

    private fun handlePhotoUpdate(value: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )

        val uriPath = profilePictureProcessingUseCase.invoke(value)

        _uiState.value = _uiState.value.copy(
            profilePhotoUri = uriPath
        )

        _uiState.value = _uiState.value.copy(
            isLoading = false
        )

        validateCurrentStep()
    }

    private fun handlePhotoDelete(value: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val isSuccessful = profilePictureProcessingUseCase.deleteImage(value)

            if(isSuccessful) {
                _uiState.value = _uiState.value.copy(
                    profilePhotoUri = null,
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _sideEffect.tryEmit(
                    CreateBusinessCardSideEffect.ShowError(
                        application.getString(R.string.error_delete_photo)
                    )
                )
            }

        }
    }

    private fun handleNextStep() {
        val nextStep = _uiState.value.currentStep.getNextStep()
        if (nextStep != null) {
            _uiState.value = _uiState.value.copy(currentStep = nextStep)
            validateCurrentStep()
        } else {

        }
    }

    private fun handlePreviousStep() {
        val previousStep = _uiState.value.currentStep.getPreviousStep()
        if (previousStep != null) {
            _uiState.value = _uiState.value.copy(currentStep = previousStep)
            validateCurrentStep()
        } else {
            _sideEffect.tryEmit(CreateBusinessCardSideEffect.NavigateBack)
        }
    }

    private fun validateCurrentStep() {
        val validator = stepValidatorFactory.getValidator(_uiState.value.currentStep)
        val isValid = validator.validate(_uiState.value)
        _uiState.value = _uiState.value.copy(isNextButtonDisabled = !isValid)
    }

    private fun CardProfileFieldValidationResult.errorOrNull(): String? =
        (this as? CardProfileFieldValidationResult.Error)?.let {
            application.getString(it.errorMessageResId)
        }
}
