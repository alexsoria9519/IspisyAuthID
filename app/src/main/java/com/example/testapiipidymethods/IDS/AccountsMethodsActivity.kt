package com.example.testapiipidymethods.IDS

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapiipidymethods.IDS.Admin.ApiKeyLite
import com.example.testapiipidymethods.R
import com.example.testapiipidymethods.WebServices.IpsidyAccountServices
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.testapiipidymethods.IDS.Authorization.CostumerInfo
import com.example.testapiipidymethods.Methods.GetApisAdapter
import com.google.gson.Gson
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Response
import java.util.*

class AccountsMethodsActivity : AppCompatActivity() {

    private lateinit var getAPiKeysTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts_methods)

        getAPiKeysTv = findViewById(R.id.getApiKeysMethod)
        login()

        getAPiKeysTv.setOnClickListener {
            val intent = Intent(this, AccountMethodsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login(){
        val utils = Utils(this)
        val service = Services()
        val gson = Gson()
        if(!utils.existDataAccount()){
            val call = service.getAccountService(this).login()
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