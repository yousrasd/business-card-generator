package com.yousrasdn.businesscardgenerator.presentation.screens.home

sealed interface HomeScreenEvent {
    object ViewFullCard : HomeScreenEvent
    object ShareCard : HomeScreenEvent
    object ShowQRCode : HomeScreenEvent
    object RefreshCard : HomeScreenEvent
}
