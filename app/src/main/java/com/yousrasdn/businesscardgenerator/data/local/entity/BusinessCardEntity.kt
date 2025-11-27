package com.yousrasdn.businesscardgenerator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_cards")
data class BusinessCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val jobTitle: String,
    val company: String,
    val email: String,
    val phone: String,
    val website: String,
    val photoPath: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val isMyCard: Boolean = true
)
