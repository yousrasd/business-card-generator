package com.yousrasdn.businesscardgenerator.domain.validator

import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.domain.usecase.CardProfileFieldValidationResult
import com.yousrasdn.businesscardgenerator.domain.usecase.ValidateCardProfileFieldsUseCase
import org.junit.Before
import org.junit.Test

class NameValidatorTest {
    
    private lateinit var validator: ValidateCardProfileFieldsUseCase
    
    @Before
    fun setup() {
        validator = ValidateCardProfileFieldsUseCase()
    }
    
    @Test
    fun `valid first name returns success`() {
        val result = validator.validateFirstName("John")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `valid last name returns success`() {
        val result = validator.validateLastName("Doe")
        assertThat(result).isEqualTo(CardProfileFieldValidationResult.Success)
    }
    
    @Test
    fun `empty first name returns error`() {
        val result = validator.validateFirstName("")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `empty last name returns error`() {
        val result = validator.validateLastName("")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `first name too short returns error`() {
        val result = validator.validateFirstName("J")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `last name too short returns error`() {
        val result = validator.validateLastName("D")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
    
    @Test
    fun `first name with numbers returns error`() {
        val result = validator.validateFirstName("John123")
        assertThat(result).isInstanceOf(CardProfileFieldValidationResult.Error::class.java)
    }
}
