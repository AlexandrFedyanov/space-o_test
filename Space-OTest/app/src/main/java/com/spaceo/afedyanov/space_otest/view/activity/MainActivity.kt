package com.spaceo.afedyanov.space_otest.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.OnMenuTabClickListener
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.appnavigation.showFragmentByMenuItem
import com.spaceo.afedyanov.space_otest.appnavigation.showLanguageSettingsActivity
import com.spaceo.afedyanov.space_otest.view.fragment.BaseFragment
import com.spaceo.afedyanov.space_otest.view.fragment.NotesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * Created by Alexandr on 06.06.2016.
 */
class MainActivity : BaseToolbarActivity() {

    private lateinit var bottomBar: BottomBar

    var currentFragment: BaseFragment? = null

    var currentTitleRes: Int = R.string.app_name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigationView(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        bottomBar.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomBar.setOnMenuTabClickListener(null)
    }

    override fun setupLayout() {
        addNoteButton.setOnClickListener {
            if (currentFragment is NotesFragment)
                (currentFragment as NotesFragment).onAddNoteClicked()
        }
    }

    private fun initBottomNavigationView(savedInstanceState: Bundle?) {
        bottomBar = BottomBar.attachShy(rootLayout, fragmentContainer, savedInstanceState)
        bottomBar.setItems(R.menu.activity_main_navigation)
        setupBottomNavigationListener()
    }

    private fun recreateBottomNavigationView() {
        val currentTab = bottomBar.currentTabPosition
        for (i in 0..rootLayout.childCount) {
            if (rootLayout.getChildAt(i) is BottomBar) {
                rootLayout.removeViewAt(i)
                break
            }
        }
        bottomBar = BottomBar.attachShy(rootLayout, fragmentContainer, null)
        setupBottomNavigationListener()
        bottomBar.setItems(R.menu.activity_main_navigation)
        bottomBar.selectTabAtPosition(currentTab, false)
    }

    private fun setupBottomNavigationListener() {
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
    private fun setUpTitleByMenuItem(menuItemId: Int) {
        when(menuItemId) {
            R.id.nav_notes -> currentTitleRes = R.string.nav_item_notes
            R.id.nav_image ->  currentTitleRes = R.string.nav_item_image
            R.id.nav_service -> currentTitleRes = R.string.nav_item_service
            R.id.nav_map ->  currentTitleRes = R.string.nav_item_map
        }
        title = getString(currentTitleRes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_settings -> {
                showLanguageSettingsActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun changeLanguage() {
        recreateBottomNavigationView()
        title = getString(currentTitleRes)
    }
}
