package com.yousrasdn.businesscardgenerator.presentation.screens.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import com.yousrasdn.businesscardgenerator.domain.usecase.GetMyBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.GetScannedCardsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getMyCardUseCase: GetMyBusinessCardUseCase
    private lateinit var getScannedCardsUseCase: GetScannedCardsUseCase
    private lateinit var viewModel: HomeViewModel
    
    private val testCard = BusinessCard(
        id = 1,
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
        Dispatchers.setMain(testDispatcher)
        getMyCardUseCase = mockk()
        getScannedCardsUseCase = mockk()
        
        coEvery { getScannedCardsUseCase() } returns flowOf(emptyList())
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `initial state is loading`() = runTest(testDispatcher) {
        coEvery { getMyCardUseCase() } returns flowOf(testCard)
        
        viewModel = HomeViewModel(getMyCardUseCase, getScannedCardsUseCase)
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }
    
    @Test
    fun `when card loaded successfully, state updates with card`() = runTest(testDispatcher) {
        coEvery { getMyCardUseCase() } returns flowOf(testCard)
        
        viewModel = HomeViewModel(getMyCardUseCase, getScannedCardsUseCase)
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.card).isEqualTo(testCard)
            assertThat(state.error).isNull()
        }
    }
    
    @Test
    fun `when show QR code event, qrCodeVisible becomes true`() = runTest(testDispatcher) {
        coEvery { getMyCardUseCase() } returns flowOf(testCard)
        
        viewModel = HomeViewModel(getMyCardUseCase, getScannedCardsUseCase)
        
        viewModel.onEvent(HomeScreenEvent.ShowQRCode(isVisible = true))
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.qrCodeVisible).isTrue()
        }
    }
    
    @Test
    fun `when hide QR code event, qrCodeVisible becomes false`() = runTest(testDispatcher) {
        coEvery { getMyCardUseCase() } returns flowOf(testCard)
        
        viewModel = HomeViewModel(getMyCardUseCase, getScannedCardsUseCase)
        
        viewModel.onEvent(HomeScreenEvent.ShowQRCode(isVisible = true))
        viewModel.onEvent(HomeScreenEvent.ShowQRCode(isVisible = false))
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.qrCodeVisible).isFalse()
        }
    }
    
    @Test
    fun `when card loading fails, state shows error`() = runTest(testDispatcher) {
        coEvery { getMyCardUseCase() } returns flowOf(null)
        
        viewModel = HomeViewModel(getMyCardUseCase, getScannedCardsUseCase)
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.card).isNull()
        }
    }
}
