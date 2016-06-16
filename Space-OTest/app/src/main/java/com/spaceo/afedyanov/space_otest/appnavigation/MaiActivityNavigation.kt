package com.spaceo.afedyanov.space_otest.appnavigation

import android.content.Intent
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.view.activity.LanguageSettingsActivity
import com.spaceo.afedyanov.space_otest.view.activity.MainActivity
import com.spaceo.afedyanov.space_otest.view.fragment.ImageFragment
import com.spaceo.afedyanov.space_otest.view.fragment.MyLocationFragment
import com.spaceo.afedyanov.space_otest.view.fragment.NotesFragment
import com.spaceo.afedyanov.space_otest.view.fragment.FeedsFragment
import kotlinx.android.synthetic.main.activity_main.*

fun MainActivity.showFragmentByMenuItem(menuItemId: Int) {
    when(menuItemId) {
        R.id.nav_notes -> showNotesFragment()
        R.id.nav_image -> showImageFragment()
        R.id.nav_service -> showServiceFragment()
        R.id.nav_map -> showMapFragment()
    }
}

fun MainActivity.showNotesFragment() {
    currentFragment = NotesFragment.newInstance()
    addNoteButton.show()
    animateShowCurrentFragment()
}

fun MainActivity.showImageFragment() {
    currentFragment = ImageFragment.newInstance()
    addNoteButton.hide()
    animateShowCurrentFragment()
}

fun MainActivity.showServiceFragment() {
    currentFragment = FeedsFragment.newInstance()
    addNoteButton.hide()
    animateShowCurrentFragment()
}

fun MainActivity.showMapFragment() {
    currentFragment = MyLocationFragment.newInstance()
    addNoteButton.hide()
    animateShowCurrentFragment()
}

fun MainActivity.animateShowCurrentFragment() {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out)
    fragmentTransaction.replace(R.id.fragmentContainer, currentFragment)
    fragmentTransaction.commit()
}

fun MainActivity.showLanguageSettingsActivity() {
    startActivity(Intent(this, LanguageSettingsActivity::class.java))
}
