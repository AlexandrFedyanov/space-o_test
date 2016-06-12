package com.spaceo.afedyanov.space_otest.utils

import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return getPathForV19AndUp(context, uri);
            } else {
                return getPathForPreV19(context, uri);
            }
        }

        fun  getPathForPreV19(context: Context, uri: Uri): String? {
            var file: String? = null;
            val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA );
            val cursor = context.contentResolver.query(uri, proj, null, null, null);
            if(cursor.moveToFirst()){;
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                file = cursor.getString(column_index);
            }
            cursor.close();
            return file;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun getPathForV19AndUp(context: Context, uri: Uri): String? {
            val wholeID: String = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            val id = wholeID.split(":")[1];
            val column: Array<String> = arrayOf(MediaStore.Images.Media.DATA );

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?";
            val cursor = context.contentResolver.
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, arrayOf("id"), null);

            var filePath = "";
            val columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }

            cursor.close();
            return filePath;
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