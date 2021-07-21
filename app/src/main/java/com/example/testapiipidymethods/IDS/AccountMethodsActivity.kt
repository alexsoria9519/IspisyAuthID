package com.example.testapiipidymethods.IDS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.testapiipidymethods.IDS.Admin.ApiKeyLite
import com.example.testapiipidymethods.Methods.GetApisAdapter
import com.example.testapiipidymethods.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AccountMethodsActivity : AppCompatActivity() {

    private lateinit var accountsView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_methods)

        accountsView = findViewById(R.id.accountsView)
        getApiKeys()

    }

    private fun getApiKeys(){
        val service = Services()
        val call = service.getAdminService(this).getApiKeys()

        call.enqueue(object: Callback<ArrayList<ApiKeyLite>> {
            override fun onResponse(call: Call<ArrayList<ApiKeyLite>>, response: Response<ArrayList<ApiKeyLite>>) {
                if (response.code() == 200) {
                    val dataResponse = response.body()!!
                    var adapter = GetApisAdapter(dataResponse!!)
                    accountsView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ArrayList<ApiKeyLite>>, t: Throwable) {
                Log.e("Error", t.message!!)
            }
        })
    }



}