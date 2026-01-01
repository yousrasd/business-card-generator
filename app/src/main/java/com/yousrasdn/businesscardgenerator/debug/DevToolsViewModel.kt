package com.yousrasdn.businesscardgenerator.debug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevToolsViewModel @Inject constructor(
    private val devToolsManager: DevToolsManager,
    private val devToolsRepository: DevToolsRepository
) : ViewModel() {
    
    val prefillData = devToolsRepository.prefillData
    
    fun clearDatabase() {
        viewModelScope.launch {
            devToolsManager.clearDatabase()
        }
    }
    
    fun setPrefillData(variant: Int) {
        val data = when (variant) {
            1 -> devToolsManager.getPrefillData()
            2 -> devToolsManager.getPrefillData2()
            3 -> devToolsManager.getPrefillData3()
            4 -> devToolsManager.getPrefillData4()
            5 -> devToolsManager.getPrefillData5()
            else -> null
        }
        devToolsRepository.setPrefillData(data)
    }
    
    fun clearPrefillData() {
        devToolsRepository.clearPrefillData()
    }
}
