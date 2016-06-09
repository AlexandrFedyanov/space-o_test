package com.spaceo.afedyanov.space_otest.utils

import android.content.Context

class PreferenceHelper {

    companion object {
        private val PREFERENCE_NAME = "space-o-test-preference"
        private val KEY_LANGUAGE = "language"
        val ENGLISH_LANGUAGE_CODE = "en"
        val RUSSIAN_LANGUAGE_CODE = "ru"

        fun getSavedLanguage(context: Context): String {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(KEY_LANGUAGE, context.resources.configuration.locale.language)
        }

        fun saveLanguage(context: Context, language: String) {
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(KEY_LANGUAGE, language).apply()
        }
    }
}