package com.spaceo.afedyanov.space_otest.view.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.OnMenuTabClickListener
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.appnavigation.showFragmentByMenuItem
import com.spaceo.afedyanov.space_otest.view.fragment.BaseFragment
import com.spaceo.afedyanov.space_otest.view.fragment.NotesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by Alexandr on 06.06.2016.
 */
class MainActivity : AppCompatActivity() {

    private val NOTES_TAB_POSITION = 0
    private val IMAGE_TAB_POSITION = 1
    private val SERVICE_TAB_POSITION = 2
    private val MAP_TAB_POSITION = 3

    private lateinit var bottomBar: BottomBar

    var currentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolBar()
        initBottomNavigationView(savedInstanceState)
        showCurrentFragment()
        addNoteButton.setOnClickListener {
            if (currentFragment is NotesFragment)
                (currentFragment as NotesFragment).onAddNoteClicked()
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        bottomBar.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomBar.setOnMenuTabClickListener(null)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun initBottomNavigationView(savedInstanceState: Bundle?) {
        bottomBar = BottomBar.attachShy(rootLayout, fragmentContainer, savedInstanceState)
        bottomBar.setItems(R.menu.activity_main_navigation)
        bottomBar.setOnMenuTabClickListener(object: OnMenuTabClickListener {
            override fun onMenuTabSelected(menuItemId: Int) {
                showFragmentByMenuItem(menuItemId)
                setUpTitleByMenuItem(menuItemId)
            }

            override fun onMenuTabReSelected(menuItemId: Int) {
                currentFragment?.scrollContentToTop()
            }
        })
    }

    private fun showCurrentFragment() {
        var itemId = R.id.nav_notes;
        when(bottomBar.currentTabPosition) {
            NOTES_TAB_POSITION -> itemId = R.id.nav_notes
            IMAGE_TAB_POSITION -> itemId = R.id.nav_image
            SERVICE_TAB_POSITION -> itemId = R.id.nav_service
            MAP_TAB_POSITION -> itemId = R.id.nav_map
        }
        showFragmentByMenuItem(itemId)
        setUpTitleByMenuItem(itemId)
    }

    private fun setUpTitleByMenuItem(menuItemId: Int) {
        when(menuItemId) {
            R.id.nav_notes -> title = getString(R.string.nav_item_notes)
            R.id.nav_image -> title = getString(R.string.nav_item_image)
            R.id.nav_service -> title = getString(R.string.nav_item_service)
            R.id.nav_map -> title = getString(R.string.nav_item_map)
        }
    }
}
