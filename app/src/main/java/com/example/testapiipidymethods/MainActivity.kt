package com.example.testapiipidymethods

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.testapiipidymethods.IDS.AccountsMethodsActivity
import com.example.testapiipidymethods.IDS.Actions.CreateAccountActivity
import com.example.testapiipidymethods.IDS.Actions.VerifyActivity
import com.example.testapiipidymethods.IDS.AdminMethodsActivity
import com.example.testapiipidymethods.IDS.AuthorizationMethodsActivity
import com.example.testapiipidymethods.IDS.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var accounts: TextView
    private lateinit var auth: TextView
    private lateinit var admin: TextView
    private lateinit var cameraPreview: TextView
    private lateinit var createAccountButton: Button
    private lateinit var verifyIdentity :Button
    private lateinit var deleteStorage :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accounts = findViewById(R.id.accounts)
        auth = findViewById(R.id.auth)
        admin = findViewById(R.id.admin)
        cameraPreview = findViewById(R.id.camera_preview_label)
        createAccountButton = findViewById(R.id.create_account_button)
        verifyIdentity = findViewById(R.id.verify_identify_button)
        deleteStorage = findViewById(R.id.delete_data_storage)


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

        createAccountButton.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        verifyIdentity.setOnClickListener {
            val intent = Intent(this, VerifyActivity::class.java)
            startActivity(intent)
        }

        deleteStorage.setOnClickListener {
            val utils = Utils(this)
            utils.deleteDataStorage("DATA_ACCOUNT");
            utils.deleteDataStorage("DATA_ACCOUNT_IPSIDY");
        }



        saveLocalStorage(getString(R.string.username_ipsidy), getString(R.string.password_ipsidy))

        isStoragePermissionGranted()
    }

    private fun saveLocalStorage(username: String?, password: String?) {
        val preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        var editor = preferences.edit()
        editor.putString("USERNAME", username)
        editor.putString("PASSWORD", password)
        editor.commit()
    }

    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                === PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("Data Permission", "Permission is granted")
                true
            } else {
                Log.v("Data Permission", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("DAta", "Permission is granted")
            true
        }
    }


}