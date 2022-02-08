package com.example.testapiipidymethods.IDS

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.example.testapiipidymethods.IDS.Accounts.Account
import com.example.testapiipidymethods.IDS.Accounts.BiometricCredential
import com.example.testapiipidymethods.IDS.Accounts.BiometricVerificationResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class IpsidyData() {


    fun setDataFoto(data: String, bitImage: Bitmap, context: Context) {
        val util = Utils(context)
        Log.e("Data Desde Clase Kotlin", data)
        util.saveDataPhoto(data) //Save Local Storage
        saveDataToDevice(bitImage) // Save Image in the Storage
    }

    private fun saveDataToDevice(bitImage: Bitmap) {
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

    private fun convertBitmapImageToBase64String(image: Bitmap?): String? {
        if (image != null) {
            /* Get the image as string */
            // Normal
            val full_stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, full_stream)
            val full_bytes = full_stream.toByteArray()
            return Base64.encodeToString(full_bytes, Base64.DEFAULT)
        }
        return ""
    }


    private fun getDataModelBioCredential(
        accountNumber: String,
        dataImage: String, //Image in base 64
        imageType: Int
    ): BiometricCredential {
        val biometric = BiometricCredential()
        biometric.Description = "This is a face record of the $accountNumber"
        biometric.CredentialType = 1 // 0 Unknown Face=1
        biometric.DataType =
            imageType // Unknown=0 Jpeg=1 Bmp=2 Gif=3 Png=4 Tiff=5 Jpeg2K=6 Template=100
        biometric.Data = dataImage
        biometric.CreatedDate = Date()
        return biometric
    }

    fun  verifyIdentification(
        context: Context,
        dataImage: String,
        accountNumber: String
    ){
        val service = Services()
        val gson = Gson()
        try {
            val api = service.getAccountService(context)
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.verifyAccountBiometricCredential2(
                accountNumber,
                getDataModelBioCredential(accountNumber, dataImage, getImageType("JPEG")))

                if(response.isSuccessful){
                    if (response.code() == 200) {
                        val dataResponse = response.body()!!
                        Log.d("dataResponse", gson.toJson(dataResponse))
                        println("You biometrical account has been created successfully")
                    } else if (response.code() === 409) {

                    }
                }

            }
        } catch (e: Exception) {

        }
    }
    private fun getImageType(extension: String): Int {
        var number = 0;
        when (extension) {
            "JPEG" -> number = 1
            "BMP" -> number = 2
            "GIF" -> number = 3
            "PNG" -> number = 4
            "TIFF" -> number = 5
            "JPEG2K" -> number = 6
            "TEMPLATE" -> number = 100
            else -> number = 0
        }
        return number
    }

    private fun analyzeDataVerification(
        dataVerification: BiometricVerificationResult,
        context: Context
    ) {
        if (dataVerification.MatchProbability != null && dataVerification.Score != null) {
            if (dataVerification.Verified!! && dataVerification.Score!! >= 90 && dataVerification.MatchProbability >= 0.90) {
                val toast: Toast = Toast.makeText(
                    context,
                    "Congratulations, you has been verified successfully",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (dataVerification.Verified!! && dataVerification.Score!! < 90 && dataVerification.MatchProbability < 0.90) {
                val toast: Toast = Toast.makeText(
                    context,
                    "We're sorry, your identity could not be verified. Rescan your face please",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (!dataVerification.Verified!!) {
                val toast: Toast = Toast.makeText(
                    context,
                    "We're sorry, your identity could not be verified",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }


    fun createIpsidyAccount(context: Context, dataImage: String){

        val utils = Utils(context)
        val gson = Gson();
        val service = Services()
        var account = Account()

        try {
            val dataModel = this.getDataModelAccount()
            val api = service.getAccountService(context)

            CoroutineScope(Dispatchers.IO).launch {
                val response = api.createAccount(dataModel)

                if(response.isSuccessful){
                    if (response.code() == 200) {
                        val dataResponse = response.body()!!
                        utils.saveDataResponse(dataResponse)
                        account = dataResponse
                        Log.d("dataAccount", gson.toJson(dataResponse))
                        println("Your account has been created")
                        createIpsidyBiometricalAccount(context,dataImage, account.AccountNumber!!)
                    } else if (response.code() === 409) {

                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.message!!)
        }
    }


    private fun getDataModelAccount(): Account {
        val dataAccount = Account()
        dataAccount.AccountNumber =
            "TestAccount2-OTF" // Compose for the username of OTF Account
        dataAccount.DisplayName = "TestAccount 2  OTF"
        dataAccount.CustomDisplayName = "TestAccount 2  OTF"
        dataAccount.Description = "TestAccount 2  OTF for OnTheFlyPOS Inc."
        dataAccount.Currency = "USD"
        dataAccount.External = false
        dataAccount.CustomerNumber = "OnTheFlyPOS" // Data get Of Interface of OTF Service
        dataAccount.CreatedDate = Date()
        return dataAccount
    }


    fun createIpsidyBiometricalAccount(
        context: Context,
        dataImage: String,
        accountNumber: String
    ){
        val utils = Utils(context)
        val gson = Gson();
        val service = Services()
        try {

            val api = service.getAccountService(context)

            CoroutineScope(Dispatchers.IO).launch {
                val response = api.createAccountBiometricCredential(
                    accountNumber,
                    getDataModelBioCredential(accountNumber, dataImage, getImageType("JPEG")))

                 if(response.isSuccessful){
                     if (response.code() == 200) {
                        val dataResponse = response.body()!!
                        utils.saveDataResponse(dataResponse)
                        println("You biometrical account has been created successfully")
                    } else if (response.code() === 409) {

                    }
                 }

            }
        } catch (e: Exception) {

        }
    }

}