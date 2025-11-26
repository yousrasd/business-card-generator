package com.yousrasdn.businesscardgenerator.domain.validator

import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.CreateBusinessCardState
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.ProfileCreationStep
import javax.inject.Inject

class StepValidatorFactory @Inject constructor(
    private val basicInfoValidator: BasicInfoStepValidator,
    private val contactInfoValidator: ContactInfoStepValidator
) {
    fun getValidator(step: ProfileCreationStep): StepValidator = when(step) {
        is ProfileCreationStep.BasicInfo -> basicInfoValidator
        is ProfileCreationStep.ContactInfo -> contactInfoValidator
        is ProfileCreationStep.Photo -> AlwaysValidValidator
        is ProfileCreationStep.Review -> AlwaysValidValidator
    }
}

object AlwaysValidValidator : StepValidator {
    override fun validate(state: CreateBusinessCardState) = true
}
