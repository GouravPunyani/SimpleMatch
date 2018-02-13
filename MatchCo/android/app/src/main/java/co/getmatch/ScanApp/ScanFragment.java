/*
 Copyright (C) 2013-2017 MatchCo Inc
 All rights reserved.
 This document is proprietary information of MatchCo Inc.
 Do not distribute outside of the company without written authorization.
 Portions of this document may be protected by patent.
 Portions of this document may be covered by patent pending.
 */

/**
 * Created by young.harvill on 2/28/2017.
 * Modified code from Camera2Raw sample
 */

package co.getmatch.ScanApp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.media.AsyncPlayer;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 */
public class ScanFragment extends Fragment
        implements View.OnClickListener,
        FragmentCompat.OnRequestPermissionsResultCallback,
        android.hardware.SensorEventListener, Runnable{

    /**
     * Request code for camera permissions.
     */

    /**
     * Permissions required to take a picture.
     */
    public static final String[] CAMERA_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE
    };

    public static final int REQUEST_CAMERA_PERMISSIONS = 1;
    public static final String FRAGMENT_DIALOG = "dialog";

    ScanState mScanState;
    SwatchView mSwatchView;
    Vibrator mVibrator;

    private SensorManager mSensorManager;
    double ax,ay,az;   // these are the acceleration in x,y and z axis
    int lastHint;
    int currentScan;
    PsSample samples[];
    CviColor displayColors[];
    CviColor previewColor;
    CviColor bestColor;

    private android.os.Handler mHandler;
    static final int gPauseCount = 20;
    static final int gResumeCount = 1;
    int suspend = 0;
    boolean lastHighPassState;
    SwatchSounds mSwatchSounds;




    private synchronized int getSuspend()
    {
        return(suspend);
    }

    private synchronized void decSuspend()
    {
        if (suspend > 0)
            suspend--;
    }

    public synchronized void setPause()
    {
        suspend = gPauseCount;
    }

    public void run()
    {
        int t = getSuspend();
        if (t > 0)
        {
            if (t == gPauseCount)
            {
                pause();
            }
            if (t == gResumeCount)
            {
                resume();
            }
            decSuspend();
        }
        mHandler.postDelayed(this, 100);
    }

    public static ScanFragment newInstance() {
        return new ScanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan_basic, container, false);


    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //view.findViewById(R.id.info).setOnClickListener(this);

        mSwatchView = (SwatchView)view.findViewById(R.id.swatchView);
        mSwatchSounds = new SwatchSounds();
        mScanState = new ScanState(this);
        mScanState.initialize(view);

        Activity activity = getActivity();
        if (activity != null)
        {
            mVibrator = (Vibrator) activity.getSystemService(activity.VIBRATOR_SERVICE);
            mSensorManager=(SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
        }

        if (mSensorManager != null)
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER),
                    mSensorManager.SENSOR_DELAY_NORMAL);

        lastHint = 0;
        samples = PsSample.build(ProcessSample.stMax);
        currentScan = ProcessSample.stWhitepoint;
        if (mScanState != null)
        {
            mScanState.clear(samples[currentScan], currentScan);
            updateHintTimes();
            mScanState.startScan(samples[currentScan]);
            mSwatchView.setProcess(currentScan);
        }
        displayColors = CviColor.build(ScanData.gMaxFoundColors);
        previewColor = new CviColor();
        bestColor = new CviColor();
        mHandler = new android.os.Handler();
        mHandler.postDelayed(this, 100);
        lastHighPassState = false;

    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }

    public void resume()
    {
        lastHint = 0;
        lastHighPassState = false;

        if (mSensorManager != null)
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);

        if (mScanState != null)
        {
            currentScan = ProcessSample.stWhitepoint;
            if (samples[currentScan] != null) {
                mScanState.clear(samples[currentScan], currentScan);
                mSwatchView.setProcess(currentScan);
                mScanState.becameActive();
                updateHintTimes();
                mScanState.startScan(samples[currentScan]);
            }
        }
    }

    public void pause()
    {
        if (mSwatchSounds != null)
        {
            mSwatchSounds.pause();
        }
        if (mSensorManager != null)
            mSensorManager.unregisterListener(this);

        if (mScanState != null)
        {
            mScanState.resignActive();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onPause() {

        pause();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSIONS) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    showMissingPermissionError();
                    return;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {



        }
    }

    public void updateHintTimes()
    {
        if (currentScan >= 0 && currentScan < ProcessSample.stMax)
        {
            for(int i = 0; i < MetricHints.mhCount; i++)
            {
                float hintTime = mSwatchSounds.getHintTime(currentScan, i);
                mScanState.setHintTime(i, hintTime);
            }
        }
    }
    public void didCaptureFeatures()
    {
        Activity activity = getActivity();
        mScanState.endScan();
        mScanState.getSamples(samples[currentScan]);
        currentScan++;

        if (currentScan >= ProcessSample.stMax)
        {
            currentScan = ProcessSample.stWhitepoint;

            // write a file to storage
            String filename = mScanState.writeOutput(samples);

            // NOTE(steve): Since this fragment lives in an activity that is not the
            // main activity, we close this activity once we've successfully scanned.
            // we pass back the filename we created as well.
            Intent resultIntent = new Intent();
            resultIntent.putExtra("SCAN_FILENAME", filename);
            activity.setResult(Activity.RESULT_OK, resultIntent);
            activity.finish();
        }
        if (samples[currentScan] != null)
        {
            mScanState.clear(samples[currentScan], currentScan);
            if (currentScan > ProcessSample.stWhitepoint)
            {
                samples[currentScan].calibrationWhite[0] =
                        samples[ProcessSample.stWhitepoint].calibrationWhite[0];
                samples[currentScan].calibrationWhite[1] =
                        samples[ProcessSample.stWhitepoint].calibrationWhite[1];
                samples[currentScan].calibrationWhite[2] =
                        samples[ProcessSample.stWhitepoint].calibrationWhite[2];
            }
            if (currentScan > ProcessSample.stUnderWrist)
            {
                samples[currentScan].baseTone[0] =
                        samples[ProcessSample.stUnderWrist].baseTone[0];
                samples[currentScan].baseTone[1] =
                        samples[ProcessSample.stUnderWrist].baseTone[1];
                samples[currentScan].baseTone[2] =
                        samples[ProcessSample.stUnderWrist].baseTone[2];
            }
            updateHintTimes();
            mScanState.startScan(samples[currentScan]);
            mSwatchView.setProcess(currentScan);
        }
    }


    public void didCaptureHints(int hint)
    {
        if (lastHint != hint)
        {
            lastHint = hint;
            mSwatchView.setHint(lastHint);
            if (hint == MetricHints.mhCalibrateFail)
            {
                setPause();
            }

            if (mSwatchSounds != null && mScanState != null)
            {
                if (hint >= 0 && hint < MetricHints.mhCount &&
                        (currentScan >= 0 && currentScan < ProcessSample.stMax))
                {
                    mSwatchSounds.playHint(getActivity(), currentScan, hint);
                }
            }
            if (hint == MetricHints.mhTapping)
            {
                mVibrator.vibrate(100);
            }
        }

        // sense trailing edge of highPassState
        boolean highPassState = mScanState.getHighPassState();
        if (lastHighPassState == true && highPassState == false)
        {
            mVibrator.vibrate(10);
        }
        lastHighPassState = highPassState;

        int foundColors = mScanState.getSamples(samples[currentScan]);
        if (foundColors >= 0 && displayColors != null)
        {
            if (foundColors > ScanData.gMaxFoundColors)
                foundColors = ScanData.gMaxFoundColors;
            for(int i = 0; i < foundColors; i++)
            {
                displayColors[i].setWithWhitepoint(samples[currentScan].foundColors[i].sampleColor,
                        samples[currentScan].calibrationWhite, 0.95f);
            }
            mSwatchView.setSwatches(displayColors, foundColors);
        }
        mScanState.getPreviewColor(previewColor, samples[currentScan]);
        mSwatchView.setPreview(previewColor);

        mScanState.getBestColor(bestColor, samples[currentScan]);
        mSwatchView.setBestColor(bestColor);
    }



    /**
     * A dialog that explains about the necessary permissions.
     */
    public static class PermissionConfirmationDialog extends DialogFragment {

        public static ScanFragment.PermissionConfirmationDialog newInstance() {
            return new ScanFragment.PermissionConfirmationDialog();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentCompat.requestPermissions(parent, CAMERA_PERMISSIONS,
                                    REQUEST_CAMERA_PERMISSIONS);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                }
                            })
                    .create();
        }

    }

    /**
     * Requests permissions necessary to use camera and save pictures.
     */
    public void requestCameraPermissions() {
        if (shouldShowRationale()) {
            PermissionConfirmationDialog.newInstance().show(this.getChildFragmentManager(), "dialog");
        } else {
            FragmentCompat.requestPermissions(this, CAMERA_PERMISSIONS, REQUEST_CAMERA_PERMISSIONS);
        }
    }

    /**
     * Tells whether all the necessary permissions are granted to this app.
     *
     * @return True if all the required permissions are granted.
     */
    public boolean hasAllPermissionsGranted() {
        for (String permission : CAMERA_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this.getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets whether you should show UI with rationale for requesting the permissions.
     *
     * @return True if the UI should be shown.
     */
    public boolean shouldShowRationale() {
        for (String permission : CAMERA_PERMISSIONS) {
            if (FragmentCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shows that this app really needs the permission and finishes the app.
     */
    private void showMissingPermissionError() {
        Activity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, R.string.request_permission, Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    public void onAccuracyChanged(android.hardware.Sensor arg0, int arg1) {
    }


    public synchronized void onSensorChanged(android.hardware.SensorEvent event) {
        if (event.sensor.getType()==android.hardware.Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
        }
    }

    public synchronized void getAccleration(double accelVect[])
    {
        if (accelVect != null)
        {
            accelVect[0] = ax;
            accelVect[1] = ay;
            accelVect[2] = az;
        }
    }

    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.finish();
                        }
                    })
                    .create();
        }

    }

}
