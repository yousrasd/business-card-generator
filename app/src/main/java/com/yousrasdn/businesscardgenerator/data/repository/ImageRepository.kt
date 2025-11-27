package com.yousrasdn.businesscardgenerator.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import javax.inject.Inject
import androidx.core.net.toUri
import java.io.File

interface ImageRepository {

    /**
     * Saves an image to the app's storage and returns the storage path
     */
    fun saveImage(uri: String): String

    fun deleteImage(uri: String): Boolean

}

class ImageRepositoryImpl @Inject constructor(
    val context: Context
): ImageRepository {

    override fun saveImage(uri: String): String {
        val inputStream = context.contentResolver.openInputStream(uri.toUri())
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val file = File(context.filesDir, "profile_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }

        return file.absolutePath
    }

    override fun deleteImage(uri: String): Boolean {
        val file = File(uri)
        if (file.exists()) {
            return file.delete()
        }

        return false
    }


}
