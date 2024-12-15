package com.example.diarynote.Data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("DiaryPreferences", Context.MODE_PRIVATE)

    fun setUsername(username: String) {
        sharedPreferences.edit().putString("USERNAME", username).apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString("USERNAME", "User") ?: "User"
    }

    fun setTheme(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean("THEME", isDarkMode).apply()
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean("THEME", false)
    }
}