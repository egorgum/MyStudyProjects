package com.example.practice_11

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences

private const val PREF_NAME = "pref_name"
private const val PREF_KEY = "pref_key"
class Repository(context: Context) {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_KEY, MODE_PRIVATE)
    }
    private var data: String? = null

    private fun getDataFromSharedPreference(): String?{
        return prefs.getString(PREF_KEY, null)
    }

    private fun getDataFromLocalVariable(): String?{
        return data
    }

    fun saveText(text: String){
        data = text
        prefs.edit().putString(PREF_KEY, text).apply()
    }

    fun clearText(){
        data = null
        prefs.edit().remove(PREF_KEY).apply()
    }

    fun getText(): String{
        return when{
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference() != null -> getDataFromSharedPreference()!!
            else -> return "Storage is empty"
        }
    }
}