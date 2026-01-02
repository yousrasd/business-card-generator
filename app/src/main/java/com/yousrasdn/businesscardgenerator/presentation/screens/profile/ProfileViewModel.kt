package com.yousrasdn.businesscardgenerator.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.domain.usecase.DeleteBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.GetMyBusinessCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyCardUseCase: GetMyBusinessCardUseCase,
    private val deleteBusinessCardUseCase: DeleteBusinessCardUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileViewState())
    val uiState = _uiState.asStateFlow()
    
    init {
        loadCard()
    }
    
    fun onEvent(event: ProfileViewEvent) {
        when (event) {
            is ProfileViewEvent.DeleteCard -> {
                deleteCard(event.onDeleted)
            }
        }
    }

    private fun deleteCard(onDeleted: () -> Unit) {
        val card = uiState.value.card ?: return
        viewModelScope.launch {
            try {
                deleteBusinessCardUseCase(card)
                onDeleted()
            } catch (e: Exception) {
                // Handle error
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
