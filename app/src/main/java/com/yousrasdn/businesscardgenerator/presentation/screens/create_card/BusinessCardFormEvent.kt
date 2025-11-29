package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

sealed interface BusinessCardFormEvent {
    data class UpdateFirstName(val value: String) : BusinessCardFormEvent
    data class UpdateLastName(val value: String) : BusinessCardFormEvent
    data class UpdateJobTitle(val value: String) : BusinessCardFormEvent
    data class UpdateCompany(val value: String) : BusinessCardFormEvent
    data class UpdateEmail(val value: String) : BusinessCardFormEvent
    data class UpdatePhone(val value: String) : BusinessCardFormEvent
    data class UpdateWebsite(val value: String) : BusinessCardFormEvent
    data class UpdatePhoto(val value: String) : BusinessCardFormEvent
    data class DeletePhoto(val value: String) : BusinessCardFormEvent
    data object NextStep : BusinessCardFormEvent
    data object PreviousStep : BusinessCardFormEvent
}

