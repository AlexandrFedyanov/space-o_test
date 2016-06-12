package com.spaceo.afedyanov.space_otest.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alexandr on 12.06.2016.
 */
class FileUtility() {
    companion object {
        fun getFilePathFromUri(context: Context, uri: Uri): String? {
            var file: String? = null;
            val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA );
            val cursor = context.contentResolver.query(uri, proj, null, null, null);
            cursor ?: return uri.path
            if(cursor.moveToFirst()){;
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                file = cursor.getString(column_index);
            }
            cursor.close();
            return file;

        }

        fun createTempImageFile(): File {
            // Create an image file name
            val timeStamp = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
            val imageFileName = "space_o_"+timeStamp
            val storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES)
            val image = File.createTempFile(
                    imageFileName, ".jpg", storageDir )
            return image
        }
    }
}