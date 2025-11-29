package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard

data class BusinessCardFormState(
    val currentStep: ProfileCreationStep = ProfileCreationStep.BasicInfo,
    val isNextButtonDisabled: Boolean = true,
    
    val cardId: Long? = null,
    val isEditMode: Boolean = false,
    
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

    val isLoading: Boolean = false,
    val loadingMessage: String? = null
)

fun BusinessCardFormState.mapToBusinessCard(): BusinessCard = BusinessCard(
    id = cardId ?: 0,
    firstName = firstName,
    lastName = lastName,
    jobTitle = jobTitle,
    company = company,
    email = email,
    phone = phone,
    website = website,
    photoPath = profilePhotoUri
)

fun BusinessCard.toBusinessCardFormState(): BusinessCardFormState = BusinessCardFormState(
    cardId = id,
    isEditMode = true,
    firstName = firstName,
    lastName = lastName,
    jobTitle = jobTitle,
    company = company,
    email = email,
    phone = phone,
    website = website,
    profilePhotoUri = photoPath
)
