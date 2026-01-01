package com.yousrasdn.businesscardgenerator.presentation.screens.scanned_card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import com.yousrasdn.businesscardgenerator.domain.usecase.DeleteBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.GetBusinessCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScannedCardDetailsState(
    val card: BusinessCard? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ScannedCardDetailsViewModel @Inject constructor(
    private val getBusinessCardByIdUseCase: GetBusinessCardByIdUseCase,
    private val deleteBusinessCardUseCase: DeleteBusinessCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannedCardDetailsState())
    val uiState = _uiState.asStateFlow()

    fun loadCard(cardId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val card = getBusinessCardByIdUseCase(cardId)
                if (card != null) {
                    _uiState.value = _uiState.value.copy(card = card, isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Card not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun deleteCard(onSuccess: () -> Unit) {
        val currentCard = _uiState.value.card ?: return
        viewModelScope.launch {
            try {
                deleteBusinessCardUseCase(currentCard)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Failed to delete card: ${e.message}")
            }
        }
    }
}
