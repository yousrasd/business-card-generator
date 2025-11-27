package com.yousrasdn.businesscardgenerator.di

import android.content.Context
import com.yousrasdn.businesscardgenerator.data.repository.ImageRepository
import com.yousrasdn.businesscardgenerator.data.repository.ImageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context = context

    @Provides
    @Singleton
    fun provideImageRepository(
        context: Context
    ): ImageRepository = ImageRepositoryImpl(context)

}
