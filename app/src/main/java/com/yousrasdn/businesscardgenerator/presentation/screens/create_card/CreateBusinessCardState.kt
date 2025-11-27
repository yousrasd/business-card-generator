package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

data class CreateBusinessCardState(
    val currentStep: ProfileCreationStep = ProfileCreationStep.BasicInfo,
    val isNextButtonDisabled: Boolean = true,
    
    val firstName: String = "",
    val lastName: String = "",
    val jobTitle: String = "",
    val company: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = "",
    val profilePhotoUri: String? = null,
    
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val jobTitleError: String? = null,
    val companyError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val websiteError: String? = null,

    val isLoading: Boolean = false
)


