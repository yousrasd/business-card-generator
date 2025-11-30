package com.yousrasdn.businesscardgenerator.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import javax.inject.Inject
import androidx.core.net.toUri
import java.io.File

interface ImageRepository {

    /**
     * Saves an image to the app's storage and returns the storage path
     *
     * @param uri The URI of the image to save - the URI points to local cache folder on app storage
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
        inputStream?.close()

        val rotatedBitmap = fixImageOrientation(uri, bitmap)

        val file = File(context.filesDir, "profile_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { out ->
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }

        if (rotatedBitmap != bitmap) {
            bitmap.recycle()
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

    private fun fixImageOrientation(uri: String, bitmap: Bitmap): Bitmap {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri.toUri())
            val exif = inputStream?.let { ExifInterface(it) }
            inputStream?.close()

            val orientation = exif?.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            ) ?: ExifInterface.ORIENTATION_NORMAL

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
                else -> return bitmap
            }

            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            bitmap
        }
    }
}
