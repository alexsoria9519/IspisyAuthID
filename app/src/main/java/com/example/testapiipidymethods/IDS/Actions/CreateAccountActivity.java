package com.example.testapiipidymethods.IDS.Actions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.testapiipidymethods.IDS.CameraPreview;
import com.example.testapiipidymethods.IDS.FacelokCallback;
import com.example.testapiipidymethods.R;
import com.ipsidy.faceloksdk.CameraFacingEnum;
import com.ipsidy.faceloksdk.CameraParameters;
import com.ipsidy.faceloksdk.FacelokImpl;
import com.ipsidy.faceloksdk.LoggerLevel;

public class CreateAccountActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 200;
    private CameraPreview mCameraView;
    private FacelokCallback mCallback;
    private FacelokImpl mInterface;
    private static String TAG = "facelok-sampleapp";
    private Button confirmCreateAccount;
    private Button tryAgain_Account;
    private Button closePopup;

    private LayoutInflater layoutInflater;
    private View popupView;
    private PopupWindow popupWindow;

    static {
        System.loadLibrary("facelok");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

         //create our callback
//        relativeLayout = findViewById(R.id.relative_create_account);
//        relativeLayout.bringToFront();
//
        confirmCreateAccount = findViewById(R.id.confirm_create_account);



//        confirmCreateAccount.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//            public void onClick(View arg0) {
//                layoutInflater =(LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//                popupView = layoutInflater.inflate(R.layout.popup_identity_created, null);
//                popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT,
//                        RadioGroup.LayoutParams.WRAP_CONTENT);
//
//                closePopup = (Button)popupView.findViewById(R.id.close_created_popup);
//                closePopup.setOnClickListener(new Button.OnClickListener(){
//
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }});
//
//                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//
//
//            }});





//        tryAgain_Account = findViewById(R.id.try_again_account);
//
//        confirmCreateAccount.bringToFront();
//        tryAgain_Account.bringToFront();


        // setup facelok
        mInterface = new FacelokImpl();

        mCallback = new FacelokCallback(mInterface, this);
        mCallback.setLayout(findViewById(R.id.camera_preview_create_account));
        mInterface.setActivity(this);

        mInterface.initialize();
        mInterface.setCallback(mCallback);

        // make sure we have camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mInterface.log(LoggerLevel.INFO, "Asking for camera permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
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