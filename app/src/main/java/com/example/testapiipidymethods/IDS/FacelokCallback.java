package com.example.testapiipidymethods.IDS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.util.Base64;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import com.example.testapiipidymethods.IDS.Accounts.Account;
import com.ipsidy.faceloksdk.*;

public class FacelokCallback extends com.ipsidy.faceloksdk.FacelokCallback {

    private FacelokImpl mInterface;
    private CameraPreview mCameraView;
    private Context context;
    private FrameLayout layout;
    private RelativeLayout relativeLayoutControls;
    private RelativeLayout relativeLayoutSensorsData;
    private HashMap<LandmarkType, Point> landmarkPoints;


    public void setLayout(FrameLayout layout) {
        this.layout = layout;
    }

    public void setRelativeLayoutControls(RelativeLayout relativeLayoutControls) {
        this.relativeLayoutControls = relativeLayoutControls;
    }

    public void setRelativeLayoutSensorsData(RelativeLayout relativeLayoutSensorsData) {
        this.relativeLayoutSensorsData = relativeLayoutSensorsData;
    }

    public void setLandmarkPoints(HashMap<LandmarkType, Point> landmarkPoints) {
        this.landmarkPoints = landmarkPoints;
    }

    public void setmCameraView(CameraPreview mCameraView) {
        this.mCameraView = mCameraView;
    }

    public FacelokCallback(FacelokImpl face, Context context) {
        this.mInterface = face;
        this.context = context;
    }

    @Override
    public void detectionComplete(Bitmap photo, String imageInfo) {
        mInterface.log(LoggerLevel.INFO, "Got a detection complete event with bmp size of " + (photo.getWidth() * photo.getHeight()));
        mInterface.log(LoggerLevel.INFO, "--CUSTOM-- Got a detection complete event with bmp size of - IMAGE INFO " + imageInfo);


        mInterface.stop();

        IpsidyData ipsidy = new IpsidyData();
        ipsidy.setDataFoto(convertBitmapImageToBase64String(photo), photo, context);
//        Boolean idenAction = actionToVerify(convertBitmapImageToBase64String(photo));

//        mInterface.log(LoggerLevel.ERROR, "Result of the action: " + idenAction);

        CharSequence text = "Hello toast!";

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    private Boolean actionToVerify(String dataImage) {
        Utils utils = new Utils(this.context);
        IpsidyData ipsidyData = new IpsidyData();
        Boolean responseData = false;
        if (utils.existsDataSavedLocalStorage("ACTION_IDENTIFICATION")) {
            String data = utils.getStringDataLocalStorage("ACTION_IDENTIFICATION");

            if (data.equals("CREATE_BIO_ACCOUNT")) {
                responseData = ipsidyData.createIpsidyAccount(this.context);
                if (responseData) {
                    Account account = utils.getDataAccountIpsidy();
                    responseData = ipsidyData.createIpsidyBiometricalAccount(this.context, account.getAccountNumber(), dataImage);
                }
            } else if (data.equals("VERIFY_BIO_ACCOUNT")) {
                if (utils.existsDataAccountIpsidy()) {
                    Account account = utils.getDataAccountIpsidy();
                    responseData = ipsidyData.verifyIdentification(this.context, dataImage, account.getAccountNumber());
                }
            }
        }
        return responseData;
    }

    @Override
    public void validFaceAngle(boolean valid, int angle) {
        if (valid) {
            mInterface.log(LoggerLevel.DEBUG, "face angle change to VALID, angle: " + angle);
        } else {
            mInterface.log(LoggerLevel.DEBUG, "face angle change to INVALID, angle: " + angle);
        }
    }

    @Override
    public void faceDetected() {

        mInterface.log(LoggerLevel.DEBUG, "Face Detected!");
    }

    @Override
    public void faceLost() {

        mInterface.log(LoggerLevel.DEBUG, "Face Lost!");
    }

    @Override
    public void status(boolean failure, String message) {
        mInterface.log(failure ? LoggerLevel.ERROR : LoggerLevel.INFO, message);

    }

    @Override
    public void cameraReady() {
        mInterface.log(LoggerLevel.INFO, "Camera ready");

        // Adding preview needs to be done after the camera is started

        mCameraView = new CameraPreview(context, mInterface.getCamera());

        mCameraView.mFacelok = mInterface;

//        FrameLayout layout = findViewById(R.id.camera_preview);

        layout.addView(mCameraView);


        // TODO DRAW LANDMARKS

//        RelativeLayout relativeLayoutControls = findViewById(R.id.controls_layout);
//        relativeLayoutControls.bringToFront();

//        RelativeLayout relativeLayoutSensorsData = findViewById(R.id.sensors_data_layout);
//        relativeLayoutSensorsData.bringToFront();


    }

    @Override
    public void faceTooSmall() {
        mInterface.log(LoggerLevel.DEBUG, "Face lost because it was too small");
    }

    // NEW
    @Override
    public void imageProcessed(FaceState state) {
        //mInterface.log(LoggerLevel.DEBUG, "--CUSTOM-- Image Landmarks" + state.getLandmarks());
        landmarkPoints = state.getLandmarks();

        // Get Last image
        Image image = mInterface.getLastImage();

        Bitmap img = convertRawImageToBitmap(image.getData(), image.getWidth(), image.getHeight());

        mInterface.log(LoggerLevel.DEBUG, "--CUSTOM-- Image CONVERTED: " + img);

        //mInterface.log(LoggerLevel.DEBUG, "--CUSTOM-- Image: " + image);
        //mCameraView.draw(landmarkPoints)
    }

    @Override
    public void livenessEvent(LivenessEvent event) {
        mInterface.log(LoggerLevel.DEBUG, "--CUSTOM-- Liveness event signaled: " + event);
    }

    @Override
    public void checkingImageQuality(Bitmap previewImage, String imageInfo) {
        mInterface.log(LoggerLevel.DEBUG, "--CUSTOM--  Image Queality: " + imageInfo);
    }

    public Bitmap convertRawImageToBitmap(byte[] nv21Image, int width, int height) {
        YuvImage yuvImage = new YuvImage(nv21Image, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        android.graphics.Rect rect = new android.graphics.Rect(0, 0, width, height);
        yuvImage.compressToJpeg(rect, 100, os);

        byte[] jpegByteArray = os.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.length);

        return bitmap;
    }

    public String convertBitmapImageToBase64String(Bitmap image) {

        if (image != null) {
            /* Get the image as string */
            // Normal
            ByteArrayOutputStream full_stream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, full_stream);

            byte[] full_bytes = full_stream.toByteArray();

            String img_full = Base64.encodeToString(full_bytes, Base64.DEFAULT);

            return img_full;

            // new HTTPWorker(ctx, mHandler, HTTPWorker.WRITE_COMMENT, true).execute(
            // Integer.toString(id), comment, author, img_thumbnail, img_full);
        } else {
            // new HTTPWorker(ctx, mHandler, HTTPWorker.WRITE_COMMENT, true).execute(
            // Integer.toString(id), comment, author, null, null);
        }
        return "";
    }
}
