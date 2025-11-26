package com.yousrasdn.businesscardgenerator.domain.validator

import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.CreateBusinessCardState

interface StepValidator {
    fun validate(state: CreateBusinessCardState): Boolean
}
