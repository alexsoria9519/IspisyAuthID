package com.example.testapiipidymethods

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.testapiipidymethods.IDS.AccountsMethodsActivity
import com.example.testapiipidymethods.IDS.AdminMethodsActivity
import com.example.testapiipidymethods.IDS.AuthorizationMethodsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var accounts: TextView
    private lateinit var auth: TextView
    private lateinit var admin: TextView
    private lateinit var cameraPreview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accounts = findViewById(R.id.accounts)
        auth = findViewById(R.id.auth)
        admin = findViewById(R.id.admin)
        cameraPreview = findViewById(R.id.camera_preview_label)


        accounts.setOnClickListener {
            val intent = Intent(this, AccountsMethodsActivity::class.java)
            startActivity(intent)
        }

        auth.setOnClickListener {
            val intent = Intent(this, AuthorizationMethodsActivity::class.java)
            startActivity(intent)
        }

        admin.setOnClickListener {
            val intent = Intent(this, AdminMethodsActivity::class.java)
            startActivity(intent)
        }

        cameraPreview.setOnClickListener {
            val intent = Intent(this, CameraJavaActivity::class.java)
//            val intent = Intent(this, CameraPreviewActivity::class.java)
            startActivity(intent)
        }

        saveLocalStorage(getString(R.string.username_ipsidy), getString(R.string.password_ipsidy))
    }

    private fun saveLocalStorage(username: String?, password: String?) {
        val preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        var editor = preferences.edit()
        editor.putString("USERNAME", username)
        editor.putString("PASSWORD", password)
        editor.commit()
    }


}