package com.spaceo.afedyanov.space_otest.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.media.ExifInterface
import android.net.Uri
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class BitmapHelper(private var context: Context?) {

    val defaultSizeKey = -1

    fun createBitmap(filePath: String,  width : Int, height : Int): Bitmap? {
        val bmp = decodeSampledBitmapFromResource(filePath, width, height)
        return bmp
    }

    fun createBitmap(uri: Uri,  width : Int, height : Int): Bitmap? {
        val bmp = decodeSampledBitmapFromUri(uri, width, height)
        return bmp
    }

    fun createBitmapAsync(filePath: String,  width : Int, height : Int, onComplete: (Bitmap?) -> Unit) {
        async() {
            val bitmap = createBitmap(filePath, width, height)
            uiThread { onComplete.invoke(bitmap) }
        }
    }

    fun createBitmapAsync(uri: Uri,  width : Int, height : Int, onComplete: (Bitmap?) -> Unit) {
        async() {
            val bitmap = createBitmap(uri, width, height)
            uiThread { onComplete.invoke(bitmap) }
        }
    }

    fun createBitmapAsync(filePath: String, onComplete: (Bitmap?) -> Unit) {
        createBitmapAsync(filePath, defaultSizeKey, defaultSizeKey, onComplete)
    }

    fun createBitmapAsync(uri: Uri, onComplete: (Bitmap?) -> Unit) {
        createBitmapAsync(uri, defaultSizeKey, defaultSizeKey, onComplete)
    }

    private fun decodeSampledBitmapFromResource(filePath : String, width : Int, height : Int) : Bitmap? {
        var options: BitmapFactory.Options? = null
        if (width != defaultSizeKey) {
            options = BitmapFactory.Options()
            options.inJustDecodeBounds = true;
            options.inSampleSize = calculateInSampleSize(options, width, height);
            options.inJustDecodeBounds = false;
        }
        val exifInterface = ExifInterface(filePath)
        val orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)
        var rotation = 0f
        when (orientation.toInt()){
            ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270f
        }
        val matrix = Matrix()
        matrix.postRotate(rotation)
        var bitmap: Bitmap?
        if (options != null) {
            bitmap = BitmapFactory.decodeFile(filePath, options);
        } else {
            bitmap = BitmapFactory.decodeFile(filePath);
        }
        bitmap ?: return  null;
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun decodeSampledBitmapFromUri(uri : Uri, width : Int, height : Int) : Bitmap? {
        context ?: return null
        var options: BitmapFactory.Options? = null
        if (width != defaultSizeKey) {
            options = BitmapFactory.Options()
            options.inJustDecodeBounds = true;
            options.inSampleSize = calculateInSampleSize(options, width, height);
            options.inJustDecodeBounds = false;
        }
        val inputStream = context?.contentResolver?.openInputStream(uri)
        var bitmap: Bitmap?
        if (options != null) {
            bitmap = BitmapFactory.decodeStream(inputStream, Rect(), options);
        } else {
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        return bitmap
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 2

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round(height.toFloat() / reqHeight.toFloat())
            } else {
                inSampleSize = Math.round(width.toFloat() / reqWidth.toFloat())
            }
        }
        return inSampleSize
    }

    fun cleanUp() {
        context = null
    }

}