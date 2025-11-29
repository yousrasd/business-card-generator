package com.yousrasdn.businesscardgenerator.data.mapper

import com.google.common.truth.Truth.assertThat
import com.yousrasdn.businesscardgenerator.data.local.entity.BusinessCardEntity
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard
import org.junit.Test

class BusinessCardMapperTest {
    
    private val mapper = BusinessCardMapper()
    
    @Test
    fun `entity to domain maps all fields correctly`() {
        val entity = BusinessCardEntity(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            jobTitle = "Developer",
            company = "Tech Corp",
            email = "john@test.com",
            phone = "+1234567890",
            website = "www.test.com",
            photoPath = "/path/to/photo.jpg",
            createdAt = 123456789L,
            isMyCard = true
        )
        
        val domain = mapper.toDomain(entity)
        
        assertThat(domain.id).isEqualTo(1)
        assertThat(domain.firstName).isEqualTo("John")
        assertThat(domain.lastName).isEqualTo("Doe")
        assertThat(domain.jobTitle).isEqualTo("Developer")
        assertThat(domain.company).isEqualTo("Tech Corp")
        assertThat(domain.email).isEqualTo("john@test.com")
        assertThat(domain.phone).isEqualTo("+1234567890")
        assertThat(domain.website).isEqualTo("www.test.com")
        assertThat(domain.photoPath).isEqualTo("/path/to/photo.jpg")
        assertThat(domain.isMyCard).isTrue()
    }
    
    @Test
    fun `domain to entity maps all fields correctly`() {
        val domain = BusinessCard(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            jobTitle = "Developer",
            company = "Tech Corp",
            email = "john@test.com",
            phone = "+1234567890",
            website = "www.test.com",
            photoPath = "/path/to/photo.jpg",
            createdAt = 123456789L,
            isMyCard = true
        )
        
        val entity = mapper.toEntity(domain)
        
        assertThat(entity.id).isEqualTo(1)
        assertThat(entity.firstName).isEqualTo("John")
        assertThat(entity.lastName).isEqualTo("Doe")
        assertThat(entity.jobTitle).isEqualTo("Developer")
        assertThat(entity.company).isEqualTo("Tech Corp")
        assertThat(entity.email).isEqualTo("john@test.com")
        assertThat(entity.phone).isEqualTo("+1234567890")
        assertThat(entity.website).isEqualTo("www.test.com")
        assertThat(entity.photoPath).isEqualTo("/path/to/photo.jpg")
        assertThat(entity.isMyCard).isTrue()
    }
    
    @Test
    fun `entity with null photo maps correctly`() {
        val entity = BusinessCardEntity(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            jobTitle = "Developer",
            company = "Tech Corp",
            email = "john@test.com",
            phone = "",
            website = "",
            photoPath = null,
            createdAt = 123456789L,
            isMyCard = true
        )
        
        val domain = mapper.toDomain(entity)
        
        assertThat(domain.photoPath).isNull()
    }
    
    @Test
    fun `round trip conversion preserves data`() {
        val original = BusinessCard(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            jobTitle = "Developer",
            company = "Tech Corp",
            email = "john@test.com",
            phone = "+1234567890",
            website = "www.test.com",
            photoPath = "/path/to/photo.jpg",
            isMyCard = true
        )
        
        val entity = mapper.toEntity(original)
        val result = mapper.toDomain(entity)
        
        assertThat(result).isEqualTo(original)
    }
}
