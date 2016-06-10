package com.spaceo.afedyanov.space_otest.appnavigation

import android.content.Intent
import com.spaceo.afedyanov.space_otest.view.activity.ImageZoomActivity
import com.spaceo.afedyanov.space_otest.view.fragment.ImageFragment

fun ImageFragment.showImageActivityTakePicture() {
    showImageActivity(NavigationConstants.Actions.ACTION_TAKE_PICTURE)
}

fun ImageFragment.showImageActivitySelectPicture() {
    showImageActivity(NavigationConstants.Actions.ACTION_SELECT_PICTURE)
}

fun ImageFragment.showImageActivity(action: String) {
    val intent = Intent(activity, ImageZoomActivity::class.java)
    intent.action = action
    startActivity(intent)
}