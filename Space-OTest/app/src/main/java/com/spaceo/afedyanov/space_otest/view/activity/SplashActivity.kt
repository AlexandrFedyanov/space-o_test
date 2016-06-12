package com.spaceo.afedyanov.space_otest.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.spaceo.afedyanov.space_otest.utils.PreferenceHelper
import java.util.*

class SplashActivity : AppCompatActivity() {

    private val splashDelay = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppLanguage()
        Handler().postDelayed({ showMainActivity() }, splashDelay)
    }

    fun setAppLanguage() {
        val locale = Locale(PreferenceHelper.getSavedLanguage(this))
        val newConfiguration = resources.configuration
        newConfiguration.locale = locale
        resources.updateConfiguration(newConfiguration, resources.displayMetrics)
    }

    fun showMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
