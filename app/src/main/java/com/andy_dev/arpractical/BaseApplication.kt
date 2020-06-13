package com.andy_dev.arpractical

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class BaseApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: BaseApplication

        private const val prefFile = R.string.app_name.toString()

        fun getApplicationContext(): Context {
            return instance.applicationContext!!
        }

        private fun getSharedPreference(): SharedPreferences {
            return instance.getSharedPreferences(prefFile, Context.MODE_PRIVATE)
        }

        private fun getSharedPreferenceEditor(): SharedPreferences.Editor {
            val preferences = instance.getSharedPreferences(prefFile, Context.MODE_PRIVATE)
            return preferences.edit()
        }

        private val editor: SharedPreferences.Editor
            get() = getSharedPreferenceEditor()

        private val pref: SharedPreferences
            get() = getSharedPreference()

        fun setTitle(token: String) {
            editor.putString("TITLE", token).apply()
        }

        fun getTitle(): String {
            return pref.getString("TITLE", "Facts") ?: "Facts"
        }
    }
}