package com.yousrasdn.businesscardgenerator.presentation.screens.scan_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yousrasdn.businesscardgenerator.core.util.VCardParser
import com.yousrasdn.businesscardgenerator.domain.usecase.SaveBusinessCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRScannerViewModel @Inject constructor(
    private val saveBusinessCardUseCase: SaveBusinessCardUseCase
) : ViewModel() {
    
    fun handleScannedQRCode(
        qrData: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val businessCard = VCardParser.parse(qrData)
            
            if (businessCard == null) {
                onError("Invalid QR code format")
                return@launch
            }
            
            val result = saveBusinessCardUseCase(businessCard)
            
            result.fold(
                onSuccess = {
                    onSuccess()
                },
                onFailure = { error ->
                    onError(error.message ?: "Failed to save card")
                }
            )
        }
    }
}
