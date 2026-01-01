package com.yousrasdn.businesscardgenerator.domain.usecase

import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepository
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import javax.inject.Inject

class DeleteBusinessCardUseCase @Inject constructor(
    private val repository: BusinessCardRepository
) {
    suspend operator fun invoke(card: BusinessCard) {
        repository.deleteCard(card)
    }
}
