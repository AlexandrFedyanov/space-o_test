package com.spaceo.afedyanov.space_otest.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.appnavigation.NavigationConstants
import com.spaceo.afedyanov.space_otest.utils.BitmapHelper
import kotlinx.android.synthetic.main.content_image_zoom.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImageZoomActivity : BaseToolbarActivity() {

    private var createdPhoto: File? = null
    private var bitmapHelper: BitmapHelper? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent.action) {
            NavigationConstants.Actions.ACTION_SELECT_PICTURE -> selectPictureFromGallery()
            NavigationConstants.Actions.ACTION_TAKE_PICTURE -> takePictureFromCamera()
        }
        setContentView(R.layout.activity_image_zoom)
        bitmapHelper = BitmapHelper(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        bitmapHelper?.cleanUp()
        bitmapHelper = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == NavigationConstants.Codes.TAKE_PICTURE_REQUEST) {
            bitmapHelper?.createBitmapAsync(createdPhoto!!.absolutePath, { bitmap -> showImage(bitmap) })
        } else if (resultCode == RESULT_CANCELED && requestCode == NavigationConstants.Codes.TAKE_PICTURE_REQUEST) {
            createdPhoto?.delete()
        } else if (resultCode == RESULT_OK && requestCode == NavigationConstants.Codes.SELECT_PICTURE_REQUEST) {
            if (data != null)
                bitmapHelper?.createBitmapAsync(data.data, { bitmap -> showImage(bitmap) })
        }
        if (resultCode == RESULT_CANCELED)
            finish()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun setupLayout() {
    }


    fun showImage(bitmap: Bitmap?) {
        zoomImage.setImageBitmap(bitmap)
    }

    fun selectPictureFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, NavigationConstants.Codes.SELECT_PICTURE_REQUEST)
    }

    fun takePictureFromCamera() {
        createdPhoto = createImageFile()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createdPhoto));
        startActivityForResult(intent, NavigationConstants.Codes.TAKE_PICTURE_REQUEST)
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
        val imageFileName = "space_o_"+timeStamp
        val storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, ".jpg", storageDir )
        return image
    }
}
