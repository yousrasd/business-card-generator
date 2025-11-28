package com.yousrasdn.businesscardgenerator.domain.usecase

import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepository
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyBusinessCardUseCase @Inject constructor(
    private val repository: BusinessCardRepository
) {
    operator fun invoke(): Flow<BusinessCard?> {
        return repository.getMyCard()
    }
}
