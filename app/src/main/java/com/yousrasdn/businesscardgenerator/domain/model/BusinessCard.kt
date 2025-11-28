package com.yousrasdn.businesscardgenerator.domain.model

data class BusinessCard(
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val jobTitle: String,
    val company: String,
    val email: String,
    val phone: String,
    val website: String,
    val photoPath: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isMyCard: Boolean = true
)
