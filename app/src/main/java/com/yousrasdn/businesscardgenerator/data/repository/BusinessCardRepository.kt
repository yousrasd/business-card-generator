package com.yousrasdn.businesscardgenerator.data.repository

import com.yousrasdn.businesscardgenerator.data.local.dao.BusinessCardDao
import com.yousrasdn.businesscardgenerator.data.mapper.toDomain
import com.yousrasdn.businesscardgenerator.data.mapper.toEntity
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface BusinessCardRepository {
    suspend fun saveCard(card: BusinessCard): Result<Long>
    suspend fun updateCard(card: BusinessCard)
    suspend fun deleteCard(card: BusinessCard)
    fun getMyCard(): Flow<BusinessCard?>
    suspend fun getCardById(id: Long): BusinessCard?
    fun getAllCards(): Flow<List<BusinessCard>>

    suspend fun clearAllTables()
}

@Singleton
class BusinessCardRepositoryImpl @Inject constructor(
    private val dao: BusinessCardDao
) : BusinessCardRepository {
    
    override suspend fun saveCard(card: BusinessCard): Result<Long> {
        return try {
            Result.success(dao.insert(card.toEntity()))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateCard(card: BusinessCard) {
        dao.update(card.toEntity())
    }
    
    override suspend fun deleteCard(card: BusinessCard) {
        dao.delete(card.toEntity())
    }
    
    override fun getMyCard(): Flow<BusinessCard?> {
        return dao.getMyCard().map { it?.toDomain() }
    }
    
    override suspend fun getCardById(id: Long): BusinessCard? {
        return dao.getCardById(id)?.toDomain()
    }
    
    override fun getAllCards(): Flow<List<BusinessCard>> {
        return dao.getAllCards().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun clearAllTables() {
        dao.deleteAll()
    }
}
