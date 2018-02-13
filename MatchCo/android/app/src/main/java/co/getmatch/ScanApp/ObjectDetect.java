package co.getmatch.ScanApp;

/*
 Copyright (C) 2013-2017 MatchCo Inc
 All rights reserved.
 This document is proprietary information of MatchCo Inc.
 Do not distribute outside of the company without written authorization.
 Portions of this document may be protected by patent.
 Portions of this document may be covered by patent pending.
 */
/**
 * Created by young.harvill on 2/23/2017.
 */

public class ObjectDetect {



    public static final int    sdsCalibrationFail = 0;
    public static final int    sdsNeedsCalibration = 1;
    public static final int    sdsCalibrateComplete = 2;
    public static final int    sdsInit = 3;
    public static final int    sdsPlaceOver = 4;
    public static final int    sdsTapAgain = 5;
    public static final int    sdsStartTapping = 6;
    public static final int    sdsTapping = 7;
    public static final int    sdsEndTapping = 8;
    public static final int    sdsTapFail = 9;
    public static final int    sdsComplete = 10;
    public static final int    sdsSuccess = 11;



    /**
     * dimLum must be less than MIN_LUMINANCE_SCALAR *  brightLum
     * to start tap processing
     */
    private static final float MIN_WHITE_LUM_TAP = 0.001f;

    /**
     * dimLum must be less than MIN_LUMINANCE_SCALAR *  brightLum
     * to start tap processing
     */
    private static final float MIN_LUMINANCE_SCALAR = 0.75f;

    /**
     * SAMPLE_WIDTH is the width of scanSample in pixels
     */
    private static final int SAMPLE_WIDTH = 80;

    /**
     * SAMPLE_HEIGHT is the height of scanSample in pixels
     */
    private static final int SAMPLE_HEIGHT = 110;


    /**
     * whiteRef is the luminance reference for white paper in sRGB space.
     */
    public static final float gWhiteRef = 0.95f;


    static final float whiteCalibrationFloor = 0.70f;
    static final float maxSkinLuminance = 0.85f; // NOTE(steve): up this to 1.0f if your device is having a hard time in development
    static final float minSkinLuminance = 0.005f;
    static final float maxSkinVariance = 0.3f;
    static final float minMotionChange = 0.01f;
    static final float minCalibrationFlatness = 0.998f;
    static final float maxFaceFlatness = 0.60f;
    static final float minPaperFlatness = 0.98f;
    static final float maxBaseToneDiff = 0.3f;

    int sdsState;
    int scanType;

    float brightLum;
    float dimLum;
    float closeLuminance;


    int activeColorIndex;
    int foundColorIndex;
    int frameCount;
    int hints;
    int nTrys;

    ScanSample scanSample;
    HistoSample histoSample;
    ScanData foundColors[];
    ScanSettings settings;
    ScanData currentData;
    ScanData bestColor;
    float baseTone[];
    float bestLAB[];
    float calibrationWhite[];
    float whitePointLuminance;
    double gravity[];
    double averageGravity[];

    float minHintTime[];
    float maxHintTime[];
    float hintTime[];
    double lastTime;
    double currentTime;
    double startTime;
    double frameTime;
    double averageFrameTime;
    float highPassBright;
    float lowPassBright;
    boolean highPassGreater;

    /**
     * Constructor for ObjectDetect
     */
    ObjectDetect()
    {
        sdsState = sdsInit;
        scanType = ProcessSample.stInitScan;
        settings = new ScanSettings(ProcessSettings.getPlatform());
        histoSample = new HistoSample(settings);
        foundColors = ScanData.build(ScanData.gMaxSenseColors);

        scanSample = new ScanSample(settings.sampleWidth, settings.sampleHeight);
        currentData = new ScanData();
        bestColor = new ScanData();
        bestLAB = new float[4];
        calibrationWhite = new float[3];
        minHintTime = new float[MetricHints.mhCount];
        maxHintTime = new float[MetricHints.mhCount];
        gravity = new double[3];
        averageGravity = new double[3];
        baseTone = new float[3];

        setMinHintTimes();
        setMaxHintTimes();
        hintTime = minHintTime.clone();
        highPassBright = 0;
        lowPassBright = 0;
        highPassGreater = false;

        init();

    }

    void init()
    {
        sdsState = sdsInit;
        scanType = ProcessSample.stInitScan;

        brightLum = 0.0f;
        dimLum = 1.0f;
        activeColorIndex = 0;
        foundColorIndex = 0;
        closeLuminance = 0;
        frameCount = 0;
        nTrys= 0;
        whitePointLuminance = 1.0f;
        histoSample.clear();
        ScanData.clear(foundColors);
        currentData.clear();
        bestColor.clear();
        bestLAB[0] = 0;
        bestLAB[1] = 0;
        bestLAB[2] = 0;
        bestLAB[3] = 0;

        calibrationWhite[0] = 1.0f;
        calibrationWhite[1] = 1.0f;
        calibrationWhite[2] = 1.0f;

        baseTone[0] = 1.0f;
        baseTone[1] = 1.0f;
        baseTone[2] = 1.0f;

        gravity[0] = 0;
        gravity[1] = 0;
        gravity[2] = 0;

        averageGravity[0] = 0;
        averageGravity[1] = 0;
        averageGravity[2] = 0;


        currentTime =  (double)android.os.SystemClock.elapsedRealtime() * 1.0/1000.0;
        lastTime = currentTime;
        frameTime = 0.0;
        averageFrameTime = 0.0;
        hints = MetricHints.mhDetectNothing;

    }

    void setMinHintTimes()
    {
        minHintTime[MetricHints.mhDetectNothing] = 0.1f;
        minHintTime[MetricHints.mhNoHint] = 1.0f;
        minHintTime[MetricHints.mhNeedsCalibrate] = 2.0f;
        minHintTime[MetricHints.mhFail] = 1.0f;
        minHintTime[MetricHints.mhCalibratePlacePhoneFlatOnPaper] = 3.0f;
        minHintTime[MetricHints.mhCalibrateComplete] = 2.0f;
        minHintTime[MetricHints.mhPlaceOverSurface] = 2.0f;
        minHintTime[MetricHints.mhStartTapping] = 1.5f;
        minHintTime[MetricHints.mhTapping] = 10.0f;
        minHintTime[MetricHints.mhKeepTapping] = 0.5f;
        minHintTime[MetricHints.mhEndTapping] = 1.5f;
        minHintTime[MetricHints.mhCalibrateFail] = 2.0f;
        minHintTime[MetricHints.mhTapFail] = 2.0f;
        minHintTime[MetricHints.mhFlatterToCalibrate] = 2.0f;
        minHintTime[MetricHints.mhFlatterToScanPaper] = 2.0f;
        minHintTime[MetricHints.mhFlatterToCheek] = 2.0f;
        minHintTime[MetricHints.mhFlatterToJawline] = 2.0f;
        minHintTime[MetricHints.mhTapSuccess] = 1.0f;
        minHintTime[MetricHints.mhSteady] = 1.0f;
        minHintTime[MetricHints.mhPreview] = 2.0f;
        minHintTime[MetricHints.mhProcessing] = 2.0f;
        minHintTime[MetricHints.mhSuccess] = 10;
    }

    void setMaxHintTimes()
    {
        maxHintTime[MetricHints.mhDetectNothing] = 0.1f;
        maxHintTime[MetricHints.mhNoHint] = 1.0f;
        maxHintTime[MetricHints.mhNeedsCalibrate] = 15.0f;
        maxHintTime[MetricHints.mhFail] = 15.0f;
        maxHintTime[MetricHints.mhCalibratePlacePhoneFlatOnPaper] = 15.0f;
        maxHintTime[MetricHints.mhCalibrateComplete] = 15.0f;
        maxHintTime[MetricHints.mhPlaceOverSurface] = 15;
        maxHintTime[MetricHints.mhStartTapping] = 15.0f;
        maxHintTime[MetricHints.mhTapping] = 10.0f;
        maxHintTime[MetricHints.mhKeepTapping] = 1.0f;
        maxHintTime[MetricHints.mhEndTapping] = 3.0f;
        maxHintTime[MetricHints.mhCalibrateFail] = 10.0f;
        maxHintTime[MetricHints.mhTapFail] = 10.0f;
        maxHintTime[MetricHints.mhFlatterToCalibrate] = 10.0f;
        maxHintTime[MetricHints.mhFlatterToScanPaper] = 10.0f;
        maxHintTime[MetricHints.mhFlatterToCheek] = 10.0f;
        maxHintTime[MetricHints.mhFlatterToJawline] = 10.0f;
        maxHintTime[MetricHints.mhTapSuccess] = 10.0f;
        maxHintTime[MetricHints.mhSteady] = 4.0f;
        maxHintTime[MetricHints.mhPreview] = 5.0f;
        maxHintTime[MetricHints.mhProcessing] = 5.0f;
        maxHintTime[MetricHints.mhSuccess] = 10.0f;
    }

    void setHintTime(int hint, float time)
    {
        if (settings.calibrateMode == false && hint >= 0 && hint < MetricHints.mhCount)
        {
            time = java.lang.Math.max(time, minHintTime[hint]);
            time = java.lang.Math.min(time, maxHintTime[hint]);
            hintTime[hint] = time;
        }
    }

    /**
     * Updates a frame of the entire state model.
     * @returns true when success state is reached.
     */
    boolean update(android.graphics.Bitmap bitmap){

        boolean aResult = false;

        currentTime =  (double)android.os.SystemClock.elapsedRealtime() * 1.0/1000.0;
        frameTime = currentTime-lastTime;
        averageFrameTime = averageFrameTime*0.9 + frameTime*0.1;
        lastTime = currentTime;

        updateMotion();
        if (sdsState <= sdsSuccess ) {
            detectSurface(bitmap);

            if (sdsState == sdsCalibrationFail) {
                if ((currentTime - startTime) > hintTime[hints]) {
                    startTime = currentTime;
                    hints = MetricHints.mhCalibrateFail;
                    sdsState = sdsNeedsCalibration;
                    foundColorIndex = 0;
                    activeColorIndex = 0;
                    closeLuminance = 0;
                    ScanData.clear(foundColors);
                    clearTaps();
                }
            } else if (sdsState == sdsNeedsCalibration) {
                if (hints != MetricHints.mhNeedsCalibrate) {
                    foundColorIndex = 0;
                    activeColorIndex = 0;
                    closeLuminance = 0;
                    ScanData.clear(foundColors);
                    clearTaps();
                    startTime = currentTime;
                    hints = MetricHints.mhNeedsCalibrate;
                    sdsState = sdsNeedsCalibration;
                }
                if (hints == MetricHints.mhNeedsCalibrate) {
                    if ((currentTime - startTime) > hintTime[hints]) {
                        startTime = currentTime;
                        frameCount = 0;
                        sdsState = sdsCalibrateComplete;
                        hints = MetricHints.mhCalibratePlacePhoneFlatOnPaper;
                    }
                }
            } else if (sdsState == sdsCalibrateComplete) {
                if ((currentTime - startTime) > hintTime[MetricHints.mhCalibrateComplete]) {
                    sdsState = sdsInit;
                    startTime = currentTime;
                }
            } else if (sdsState == sdsTapFail) {
                if ((currentTime - startTime) > hintTime[hints]) {
                    foundColorIndex = 0;
                    activeColorIndex = 0;
                    closeLuminance = 0;
                    ScanData.clear(foundColors);
                    clearTaps();
                    startTime = currentTime;

                    sdsState = sdsStartTapping;
                    hints = MetricHints.mhStartTapping;
                }
            } else if (sdsState == sdsInit) {
                foundColorIndex = 0;
                activeColorIndex = 0;
                closeLuminance = 0;
                ScanData.clear(foundColors);
                clearTaps();
                startTime = currentTime;

                frameCount = 0;
                sdsState = sdsPlaceOver;
                hints = MetricHints.mhPlaceOverSurface;
            } else if (sdsState == sdsPlaceOver) {
                if ((currentTime - startTime) > hintTime[hints]) {
                    if (scanType == ProcessSample.stWhitepoint) {
                        if (flatForPaperMeasure()) {
                            sdsState = sdsStartTapping;
                            startTime = currentTime;
                            hints = MetricHints.mhStartTapping;
                            frameCount = 0;
                            foundColorIndex = 0;
                            activeColorIndex = 0;
                            closeLuminance = 0;
                            ScanData.clear(foundColors);
                            clearTaps();
                            startTime = currentTime;
                        } else {
                            sdsState = sdsTapFail;
                            startTime = currentTime;
                            hints = MetricHints.mhFlatterToScanPaper;
                        }
                    } else if (scanType > ProcessSample.stUnderWrist) {
                        if (flatForFaceMeasure()) {
                            sdsState = sdsStartTapping;
                            startTime = currentTime;
                            hints = MetricHints.mhStartTapping;
                            frameCount = 0;
                            foundColorIndex = 0;
                            activeColorIndex = 0;
                            closeLuminance = 0;
                            ScanData.clear(foundColors);
                            clearTaps();
                        } else {
                            sdsState = sdsTapFail;
                            startTime = currentTime;
                            setFlatHint();
                        }

                    } else {
                        sdsState = sdsStartTapping;
                        startTime = currentTime;
                        hints = MetricHints.mhStartTapping;
                        frameCount = 0;
                        foundColorIndex = 0;
                        activeColorIndex = 0;
                        closeLuminance = 0;
                        ScanData.clear(foundColors);
                        clearTaps();
                    }
                }
            } else if (sdsState == sdsTapAgain) {
                if ((currentTime - startTime) > hintTime[hints]) {
                    sdsState = sdsStartTapping;
                    startTime = currentTime;
                    hints = MetricHints.mhStartTapping;
                    frameCount = 0;
                    foundColorIndex = 0;
                    activeColorIndex = 0;
                    closeLuminance = 0;
                    ScanData.clear(foundColors);
                    clearTaps();
                }
            } else if (sdsState == sdsStartTapping) {
                if ((currentTime - startTime) > hintTime[hints]) {
                    sdsState = sdsTapping;
                    startTime = currentTime;
                    hints = MetricHints.mhTapping;
                    frameCount = 0;
                    foundColorIndex = 0;
                    activeColorIndex = 0;
                    closeLuminance = 0;
                    ScanData.clear(foundColors);
                    clearTaps();
                }
            } else if (sdsState == sdsTapping) {
                if ((currentTime - startTime) > 20) {
                    sdsState = sdsTapAgain;
                    startTime = currentTime;
                    foundColorIndex = 0;
                    hints = MetricHints.mhKeepTapping;
                }
                if ((currentTime - startTime) > hintTime[MetricHints.mhTapping] && foundColorIndex > ScanData.gMaxSenseColors - 2) {
                    sdsState = sdsComplete;
                    hints = MetricHints.mhNoHint;
                    startTime = currentTime;
                    nTrys = 4;
                } else if ((currentTime - startTime) > hintTime[MetricHints.mhTapping]) {
                    sdsState = sdsTapAgain;
                    startTime = currentTime;
                    foundColorIndex = 0;
                    hints = MetricHints.mhKeepTapping;
                }
            } else if (sdsState == sdsComplete) {
                if (setBestColor()) {
                    hints = MetricHints.mhTapSuccess;
                    sdsState = sdsSuccess;
                    startTime = currentTime;
                } else {
                    if (scanType == ProcessSample.stWhitepoint) {

                        CvColor.rgbToLABWhitepoint(bestLAB,
                                bestColor.sampleColor,
                                calibrationWhite, gWhiteRef);
                        float aLabExposure = bestLAB[0];
                        if (aLabExposure >= settings.minWhiteLum && aLabExposure < settings.maxWhiteLum) {
                            float aColorDist = (float) java.lang.Math.sqrt((bestLAB[1] * bestLAB[1]) + (bestLAB[2] * bestLAB[2]));
                            if (aColorDist > settings.maxLABWhiteBalanceDiff) {
                                sdsState = sdsCalibrationFail;
                                startTime = currentTime;
                                hints = MetricHints.mhCalibrateFail;
                            }
                        } else {
                            if (nTrys > 0) {
                                sdsState = sdsCalibrationFail;
                                startTime = currentTime;
                                hints = MetricHints.mhCalibrateFail;
                            }
                        }
                    } else {
                        hints = MetricHints.mhTapFail;
                        sdsState = sdsTapFail;
                        startTime = currentTime;
                        setFlatHint();
                    }
                }

            }
            else if (sdsState == sdsSuccess)
            {
                if ((currentTime-startTime) > hintTime[hints])
                {
                    hints = MetricHints.mhSuccess;
                    aResult = true;
                }
            }
        }
        return(aResult);
    }

    /**
     * Updates gravity and the motion of the phone
     */
    void updateMotion()
    {
        double mag = gravity[0] * gravity[0] +
                gravity[1] * gravity[1]+
                gravity[2] * gravity[2];

        mag  = java.lang.Math.sqrt(mag);
        if (mag > 0.000001) {
            mag = 1.0 / mag;
        }
        gravity[0] *= mag;
        gravity[1] *= mag;
        gravity[2] *= mag;

        averageGravity[0] = averageGravity[0] * 0.9 + gravity[0] * 0.1;
        averageGravity[1] = averageGravity[1] * 0.9 + gravity[1] * 0.1;
        averageGravity[2] = averageGravity[2] * 0.9 + gravity[2] * 0.1;
    }

    /**
     * Checks to see if the phone is moving
     * @returns true if the phone is moving
     */
    boolean moving()
    {
        double diff;
        double dist = 0;
        diff = gravity[0] - averageGravity[0];
        dist = diff*diff;
        diff = gravity[1] - averageGravity[1];
        dist += diff*diff;
        diff = gravity[2] - averageGravity[2];
        dist += diff*diff;
        return(dist <= minMotionChange);
    }

    /**
     * Checks for the phone being held flat for measuring the face
     * @returns true if the phone is flat enough
     */
    boolean flatForFaceMeasure()
    {
        boolean still = ! !moving();
        boolean flat = (-averageGravity[2]) > minCalibrationFlatness;
        return(still && flat);
    }

    /**
     * Sets the proper hint for holdigng the phone flatter based on scanType
     */
    void setFlatHint()
    {
        if (scanType == ProcessSample.stWhitepoint)
        {
            hints = MetricHints.mhFlatterToScanPaper;
        }
        else if (scanType == ProcessSample.stRightJawline || scanType == ProcessSample.stLeftJawline)
        {
            hints = MetricHints.mhFlatterToJawline;
        }
        else if (scanType == ProcessSample.stRightCheek || scanType == ProcessSample.stLeftCheek)
        {
            hints = MetricHints.mhFlatterToCheek;
        }
        else if (scanType == ProcessSample.stForehead)
        {
            hints = MetricHints.mhFlatterToForehead;
        }
    }

    /**
     * Checks for the phone being held flat for paper calibration
     * @returns true if the phone is flat enough
     */
    boolean flatForPaperMeasure(){
        double angle[] = {0.0f, 0.08f, 0.997f};
        double dot = angle[0] * averageGravity[0]+angle[1] * averageGravity[1]+angle[2] * averageGravity[2];
        boolean flat = (dot > minPaperFlatness);
        return(flat);
    }

    /**
     * Samples the surface and processes a single frame of a tap.
     * @param bitmap, The raw pixels from the camera to sample
     */
    boolean detectSurface(android.graphics.Bitmap bitmap)
    {
        boolean aResult = false;

        if (    bitmap != null &&
                scanSample != null &&
                histoSample != null &&
                foundColors != null &&
                currentData != null)
        {
            scanSample.update(histoSample, bitmap, (int)((bitmap.getWidth()-scanSample.width)* settings.horizOffset),
                    (int)((bitmap.getHeight()-scanSample.height)* settings.vertOffset));
            histoSample.getScanData(currentData);
            brightLum = currentData.highlightLuminance;
            highPassBright = brightLum * 0.5f + highPassBright*0.5f;
            lowPassBright = brightLum * 0.25f + lowPassBright*0.75f;

            if (dimLum > brightLum)
            {
                dimLum = brightLum;
            }
            if (closeLuminance < brightLum)
            {
                closeLuminance = brightLum;
                currentData.copy(foundColors[activeColorIndex]);

            }

            highPassGreater = ((highPassBright*0.9f) > lowPassBright) &&
                        sdsState >= sdsTapping && sdsState < sdsComplete;

            if (highPassGreater)
            {
                processTaps();
            }

            frameCount++;
            aResult = true;
        }
        return(aResult);
    }

    /**
     * Processes a single sample to find taps.
     */
    void processTaps()
    {
        if (sdsState >= sdsTapping && sdsState < sdsComplete)
        {
            if (brightLum > settings.minTapLum)
            {
                if (dimLum < (brightLum * MIN_LUMINANCE_SCALAR))
                {
                    if (scanType == ProcessSample.stWhitepoint)
                    {
                        if(flatForPaperMeasure())
                        {
                            float foundLum = foundColors[activeColorIndex].sampleLuminance;
                             if (foundLum > MIN_WHITE_LUM_TAP)
                            {
                                foundColorIndex = activeColorIndex;
                                sortFoundColors();
                                if (activeColorIndex < (ScanData.gMaxSenseColors-1))
                                {
                                    activeColorIndex++;
                                }
                                int colorCount = activeColorIndex-1;
                                if (colorCount > ScanData.gMaxFoundColors)
                                    colorCount = ScanData.gMaxFoundColors;

                                if (colorCount > 0)
                                {
                                    calcBestColor(colorCount);
                                }
                            }
                        }
                    }
                    else
                    {
                        float foundLum = foundColors[activeColorIndex].sampleLuminance;
                        if (foundLum > settings.minTapLum)
                        {
                            sortFoundColors();
                            foundColorIndex = activeColorIndex;
                            if (activeColorIndex < (ScanData.gMaxSenseColors-1))
                            {
                                activeColorIndex++;
                            }

                            int colorCount = activeColorIndex-1;
                            if (colorCount > ScanData.gMaxFoundColors)
                                colorCount = ScanData.gMaxFoundColors;

                            if (colorCount > 0)
                            {
                                calcBestColor(colorCount);
                            }
                        }
                    }
                    clearTaps();
                }
            }
        }
    }

    /**
     * Clears data for starting a new tap.
     */
    void clearTaps()
    {
        closeLuminance = 0.0f;
        dimLum = 1.0f;
    }

    /**
     * Sorts the found colors, and removes the brightest entry if it is too bright.
     */
    void sortFoundColors()
    {
        ScanData.sort(foundColors, foundColorIndex+1);

        if (activeColorIndex > 6)
        {
            if (CvColor.colorDiff(foundColors[0].sampleColor, foundColors[2].sampleColor) > 0.20f)
            {
                for(int i = 0; i < activeColorIndex-1; i++)
                {
                    foundColors[i+1].copy( foundColors[i]);
                }
                activeColorIndex -= 1;
                foundColorIndex = activeColorIndex-1;
            }
        }
    }

    void calcBestColor(int colorCount)
    {
        double r = 0;
        double g = 0;
        double b = 0;
        double a = 0;
        double luma = 0;
        double lumsam = 0;
        double lumspec = 0;

        for(int i = 0; i < colorCount; i++)
        {
            r += foundColors[i].sampleColor[0];
            g += foundColors[i].sampleColor[1];
            b += foundColors[i].sampleColor[2];
            a += foundColors[i].sampleColor[3];
            luma += foundColors[i].averageLuminance;
            lumsam += foundColors[i].sampleLuminance;
            lumspec += foundColors[i].highlightLuminance;
        }
        if (colorCount > 0)
        {
            double invAccum = 1.0/(double)colorCount;
            r *= invAccum;
            g *= invAccum;
            b *= invAccum;
            a *= invAccum;
            luma *= invAccum;
            lumsam *= invAccum;
            lumspec *= invAccum;

            bestColor.sampleColor[0] = (float)r;
            bestColor.sampleColor[1] = (float)g;
            bestColor.sampleColor[2] = (float)b;
            bestColor.sampleColor[3] = (float)a;
            bestColor.averageLuminance = (float)luma;
            bestColor.sampleLuminance = (float)lumsam;
            bestColor.highlightLuminance = (float)lumspec;
        }

    }
    /**
     * Finds and filters bestColor.
     */
    boolean setBestColor()
    {
        boolean result = false;

        float minLum = 10000.0f;
        float maxLum = 0;
        int colorCount = activeColorIndex-1;
        if (colorCount > ScanData.gMaxFoundColors)
            colorCount = ScanData.gMaxFoundColors;


        if (colorCount == ScanData.gMaxFoundColors)
        {
            if (colorCount > 0)
            {
                calcBestColor(colorCount);
            }

            for(int i = 0; i < colorCount; i++)
            {
                float lum0 = foundColors[i].averageLuminance;
                minLum = java.lang.Math.min(lum0, minLum);
                maxLum = java.lang.Math.max(lum0, maxLum);
            }
            if ((maxLum - minLum) < maxSkinVariance)
            {
                if (foundColorIndex > 1)
                {
                    float lum0 = 0;
                    switch(scanType)
                    {
                        case ProcessSample.stWhitepoint:
                        {
                            CvColor.rgbToLABWhitepoint(bestLAB,
                                    bestColor.sampleColor,
                                    calibrationWhite, gWhiteRef);

                            float aLabExposure = bestLAB[0];
                            if (aLabExposure >= settings.minWhiteLum &&
                                    aLabExposure < settings.maxWhiteLum)
                            {
                                float aColorDist =
                                        (float)java.lang.Math.sqrt((bestLAB[1] * bestLAB[1]) +
                                                (bestLAB[2] * bestLAB[2]));
                                if (aColorDist < settings.maxLABWhiteBalanceDiff)
                                {
                                    result = true;
                                }
                            }
                        }
                        break;

                        case ProcessSample.stUnderWrist:
                            lum0 = bestColor.averageLuminance;
                            lum0 = CvColor.applyWhiteLuminance(lum0, whitePointLuminance, gWhiteRef);
                            result = lum0 < maxSkinLuminance && lum0 > minSkinLuminance;
                            break;

                        case ProcessSample.stForehead:
                        case ProcessSample.stRightCheek:
                        case ProcessSample.stLeftCheek:
                        case ProcessSample.stRightJawline:
                        case ProcessSample.stLeftJawline:

                            lum0 = bestColor.averageLuminance;
                            lum0 = CvColor.applyWhiteLuminance(lum0, whitePointLuminance, gWhiteRef);
                            if (baseTone != null)
                            {
                                float dist = CvColor.colorDiff(baseTone, bestColor.sampleColor);
                                result = dist < maxBaseToneDiff;
                            }
                            result = result && lum0 < maxSkinLuminance && lum0 > minSkinLuminance;
                            break;
                    }
                }
            }
        }
        return(result || settings.calibrateMode);
    }
}
