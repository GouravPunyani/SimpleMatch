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
 * Created by young.harvill on 3/14/2017.
 */

public class ProcessSample {

    public static final int    stInitScan = -1;
    public static final int    stWhitepoint = 0;
    public static final int    stUnderWrist = 1;
    public static final int    stForehead = 2;
    public static final int    stRightCheek = 3;
    public static final int    stRightJawline = 4;
    public static final int    stLeftCheek = 5;
    public static final int    stLeftJawline = 6;
    public static final int    stMax = 7;

    public static final java.lang.String    gInitScan = "Init";
    public static final java.lang.String    gWhitepoint = "Whitepoint";
    public static final java.lang.String    gUnderWrist = "UnderWrist";
    public static final java.lang.String    gForehead = "Forehead";
    public static final java.lang.String    gRightCheek = "Right Cheek";
    public static final java.lang.String    gLeftCheek = "Left Cheek";
    public static final java.lang.String    gRightJawline = "Right Jawline";
    public static final java.lang.String    gLeftJawline = "Left Jawline";
    public static final java.lang.String    gMax  = "Max";

    static public java.lang.String getName(int sampleId)
    {
        java.lang.String result = gInitScan;
        switch(sampleId){
            case stInitScan:
                result = gInitScan;
                break;
            case stWhitepoint:
                result = gWhitepoint;
                break;
            case stUnderWrist:
                result = gUnderWrist;
                break;
            case stForehead:
                result = gForehead;
                break;
            case stRightCheek:
                result = gRightCheek;
                break;
            case stLeftCheek:
                result = gLeftCheek;
                break;
            case stRightJawline:
                result = gRightJawline;
                break;
            case stLeftJawline:
                result = gLeftJawline;
                break;
            case stMax:
                result = gMax;
                break;
        }
        return(result);
    }
}

/*

 */