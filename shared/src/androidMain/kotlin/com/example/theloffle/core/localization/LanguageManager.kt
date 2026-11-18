package com.example.theloffle.core.localization

import android.app.LocaleManager
import android.content.Context
import android.os.LocaleList

object LanguageManager {

    fun changeLanguage(context: Context, language: String) {
        val localeManager =
            context.getSystemService(LocaleManager::class.java)

        localeManager.applicationLocales =
            LocaleList.forLanguageTags(language)
    }
}