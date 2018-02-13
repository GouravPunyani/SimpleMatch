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
 * Created by young.harvill on 2/27/2017.
 */

public class PsSample {
    float calibrationWhite[];
    float baseTone[];
    ScanData foundColors[];
    int scanType;

    PsSample(){
        scanType = 0;
        calibrationWhite = new float[3];
        calibrationWhite[0] = 1.0f;
        calibrationWhite[1] = 1.0f;
        calibrationWhite[2] = 1.0f;

        baseTone = new float[3];
        baseTone[0] = 0.0f;
        baseTone[1] = 0.0f;
        baseTone[2] = 0.0f;
        foundColors = ScanData.build(ScanData.gMaxFoundColors);
    }
    void copy(PsSample dest)
    {
        if (dest != null)
        {
            dest.calibrationWhite[0] = calibrationWhite[0];
            dest.calibrationWhite[0] = calibrationWhite[1];
            dest.calibrationWhite[0] = calibrationWhite[2];

            dest.baseTone[0] = baseTone[0];
            dest.baseTone[1] = baseTone[1];
            dest.baseTone[2] = baseTone[2];

            for(int i = 0; i < ScanData.gMaxFoundColors; i++)
            {
                foundColors[i].copy(dest.foundColors[i]);
            }
            dest.scanType = scanType;
        }
    }
    static public PsSample[] build(int count)
    {
        PsSample sample[] = new PsSample[count];
        if (sample != null)
        {
            for(int i = 0; i < count; i++)
            {
                sample[i] = new PsSample();
                sample[i].scanType = i;
            }
        }
        return(sample);
    }

    public void clear(int scanId, float whitePoint[])
    {
        scanType = scanId;
        ScanData.clear(foundColors);
        if (whitePoint != null && whitePoint.length >= 3)
        {
            calibrationWhite[0] = whitePoint[0];
            calibrationWhite[1] = whitePoint[1];
            calibrationWhite[2] = whitePoint[2];
        }
        else
        {
            calibrationWhite[0] = 1.0f;
            calibrationWhite[1] = 1.0f;
            calibrationWhite[2] = 1.0f;
        }
        baseTone[0] = 0.0f;
        baseTone[1] = 0.0f;
        baseTone[2] = 0.0f;
    }
}
