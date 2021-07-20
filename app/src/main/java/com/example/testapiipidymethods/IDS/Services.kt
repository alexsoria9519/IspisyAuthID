package com.example.testapiipidymethods.IDS

import android.content.Context
import com.example.testapiipidymethods.WebServices.IpsidyAccountServices
import com.example.testapiipidymethods.WebServices.IpsidyAdminServices
import com.example.testapiipidymethods.WebServices.IpsidyAuthorizationService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Services {

    fun getAccountService(context: Context): IpsidyAccountServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://idlok.ipsidy.net/IDCompleteBackendEngine/Default/AdministrationServiceRest/")
            .client(interceptor(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(IpsidyAccountServices::class.java)
    }


    private fun interceptor(context: Context): OkHttpClient{
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val username = preferences.getString("USERNAME", "")
        val password = preferences.getString("PASSWORD", "")

        val okhttpclient =  OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(username!!,password!!))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        return okhttpclient
    }

    fun getAdminService(context: Context): IpsidyAdminServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://idlok.ipsidy.net/IDCompleteBackendEngine/Default/AdministrationServiceRest/")
            .client(interceptor(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(IpsidyAdminServices::class.java)
    }

    fun getService(context: Context): IpsidyAuthorizationService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8081/IDCompleteBackendEngine/Default/AuthorizationServiceRest")
            .client(interceptor(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(IpsidyAuthorizationService::class.java)
    }

}