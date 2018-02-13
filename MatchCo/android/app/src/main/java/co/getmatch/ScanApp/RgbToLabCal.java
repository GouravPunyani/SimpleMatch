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
 * Created by young.harvill on 3/11/2017.
 */

public class RgbToLabCal {
    static boolean getLabColorRGB(float labColor[], float rgbColor[], float whitePoint[], int platform)
    {
        boolean result = false;

        if (platform == 0)
        {
            float whiteAdj[] = {1.0f,1.0f,1.0f};

            if (whitePoint[0] > 0.001)
                whiteAdj[0] = 0.95f / whitePoint[0];
            if (whitePoint[1] > 0.001)
                whiteAdj[1] = 0.95f / whitePoint[1];
            if (whitePoint[2] > 0.001)
                whiteAdj[2] = 0.95f / whitePoint[2];

            labColor[0] = rgbColor[0]*whiteAdj[0];
            labColor[1] = rgbColor[1]*whiteAdj[1];
            labColor[2] = rgbColor[2]*whiteAdj[2];

            CvColor.rgbToLAB(labColor, labColor);

            result = true;
        }
        else
        {
            float whiteAdj[] = {1.0f,1.0f,1.0f};

            if (whitePoint[0] > 0.001)
                whiteAdj[0] = 0.95f / whitePoint[0];
            if (whitePoint[1] > 0.001)
                whiteAdj[1] = 0.95f / whitePoint[1];
            if (whitePoint[2] > 0.001)
                whiteAdj[2] = 0.95f / whitePoint[2];

            labColor[0] = rgbColor[0]*whiteAdj[0];
            labColor[1] = rgbColor[1]*whiteAdj[1];
            labColor[2] = rgbColor[2]*whiteAdj[2];

            CvColor.rgbToLAB(labColor, labColor);

            result = true;
        }
        return(result);
    }
}
