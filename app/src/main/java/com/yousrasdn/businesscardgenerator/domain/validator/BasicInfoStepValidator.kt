package com.yousrasdn.businesscardgenerator.domain.validator

import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormState
import javax.inject.Inject

class BasicInfoStepValidator @Inject constructor(
    private val validateFields: ValidateCardProfileFieldsUseCase
) : StepValidator {
    
    override fun validate(state: BusinessCardFormState): Boolean {
        val firstNameValid = validateFields.validateFirstName(state.firstName) is CardProfileFieldValidationResult.Success
        val lastNameValid = validateFields.validateLastName(state.lastName) is CardProfileFieldValidationResult.Success
        val jobTitleValid = validateFields.validateJobTitle(state.jobTitle) is CardProfileFieldValidationResult.Success
        val companyValid = validateFields.validateCompany(state.company) is CardProfileFieldValidationResult.Success
            
        return firstNameValid && lastNameValid && jobTitleValid && companyValid
    }
}
