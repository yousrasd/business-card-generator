package com.yousrasdn.businesscardgenerator.domain.usecase

import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepository
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import javax.inject.Inject

class SaveBusinessCardUseCase @Inject constructor(
    private val repository: BusinessCardRepository
) {
    suspend operator fun invoke(card: BusinessCard): Result<Long> = repository.saveCard(card)
}
