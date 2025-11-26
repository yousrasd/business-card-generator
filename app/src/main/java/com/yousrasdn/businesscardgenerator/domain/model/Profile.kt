package com.yousrasdn.businesscardgenerator.domain.model

import android.net.Uri

data class Profile(
    val id: String,
    val fullName: String,
    val jobTitle: String,
    val company: String,
    val email: String,
    val phone: String,
    val website: String,
    val bio: String,
    val photoUri: Uri? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
