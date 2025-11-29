package com.yousrasdn.businesscardgenerator.domain.validator

import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import org.junit.Before
import org.junit.Test

class PhoneValidatorTest {
    
    private lateinit var validator: ValidateCardProfileFieldsUseCase
    
    @Before
    fun setup() {
        validator = ValidateCardProfileFieldsUseCase()
    }
    
    @Test
    fun `valid phone with country code returns success`() {
        val result = validator.validatePhone("+1234567890")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `valid phone with spaces returns success`() {
        val result = validator.validatePhone("+1 234 567 890")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `valid phone with dashes returns success`() {
        val result = validator.validatePhone("+1-234-567-890")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `empty phone returns success for optional field`() {
        val result = validator.validatePhone("")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `phone too short returns error`() {
        val result = validator.validatePhone("+123")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `phone with letters returns error`() {
        val result = validator.validatePhone("+1234abc567")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
}
