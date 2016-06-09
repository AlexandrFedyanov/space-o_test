package com.spaceo.afedyanov.space_otest.view.activity

import android.os.Bundle
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.utils.PreferenceHelper
import kotlinx.android.synthetic.main.content_language_settings.*
import java.util.*

class LanguageSettingsActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labguage_settings)
    }

    override fun setupLayout() {
        title = getString(R.string.select_language)
        englishButton.isChecked = true
        when (resources.configuration.locale.language) {
            PreferenceHelper.ENGLISH_LANGUAGE_CODE -> englishButton.isChecked = true
            PreferenceHelper.RUSSIAN_LANGUAGE_CODE -> russianButton.isChecked = true
        }
        englishButton.setOnClickListener({ setAppLanguage(PreferenceHelper.ENGLISH_LANGUAGE_CODE) })
        russianButton.setOnClickListener({ setAppLanguage(PreferenceHelper.RUSSIAN_LANGUAGE_CODE) })
    }

    private fun setAppLanguage(langCode: String) {
        PreferenceHelper.saveLanguage(this, langCode);
        val locale = Locale(langCode)
        val newConfiguration = resources.configuration
        newConfiguration.locale = locale
        resources.updateConfiguration(newConfiguration, resources.displayMetrics)
        changeLanguage()
    }

    override fun changeLanguage() {
        englishButton.text = getString(R.string.eng)
        russianButton.text = getString(R.string.ru)
        languageTitle.text = getString(R.string.select_language)
        title = getString(R.string.select_language)
    }
}
