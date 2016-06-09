package com.spaceo.afedyanov.space_otest.view.activity

import android.support.v7.app.AppCompatActivity
import android.view.ActionMode
import kotlinx.android.synthetic.main.view_toolbar.*
import java.util.*

abstract class BaseToolbarActivity : AppCompatActivity() {

    private var currentLocale: Locale? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolBar()
        setupLayout()
    }

    override fun onResume() {
        super.onResume()
        checkLanguageChanged()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
    }

    abstract fun setupLayout()

    override fun startActionMode(callback: ActionMode.Callback): ActionMode? {
        return toolbar.startActionMode(callback)
    }

    private fun checkLanguageChanged() {
        if (currentLocale != resources.configuration.locale)
            changeLanguage()
        currentLocale = resources.configuration.locale
    }

    open fun changeLanguage() {

    }
}