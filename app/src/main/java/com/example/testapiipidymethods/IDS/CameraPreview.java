package com.example.testapiipidymethods.IDS;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.ipsidy.faceloksdk.FacelokImpl;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "facelok-CAMERA";
    private Camera mCamera = null;
    private SurfaceHolder mHolder;
    public FacelokImpl mFacelok;

     private Path mPath = new Path();
    private Paint mPaint = new Paint();


    public CameraPreview(Context context, Camera cam) {
        super(context);

        mCamera = cam;
        Log.d(TAG, "CameraPreview: Created ... camera is: " + cam);

        mHolder = getHolder();
        mHolder.addCallback(this);

        // used for android versions < 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    private void startPreview() {
        stopPreview();
        try {
            Log.i(TAG, "Attaching camera and starting preview");
            mCamera.setPreviewDisplay(mHolder);

            mFacelok.resetCallback();

            mCamera.startPreview();

        } catch (IOException e) {
            Log.e(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    private void stopPreview() {
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignored, tried to stop a non-existent preview
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null) {
            return;
        }
        Log.i(TAG, "surface Changed!!!  ... " );

        stopPreview();

        // TODO: set size, resize, rotate or reformatting changes here

        startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }





}