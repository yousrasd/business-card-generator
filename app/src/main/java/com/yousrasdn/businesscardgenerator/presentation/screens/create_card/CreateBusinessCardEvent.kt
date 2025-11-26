package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

sealed interface CreateBusinessCardEvent{

    data class UpdateFirstName(val value: String) : CreateBusinessCardEvent
    data class UpdateLastName(val value: String) : CreateBusinessCardEvent
    data class UpdateJobTitle(val value: String) : CreateBusinessCardEvent
    data class UpdateCompany(val value: String) : CreateBusinessCardEvent

    data class UpdateEmail(val value: String) : CreateBusinessCardEvent
    data class UpdatePhone(val value: String) : CreateBusinessCardEvent
    data class UpdateWebsite(val value: String) : CreateBusinessCardEvent



    data object NextStep : CreateBusinessCardEvent
    data object PreviousStep : CreateBusinessCardEvent


}

