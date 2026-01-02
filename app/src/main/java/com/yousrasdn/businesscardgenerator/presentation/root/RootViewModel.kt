package com.yousrasdn.businesscardgenerator.presentation.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.domain.usecase.GetMyBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.GetScannedCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val getMyCardUseCase: GetMyBusinessCardUseCase,
    private val getScannedCardsUseCase: GetScannedCardsUseCase
) : ViewModel() {
    
    private val _destination = MutableStateFlow<RootDestination>(RootDestination.Loading)
    val destination = _destination.asStateFlow()
    
    init {
        checkInitialDestination()
    }
    
    private fun checkInitialDestination() {
        viewModelScope.launch {
            combine(
                getMyCardUseCase(),
                getScannedCardsUseCase()
            ) { myCard, scannedCards ->
                if (myCard != null || scannedCards.isNotEmpty()) {
                    RootDestination.Home
                } else {
                    RootDestination.Onboarding
                }
            }.collect { destination ->
                _destination.value = destination
            }
        }
    }
}

sealed interface RootDestination {
    object Loading : RootDestination
    object Onboarding : RootDestination
    object Home : RootDestination
}
