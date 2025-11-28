package com.yousrasdn.businesscardgenerator.presentation.screens.profile

import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard

data class ProfileViewState(
    val card: BusinessCard? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
