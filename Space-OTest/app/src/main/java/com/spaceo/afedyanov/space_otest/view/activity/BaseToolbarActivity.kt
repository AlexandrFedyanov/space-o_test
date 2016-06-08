package com.spaceo.afedyanov.space_otest.view.activity

import android.support.v7.app.AppCompatActivity
import android.view.ActionMode
import kotlinx.android.synthetic.main.view_toolbar.*

abstract class BaseToolbarActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolBar()
        setupLayout()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
    }

    abstract fun setupLayout()

    override fun startActionMode(callback: ActionMode.Callback): ActionMode? {
        return toolbar.startActionMode(callback)
    }
}