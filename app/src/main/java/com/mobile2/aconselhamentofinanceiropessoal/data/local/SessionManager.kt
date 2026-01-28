package com.mobile2.aconselhamentofinanceiropessoal.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUserSession(userId: Int, name: String) {
        val editor = prefs.edit()
        editor.putInt("USER_ID", userId)
        editor.putString("USER_NAME", name)
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.apply()
    }

    fun getUserId(): Int {
        return prefs.getInt("USER_ID", -1)
    }

    fun getUserName(): String? {
        return prefs.getString("USER_NAME", null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("IS_LOGGED_IN", false)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}