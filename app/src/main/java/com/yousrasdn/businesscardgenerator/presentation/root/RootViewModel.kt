package com.yousrasdn.businesscardgenerator.presentation.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.domain.usecase.GetMyBusinessCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val getMyCardUseCase: GetMyBusinessCardUseCase
) : ViewModel() {
    
    private val _destination = MutableStateFlow<RootDestination>(RootDestination.Loading)
    val destination = _destination.asStateFlow()
    
    init {
        checkInitialDestination()
    }
    
    private fun checkInitialDestination() {
        viewModelScope.launch {
            getMyCardUseCase().collect { card ->
                _destination.value = if (card != null) {
                    RootDestination.Home
                } else {
                    RootDestination.Onboarding
                }
            }
        }
    }
}

sealed interface RootDestination {
    object Loading : RootDestination
    object Onboarding : RootDestination
    object Home : RootDestination
}
