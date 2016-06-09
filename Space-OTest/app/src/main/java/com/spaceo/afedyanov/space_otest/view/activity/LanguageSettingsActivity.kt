package com.spaceo.afedyanov.space_otest.view.activity

import android.os.Bundle
import com.spaceo.afedyanov.space_otest.R
import kotlinx.android.synthetic.main.content_language_settings.*

class LanguageSettingsActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labguage_settings)
    }

    override fun setupLayout() {
        englishButton.setOnClickListener({ })
        russianButton.setOnClickListener({ })
    }
}
