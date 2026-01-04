package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

import android.app.Application
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.debug.DevToolsRepository
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.GetMyBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.ProfilePictureProcessingUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.SaveBusinessCardUseCase
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import com.yousrasdn.businesscardgenerator.domain.validator.StepValidatorFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.MutableStateFlow
import com.yousrasdn.businesscardgenerator.debug.PrefillData
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BusinessCardFormViewModelTest {
    
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var application: Application
    private lateinit var validateFields: ValidateCardProfileFieldsUseCase
    private lateinit var stepValidatorFactory: StepValidatorFactory
    private lateinit var profilePictureProcessing: ProfilePictureProcessingUseCase
    private lateinit var saveBusinessCard: SaveBusinessCardUseCase
    private lateinit var devToolsRepository: DevToolsRepository
    private lateinit var getMyCard: GetMyBusinessCardUseCase
    private lateinit var viewModel: BusinessCardFormViewModel
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = mockk(relaxed = true)
        validateFields = mockk()
        stepValidatorFactory = mockk(relaxed = true)
        profilePictureProcessing = mockk()
        saveBusinessCard = mockk()
        devToolsRepository = mockk()
        getMyCard = mockk()
        
        every { application.getString(any()) } returns "Test String"
        every { devToolsRepository.prefillData } returns MutableStateFlow<PrefillData?>(null)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `initial state has first step`() = runTest(testDispatcher) {
        viewModel = BusinessCardFormViewModel(
            application, validateFields, stepValidatorFactory,
            profilePictureProcessing, saveBusinessCard, devToolsRepository, getMyCard
        )
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.currentStep).isEqualTo(ProfileCreationStep.BasicInfo)
            assertThat(state.isEditMode).isFalse()
        }
    }
    
    @Test
    fun `when first name updated, state reflects change`() = runTest(testDispatcher) {
        every { validateFields.validateFirstName(any()) } returns CardProfileFieldValidationResult.Success
        
        viewModel = BusinessCardFormViewModel(
            application, validateFields, stepValidatorFactory,
            profilePictureProcessing, saveBusinessCard, devToolsRepository, getMyCard
        )
        
        viewModel.onEvent(BusinessCardFormEvent.UpdateFirstName("John"))
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.firstName).isEqualTo("John")
            assertThat(state.firstNameError).isNull()
        }
    }
    
    @Test
    fun `when email invalid, error is set`() = runTest(testDispatcher) {
        every { validateFields.validateEmail(any()) } returns 
            CardProfileFieldValidationResult.Error(R.string.error_email_invalid)
        
        viewModel = BusinessCardFormViewModel(
            application, validateFields, stepValidatorFactory,
            profilePictureProcessing, saveBusinessCard, devToolsRepository, getMyCard
        )
        
        viewModel.onEvent(BusinessCardFormEvent.UpdateEmail("invalid"))
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.email).isEqualTo("invalid")
            assertThat(state.emailError).isNotNull()
        }
    }
    
    @Test
    fun `when card saved successfully in create mode, emits success`() = runTest(testDispatcher) {
        coEvery { saveBusinessCard(any()) } returns Result.success(1L)
        
        viewModel = BusinessCardFormViewModel(
            application, validateFields, stepValidatorFactory,
            profilePictureProcessing, saveBusinessCard, devToolsRepository, getMyCard
        )
        
        viewModel.sideEffect.test {
            // Navigate through steps: BasicInfo -> ContactInfo -> Photo -> Review
            viewModel.onEvent(BusinessCardFormEvent.NextStep)
            viewModel.onEvent(BusinessCardFormEvent.NextStep)
            viewModel.onEvent(BusinessCardFormEvent.NextStep)
            
            // Final NextStep on Review step triggers save
            viewModel.onEvent(BusinessCardFormEvent.NextStep)
            
            assertThat(awaitItem()).isEqualTo(BusinessCardFormSideEffect.CardCreationSuccess)
        }
    }
    
    @Test
    fun `when loading card for edit, state is populated`() = runTest(testDispatcher) {
        val testCard = BusinessCard(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            jobTitle = "Developer",
            company = "Tech Corp",
            email = "john@test.com",
            phone = "+1234567890",
            website = "www.test.com"
        )
        
        coEvery { getMyCard() } returns flowOf(testCard)
        
        viewModel = BusinessCardFormViewModel(
            application, validateFields, stepValidatorFactory,
            profilePictureProcessing, saveBusinessCard, devToolsRepository, getMyCard
        )
        
        viewModel.loadCardForEdit()
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isEditMode).isTrue()
            assertThat(state.firstName).isEqualTo("John")
            assertThat(state.lastName).isEqualTo("Doe")
            assertThat(state.email).isEqualTo("john@test.com")
        }
    }
}
