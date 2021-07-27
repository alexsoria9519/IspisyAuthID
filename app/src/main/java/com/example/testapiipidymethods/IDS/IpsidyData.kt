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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class IpsidyData() {


    fun getDataFoto(data: String, bitImage: Bitmap, context: Context) {
        val util = Utils(context)
        Log.e("Data Desde Clase Kotlin", data)
        util.saveDataPhoto(data) //Save Local Storage
        saveDataToDevice(bitImage) // Save Image in the Storage
        val dataImage = convertBitmapImageToBase64String(bitImage)
        validateAccountIpsidy(context, util, dataImage!!)
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


    private fun validateAccountIpsidy(context: Context, utils: Utils, dataImage: String): String {
        if (utils.existsDataAccountIpsidy()) {
            val dataAccount: Account = utils.getDataAccountIpsidy()
//            Log.e("Data Account Ipsidy", dataAccount.toString())

            if (utils.existsDataBimetricalAccount()) {
//                Log.e("Verification", "The next step for the verification data")
                verifyIdentification(context, dataImage, dataAccount.AccountNumber!!)
            } else {
                createIpsidyBiometricalAccount(context, dataAccount.AccountNumber!!, dataImage)
            }
        } else {
            createIpsidyAccount(context)
        }
        return ""
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

    private fun createIpsidyAccount(context: Context): Boolean {
        val service = Services()
        val utils = Utils(context)
        var responseCreation = false

        try {
            val call = service.getAccountService(context).createAccount(getDataModelAccount())

//            Log.e("Data to Send ", gson.toJson(getDataModelAccount()))

            call.enqueue(object : Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.code() == 200) {
                        val dataResponse = response.body()!!
                        utils.saveDataResponse(dataResponse)
                        responseCreation = true
                    } else if (response.code() === 409) {
                        Log.e("Error Response ", response.raw().networkResponse().message())
                        Log.e("Error Response ", response.raw().networkResponse().toString())
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                    Log.e("Error", t.message!!)
                }
            })
        } catch (e: Exception) {

        }
        return responseCreation!!
    }

    private fun getDataModelAccount(): Account {
        val dataAccount = Account()
        dataAccount.AccountNumber = "TestAccount6-OTF" // Compose for the username of OTF Account
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
    ): Boolean {
        val service = Services()
        val utils = Utils(context)
        var responseCreation: Boolean? = false


        val call = service.getAccountService(context).createAccountBiometricCredential(
            accountNumber,
            getDataModelBioCredential(accountNumber, dataImage, getImageType("JPEG"))
        );

        try {
            call.enqueue(object : Callback<BiometricCredential> {
                override fun onResponse(
                    call: Call<BiometricCredential>,
                    response: Response<BiometricCredential>
                ) {
                    if (response.code() == 200) {
                        val dataResponse = response.body()!!
                        utils.saveDataResponse(dataResponse)
                        responseCreation = true
                    } else if (response.code() === 409) {
                        Log.e("Error Response ", response.raw().networkResponse().message())
                        Log.e("Error Response ", response.raw().networkResponse().toString())
                    }
                }

                override fun onFailure(call: Call<BiometricCredential>, t: Throwable) {
                    Log.e("Error", t.message!!)
                }
            })
        } catch (e: Exception) {

        }

        return responseCreation!!
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

    private fun getImageType(extension: String): Int {
        var number = 0;
        when (extension) {
            "JPEG" -> number = 1
            "BMP" -> number = 2
            "GIF" -> number = 3
            "PNG" -> number = 4
            "TIFF" -> number = 5
            "JPEG2K" -> number = 6
            "TEMPLATE" -> 100
            else -> number = 0
        }
        return number
    }

    private fun verifyIdentification(
        context: Context,
        dataImage: String,
        accountNumber: String
    ): Boolean {
        val service = Services()
        val gson = Gson()
        var responseVerification = false

        try {
            val call = service.getAccountService(context).verifyAccountBiometricCredential2(
                accountNumber,
                getDataModelBioCredential(accountNumber, dataImage, getImageType("JPEG"))
            )


            call.enqueue(object : Callback<BiometricVerificationResult> {
                override fun onResponse(
                    call: Call<BiometricVerificationResult>,
                    response: Response<BiometricVerificationResult>
                ) {
                    if (response.code() == 200) {
                        val dataResponse = response.body()!!
                        verify = dataResponse
                        Log.e("Data Response Match", gson.toJson(dataResponse))
                        analyzeDataVerification(dataResponse, context)
                        responseVerification = true
                    } else if (response.code() === 409) {
                        Log.e("Error Response ", response.raw().networkResponse().message())
                        Log.e("Error Response ", response.raw().networkResponse().toString())
                    } else if (response.code() === 404) {
                        Log.e("Error Response", "Not Found Request")
                    }
                }

                override fun onFailure(call: Call<BiometricVerificationResult>, t: Throwable) {
                    Log.e("Error", t.message!!)
                }
            })
        } catch (e: Exception) {

        }
        return responseVerification!!
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


}