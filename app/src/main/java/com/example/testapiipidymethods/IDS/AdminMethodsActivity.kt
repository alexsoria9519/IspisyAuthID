package com.example.testapiipidymethods.IDS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapiipidymethods.R
import com.example.testapiipidymethods.WebServices.IpsidyAdminServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminMethodsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_methods)
    }

    fun getService(): IpsidyAdminServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8081/IDCompleteBackendEngine/Default/AdministrationServiceRest")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(IpsidyAdminServices::class.java)
    }
}