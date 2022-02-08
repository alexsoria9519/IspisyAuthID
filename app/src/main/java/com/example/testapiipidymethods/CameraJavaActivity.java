package com.example.testapiipidymethods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.example.testapiipidymethods.IDS.CameraPreview;
import com.example.testapiipidymethods.IDS.FacelokCallback;
import com.ipsidy.faceloksdk.CameraFacingEnum;
import com.ipsidy.faceloksdk.CameraParameters;
import com.ipsidy.faceloksdk.FacelokImpl;
import com.ipsidy.faceloksdk.LandmarkType;
import com.ipsidy.faceloksdk.LivenessEvent;
import com.ipsidy.faceloksdk.LivenessOrder;
import com.ipsidy.faceloksdk.LoggerLevel;

import java.net.URL;
import java.util.HashMap;

public class CameraJavaActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 200;
    private CameraPreview mCameraView;
    private FacelokCallback mCallback;
    private FacelokImpl mInterface;

    private static String TAG = "facelok-sampleapp";

    private HashMap<LandmarkType, Point> landmarkPoints;

    static {
        System.loadLibrary("facelok");
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera_java);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_java);

        // create our callback
        // setup facelok
        mInterface = new FacelokImpl();
        mCallback = new FacelokCallback(mInterface, this);
        mCallback.setLayout(findViewById(R.id.camera_preview));
        mInterface.setActivity(this);

        mInterface.initialize();




//        mInterface.useCustomLiveness(LivenessOrder.INORDER);
//        mInterface.pushLivenessEvent(LivenessEvent.SMILE);
//        mInterface.pushLivenessEvent(LivenessEvent.BLINK);
//        mInterface.pushLivenessEvent(LivenessEvent.SMILE);
//        mInterface.pushLivenessEvent(LivenessEvent.BLINK);

        mInterface.setCallback(mCallback);

        // make sure we have camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mInterface.log(LoggerLevel.INFO, "Asking for camera permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        // API URL
    }

    @Override
    public void onResume() {
        CameraParameters params = new CameraParameters(CameraFacingEnum.FRONT, 30, 1024, 768);
        mInterface.start(params);
        super.onResume();
    }

    @Override
    public void onPause() {
        mInterface.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "DESTROYING!");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "STOPPED!");
        mInterface.stop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mInterface.log(LoggerLevel.INFO, "Got camera permission, starting camera");
                    recreate();
                }
                else {
                    mInterface.log(LoggerLevel.ERROR, "Could not get permission for camera");
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}