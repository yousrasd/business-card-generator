package com.yousrasdn.businesscardgenerator.domain.usecase

import com.yousrasdn.businesscardgenerator.data.repository.ImageRepository
import javax.inject.Inject


class ProfilePictureProcessingUseCase @Inject constructor(
    val imageRepository: ImageRepository
){
    fun saveImage(uri: String): String =
        imageRepository.saveImage(uri)
    
    fun deleteImage(uri: String): Boolean =
        imageRepository.deleteImage(uri)
}