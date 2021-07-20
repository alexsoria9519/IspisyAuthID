package com.example.testapiipidymethods.IDS

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testapiipidymethods.R
import com.example.testapiipidymethods.WebServices.IpsidyAccountServices
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.testapiipidymethods.IDS.Authorization.CostumerInfo
import com.google.gson.Gson
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Response

class AccountsMethodsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts_methods)
        login()
    }

    private fun getService(): IpsidyAccountServices {
            val preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
            val username = preferences.getString("USERNAME", "")
            val password = preferences.getString("PASSWORD", "")

            val okhttpclient =  OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(username!!,password!!))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://idlok.ipsidy.net/IDCompleteBackendEngine/Default/AdministrationServiceRest/")
                .client(okhttpclient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return  retrofit.create(IpsidyAccountServices::class.java)
    }

    private fun login(){
        val utils = Utils(this)
        val gson = Gson()
        if(!utils.existDataLogin()){
            val call = getService().login()
            call.enqueue(object : Callback<CostumerInfo> {
                override fun onResponse(call: Call<CostumerInfo>, response: Response<CostumerInfo>) {
                    if (response.code() == 200) {
                        val dataResponse = response.body()!!
                        utils.saveLocalStorage(gson.toJson(dataResponse))
                    }
                }
                override fun onFailure(call: Call<CostumerInfo>, t: Throwable) {
                    Log.e("Error", t.message!!)
                }
            })
        }
    }
}