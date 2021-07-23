package com.example.testapiipidymethods.IDS

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Base64
import android.util.Log
import com.example.testapiipidymethods.IDS.Accounts.Account
import com.example.testapiipidymethods.IDS.Accounts.BiometricCredential
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class IpsidyData {

    constructor()


    fun getDataFoto(data: String, bitImage: Bitmap, context: Context) {
        val util = Utils(context)
        Log.e("Data Desde Clase Kotlin", data)
        util.saveDataPhoto(data)
        saveDataToDevice(bitImage) // Save Image in the Storage
        val dataImage = convertBitmapImageToBase64String(bitImage)
        validateAccountIpsidy(context, util, dataImage!!)
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


    private fun validateAccountIpsidy(context: Context, utils: Utils, dataImage: String) {
        if (utils.existsDataAccountIpsidy()) {
            val dataAccount: Account = utils.getDataAccountIpsidy()
            Log.e("Data Account Ipsidy", dataAccount.toString())

            if (utils.existsDataBimetricalAccount()) {
                // verification process
            } else {
                createIpsidyBiometricalAccount(context, dataAccount.AccountNumber!!, dataImage)
            }
        } else {
            createIpsidyAccount(context)
        }
    }

    private fun createIpsidyAccount(context: Context) {
        val service = Services()
        val utils = Utils(context)
        val gson = Gson()

        val call = service.getAccountService(context).createAccount(getDataModelAccount());

        Log.e("Data to Send ", gson.toJson(getDataModelAccount()))

        call.enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.code() == 200) {
                    val dataResponse = response.body()!!
                    utils.saveDataResponse(dataResponse)
                } else if (response.code() === 409) {
                    Log.e("Error Response ", response.raw().networkResponse().message())
                    Log.e("Error Response ", response.raw().networkResponse().toString())
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                Log.e("Error", t.message!!)
            }
        })
    }

    private fun getDataModelAccount(): Account {
        val dataAccount: Account = Account()
        dataAccount.AccountNumber = "TestAccount6-OTF" // Compose for the username ot OTF Account
        dataAccount.DisplayName = "TestAccount 6 OTF"
        dataAccount.CustomDisplayName = "TestAccount 6 OTF"
        dataAccount.Description = "TestAccount 6 OTF for OnTheFlyPOS Inc."
        dataAccount.Currency = "USD"
        dataAccount.External = false
        dataAccount.CustomerNumber = "OnTheFlyPOS" // Data get Of Interface of OTF Service
        dataAccount.CreatedDate = Date()
        return dataAccount
    }

    private fun createIpsidyBiometricalAccount(
        context: Context,
        accountNumber: String,
        dataImage: String // Image base 64
    ) {
        val service = Services()
        val utils = Utils(context)
        val gson = Gson()


        val call = service.getAccountService(context).createAccountBiometricCredential(
            accountNumber,
            getDataModelBioCredential(accountNumber, dataImage)
        );

        Log.e("Data to Send ", gson.toJson(getDataModelBioCredential(accountNumber, dataImage)))

        call.enqueue(object : Callback<BiometricCredential> {
            override fun onResponse(
                call: Call<BiometricCredential>,
                response: Response<BiometricCredential>
            ) {
                if (response.code() == 200) {
                    val dataResponse = response.body()!!
                    utils.saveDataResponse(dataResponse)
                } else if (response.code() === 409) {
                    Log.e("Error Response ", response.raw().networkResponse().message())
                    Log.e("Error Response ", response.raw().networkResponse().toString())
                }
            }

            override fun onFailure(call: Call<BiometricCredential>, t: Throwable) {
                Log.e("Error", t.message!!)
            }
        })


    }

    private fun getDataModelBioCredential(
        accountNumber: String,
        dataImage: String //Image in base 64
    ): BiometricCredential {
        val biometric: BiometricCredential = BiometricCredential()
        biometric.Description = "This is a face record of the $accountNumber"
        biometric.CredentialType = 1 // 0 Unknown Face=1
        biometric.DataType = 1 // Unknown=0 Jpeg=1 Bmp=2 Gif=3 Png=4 Tiff=5 Jpeg2K=6 Template=100
        biometric.Data = dataImage
        biometric.CreatedDate = Date()
        return biometric
    }

    fun convertBitmapImageToBase64String(image: Bitmap?): String? {
        if (image != null) {
            /* Get the image as string */
            // Normal
            val full_stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, full_stream)
            val full_bytes = full_stream.toByteArray()
            return Base64.encodeToString(full_bytes, Base64.DEFAULT)

            // new HTTPWorker(ctx, mHandler, HTTPWorker.WRITE_COMMENT, true).execute(
            // Integer.toString(id), comment, author, img_thumbnail, img_full);
        } else {
            // new HTTPWorker(ctx, mHandler, HTTPWorker.WRITE_COMMENT, true).execute(
            // Integer.toString(id), comment, author, null, null);
        }
        return ""
    }


}