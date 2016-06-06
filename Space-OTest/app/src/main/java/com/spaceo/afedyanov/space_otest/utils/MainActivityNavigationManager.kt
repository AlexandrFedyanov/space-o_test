package com.spaceo.afedyanov.space_otest.utils

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import com.spaceo.afedyanov.space_otest.R

/**
 * Created by Alexandr on 06.06.2016.
 */
class MainActivityNavigationManager(private var context: Context?, private var fragmentManager: FragmentManager?) {

    private var currentItem: Int? = null

    /**
     * Call this method on activity life cycle to avoid memory leak
     */
    fun onDestroy() {
        context = null;
        fragmentManager = null;
    }

    fun showFragmentByMenuItem(menuItemId: Int) {
        if (menuItemId != currentItem) {
            currentItem = menuItemId
            when(menuItemId) {
                R.id.nav_notes -> showNotesFragment()
                R.id.nav_image -> showImageFragment()
                R.id.nav_service -> showServiceFragment()
                R.id.nav_map -> showMapFragment()
            }
        }
    }

    fun scrollCurrentFragmentToTop() {

    }

    fun showNotesFragment() {

    }

    fun showImageFragment() {

    }

    fun showServiceFragment() {

    }

    fun showMapFragment() {

    }

    fun showImageScreen() {

    }
}