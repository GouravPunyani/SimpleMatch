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
 * Created by young.harvill on 2/24/2017.
 */

public class MetricHints {
    public static final int mhNoHint = 0;
    public static final int mhFail = 1;
    public static final int mhDetectNothing = 2;
    public static final int mhNeedsCalibrate = 3;
    public static final int mhCalibratePlacePhoneFlatOnPaper = 4;
    public static final int mhCalibrateComplete = 5;
    public static final int mhPlaceOverSurface = 6;
    public static final int mhStartTapping = 7;
    public static final int mhTapping = 8;
    public static final int mhKeepTapping = 9;
    public static final int mhEndTapping = 10;
    public static final int mhCalibrateFail = 11;
    public static final int mhTapFail = 12;
    public static final int mhTapSuccess = 13;
    public static final int mhSteady = 14;
    public static final int mhPreview = 15;
    public static final int mhProcessing = 16;
    public static final int mhFlatterToCalibrate = 17;
    public static final int mhFlatterToScanPaper = 18;
    public static final int mhFlatterToForehead = 19;
    public static final int mhFlatterToCheek = 20;
    public static final int mhFlatterToJawline = 21;
    public static final int mhSuccess = 22;
    public static final int mhCount = 23;

    static public java.lang.String getHint(int hintId)
    {
        java.lang.String result = "No Hint";
        switch(hintId){
            case mhNoHint:
                result = "No Hint";
                break;
            case mhFail:
                result = "Fail";
                break;
            case mhDetectNothing:
                result = "Detect Nothing";
                break;
            case mhCalibratePlacePhoneFlatOnPaper:
                result = "Place On Paper";
                break;
            case mhCalibrateComplete:
                result = "Calibration Complete";
                break;
            case mhPlaceOverSurface:
                result = "Place";
                break;
            case mhStartTapping:
                result = "Start Tapping";
                break;

            case mhTapping:
                result = "Tapping";
                break;

            case mhKeepTapping:
                result = "Keep Tapping";
                break;
            case mhEndTapping:
                result = "End Tapping";
                break;
            case mhCalibrateFail:
                result = "Calibrate Fail";
                break;
            case mhTapFail:
                result = "Tap Fail";
                break;
            case mhTapSuccess:
                result = "Tap Success";
                break;
            case mhSteady:
                result = "Steady";
                break;
            case mhPreview:
                result = "Preview";
                break;
            case mhProcessing:
                result = "Processing";
                break;
            case mhFlatterToCalibrate:
                result = "Flat Calibrate";
                break;
            case mhFlatterToScanPaper:
                result = "Flat Paper";
                break;
            case mhFlatterToForehead:
                result = "Flat Forehead";
                break;
            case mhFlatterToCheek:
                result = "Flat Cheek";
                break;
            case mhFlatterToJawline:
                result = "Flat Jawline";
                break;
            case mhSuccess:
                result = "Success";
                break;
        }
        return(result);
    }
}
