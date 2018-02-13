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

public class ScanSettings {

    public float minTapLum;
    public float minWhiteLum;
    public float maxWhiteLum;
    public float maxLABWhiteBalanceDiff;
    public long exposure;
    public int sensitivity;
    public float whitePoint[];
    public float horizOffset;
    public float vertOffset;
    public int bitmapWidth;
    public int bitmapHeight;
    public int sampleWidth;
    public int sampleHeight;
    public float minSampleHisto;
    public float maxSampleHisto;
    public float minSpecularHisto;
    public float maxSpecularHisto;
    public float tapDistanceMM;
    public boolean calibrateMode;
    public boolean calibrateDisplay;
    public boolean calibrateAudio;
    public boolean noTap;

    /**
     * Constructor for ScanSettings
     */
    ScanSettings()
    {

        minTapLum = 0.001f;
        minWhiteLum = 40;
        maxWhiteLum = 70; 
        whitePoint = new float[3];
        whitePoint[0] = 1;
        whitePoint[1] = 1;
        whitePoint[2] = 1;
        maxLABWhiteBalanceDiff = 10.0f;
        horizOffset = 1.0f/8.0f;
        vertOffset = 1.0f/2.0f;
        bitmapWidth = 240;
        bitmapHeight = 320;
        sampleWidth = 80;
        sampleHeight = 110;
        minSampleHisto = 0.4f;
        maxSampleHisto = 0.8f;
        minSpecularHisto = 0.95f;
        maxSpecularHisto = 0.99f;
        tapDistanceMM = 25.4f;
        calibrateMode = false;
        calibrateDisplay = false;
        calibrateAudio = false;
        noTap = false;
    }

    ScanSettings (int platform)
    {
        minTapLum = 0.001f;
        minWhiteLum = 50;
        maxWhiteLum = 70; 
        exposure = 500000L;
        sensitivity = 50;
        whitePoint = new float[3];
        whitePoint[0] = 1;
        whitePoint[1] = 1;
        whitePoint[2] = 1;
        maxLABWhiteBalanceDiff = 10.0f;
        horizOffset = 1.0f/8.0f;
        vertOffset = 1.0f/2.0f;
        bitmapWidth = 240;
        bitmapHeight = 320;
        sampleWidth = 80;
        sampleHeight = 110;
        minSampleHisto = 0.4f;
        maxSampleHisto = 0.8f;
        minSpecularHisto = 0.95f;
        maxSpecularHisto = 0.99f;
        tapDistanceMM = 25.4f;
        calibrateMode = false;
        calibrateDisplay = false;
        calibrateAudio = false;
        noTap = false;

        switch(platform)
        {
            case ProcessSettings.psDeviceGalaxyS7:
                exposure = 1000000000L/10000;
                sensitivity = 50;
                maxLABWhiteBalanceDiff = 30.0f;
                horizOffset = 1.0f/8.0f;
                vertOffset = 1.0f/2.0f;

                break;
            case ProcessSettings.psDeviceGalaxyS7Edge:
                exposure = 1000000000L/500;
                sensitivity = 50;
                maxLABWhiteBalanceDiff = 10.0f;
                horizOffset = 1.0f/8.0f;
                vertOffset = 1.0f/2.0f;
                break;
            case ProcessSettings.psDeviceGooglePixel:
                exposure = 1000000000L/30000;
                sensitivity = 50;
                horizOffset = 0.4f;
                vertOffset = 1.0f/2.0f;
                sampleWidth = 150;
                sampleHeight = 150;
                maxLABWhiteBalanceDiff = 5.0f;
                minSampleHisto = 0.1f;
                maxSampleHisto = 0.7f;
                minWhiteLum = 25;
                maxWhiteLum = 40;
                whitePoint[0] = 1.0f;
                whitePoint[1] = 0.710153061f;
                whitePoint[2] = 0.62670068f;

                break;

            case ProcessSettings.psDeviceGooglePixXL:
                exposure = 1000000000L/30000;
                sensitivity = 50;
                horizOffset = 0.4f;
                vertOffset = 1.0f/2.0f;
                sampleWidth = 150;
                sampleHeight = 150;
                maxLABWhiteBalanceDiff = 5.0f;
                minSampleHisto = 0.1f;
                maxSampleHisto = 0.7f;
                minWhiteLum = 25;
                maxWhiteLum = 50;
                whitePoint[0] = 1.0f;
                whitePoint[1] = 0.710153061f;
                whitePoint[2] = 0.62670068f;

                break;

            // NOTE(steve): this is a test device
            // case ProcessSettings.psDeviceLGG4:
            //     minWhiteLum = 10;
            //     maxWhiteLum = 100;
            //     maxLABWhiteBalanceDiff = 100.0f;
            //     break;

        }
    }

}
