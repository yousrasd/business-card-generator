package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.BuildConfig
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.debug.DevToolsRepository
import com.yousrasdn.businesscardgenerator.debug.PrefillData
import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.GetMyBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.ProfilePictureProcessingUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.SaveBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.validator.StepValidatorFactory
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TOTAL_CARD_CREATION_STEPS = 4

@HiltViewModel
class BusinessCardFormViewModel @Inject constructor(
    private val application: Application,
    private val validateFields: ValidateCardProfileFieldsUseCase,
    private val stepValidatorFactory: StepValidatorFactory,
    private val profilePictureProcessingUseCase: ProfilePictureProcessingUseCase,
    private val saveBusinessCardUseCase: SaveBusinessCardUseCase,
    private val devToolsRepository: DevToolsRepository,
    private val getMyCardUseCase: GetMyBusinessCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusinessCardFormState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<BusinessCardFormSideEffect>(
        extraBufferCapacity = 1
    )
    val sideEffect = _sideEffect.asSharedFlow()
    
    init {
        if (BuildConfig.DEBUG) {
            viewModelScope.launch {
                devToolsRepository.prefillData.collect { prefillData ->
                    prefillData?.let { applyPrefillData(it) }
                }
            }
        }
    }
    
    fun loadCardForEdit() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                currentStep = ProfileCreationStep.BasicInfo
                )
            getMyCardUseCase().collect { card ->
                card?.let {
                    _uiState.value = _uiState.value.copy(
                        cardId = it.id,
                        isEditMode = true,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        jobTitle = it.jobTitle,
                        company = it.company,
                        email = it.email,
                        phone = it.phone,
                        website = it.website,
                        profilePhotoUri = it.photoPath
                    )
                    validateCurrentStep()
                }
            }
        }
    }
    
    private fun applyPrefillData(data: PrefillData) {
        _uiState.value = _uiState.value.copy(
            firstName = data.firstName,
            lastName = data.lastName,
            jobTitle = data.jobTitle,
            company = data.company,
            email = data.email,
            phone = data.phone,
            website = data.website,
        )
    }

    fun onEvent(event: BusinessCardFormEvent) {
        when(event) {
            is BusinessCardFormEvent.UpdateFirstName -> handleFirstNameUpdate(event.value)
            is BusinessCardFormEvent.UpdateLastName -> handleLastNameUpdate(event.value)
            is BusinessCardFormEvent.UpdateJobTitle -> handleJobTitleUpdate(event.value)
            is BusinessCardFormEvent.UpdateCompany -> handleCompanyUpdate(event.value)
            is BusinessCardFormEvent.UpdateEmail -> handleEmailUpdate(event.value)
            is BusinessCardFormEvent.UpdatePhone -> handlePhoneUpdate(event.value)
            is BusinessCardFormEvent.UpdateWebsite -> handleWebsiteUpdate(event.value)
            is BusinessCardFormEvent.NextStep -> handleNextStep()
            is BusinessCardFormEvent.PreviousStep -> handlePreviousStep()
            is BusinessCardFormEvent.UpdatePhoto -> handlePhotoUpdate(event.value)
            is BusinessCardFormEvent.DeletePhoto -> handlePhotoDelete(event.value)
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
            isLoading = true,
            loadingMessage = application.getString(R.string.loading_processing_photo)
        )

        val uriPath = profilePictureProcessingUseCase.saveImage(value)

        _uiState.value = _uiState.value.copy(
            profilePhotoUri = uriPath,
            isLoading = false,
            loadingMessage = null
        )

        validateCurrentStep()
    }

    private fun handlePhotoDelete(value: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                loadingMessage = application.getString(R.string.loading_deleting_photo)
            )

            val isSuccessful = profilePictureProcessingUseCase.deleteImage(value)

            if(!isSuccessful) {
                _sideEffect.tryEmit(
                    BusinessCardFormSideEffect.ShowError(
                        application.getString(R.string.error_delete_photo)
                    )
                )
            }

            _uiState.value = _uiState.value.copy(
                profilePhotoUri = if(isSuccessful) null else _uiState.value.profilePhotoUri,
                isLoading = false,
                loadingMessage = null
            )

        }
    }

    private fun handleNextStep() {
        val nextStep = _uiState.value.currentStep.getNextStep()
        if (nextStep != null) {
            _uiState.value = _uiState.value.copy(currentStep = nextStep)
            validateCurrentStep()
        } else { // Review step (last step)
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                loadingMessage = application.getString(R.string.loading_saving_card)
            )
            val businessCard = _uiState.value.mapToBusinessCard()

            viewModelScope.launch {
                val result = saveBusinessCardUseCase(businessCard)
                
                result.onSuccess { cardId ->
                    _sideEffect.tryEmit(
                        if (_uiState.value.isEditMode) {
                            BusinessCardFormSideEffect.CardUpdateSuccess
                        } else {
                            BusinessCardFormSideEffect.CardCreationSuccess
                        }
                    )
                }.onFailure { error ->
                    _sideEffect.tryEmit(
                        BusinessCardFormSideEffect.ShowError(
                            application.getString(R.string.error_save_card)
                        )
                    )
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loadingMessage = null
                )
            }
        }
    }

    private fun handlePreviousStep() {
        val previousStep = _uiState.value.currentStep.getPreviousStep()
        if (previousStep != null) {
            _uiState.value = _uiState.value.copy(currentStep = previousStep)
            validateCurrentStep()
        } else {
            _sideEffect.tryEmit(BusinessCardFormSideEffect.NavigateBack)
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
