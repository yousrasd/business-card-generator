package com.yousrasdn.businesscardgenerator.core.util

import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard

object VCardParser {
    
    /**
     * Parses a vCard string into a BusinessCard object
     * Returns null if parsing fails
     */
    fun parse(vCardString: String): BusinessCard? {
        return try {
            if (!vCardString.contains("BEGIN:VCARD")) {
                return null
            }
            
            val lines = vCardString.lines()
            var firstName = ""
            var lastName = ""
            var jobTitle = ""
            var company = ""
            var email = ""
            var phone = ""
            var website = ""
            
            for (line in lines) {
                when {
                    line.startsWith("FN:") -> firstName = line.substringAfter("FN:").trim()
                    line.startsWith("N:") -> lastName = line.substringAfter("N:").trim()
                    line.startsWith("TITLE:") -> jobTitle = line.substringAfter("TITLE:").trim()
                    line.startsWith("ORG:") -> company = line.substringAfter("ORG:").trim()
                    line.startsWith("EMAIL:") -> email = line.substringAfter("EMAIL:").trim()
                    line.startsWith("TEL:") -> phone = line.substringAfter("TEL:").trim()
                    line.startsWith("URL:") -> website = line.substringAfter("URL:").trim()
                }
            }
            
            // Validate required fields
            if (firstName.isEmpty() || email.isEmpty()) {
                return null
            }
            
            BusinessCard(
                firstName = firstName,
                lastName = lastName,
                jobTitle = jobTitle,
                company = company,
                email = email,
                phone = phone,
                website = website,
                isMyCard = false // Scanned cards are not "my card"
            )
        } catch (e: Exception) {
            null
        }
    }
}
