package com.example.testapiipidymethods.IDS

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapiipidymethods.R
import com.example.testapiipidymethods.WebServices.IpsidyAccountServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AccountsMethodsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts_methods)
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
            .baseUrl("https://idlok.ipsidy.net/IDCompleteBackendEngine/Default/AdministrationServiceRest")
            .client(okhttpclient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(IpsidyAccountServices::class.java)
    }
}