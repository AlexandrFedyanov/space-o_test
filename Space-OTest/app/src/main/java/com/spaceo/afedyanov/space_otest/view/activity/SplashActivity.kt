package com.spaceo.afedyanov.space_otest.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private val splashDelay = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({ showMainActivity() }, splashDelay)
    }

    fun showMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
