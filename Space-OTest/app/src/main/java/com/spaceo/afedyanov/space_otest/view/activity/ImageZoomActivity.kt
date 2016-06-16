package com.spaceo.afedyanov.space_otest.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.appnavigation.NavigationConstants
import com.spaceo.afedyanov.space_otest.utils.BitmapHelper
import com.spaceo.afedyanov.space_otest.utils.FileUtility
import kotlinx.android.synthetic.main.content_image_zoom.*
import java.io.File

//TODO: в задание нужно показать, как работать с обработкой мульти-тач, моя реализация в ZoomImageView. В обычной ситуации разумнее использовать
//уже готовые решения, на пример https://github.com/davemorrissey/subsampling-scale-image-view, т.к нормальная реализация
//полноценного зумирования изображений более обширная задача

class ImageZoomActivity : BaseToolbarActivity() {

    private val BITMAP_KEY = "bitmap"
    private val PHOTO_FILE = "photo_file"

    private var createdPhoto: File? = null
    private var bitmapHelper: BitmapHelper? = null;
    private var bitmap: Bitmap? = null
    private val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 3223

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_zoom)
        if (savedInstanceState == null)
           doStartAction()
        bitmapHelper = BitmapHelper(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BITMAP_KEY, bitmap)
        outState?.putString(PHOTO_FILE, createdPhoto?.absolutePath)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        bitmap = savedInstanceState?.getParcelable(BITMAP_KEY)
        showImage(bitmap)
        val photoFilePath = savedInstanceState?.getString(PHOTO_FILE)
        if (photoFilePath != null)
            createdPhoto = File(photoFilePath)
    }

    override fun onDestroy() {
        super.onDestroy()
        bitmapHelper?.cleanUp()
        bitmapHelper = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == NavigationConstants.Codes.TAKE_PICTURE_REQUEST) {
            bitmapHelper?.createBitmapAsync(createdPhoto!!.absolutePath, zoomImage.measuredWidth, zoomImage.measuredHeight,
                    { bitmap -> showImage(bitmap) })
        } else if (resultCode == RESULT_CANCELED && requestCode == NavigationConstants.Codes.TAKE_PICTURE_REQUEST) {
            createdPhoto?.delete()
        } else if (resultCode == RESULT_OK && requestCode == NavigationConstants.Codes.SELECT_PICTURE_REQUEST) {
            if (data != null)
                bitmapHelper?.createBitmapAsync(data.data, zoomImage.measuredWidth, zoomImage.measuredHeight,
                        { bitmap -> showImage(bitmap) })
        }
        if (resultCode == RESULT_CANCELED)
            finish()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST -> {
                if (grantResults.size > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doStartAction()
                }
            }
        }
    }

    override fun setupLayout() {
        zoomInButton.setOnClickListener({ zoomImage.zoomIn() })
        zoomOutButton.setOnClickListener({ zoomImage.zoomOut() })
    }

    private fun doStartAction() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED)
            when (intent.action) {
                NavigationConstants.Actions.ACTION_SELECT_PICTURE -> selectPictureFromGallery()
                NavigationConstants.Actions.ACTION_TAKE_PICTURE -> takePictureFromCamera()
            }
        else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
        }

    }

    fun showImage(bitmap: Bitmap?) {
        this.bitmap = bitmap
        zoomImage.setImageBitmap(bitmap)
    }

    fun selectPictureFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, NavigationConstants.Codes.SELECT_PICTURE_REQUEST)
    }

    fun takePictureFromCamera() {
        createdPhoto = FileUtility.createTempImageFile()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createdPhoto));
        startActivityForResult(intent, NavigationConstants.Codes.TAKE_PICTURE_REQUEST)
    }
}
