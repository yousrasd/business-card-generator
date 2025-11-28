package com.yousrasdn.businesscardgenerator.presentation.screens.home

import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard

data class HomeScreenState(
    val card: BusinessCard? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
