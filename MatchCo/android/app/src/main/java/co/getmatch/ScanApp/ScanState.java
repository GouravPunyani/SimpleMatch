package co.getmatch.ScanApp;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 Copyright (C) 2013-2017 MatchCo Inc
 All rights reserved.
 This document is proprietary information of MatchCo Inc.
 Do not distribute outside of the company without written authorization.
 Portions of this document may be protected by patent.
 Portions of this document may be covered by patent pending.
 */

/**
 * Created by young.harvill on 2/27/2017.
 */



public class ScanState {

    /**
     * gWhiteDisplayRef is the luminance reference for color display in sRGB space.
     */
    private static final float gWhiteDisplayRef = 1.0f;

    ScanFragment delegate;
    VideoProcessor videoProcess;
    FrameProcessor mFrameProcessor;
    ScanSettings settings;
    private static final String TAG = "ScanState";
    public static final String    gOutputVersion = "3.0";

    ScanState(ScanFragment aDelegate)
    {
        delegate = aDelegate;
    }
    /**
     * Initializes the scanning class once a view controller has been built
     * need to set delegate to the view controller object first.
     * the view controller must implement the GraeVideoProcessorDelegate methods.
     */
    void initialize(View view){

        mFrameProcessor = new FrameProcessor();
        videoProcess = new VideoProcessor(delegate, mFrameProcessor);
        videoProcess.init(view);
        settings = new ScanSettings(ProcessSettings.getPlatform());

    }
    /**
     * becameActive needs to called by the ScanState's owner AppDelegate when
     * the App Delegates applicationDidBecomeActive handler is called.
     */
    void becameActive() {
        if(videoProcess != null)
        {
            videoProcess.resume();

        }
    }
    /**
     * resignActive needs to called by the ScanState's owner AppDelegate when
     * the App Delegates applicationWillResignActive handler is called.
     */
    void resignActive() {
        if(videoProcess != null)
        {
            videoProcess.pause();
        }
    }
    /**
     * cleanup is called by a view controller to release itself as a
     * GraeVideoProcessorDelegate.
     */
    void cleanup() {
    }

/**
 * getSamples returns the data found during the scan process.
 */
    int getSamples(PsSample destSample)
    {
        int result = 0;

        if (mFrameProcessor != null && destSample != null)
        {
            if (destSample.foundColors != null) {
                if (destSample.scanType >= ProcessSample.stWhitepoint &&
                        destSample.scanType < ProcessSample.stMax) {
                    result = mFrameProcessor.updateSample(destSample);
                }
            }
        }
        return(result);
    }
/**
 * startScan begins set of scanning steps.
 */
    void startScan(PsSample destSample)
    {
        if (mFrameProcessor != null) {
            mFrameProcessor.startScan();
        }
        getSamples(destSample);
    }

    /**
     * startScan begins set of scanning steps.
     */
    void clear(PsSample destSample, int scanState)
    {
        if (destSample != null && settings != null)
        {
            destSample.clear(scanState, settings.whitePoint);
        }
    }

/**
 * endScan is called to end the current scan.
 */
    void endScan()
    {

    }
/**
 * setHintTime is called set the time needed to speak the current hint.
 * In some cases it may be clipped to preserve the flow of the state
 * machine.
 */
    void setHintTime( int hint, float hintTime){
        if(mFrameProcessor != null)
        {
            if (mFrameProcessor.objectDetect != null)
            {
                mFrameProcessor.objectDetect.setHintTime(hint, hintTime);
            }
        }
    }

    boolean getHighPassState()
    {
        boolean result = false;
        if(mFrameProcessor != null)
        {
            if (mFrameProcessor.objectDetect != null)
            {
                result = mFrameProcessor.objectDetect.highPassGreater;
            }
        }
        return(result);
    }
/**
 * getProductColor calculates a productColor from the processSamples over a sampleCount.
 */
    void getProductColor ( float productColor[], int sampleCount,  PsSample samples[])
    {

    }

/**
 * getScanOutput calculates the updated scanning output for upload to the server.
 */
    void getScanOutput2(ScanOutput aScanOutput, int sampleCount, PsSample processSamples[])
    {
        if (processSamples.length == ProcessSample.stMax)
        {

        }
    }

/**
 * getProductColorFromOutput2 calculates a productColor from the ScanOutput.
 */
    void getProductColorFromOutput2(CviColor productColor, int sampleCount, PsSample processSamples[])
    {

    }

/**
 * getFramesPerSecond returns the average frames per second being processed by the scanning code.
 */
    float getFramesPerSecond(){
        return(0);
    }
/**
 * setCalibration sets the frame processor's calibration state.
 */
    void setCalibration ()
    {

    }

/**
 * setPause pauses the scan if set to true, else it resumes the scan.
 */
    void setPause (boolean state)
    {

    }
/**
 * validWhite returns true if the sample is within the range of a white sheet of paper.
 */
    boolean validWhite ( PsSample processSample){
        boolean result = false;
        float labWhite[] = new float[3];
        CvColor.rgbToLABWhitepoint(labWhite,
                processSample.foundColors[1].sampleColor,
                processSample.calibrationWhite, ObjectDetect.gWhiteRef);

        float aLabExposure = labWhite[0];
        if (aLabExposure >= settings.minWhiteLum && aLabExposure < settings.maxWhiteLum)
        {
            float aColorDist =
                    (float)java.lang.Math.sqrt((labWhite[1] * labWhite[1]) +
                            (labWhite[2] * labWhite[2]));
            if (aColorDist < settings.maxLABWhiteBalanceDiff)
            {
                result = true;
            }
        }
        return(result);
    }
/**
 * validSkinTone returns true if the sample is within the range of a skin tone
 */
    boolean validSkinTone( PsSample processSample)
    {
        boolean result = false;
        float lum0 = 0;
        float whitePointLuminance = CvColor.luminance(processSample.calibrationWhite);
        switch(processSample.scanType)
        {
            case ProcessSample.stUnderWrist:
                lum0 = processSample.foundColors[1].averageLuminance;
                lum0 = CvColor.applyWhiteLuminance(lum0, whitePointLuminance, ObjectDetect.gWhiteRef);
                result = lum0 < ObjectDetect.maxSkinLuminance && lum0 > ObjectDetect.minSkinLuminance;
                break;

            case ProcessSample.stForehead:
            case ProcessSample.stRightCheek:
            case ProcessSample.stLeftCheek:
            case ProcessSample.stRightJawline:
            case ProcessSample.stLeftJawline:

                lum0 = processSample.foundColors[1].averageLuminance;
                lum0 = CvColor.applyWhiteLuminance(lum0, whitePointLuminance, ObjectDetect.gWhiteRef);
                if (processSample.baseTone != null)
                {
                    float dist = CvColor.colorDiff(processSample.baseTone,
                            processSample.foundColors[1].sampleColor);
                    result = dist < ObjectDetect.maxBaseToneDiff;
                }
                result = result && lum0 < ObjectDetect.maxSkinLuminance &&
                        lum0 > ObjectDetect.minSkinLuminance;
                break;
        }
        return(result);
    }

/**
 * getPlatform returns the phone platform we are running.
 */
    int getPlatform()
    {
        return(ProcessSettings.getPlatform());
    }

/**
 * getDisplayColor transforms processSamples->foundColors[sampleIndex] into a calibrated displayColor.
 */
    void getDisplayColor(CviColor displayColor, int sampleIndex, PsSample processSamples)
    {
        float sampleColor[] = {0, 0, 0, 0};
        CvColor.applyWhitePoint(sampleColor,
                processSamples.foundColors[sampleIndex].sampleColor,
                processSamples.calibrationWhite, gWhiteDisplayRef);

        displayColor.r = sampleColor[0];
        displayColor.g = sampleColor[1];
        displayColor.b = sampleColor[2];
        displayColor.a = 1.0f;
    }

    /**
     * getPreviewColor
     */
    void getPreviewColor(CviColor displayColor, PsSample processSamples)
    {
        if (mFrameProcessor != null)
        {
            float sampleColor[] = {0, 0, 0, 0};
            mFrameProcessor.getPreviewColor(sampleColor);
            CvColor.applyWhitePoint(sampleColor,
                    sampleColor,
                    processSamples.calibrationWhite, gWhiteDisplayRef);

            displayColor.r = sampleColor[0];
            displayColor.g = sampleColor[1];
            displayColor.b = sampleColor[2];
            displayColor.a = 1.0f;
        }
    }
    /**
     * getBestColor
     */
    void getBestColor(CviColor displayColor, PsSample processSamples)
    {
        if (mFrameProcessor != null)
        {
            float sampleColor[] = {0, 0, 0, 0};
            mFrameProcessor.getBestColor(sampleColor);
            CvColor.applyWhitePoint(sampleColor,
                    sampleColor,
                    processSamples.calibrationWhite, gWhiteDisplayRef);

            displayColor.r = sampleColor[0];
            displayColor.g = sampleColor[1];
            displayColor.b = sampleColor[2];
            displayColor.a = 1.0f;
        }
    }
    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = android.os.Environment.getExternalStorageState();
        if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Cleanup the given {@link OutputStream}.
     *
     * @param outputStream the stream to close.
     */
    private static void closeOutput(OutputStream outputStream) {
        if (null != outputStream) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String buildColor(String outputString, float sampleColor[], float whitePoint[], int platform)
    {
        float labColor[] = new float[3];
        float rgbColor[] = new float[3];

        rgbColor[0] = sampleColor[0];
        rgbColor[1] = sampleColor[1];
        rgbColor[2] = sampleColor[2];

        if (rgbColor[0] < 0.0001f)
        {
            rgbColor[0] = 0;
        }
        if (rgbColor[1] < 0.0001f)
        {
            rgbColor[1] = 0;
        }
        if (rgbColor[2] < 0.0001f)
        {
            rgbColor[2] = 0;
        }

        outputString = String.valueOf(rgbColor[0]);
        outputString = outputString.concat("\t");
        outputString = outputString.concat(String.valueOf(rgbColor[1]));
        outputString = outputString.concat("\t");
        outputString = outputString.concat(String.valueOf(rgbColor[2]));
        outputString = outputString.concat("\t");

        RgbToLabCal.getLabColorRGB(labColor, rgbColor, whitePoint, platform);

        outputString = outputString.concat(String.valueOf(labColor[0]));
        outputString = outputString.concat("\t");
        outputString = outputString.concat(String.valueOf(labColor[1]));
        outputString = outputString.concat("\t");
        outputString = outputString.concat(String.valueOf(labColor[2]));
        return(outputString);
    }

    public String buildScanData(String outputString, ScanData data, float whitePoint[], int platform)
    {

        outputString = buildColor(outputString, data.sampleColor, whitePoint, platform);
        outputString = outputString.concat("\t");

        outputString = outputString.concat(String.valueOf(data.sampleLuminance));
        outputString = outputString.concat("\t");
        outputString = outputString.concat(String.valueOf(data.highlightLuminance));
        outputString = outputString.concat("\r");
        return(outputString);
    }

    public String buildOutput(String outputString, PsSample samples[])
    {
        if (null != outputString)
        {
            if (samples.length >= ProcessSample.stUnderWrist )
            {
                float fullWhite[] = {1, 1, 1};
                String lineString = new String();

                outputString = "sample_Red\tsample_Green\tsample_Blue\tsample_L\tsample_A\tsample_B\tsampleLum\tspecLum\r";
                lineString = buildColor(lineString, samples[ProcessSample.stWhitepoint].calibrationWhite, fullWhite, 0);
                lineString = lineString.concat("\r");
                outputString = outputString.concat(lineString);
                outputString = outputString.concat(lineString);

                for(int j = 0; j < samples[ProcessSample.stWhitepoint].foundColors.length; j++)
                {
                    lineString = buildScanData(lineString, samples[ProcessSample.stWhitepoint].foundColors[j], fullWhite, 0);
                    outputString = outputString.concat(lineString);
                }
                for(int i = ProcessSample.stUnderWrist; i < samples.length; i++)
                {
                    for(int j = 0; j < samples[i].foundColors.length; j++)
                    {
                        lineString = buildScanData(lineString, samples[i].foundColors[j], samples[ProcessSample.stWhitepoint].calibrationWhite, getPlatform());
                        outputString = outputString.concat(lineString);
                    }
                }
                outputString = outputString.concat(android.os.Build.MODEL);
                outputString = outputString.concat("\t");
                outputString = outputString.concat(gOutputVersion);
                outputString = outputString.concat("\r");
            }
        }
        return(outputString);
    }

    String writeOutput(PsSample samples[])
    {
        String currentDateTime = generateTimestamp();

        File scanFolder = new File(Environment.getExternalStorageDirectory() + "/Scans");
        boolean isPresent = true;
        if (!scanFolder.exists()) {
            isPresent = scanFolder.mkdir();
        }

        if (isPresent) {
            File outputFile = new File(scanFolder.getAbsolutePath(),"ScanOutput_" + currentDateTime + ".txt");

            if (outputFile != null) {
                String outputString = new String();
                outputString = buildOutput(outputString, samples);
                FileOutputStream output = null;
                try{
                    output = new FileOutputStream(outputFile);
                    output.write(outputString.getBytes());
                    // NOTE(steve): return the newly created filename.
                    return outputFile.getName();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeOutput(output);
                }
            }
            else
            {
                android.util.Log.e(TAG, "Scan File not created");
            }
        }
        else
        {
            android.util.Log.e(TAG, "Scan directory not created");
        }
        return null;
    }

    /**
     * Generate a string containing a formatted timestamp with the current date and time.
     *
     * @return a {@link String} representing a time.
     */
    private static String generateTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.US);
        return sdf.format(new Date());
    }
}
