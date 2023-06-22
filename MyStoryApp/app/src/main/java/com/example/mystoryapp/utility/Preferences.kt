package com.example.mystoryapp.utility

import android.content.Context

internal class Preferences(context: Context) {

    private val preferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE)
    fun Save_Token(data: String){
        val editor = preferences.edit()
        editor.putString(TOKEN, data)
        editor.apply()
    }

    fun Get_Token(): String? {
        val token = preferences.getString(TOKEN, null)
        return token
    }

    fun Logout() {
        val editor = preferences.edit().clear()
        editor.apply()
    }

    companion object{
        private const val DATABASE_NAME = "pref"
        private const val TOKEN = "token"

    }


}