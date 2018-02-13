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

public class CviColor {
    float r;
    float g;
    float b;
    float a;

    CviColor()
    {
        r = 0;
        g = 0;
        b = 0;
        a = 0;
    }

    void toRGB(float rgb[])
    {
        if (rgb != null && rgb.length >= 4)
        {
            rgb[0] = r;
            rgb[1] = g;
            rgb[2] = b;
            rgb[3] = a;
        }
    }

    void toLAB(float lab[])
    {
        toRGB(lab);
        CvColor.rgbToLAB(lab, lab);
    }

    void setWithWhitepoint(float rgbColor[], float rgbWhitePoint[], float d)
    {

        r = CvColor.applyWhiteLuminance(rgbColor[0], rgbWhitePoint[0], d);
        g = CvColor.applyWhiteLuminance(rgbColor[1], rgbWhitePoint[1], d);
        b = CvColor.applyWhiteLuminance(rgbColor[2], rgbWhitePoint[2], d);

    }
    public static CviColor[] build(int count)
    {
        CviColor colors[] = new CviColor[count];
        if (colors != null) {
            for (int i = 0; i < count; i++)
            {
                colors[i] = new CviColor();
            }
        }
        return(colors);
    }

}
