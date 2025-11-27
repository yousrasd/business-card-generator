package com.yousrasdn.businesscardgenerator.data.mapper

import com.yousrasdn.businesscardgenerator.data.local.entity.BusinessCardEntity
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard

fun BusinessCardEntity.toDomain(): BusinessCard {
    return BusinessCard(
        id = id,
        firstName = firstName,
        lastName = lastName,
        jobTitle = jobTitle,
        company = company,
        email = email,
        phone = phone,
        website = website,
        photoPath = photoPath,
        createdAt = createdAt,
        isMyCard = isMyCard
    )
}

fun BusinessCard.toEntity(): BusinessCardEntity {
    return BusinessCardEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        jobTitle = jobTitle,
        company = company,
        email = email,
        phone = phone,
        website = website,
        photoPath = photoPath,
        createdAt = createdAt,
        isMyCard = isMyCard
    )
}
