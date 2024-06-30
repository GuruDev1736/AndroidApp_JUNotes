package com.gurudev.junotes.Constants

import android.content.Context

object SPref {

     val PREF_NAME = "junotes"
     val email = "email"
     val password = "password"
     val token = "token"
     val userId = "userId"
     val yearId = "yearId"

    fun set(context : Context , prefName : String , text : String)
    {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(prefName, text)
        editor.apply()
    }


    fun get(context : Context , prefName : String) : String {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(prefName, "").toString()
    }

    fun clear(context : Context) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
    }
}