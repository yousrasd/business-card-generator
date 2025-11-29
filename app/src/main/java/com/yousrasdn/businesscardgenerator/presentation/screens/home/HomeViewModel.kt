package com.yousrasdn.businesscardgenerator.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.domain.usecase.GetMyBusinessCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMyCardUseCase: GetMyBusinessCardUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()
    
    init {
        loadCard()
    }
    
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.ShareCard -> {
                // TODO: Share card
            }
            is HomeScreenEvent.ShowQRCode -> {
                _uiState.value = _uiState.value.copy(
                    qrCodeVisible = event.isVisible
                )
            }
            HomeScreenEvent.RefreshCard -> {
                loadCard()
            }
        }
    }
    
    private fun loadCard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getMyCardUseCase()
                .catch { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
                .collect { card ->
                    _uiState.value = _uiState.value.copy(
                        card = card,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
}
