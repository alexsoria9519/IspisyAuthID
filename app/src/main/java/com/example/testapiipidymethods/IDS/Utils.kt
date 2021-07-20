package com.example.testapiipidymethods.IDS

import android.content.Context
import android.util.Log
import com.example.testapiipidymethods.IDS.Admin.ApiKeyLite
import com.google.gson.Gson
import java.util.*

class Utils(context: Context) {

    val preferences = context.getSharedPreferences("Your preference name", Context.MODE_PRIVATE)

    fun existDataAccount(): Boolean{
        val data = preferences.getString("DATA_ACCOUNT", "")
        return( data != "" )
    }

     fun saveLocalStorage(dataResponseAccount: String){
        var editor = preferences.edit()
        editor.putString("DATA_ACCOUNT", dataResponseAccount)
        editor.commit()
    }

    fun getDataAccount(): ApiKeyLite{
        try {
            val gson = Gson()
            val data = preferences.getString("DATA_ACCOUNT", "")
            return gson.fromJson(data, ApiKeyLite::class.java)
        } catch (e: Exception){
            return ApiKeyLite()
        }
    }

    fun <T> List<T>.toArrayList(): ArrayList<T>{
        return ArrayList(this)
    }
}