package com.example.testapiipidymethods.IDS

import android.content.Context
import android.util.Log

class Utils(context: Context) {

    val preferences = context.getSharedPreferences("Your preference name", Context.MODE_PRIVATE)

    fun existDataLogin(): Boolean{
        val data = preferences.getString("DATA_ACCOUNT", "")
        return( data != "" )
    }

     fun saveLocalStorage(dataResponseAccount: String){
        var editor = preferences.edit()
        editor.putString("DATA_ACCOUNT", dataResponseAccount)
        editor.commit()
    }
}