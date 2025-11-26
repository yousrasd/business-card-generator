package com.yousrasdn.businesscardgenerator.presentation.screens.create_card

sealed class ProfileCreationStep(val stepNumber: Int) {
    object BasicInfo : ProfileCreationStep(1)
    object ContactInfo : ProfileCreationStep(2)
    object Photo : ProfileCreationStep(3)
    object Review : ProfileCreationStep(4)
}

fun ProfileCreationStep.getPreviousStep(): ProfileCreationStep? = when(this) {
        ProfileCreationStep.BasicInfo -> null
        ProfileCreationStep.ContactInfo -> ProfileCreationStep.BasicInfo
        ProfileCreationStep.Photo ->  ProfileCreationStep.ContactInfo
        ProfileCreationStep.Review -> ProfileCreationStep.Photo
}

fun ProfileCreationStep.getNextStep(): ProfileCreationStep? = when(this) {
    ProfileCreationStep.BasicInfo -> ProfileCreationStep.ContactInfo
    ProfileCreationStep.ContactInfo -> ProfileCreationStep.Photo
    ProfileCreationStep.Photo ->  ProfileCreationStep.Review
    ProfileCreationStep.Review -> null
}

fun ProfileCreationStep.normalizeToNumber(): Int = when(this) {
    ProfileCreationStep.BasicInfo -> 1
    ProfileCreationStep.ContactInfo -> 2
    ProfileCreationStep.Photo ->  3
    ProfileCreationStep.Review -> 4
}