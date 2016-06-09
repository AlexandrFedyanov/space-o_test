package com.spaceo.afedyanov.space_otest.view.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.View
import java.util.*

abstract class BaseFragment: Fragment() {

    private var currentLocale: Locale? = null

    open fun scrollContentToTop() {

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    override fun onResume() {
        super.onResume()
        checkLanguageChanged()
    }

    abstract fun setupLayout()

    private fun checkLanguageChanged() {
        if (currentLocale != resources.configuration.locale)
            changeLanguage()
        currentLocale = resources.configuration.locale
    }

    open fun changeLanguage() {

    }
}