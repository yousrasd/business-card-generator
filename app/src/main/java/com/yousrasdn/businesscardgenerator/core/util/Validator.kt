package com.yousrasdn.businesscardgenerator.core.util

import android.util.Patterns

object Validator {
    
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Error("Email is required")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> 
                ValidationResult.Error("Invalid email format")
            else -> ValidationResult.Success
        }
    }
    
    fun validatePhone(phone: String): ValidationResult {
        return when {
            phone.isBlank() -> ValidationResult.Success
            !Patterns.PHONE.matcher(phone).matches() -> 
                ValidationResult.Error("Invalid phone format")
            else -> ValidationResult.Success
        }
    }
    
    fun validateUrl(url: String): ValidationResult {
        return when {
            url.isBlank() -> ValidationResult.Success
            !Patterns.WEB_URL.matcher(url).matches() -> 
                ValidationResult.Error("Invalid URL format")
            else -> ValidationResult.Success
        }
    }
    
    fun validateRequired(value: String, fieldName: String): ValidationResult {
        return if (value.isBlank()) {
            ValidationResult.Error("$fieldName is required")
        } else {
            ValidationResult.Success
        }
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}
