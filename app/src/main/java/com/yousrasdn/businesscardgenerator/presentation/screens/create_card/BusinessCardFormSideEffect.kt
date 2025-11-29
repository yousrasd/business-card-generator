package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

sealed interface BusinessCardFormSideEffect {
    data class ShowError(val message: String) : BusinessCardFormSideEffect
    data object NavigateBack : BusinessCardFormSideEffect
    data object CardCreationSuccess : BusinessCardFormSideEffect
    data object CardUpdateSuccess : BusinessCardFormSideEffect
}




