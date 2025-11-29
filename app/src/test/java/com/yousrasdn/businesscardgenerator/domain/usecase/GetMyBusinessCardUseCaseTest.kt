package com.yousrasdn.businesscardgenerator.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.data.repository.BusinessCardRepository
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMyBusinessCardUseCaseTest {
    
    private lateinit var repository: BusinessCardRepository
    private lateinit var useCase: GetMyBusinessCardUseCase
    
    private val testCard = BusinessCard(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        jobTitle = "Developer",
        company = "Tech Corp",
        email = "john@test.com",
        phone = "+1234567890",
        website = "www.test.com",
        isMyCard = true
    )
    
    @Before
    fun setup() {
        repository = mockk()
        useCase = GetMyBusinessCardUseCase(repository)
    }
    
    @Test
    fun `when card exists, returns card`() = runTest {
        coEvery { repository.getMyCard() } returns flowOf(testCard)
        
        useCase().test {
            val card = awaitItem()
            assertThat(card).isEqualTo(testCard)
            awaitComplete()
        }
    }
    
    @Test
    fun `when no card exists, returns null`() = runTest {
        coEvery { repository.getMyCard() } returns flowOf(null)
        
        useCase().test {
            val card = awaitItem()
            assertThat(card).isNull()
            awaitComplete()
        }
    }
    
    @Test
    fun `when card updates, emits new value`() = runTest {
        val updatedCard = testCard.copy(firstName = "Jane")
        coEvery { repository.getMyCard() } returns flowOf(testCard, updatedCard)
        
        useCase().test {
            assertThat(awaitItem()).isEqualTo(testCard)
            assertThat(awaitItem()).isEqualTo(updatedCard)
            awaitComplete()
        }
    }
}
