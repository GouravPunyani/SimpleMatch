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

public class HistoSample {

    /**
     * redHisto: The red channel for the histogram.
     */
    private int redHisto[];

    /**
     * greenHisto: The green channel for the histogram.
     */
    private int greenHisto[];

    /**
     * blueHisto: The blue channel for the histogram.
     */
    private int blueHisto[];

    /**
     * accum: The population of each entry in the histogram
     */
    private int accum[];

    /**
     * sampleCount: Total count of all entries in the histogram
     */
    int sampleCount;

    /**
     * maxLumIndex: The index of the brightest luminance
     */
    int maxLumIndex;

    /**
     * averageLuminance: Average luminance for the histograme 0...1.
     */
    float averageLuminance;

    /**
     * averageColor: Average color for the histograme 0...1, r, g, b, a
     */
    float averageColor[];

    ScanSettings mScanSettings;

    /**
     * histoShift: sizeof histogram as a left shift
     */
    private static final int histoShift = 8;

    /**
     * histoSize: size of the histogram
     */
    private static final int histoSize = 1 << histoShift;

    /**
     * lumShift: right shift to luminance for histoSize
     */
    private static final int lumShift = 10-histoShift;

    /**
     * Constructor for HistoSample
     */
    HistoSample(ScanSettings settings){
        redHisto = new int[histoSize];
        greenHisto = new int[histoSize];
        blueHisto = new int[histoSize];
        averageColor = new float[4];
        accum = new int[histoSize];
        maxLumIndex = 0;
        sampleCount = 0;
        mScanSettings = settings;
    }

    /**
     * Copy this HistoSample to another
     * @param a, The destination HistoSample for the copy.
     */
    void copy( HistoSample a)
    {
        a.sampleCount = sampleCount;
        a.maxLumIndex = maxLumIndex;
        a.averageLuminance = averageLuminance;
        a.averageColor = averageColor.clone();
        a.redHisto = redHisto.clone();
        a.greenHisto = greenHisto.clone();
        a.blueHisto = blueHisto.clone();
        a.accum = accum.clone();
    }

    /**
     * Clears the histogram
     */
    void clear() {
        sampleCount = 0;
        maxLumIndex = 0;
        averageLuminance = 0;
        averageColor[0] = 0;
        averageColor[1] = 0;
        averageColor[2] = 0;
        java.util.Arrays.fill(redHisto, 0);
        java.util.Arrays.fill(greenHisto, 0);
        java.util.Arrays.fill(blueHisto, 0);
        java.util.Arrays.fill(accum, 0);
    }

    /**
     * Sets a buffer to the histogram
     * @param buffer, The buffer to use to set the histogram
     * @param size, The size of the buffer.
     */
    void setBuffer(int buffer[], int size)
    {
        clear();
        if (size > 0)
        {
            sampleCount += size;
            long ar = 0;
            long ag = 0;
            long ab = 0;
            for(int i = 0; i < size; i++)
            {
                int value = buffer[i];
                int r = (value >> 16) & 0x000000ff;
                int g = (value >> 8) & 0x000000ff;
                int b = value & 0x000000ff;
                ar += r;
                ag += g;
                ab += b;
                int lum = (r + g * 2 + b) >> lumShift;
                if (maxLumIndex < lum)
                {
                    maxLumIndex = lum;
                }
                redHisto[lum] += r;
                greenHisto[lum] += g;
                blueHisto[lum] += b;
                accum[lum]++;
            }
            double iSize = 1.0 / ((double) size * 255.0);
            averageColor[0] = (float) ((double) ar * iSize);
            averageColor[1] = (float) ((double) ag * iSize);
            averageColor[2] = (float) ((double) ab * iSize);
            averageLuminance = CvColor.luminance(averageColor);
        }
    }

    /**
     * Gets the index of the histogram at samples[sampleIndex]
     * whose population is aggregated from 0.
     * @param popScalar, The population used to find an index.
     * @returns the index of the histogram at samples[sampleIndex]
     *  whose population is aggregated from 0.
     */
    int getIndexFromHistoPopulation(float popScalar)
    {
        int pop = 0;
        int index = 0;
        if (accum != null)
        {
            long population = (int)(popScalar*(float)sampleCount);
            for(int i = 0 ; i < histoSize; i++)
            {
                pop += accum[i];
                if (accum[i] > 0)
                {
                    if (pop <= population)
                    {
                        index = i;
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
        return(index);
    }

    /**
     * Fetches the color of a given histoEntry.
     * @param histoIndex, The index of the histogram to use.
     * @param aColor, The returned color.
     */
    void colorFromHisto(int histoIndex, float aColor[])
    {
        aColor[0] = 0;
        aColor[1] = 0;
        aColor[2] = 0;
        aColor[3] = 1.0f;

        if (accum != null)
        {
            if (histoIndex >= 0 && histoIndex < histoSize)
            {
                if (accum[histoIndex] > 0)
                {
                    float scalar = 1.0f/((float)accum[histoIndex] * 255.0f);
                    aColor[0] = (float)redHisto[histoIndex]*scalar;
                    aColor[1] = (float)greenHisto[histoIndex]*scalar;
                    aColor[2] = (float)blueHisto[histoIndex]*scalar;
                }
            }
        }
    }

    /**
     * Fetches the color of a given histoEntry.
     * @param index0, The start index for sampling the color
     * @param index1, The end index for sampling the color
     * @param aColor, The sampled color output
     */
    void colorFromHisto(int index0, int index1, float aColor[])
    {
        aColor[0] = 0;
        aColor[1] = 0;
        aColor[2] = 0;
        aColor[3] = 1.0f;

        if (accum != null)
        {
            if (index0 >= 0 && index1 >= index0 && index1 < histoSize)
            {
                long ar = 0;
                long ag = 0;
                long ab = 0;
                long aa = 0;
                for(int i = index0; i <= index1; i++) {
                    if (accum[i] > 0) {
                        ar += redHisto[i];
                        ag += greenHisto[i];
                        ab += blueHisto[i];
                        aa += accum[i];
                    }
                }
                if (aa > 0)
                {
                    double iSize = 1.0 / ((double) aa * 255.0);
                    aColor[0] = (float) ((double) ar * iSize);
                    aColor[1] = (float) ((double) ag * iSize);
                    aColor[2] = (float) ((double) ab * iSize);
                }
            }
        }
    }

    /**
     * Constructs the color of a given  using the overall luminance.
     * @param aColor, The returned color.
     * @returns true if the color was found.
     */
    boolean constructColorFromHisto(float aColor[])
    {
        boolean result = false;
        if (accum != null)
        {
            int index0 = getIndexFromHistoPopulation(mScanSettings.minSampleHisto);
            int index1 = getIndexFromHistoPopulation(mScanSettings.maxSpecularHisto);
            if (index0 >= 0 && index1 >= 0)
            {
                colorFromHisto(index0, index1, aColor);
                float colorLum = CvColor.luminance(aColor);

                if (colorLum > 0)
                {
                    float lumCorrect = averageLuminance/colorLum;
                    aColor[0] *= lumCorrect;
                    aColor[1] *= lumCorrect;
                    aColor[2] *= lumCorrect;
                    aColor[3] = 0;
                    result = true;
                }
            }
        }
        return(result);
    }

    /**
     * Fetches the average luminance of the histoSample
     * @returns The average luminance of the histoSample
     */
    float getAverageLuminance()
    {
        return(averageLuminance);
    }

    /**
     * Fetches ScanData from the Histo
     * @returns The average luminance of the histoSample
     */
    boolean getScanData(ScanData data)
    {
        boolean result= false;
        if (accum != null)
        {
            int specIndex0 = getIndexFromHistoPopulation(mScanSettings.minSpecularHisto);
            int specIndex1 = getIndexFromHistoPopulation(mScanSettings.maxSpecularHisto);
            if (specIndex0 >= 0 && specIndex1 >= 0) {

                int index0 = getIndexFromHistoPopulation(mScanSettings.minSampleHisto);
                int index1 = getIndexFromHistoPopulation(mScanSettings.maxSampleHisto);
                colorFromHisto(specIndex0, specIndex1, data.sampleColor);
                data.highlightLuminance = CvColor.luminance(data.sampleColor);
                if (index0 >= 0 && index1 >= 0)
                {
                    colorFromHisto(index0, index1, data.sampleColor);
                    data.sampleLuminance = CvColor.luminance(data.sampleColor);
                    data.averageLuminance = averageLuminance;

                    colorFromHisto(index0, index1, data.sampleColor);

                    if (data.sampleLuminance > 0)
                    {
                        float lumCorrect = averageLuminance/data.sampleLuminance;
                        data.sampleColor[0] *= lumCorrect;
                        data.sampleColor[1] *= lumCorrect;
                        data.sampleColor[2] *= lumCorrect;
                        data.sampleColor[3] = 0;
                        result = true;
                    }
                }
            }
        }
        return(result);
    }
}
