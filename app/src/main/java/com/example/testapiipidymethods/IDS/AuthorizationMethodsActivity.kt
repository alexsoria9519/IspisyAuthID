package com.example.testapiipidymethods.IDS

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapiipidymethods.R
import com.example.testapiipidymethods.WebServices.IpsidyAuthorizationService
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AuthorizationMethodsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization_methods)
    }

    fun getService(): IpsidyAuthorizationService {
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
            .baseUrl("http://localhost:8081/IDCompleteBackendEngine/Default/AuthorizationServiceRest")
            .client(okhttpclient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(IpsidyAuthorizationService::class.java)
    }

}