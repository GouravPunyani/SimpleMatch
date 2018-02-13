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
 * Created by young.harvill on 2/21/2017.
 */



public class ScanSample {
    private int buffer[];
    public int width;
    public int height;
    private int stride;
    private int comps;
    private int size;

    private long lastTime;
    private float lastFrame;
    private float averageFrame;

    float luminance;
    float sampleColor[];

    ScanSample()
    {
        buffer = null;
        width = 0;
        height = 0;
        stride = 0;
        comps = 0;
        size = 0;
        lastTime = android.os.SystemClock.elapsedRealtime();
        lastFrame = 0;
        averageFrame = 0;
        luminance = 0;
    }


    /**
     * Constructor using size.
     * @param twidth, width to construct, in pixels.
     * @param theight, height to construct, in pixels.
     */
    ScanSample(int twidth, int theight){
        width = twidth;
        height = theight;
        stride = width;
        size = stride*height;
        buffer = new int[size];

        comps = 1;

        lastTime = android.os.SystemClock.elapsedRealtime();
        lastFrame = 0;
        averageFrame = 0;
        sampleColor = new float[4];
        luminance = 0;
    }

    /**
     * Constructor using size.
     * @param bitmap, The source bitmap for the copy.
     * @param x, horizontal pixel offset in the bitmap to start the copy.
     * @param y, vertical pixel offset  in the bitmap to start the copy.
     */
    void copy(android.graphics.Bitmap bitmap, int x, int y)
    {

        int twidth = width;
        int theight = height;
        x = java.lang.Math.max(0, x);
        y = java.lang.Math.max(0, y);
        x = java.lang.Math.min(bitmap.getWidth(), x);
        y = java.lang.Math.min(bitmap.getHeight(), y);

        twidth = bitmap.getWidth() - x;
        theight = bitmap.getHeight() - y;

        twidth = java.lang.Math.min(twidth, width);
        theight = java.lang.Math.min(theight, height);

        if (twidth > 0 && theight > 0) {
            bitmap.getPixels(buffer, 0, stride, x, y, twidth, theight);
        }
    }

    /**
     * Calculate the average color of the sample
     * @param color, The output color
     */
    float averageColor(float color[])
    {
        color[0] = 0;
        color[1] = 0;
        color[2] = 0;
        int max = 0;
        if(size > 0) {
            long ar = 0;
            long ag = 0;
            long ab = 0;

            for (int i = 0; i < size; i++) {
                int value = buffer[i];
                int r = (value >> 16) & 0x000000ff;
                int g = (value >> 8) & 0x000000ff;
                int b = value & 0x000000ff;
                ar += r;
                ag += g;
                ab += b;
                int lum = r + g * 2 + b;
                if (lum > max) {
                    max = lum;
                }
            }
            double iSize = 1.0 / ((double) size * 255.0);
            color[0] = (float) ((double) ar * iSize);
            color[1] = (float) ((double) ag * iSize);
            color[2] = (float) ((double) ab * iSize);
        }
        return((float)max * 1.0f/1024.0f);
    }

    /**
     * Update the HistoSample from the bitmap
     * @param sample, The destination HistoSample to update.
     * @param bitmap, The source bitmap for the copy.
     * @param x, horizontal pixel offset in the bitmap to start the copy.
     * @param y, vertical pixel offset  in the bitmap to start the copy.
     */
    void update (HistoSample sample, android.graphics.Bitmap bitmap, int x, int y)
    {
        copy(bitmap, x, y);
        sample.setBuffer(buffer, size);
        luminance = sample.getAverageLuminance();
        sample.constructColorFromHisto(sampleColor);
        long currentTime = android.os.SystemClock.elapsedRealtime();
        lastFrame = currentTime-lastTime;
        lastTime = currentTime;
        averageFrame = (averageFrame * 0.9f) + ((float)lastFrame * 0.1f);
    }



}
