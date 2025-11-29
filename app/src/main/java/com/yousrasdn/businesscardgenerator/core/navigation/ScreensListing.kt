package com.yousrasdn.businesscardgenerator.core.navigation

sealed interface ScreensListing {
    data class CardProfile(val isEditMode: Boolean = false) : ScreensListing
    data object Onboarding : ScreensListing
    data object ScanCard : ScreensListing
    data object Home : ScreensListing
    data object Profile : ScreensListing
    data object DevTool : ScreensListing
}