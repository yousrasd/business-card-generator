package com.yousrasdn.businesscardgenerator.debug

import android.content.Context
import com.yousrasdn.businesscardgenerator.BuildConfig
import com.yousrasdn.businesscardgenerator.data.local.database.AppDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevToolsManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: AppDatabase
) {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    
    val isDebugBuild: Boolean
        get() = BuildConfig.DEBUG
    
    /**
     * Clear all data from Room database
     * Only works in debug builds
     */
    fun clearDatabase(onComplete: () -> Unit = {}) {
        if (!isDebugBuild) return
        
        scope.launch {
            database.clearAllTables()
            onComplete()
        }
    }
    
    /**
     * Get prefilled test data for forms
     */
    fun getPrefillData(): PrefillData {
        return PrefillData(
            firstName = "John",
            lastName = "Doe",
            jobTitle = "Senior Android Developer",
            company = "Tech Corp",
            email = "john.doe@techcorp.com",
            phone = "+14389789878",
            website = "www.johndoe.com",
            bio = "Passionate Android developer with 5+ years of experience building beautiful and performant mobile applications."
        )
    }
    
    /**
     * Get alternative prefill data
     */
    fun getPrefillData2(): PrefillData {
        return PrefillData(
            firstName = "Jane",
            lastName = "Smith",
            jobTitle = "Product Designer",
            company = "Design Studio",
            email = "jane.smith@designstudio.com",
            phone = "+14389789878",
            website = "www.janesmith.design",
            bio = "Creative product designer focused on user-centered design and delightful experiences."
        )
    }
}

data class PrefillData(
    val firstName: String = "",
    val lastName: String = "",
    val jobTitle: String = "",
    val company: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = "",
    val bio: String = ""
)
