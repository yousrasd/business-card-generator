package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

sealed interface CreateBusinessCardSideEffect {
    data class ShowError(
        val message: String
    ): CreateBusinessCardSideEffect
    data object NavigateBack : CreateBusinessCardSideEffect
    data object CardCreationSuccess : CreateBusinessCardSideEffect
}




