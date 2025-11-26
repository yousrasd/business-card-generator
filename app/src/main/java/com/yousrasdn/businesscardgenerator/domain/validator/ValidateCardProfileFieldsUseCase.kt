package com.yousrasdn.businesscardgenerator.domain.validator

import android.util.Patterns
import com.yousrasdn.businesscardgenerator.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateCardProfileFieldsUseCase @Inject constructor() {

    fun validateEmail(email: String): CardProfileFieldValidationResult {
        return when {
            email.isBlank() -> CardProfileFieldValidationResult.Error(R.string.error_email_required)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                CardProfileFieldValidationResult.Error(R.string.error_email_invalid)
            else -> CardProfileFieldValidationResult.Success
        }
    }

    fun validatePhone(phone: String): CardProfileFieldValidationResult {
        val digitsOnly = phone.replace(Regex("[^0-9]"), "")
        
        return when {
            phone.isBlank() -> CardProfileFieldValidationResult.Success
            digitsOnly.length < 10 -> CardProfileFieldValidationResult.Error(R.string.error_phone_too_short)
            !Patterns.PHONE.matcher(phone).matches() -> 
                CardProfileFieldValidationResult.Error(R.string.error_phone_invalid)
            else -> CardProfileFieldValidationResult.Success
        }
    }

    fun validateFirstName(firstName: String): CardProfileFieldValidationResult {
        return when {
            firstName.isBlank() -> CardProfileFieldValidationResult.Error(R.string.error_first_name_required)
            firstName.length < 2 -> CardProfileFieldValidationResult.Error(R.string.error_first_name_too_short)
            else -> CardProfileFieldValidationResult.Success
        }
    }

    fun validateLastName(lastName: String): CardProfileFieldValidationResult {
        return when {
            lastName.isBlank() -> CardProfileFieldValidationResult.Error(R.string.error_last_name_required)
            lastName.length < 2 -> CardProfileFieldValidationResult.Error(R.string.error_last_name_too_short)
            else -> CardProfileFieldValidationResult.Success
        }
    }

    fun validateJobTitle(jobTitle: String): CardProfileFieldValidationResult {
        return when {
            jobTitle.isBlank() -> CardProfileFieldValidationResult.Error(R.string.error_job_title_required)
            else -> CardProfileFieldValidationResult.Success
        }
    }

    fun validateCompany(company: String): CardProfileFieldValidationResult {
        return when {
            company.isBlank() -> CardProfileFieldValidationResult.Error(R.string.error_company_required)
            else -> CardProfileFieldValidationResult.Success
        }
    }

    fun validateWebsite(website: String): CardProfileFieldValidationResult {
        if (website.isBlank()) return CardProfileFieldValidationResult.Success
        
        return when {
            !Patterns.WEB_URL.matcher(website).matches() -> 
                CardProfileFieldValidationResult.Error(R.string.error_website_invalid)
            else -> CardProfileFieldValidationResult.Success
        }
    }

    fun validateBio(bio: String): CardProfileFieldValidationResult {
        return when {
            bio.length > 500 -> CardProfileFieldValidationResult.Error(R.string.error_bio_too_long)
            else -> CardProfileFieldValidationResult.Success
        }
    }
}

sealed interface CardProfileFieldValidationResult {
    data object Success : CardProfileFieldValidationResult
    data class Error(val errorMessageResId: Int) : CardProfileFieldValidationResult
}