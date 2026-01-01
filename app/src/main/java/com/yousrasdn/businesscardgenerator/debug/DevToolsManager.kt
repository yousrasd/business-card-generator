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


    fun getPrefillData3(): PrefillData {
        return PrefillData(
            firstName = "Michael",
            lastName = "Chen",
            jobTitle = "CTO",
            company = "Startup Inc",
            email = "michael.chen@startup.io",
            phone = "+143812345678",
            website = "www.startup.io",
            bio = "Tech visionary leading the next generation of software solutions."
        )
    }

    fun getPrefillData4(): PrefillData {
        return PrefillData(
            firstName = "Sarah",
            lastName = "Jones",
            jobTitle = "Marketing Director",
            company = "Global Reach",
            email = "sarah.jones@globalreach.com",
            phone = "+143812345678",
            website = "www.globalreach.com",
            bio = "Strategic marketing leader connecting brands with their audience."
        )
    }

    fun getPrefillData5(): PrefillData {
        return PrefillData(
            firstName = "David",
            lastName = "Wilson",
            jobTitle = "Data Scientist",
            company = "AI Solutions",
            email = "david.wilson@aisolutions.ai",
            phone = "+15554567890",
            website = "www.aisolutions.ai",
            bio = "Extracting insights from data to drive business decisions."
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
