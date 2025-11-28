package com.yousrasdn.businesscardgenerator.presentation.screens.profile

sealed interface ProfileViewEvent {
    object ShareCard : ProfileViewEvent
    object ShowQRCode : ProfileViewEvent
    object EditCard : ProfileViewEvent
    object DeleteCard : ProfileViewEvent
    object Back : ProfileViewEvent
}
