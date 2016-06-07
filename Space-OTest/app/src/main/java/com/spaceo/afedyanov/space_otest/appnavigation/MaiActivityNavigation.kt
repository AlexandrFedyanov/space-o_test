package com.spaceo.afedyanov.space_otest.appnavigation

import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.view.activity.MainActivity
import com.spaceo.afedyanov.space_otest.view.fragment.ImageFragment
import com.spaceo.afedyanov.space_otest.view.fragment.MapFragment
import com.spaceo.afedyanov.space_otest.view.fragment.NotesFragment
import com.spaceo.afedyanov.space_otest.view.fragment.ServiceFragment

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
    animateShowCurrentFragment()
}

fun MainActivity.showImageFragment() {
    currentFragment = ImageFragment.newInstance()
    animateShowCurrentFragment()
}

fun MainActivity.showServiceFragment() {
    currentFragment = ServiceFragment.newInstance()
    animateShowCurrentFragment()
}

fun MainActivity.showMapFragment() {
    currentFragment = MapFragment.newInstance()
    animateShowCurrentFragment()
}

fun MainActivity.animateShowCurrentFragment() {
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.setCustomAnimations(R.animator.fragment_in, R.animator.fragment_out)
    fragmentTransaction.replace(R.id.fragmentContainer, currentFragment)
    fragmentTransaction.commit()
}
