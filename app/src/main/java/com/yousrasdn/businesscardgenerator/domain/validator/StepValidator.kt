package com.yousrasdn.businesscardgenerator.domain.validator

import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormState

interface StepValidator {
    fun validate(state: BusinessCardFormState): Boolean
}
