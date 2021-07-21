package com.example.testapiipidymethods

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testapiipidymethods.IDS.FacelokCallback
import com.ipsidy.faceloksdk.CameraFacingEnum
import com.ipsidy.faceloksdk.CameraParameters
import com.ipsidy.faceloksdk.FacelokImpl
import com.ipsidy.faceloksdk.LoggerLevel

class CameraPreviewActivity : AppCompatActivity() {

    private val mInterface = FacelokImpl()
    private val MY_PERMISSIONS_REQUEST_CAMERA = 200
    private val TAG = "facelok-sampleapp"
//    private val mCameraView = CameraPreview(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_preview)

        val mCallback = FacelokCallback(mInterface, this)
        mCallback.setLayout(findViewById(R.id.camera_preview)) // Assign to Camera Preview Layout with R.id
        mInterface.setActivity(this)
        mInterface.initialize()
        mInterface.setCallback(mCallback)


        // make sure we have camera permissions
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mInterface.log(LoggerLevel.INFO, "Asking for camera permission")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                this.MY_PERMISSIONS_REQUEST_CAMERA
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val params = CameraParameters(CameraFacingEnum.FRONT, 30, 1024, 768)
        mInterface.start(params)
    }

    override fun onPause() {
        mInterface.stop()
        super.onPause()
    }

    override fun onDestroy() {
        Log.i(this.TAG, "DESTROYING!")
        super.onDestroy()
    }

    override fun onStop() {
        Log.i(this.TAG, "STOPPED!")
        mInterface.stop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            this.MY_PERMISSIONS_REQUEST_CAMERA -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mInterface.log(LoggerLevel.INFO, "Got camera permission, starting camera")
                    recreate()
                } else {
                    mInterface.log(LoggerLevel.ERROR, "Could not get permission for camera")
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}