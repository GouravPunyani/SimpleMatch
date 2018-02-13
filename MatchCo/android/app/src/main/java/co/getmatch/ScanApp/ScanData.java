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

public class ScanData implements Comparable<ScanData> {

    public static final int gMaxFoundColors = 5;
    public static final int gMaxSenseColors = gMaxFoundColors * 2;

    float averageLuminance;
    float sampleLuminance;
    float highlightLuminance;
    float sampleColor[];

    /**
     * Constructor
     */
    ScanData() {
        sampleColor = new float[4];
        clear();
    }

    /**
     * compareTo inplementation for Comparable interface.
     */
    public int compareTo(ScanData a) {

        if (averageLuminance == a.averageLuminance) {
            return (0);
        } else if (averageLuminance < a.averageLuminance) {
            return (1);
        }
        return (-1);

    }

    /**
     * Copies this ScanData into a.
     *
     * @param a, The destination of the copy;
     */
    public void copy(ScanData a) {
        a.averageLuminance = averageLuminance;
        a.sampleLuminance = sampleLuminance;
        a.highlightLuminance = highlightLuminance;
        a.sampleColor[0] = sampleColor[0];
        a.sampleColor[1] = sampleColor[1];
        a.sampleColor[2] = sampleColor[2];
        a.sampleColor[3] = sampleColor[3];
    }


    /**
     * Comparator for sorting the ScanData array
     */
    public static java.util.Comparator<ScanData> compareScanData
            = new java.util.Comparator<ScanData>() {

        public int compare(ScanData a, ScanData b) {

            return a.compareTo(b);
        }
    };

    /**
     * sorts a ScanData Array
     *
     * @param scanArray, The Array to sort.
     */
    static void sort(ScanData scanArray[], int sortCount) {
        boolean sorting = true;
        ScanData temp = new ScanData();

        if (sortCount > scanArray.length)
            sortCount = scanArray.length;

        while(sorting)
        {
            sorting = false;
            for(int i = 0; i < sortCount-1; i++) {
                if (scanArray[i].sampleLuminance < scanArray[i + 1].sampleLuminance)
                {
                    scanArray[i].copy(temp);
                    scanArray[i+1].copy(scanArray[i]);
                    temp.copy(scanArray[i+1]);
                    sorting = true;
                }
            }
        }
    }

    static int removeOutliers(ScanData scanArray[], int sortCount){
        int result = 0;
        int out0 = -1;
        int out1 = -2;
        float maxDist = 0;
        if (sortCount > 2)
        {
            for(int i = 0; i < sortCount-1; i++)
            {
                for(int j = i+1; j < sortCount; j++)
                {
                    float dist = CvColor.colorDiff(scanArray[i].sampleColor,
                            scanArray[j].sampleColor);
                    if (dist > maxDist)
                    {
                        out0 = i;
                        out1 = j;
                        maxDist = dist;
                    }
                }
            }
            for(int i = 0; i < sortCount-2; i++)
            {
                if (i < out1)
                {
                    scanArray[i+1].copy(scanArray[i]);
                }
                else
                {
                    scanArray[i+2].copy(scanArray[i]);
                }
            }
            result = sortCount-2;
        }
        return(result);
    }

    void clear(){
        averageLuminance = 0.0f;
        sampleLuminance = 0.0f;
        highlightLuminance = 0.0f;
        sampleColor[0] = 0;
        sampleColor[1] = 0;
        sampleColor[2] = 0;
        sampleColor[3] = 0;
    }
    /**
     * clears a ScanData Array
     *
     * @param scanArray, The Array to clear.
     */
    static void clear(ScanData scanArray[]) {
       for(int i = 0; i < scanArray.length; i++)
       {
           if (scanArray[i] != null)
           {
               scanArray[i].averageLuminance = 0.0f;
               scanArray[i].sampleLuminance = 0.0f;
               scanArray[i].highlightLuminance = 0.0f;
               if (scanArray[i].sampleColor != null)
               {
                   scanArray[i].sampleColor[0] = 0;
                   scanArray[i].sampleColor[1] = 0;
                   scanArray[i].sampleColor[2] = 0;
                   scanArray[i].sampleColor[3] = 0;
               }
           }
       }
    }

    static public ScanData[] build(int count){
        ScanData colors[] = new ScanData[count];
        if (colors != null)
        {
            for(int i = 0; i < count; i++)
            {
                colors[i] = new ScanData();
            }
        }
        return(colors);
    }

}
