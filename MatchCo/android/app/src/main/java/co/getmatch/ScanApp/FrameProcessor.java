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
 * Created by young.harvill on 3/9/2017.
 */

public class FrameProcessor extends Object{

    ObjectDetect objectDetect;
    boolean needsCalibration;
    boolean needsWhiteBalance;

    FrameProcessor(){
        objectDetect = new ObjectDetect();
    }

    void setCalibration()
    {

    }

    /**
     * Updates a frame of the entire state model.
     * @returns true when success state is reached.
     */
    public synchronized int doProcess(android.graphics.Bitmap bitmap){
        int result = MetricHints.mhDetectNothing;

        if (objectDetect != null)
        {
            objectDetect.update(bitmap);
            result = objectDetect.hints;
        }
        return(result);
    }

    void setAcceleration(double acceleration[])
    {
        if (acceleration != null && objectDetect != null && objectDetect.gravity != null)
        {
            objectDetect.gravity[0] = acceleration[0];
            objectDetect.gravity[1] = acceleration[1];
            objectDetect.gravity[2] = acceleration[2];
        }
    }
    /**
     * updateSample returns the data found during the scan process.
     */
    public synchronized int updateSample(PsSample processSample)
    {
        int result = 0;

        if (objectDetect != null)
        {
            if (objectDetect.sdsState  == ObjectDetect.sdsInit)
            {

                if (processSample != null)
                {
                    if (processSample.scanType == ProcessSample.stWhitepoint)
                    {
                        objectDetect.init();
                    }
                    objectDetect.scanType = processSample.scanType;
                    objectDetect.baseTone = processSample.baseTone;
                    objectDetect.calibrationWhite[0] = processSample.calibrationWhite[0];
                    objectDetect.calibrationWhite[1] = processSample.calibrationWhite[1];
                    objectDetect.calibrationWhite[2] = processSample.calibrationWhite[2];

                    objectDetect.whitePointLuminance = CvColor.luminance(objectDetect.calibrationWhite);
                }
                result = objectDetect.foundColorIndex;
            }
            if (objectDetect.hints  == MetricHints.mhSuccess)
            {
                if (processSample != null)
                {
                    if (processSample.scanType == ProcessSample.stWhitepoint)
                    {
                        processSample.calibrationWhite[0] = objectDetect.bestColor.sampleColor[0];
                        processSample.calibrationWhite[1] = objectDetect.bestColor.sampleColor[1];
                        processSample.calibrationWhite[2] = objectDetect.bestColor.sampleColor[2];
                        needsCalibration = false;
                        needsWhiteBalance = false;
                    }
                    else if (objectDetect.scanType == ProcessSample.stUnderWrist)
                    {
                        processSample.baseTone[0] = objectDetect.bestColor.sampleColor[0];
                        processSample.baseTone[1] = objectDetect.bestColor.sampleColor[1];
                        processSample.baseTone[2] = objectDetect.bestColor.sampleColor[2];
                    }

                }
            }
            if(objectDetect.sdsState  >= ObjectDetect.sdsStartTapping)
            {
                for(int i = 0; i < processSample.foundColors.length; i++)
                {
                    objectDetect.foundColors[i].copy(processSample.foundColors[i]);
                }
                result = objectDetect.foundColorIndex;
            }
        }
        return(result);
    }

    void startScan()
    {
        objectDetect.sdsState  = ObjectDetect.sdsInit;
    }

    public void getPreviewColor (float previewColor[])
    {
        if (previewColor != null && previewColor.length >= 3 && objectDetect != null)
        {
            if (objectDetect.currentData != null)
            {
                previewColor[0] = objectDetect.currentData.sampleColor[0];
                previewColor[1] = objectDetect.currentData.sampleColor[1];
                previewColor[2] = objectDetect.currentData.sampleColor[2];
            }
        }
    }

    public void getBestColor (float bestColor[])
    {
        if (bestColor != null && bestColor.length >= 3 && objectDetect != null)
        {
            if (objectDetect.currentData != null)
            {
                bestColor[0] = objectDetect.bestColor.sampleColor[0];
                bestColor[1] = objectDetect.bestColor.sampleColor[1];
                bestColor[2] = objectDetect.bestColor.sampleColor[2];
            }
        }
    }
    /*





// set up recognition stuff...
- (void) initProcessing: (GraeVideoProcessor*)videoProcess;
- (void) dumpProcessing: (GraeVideoProcessor*)videoProcess;
- (void) resetProcessing: (GraeVideoProcessor*)videoProcess;
- (BOOL) updateSample: (struct PsSample*)processSample;
- (void) setCalibration:(bool)state;
- (void) setHintTime:(int)hint time:(float)hintTime;
- (float) getFramesPerSecond;
- (void) setPause:(bool)state;

// do it...return YES if matched...
- (int) processPixelBuffer :(GraeVideoProcessor*)videoProcess buffer:(CVImageBufferRef)pixelBuffer;
- (int) doProcess :(GraeVideoProcessor*)videoProcess buffer:(CVImageBufferRef)pixelBuffer;
    */

/**
 * getSampleColor returns all current sampled colors.
 * It returns all of them, since they may change in sort order
 */
//- (int) getSampledColors: (struct CviColor *)sampleColor sampleCount:(int)sampleCount;
/**
 * setNeedsWhite sets the frame process to need white point calibration
 */
//- (void) setNeedsWhite;
}
