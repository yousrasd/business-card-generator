package com.yousrasdn.businesscardgenerator.domain.validator

import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import org.junit.Before
import org.junit.Test

class EmailValidatorTest {
    
    private lateinit var validator: ValidateCardProfileFieldsUseCase
    
    @Before
    fun setup() {
        validator = ValidateCardProfileFieldsUseCase()
    }
    
    @Test
    fun `valid email returns success`() {
        val result = validator.validateEmail("test@example.com")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `email with subdomain returns success`() {
        val result = validator.validateEmail("user@mail.example.com")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `email with plus sign returns success`() {
        val result = validator.validateEmail("user+tag@example.com")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `empty email returns error`() {
        val result = validator.validateEmail("")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `email without at symbol returns error`() {
        val result = validator.validateEmail("testexample.com")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `email without domain returns error`() {
        val result = validator.validateEmail("test@")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `email without username returns error`() {
        val result = validator.validateEmail("@example.com")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
}
