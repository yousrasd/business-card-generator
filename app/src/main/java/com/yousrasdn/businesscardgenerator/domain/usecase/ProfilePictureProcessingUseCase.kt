package com.yousrasdn.businesscardgenerator.domain.usecase

import com.yousrasdn.businesscardgenerator.data.repository.ImageRepository
import javax.inject.Inject


class ProfilePictureProcessingUseCase @Inject constructor(
    val imageRepository: ImageRepository
){
    operator fun invoke(uri: String): String =
        imageRepository.saveImage(uri)
    
    fun deleteImage(uri: String): Boolean =
        imageRepository.deleteImage(uri)
}