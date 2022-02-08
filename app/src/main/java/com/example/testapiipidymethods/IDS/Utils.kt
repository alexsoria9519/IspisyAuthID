package com.example.testapiipidymethods.IDS

import android.content.Context
import android.util.Log
import com.example.testapiipidymethods.IDS.Accounts.Account
import com.example.testapiipidymethods.IDS.Accounts.BiometricCredential
import com.example.testapiipidymethods.IDS.Admin.ApiKeyLite
import com.google.gson.Gson
import java.util.*

class Utils(context: Context) {

    private val preferences = context.getSharedPreferences("Your preference name", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun existDataAccount(): Boolean{
        val data = preferences.getString("DATA_ACCOUNT", "")
        return( data != "" )
    }

     fun saveLocalStorage(dataResponseAccount: String){
        var editor = preferences.edit()
        editor.putString("DATA_ACCOUNT", dataResponseAccount)
        editor.commit()
    }

    fun saveDataPhoto(datImage: String){
        var editor = preferences.edit()
        editor.putString("IMAGE", datImage)
        editor.commit()
    }

    fun getDataAccount(): ApiKeyLite{
        try {
            val data = preferences.getString("DATA_ACCOUNT", "")
            return gson.fromJson(data, ApiKeyLite::class.java)
        } catch (e: Exception){
            return ApiKeyLite()
        }
    }

    fun existsDataAccountIpsidy(): Boolean{
        val data = preferences.getString("DATA_ACCOUNT_IPSIDY", "")
        return( data != "" )
    }

    fun getDataAccountIpsidy(): Account{
        try {
            val data = preferences.getString("DATA_ACCOUNT_IPSIDY", "")
            return gson.fromJson(data, Account::class.java)
        }catch (e: Exception){
            return Account()
        }
    }

    fun saveDataResponse(dataAccount: Account){
        val reponse = gson.toJson(dataAccount)
        saveDataLocalStorage(reponse!!, "DATA_ACCOUNT_IPSIDY")
    }

    fun saveDataResponse(dataBiometricalAccount: BiometricCredential){
        val reponse = gson.toJson(dataBiometricalAccount)
        saveDataLocalStorage(reponse!!, "DATA_ACCOUNT_BIOMETRICAL_IPSIDY")
    }


    fun saveDataLocalStorage(data: String, key: String){
        var editor = preferences.edit()
        editor.putString(key, data)
        editor.commit()
    }


    fun existsDataBimetricalAccount(): Boolean{
        val data = preferences.getString("DATA_ACCOUNT_BIOMETRICAL_IPSIDY", "")
        return( data != "" )
    }

    fun getDataBiometricalAccount(): BiometricCredential{
        try {
            val data = preferences.getString("DATA_ACCOUNT_BIOMETRICAL_IPSIDY", "")
            return gson.fromJson(data, BiometricCredential::class.java)
        }catch (e: Exception){
            return BiometricCredential()
        }
    }


    fun <T> List<T>.toArrayList(): ArrayList<T>{
        return ArrayList(this)
    }
}