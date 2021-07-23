package com.example.testapiipidymethods.IDS

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.testapiipidymethods.IDS.Accounts.Account
import com.example.testapiipidymethods.IDS.Admin.ApiKeyLite
import com.example.testapiipidymethods.Methods.GetApisAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class IpsidyData {

    constructor()


    fun getDataFoto(data: String, bitImage: Bitmap, context: Context) {
        val util = Utils(context)
        Log.e("Data Desde Clase Kotlin", data)
        util.saveDataPhoto(data)
        saveDataToDevice(bitImage) // Save Image in the Storage

        validateAccountIpsidy(context, util)

    }

    private fun saveDataToDevice(bitImage: Bitmap) {
        // TODO check this implementation for save in the file system
        val file_path = Environment.getExternalStorageDirectory().absolutePath +
                "/PhysicsSketchpad"
        val dir = File(file_path)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "scan" + ".png")
        val fOut = FileOutputStream(file)
        bitImage.compress(Bitmap.CompressFormat.PNG, 85, fOut)
        fOut.flush()
        fOut.close()
    }


    private fun validateAccountIpsidy(context: Context, utils: Utils) {
        if (utils.existsDataAccountIpsidy()) {
            val dataAccount: Account = utils.getDataAccountIpsidy()
            Log.e("Data Account Ipsidy", dataAccount.toString())
        } else {
            createIpsidyAccount(context)
        }
    }

    private fun createIpsidyAccount(context: Context) {
        val service = Services()
        val utils = Utils(context)
        val gson = Gson()

        val dataAccount: Account = Account()

        dataAccount.AccountNumber = "TestAccount6-OTF" // Compose for the username ot OTF Account
        dataAccount.DisplayName = "TestAccount 6 OTF"
        dataAccount.CustomDisplayName = "TestAccount 6 OTF"
        dataAccount.Description = "TestAccount 6 OTF for OnTheFlyPOS Inc."
        dataAccount.Currency = "USD"
        dataAccount.External = false
        dataAccount.CustomerNumber = "OnTheFlyPOS" // Data get Of Interface of OTF Service
        dataAccount.CreatedDate = Date()

        val call = service.getAccountService(context).createAccount(dataAccount);

        Log.e("Data to Send ", gson.toJson(dataAccount))

        call.enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.code() == 200) {
                    val dataResponse = response.body()!!
                    utils.saveDataResponse(dataResponse)
                } else if(response.code() === 409){
                    Log.e("Error Response ", response.raw().networkResponse().message())
                    Log.e("Error Response ", response.raw().networkResponse().toString())
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                Log.e("Error", t.message!!)
            }
        })
    }


}