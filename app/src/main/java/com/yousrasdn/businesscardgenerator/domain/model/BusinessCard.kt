package com.yousrasdn.businesscardgenerator.domain.model

import android.net.Uri

data class BusinessCard(
    val id: String,
    val firstName: String,
    val lastName: String,
    val jobTitle: String,
    val company: String,
    val email: String,
    val phone: String,
    val website: String,
    val photoUri: Uri? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
