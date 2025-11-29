package com.yousrasdn.businesscardgenerator.domain.validator

import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormState
import javax.inject.Inject

class ContactInfoStepValidator @Inject constructor(
    private val validateFields: ValidateCardProfileFieldsUseCase
) : StepValidator {
    
    override fun validate(state: BusinessCardFormState): Boolean {
        val emailValid = validateFields.validateEmail(state.email) is CardProfileFieldValidationResult.Success
        
        val phoneValid = if (state.phone.isNotBlank()) {
            validateFields.validatePhone(state.phone) is CardProfileFieldValidationResult.Success
        } else {
            true
        }
        
        val websiteValid = if (state.website.isNotBlank()) {
            validateFields.validateWebsite(state.website) is CardProfileFieldValidationResult.Success
        } else {
            true
        }
            
        return emailValid && phoneValid && websiteValid
    }
}
