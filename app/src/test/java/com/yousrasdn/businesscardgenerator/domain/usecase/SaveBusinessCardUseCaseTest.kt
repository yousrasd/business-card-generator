package com.yousrasdn.businesscardgenerator.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepository
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveBusinessCardUseCaseTest {
    
    private lateinit var repository: BusinessCardRepository
    private lateinit var useCase: SaveBusinessCardUseCase
    
    private val testCard = BusinessCard(
        id = 0,
        firstName = "John",
        lastName = "Doe",
        jobTitle = "Developer",
        company = "Tech Corp",
        email = "john@test.com",
        phone = "+1234567890",
        website = "www.test.com"
    )
    
    @Before
    fun setup() {
        repository = mockk()
        useCase = SaveBusinessCardUseCase(repository)
    }
    
    @Test
    fun `when save succeeds, returns success with card id`() = runTest {
        coEvery { repository.saveCard(any()) } returns Result.success(1L)
        
        val result = useCase(testCard)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(1L)
        coVerify { repository.saveCard(testCard) }
    }
    
    @Test
    fun `when save fails, returns failure`() = runTest {
        val exception = Exception("Database error")
        coEvery { repository.saveCard(any()) } returns Result.failure(exception)
        
        val result = useCase(testCard)
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
        coVerify { repository.saveCard(testCard) }
    }
    
    @Test
    fun `when updating existing card, uses correct id`() = runTest {
        val existingCard = testCard.copy(id = 5)
        coEvery { repository.saveCard(any()) } returns Result.success(5L)
        
        val result = useCase(existingCard)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(5L)
        coVerify { repository.saveCard(existingCard) }
    }
}
