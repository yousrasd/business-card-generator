package com.yousrasdn.businesscardgenerator.domain.usecase

import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepository
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import javax.inject.Inject

class GetBusinessCardByIdUseCase @Inject constructor(
    private val repository: BusinessCardRepository
) {
    suspend operator fun invoke(id: Long): BusinessCard? {
        return repository.getCardById(id)
    }
}
