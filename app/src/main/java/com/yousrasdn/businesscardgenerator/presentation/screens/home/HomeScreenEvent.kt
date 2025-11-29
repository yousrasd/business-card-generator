package com.yousrasdn.businesscardgenerator.presentation.screens.home

sealed interface HomeScreenEvent {
    object ShareCard : HomeScreenEvent
    data class ShowQRCode(val isVisible: Boolean = false) : HomeScreenEvent
    object RefreshCard : HomeScreenEvent
}
