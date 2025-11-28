package com.yousrasdn.businesscardgenerator.debug

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Shared repository for dev tools state
 * This allows communication between DevTools and other ViewModels
 */
@Singleton
class DevToolsRepository @Inject constructor() {
    
    private val _prefillData = MutableStateFlow<PrefillData?>(null)
    val prefillData: StateFlow<PrefillData?> = _prefillData.asStateFlow()
    
    fun setPrefillData(data: PrefillData?) {
        _prefillData.value = data
    }
    
    fun clearPrefillData() {
        _prefillData.value = null
    }
}
