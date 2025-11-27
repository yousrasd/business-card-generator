package com.yousrasdn.businesscardgenerator.di

import android.content.Context
import androidx.room.Room
import com.yousrasdn.businesscardgenerator.data.local.dao.BusinessCardDao
import com.yousrasdn.businesscardgenerator.data.local.database.AppDatabase
import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepository
import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "business_card_database"
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideBusinessCardDao(database: AppDatabase): BusinessCardDao {
        return database.businessCardDao()
    }
    
    @Provides
    @Singleton
    fun provideBusinessCardRepository(
        dao: BusinessCardDao
    ): BusinessCardRepository {
        return BusinessCardRepositoryImpl(dao)
    }
}
