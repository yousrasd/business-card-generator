package com.yousrasdn.businesscardgenerator.presentation.screens.profile

sealed interface ProfileViewEvent {
    data class DeleteCard(val onDeleted: () -> Unit) : ProfileViewEvent
}
