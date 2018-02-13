/*
 Copyright (C) 2013-2017 MatchCo Inc
 All rights reserved.
 This document is proprietary information of MatchCo Inc.
 Do not distribute outside of the company without written authorization.
 Portions of this document may be protected by patent.
 Portions of this document may be covered by patent pending.
 */

/**
 * Created by young.harvill on 3/13/2017.
 */

package co.getmatch.ScanApp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.*;
import android.util.AttributeSet;
import android.content.Context;




public class SwatchView extends View {

    int mWidth;
    int mHeight;
    Paint mSwatchColors[];
    RectF mSwatchBounds[];
    RectF mSwatchBackground;
    RectF mSwatchBackCap0;
    RectF mSwatchBackCap1;
    Paint outlineColor;
    int mHintId;
    int mProcessId;
    Paint mHintPaint;
    Paint mProcessPaint;
    int strokeCount = 0;
    Paint mBestColor;
    RectF mBestBounds;
    RectF mBestBackground;
    float previewLum = 0;
    Paint mBackgroundColor;
    ScanSettings mSettings;

    public static final int gBackgroundColor = 0xffe0e0e0;
    public static final int gStrokeColor = 0xff707070;
    public static final int gTextColor = 0xff101010;

    /**
     * Class constructor taking only a context. Use this constructor to create
     * {@link SwatchView} objects from your own code.
     *
     * @param context
     */
    public SwatchView(Context context) {
        super(context);
        init();
    }

    /**
     * Class constructor taking a context and an attribute set.
     * Bind to XML attributes.
     *
     * @param context
     * @param attrs   An attribute set which can contain attributes
     */
    public SwatchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    void init(){
        mWidth = 0;
        mHeight = 0;

        mBackgroundColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundColor.setStyle(Paint.Style.FILL);
        mBackgroundColor.setColor(gBackgroundColor);

        mBestColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBestColor.setStyle(Paint.Style.FILL);
        mBestColor.setColor(gBackgroundColor);

        mSwatchColors = new Paint[ScanData.gMaxFoundColors];
        mSwatchBounds = new RectF[ScanData.gMaxFoundColors];
        mHintId = 0;
        mProcessId = 0;

        for(int i = 0; i < mSwatchColors.length; i++)
        {
            mSwatchColors[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            mSwatchColors[i].setStyle(Paint.Style.FILL);
            mSwatchColors[i].setColor(gBackgroundColor);
        }
        mHintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHintPaint.setColor(gTextColor);
        mProcessPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProcessPaint.setColor(gTextColor);
        outlineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlineColor.setStyle(Paint.Style.STROKE);
        outlineColor.setColor(gStrokeColor);
        outlineColor.setStrokeWidth(3.0f);
        mSettings = new ScanSettings(ProcessSettings.getPlatform());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);



        if (mSettings.calibrateDisplay)
        {

        }
        else {

            android.graphics.drawable.Drawable drawable = null;
            switch (mProcessId) {
                case ProcessSample.stWhitepoint:
                    drawable = getResources().getDrawable(R.drawable.calibrate_back, null);
                    break;
                case ProcessSample.stUnderWrist:
                    drawable = getResources().getDrawable(R.drawable.wrist_back, null);
                    break;
                case ProcessSample.stForehead:
                    drawable = getResources().getDrawable(R.drawable.forehead_back, null);
                    break;
                case ProcessSample.stRightCheek:
                    drawable = getResources().getDrawable(R.drawable.right_cheek_back, null);
                    break;
                case ProcessSample.stRightJawline:
                    drawable = getResources().getDrawable(R.drawable.right_jaw_back, null);
                    break;
                case ProcessSample.stLeftCheek:
                    drawable = getResources().getDrawable(R.drawable.left_cheek_back, null);
                    break;
                case ProcessSample.stLeftJawline:
                    drawable = getResources().getDrawable(R.drawable.left_jaw_back, null);
                    break;
            }
            if (null != drawable) {
                Rect drawBounds = drawable.getBounds();
                drawable.setBounds(0, 0, mWidth, (int) ((1920.0f / 1080.0f) * (float) mWidth));
                drawable.draw(canvas);
            }
        }

        if (mSettings.noTap)
        {

        }
        else if (mHintId >= MetricHints.mhTapping &&
                mHintId < MetricHints.mhTapSuccess)
        {
            int x = (int)mBestBounds.centerX();
            int y = (int)mBestBounds.centerY();
            float range = previewLum* 2.0f;
            range = 1.0f - Math.min(1.0f, range);

            if(strokeCount == 0) {
                range = 1.0f;
            }

            int pulseRad =  (int)((mBestBounds.width() * 0.5f)-(range * (mBestBounds.width() * 0.125f)));

            RectF pulseSwatch = new RectF(x-pulseRad, y-pulseRad, x+pulseRad, y + pulseRad);

            canvas.drawRect(mSwatchBackground, mBackgroundColor);
            canvas.drawOval(mSwatchBackCap0, mBackgroundColor);
            canvas.drawOval(mSwatchBackCap1, mBackgroundColor);

            canvas.drawOval(mBestBackground, mBackgroundColor);

            if(strokeCount > 0)
            {
                canvas.drawOval(pulseSwatch, mBestColor);
            }
            else
            {
                canvas.drawOval(pulseSwatch, mBackgroundColor);
            }
            canvas.drawOval(pulseSwatch, outlineColor);

            if (mSwatchColors != null &&
                    mSwatchBounds != null &&
                    mSwatchColors.length == mSwatchBounds.length)
            {
                for(int i = 0; i < strokeCount; i++) {
                    canvas.drawOval(mSwatchBounds[i], mSwatchColors[i]);
                }
                for(int i = 0; i < strokeCount; i++) {
                    canvas.drawOval(mSwatchBounds[i], outlineColor);
                }
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //
        // Set dimensions for text, pie chart, etc
        //
        // Account for padding

        mWidth = w;
        mHeight = h;

        int swatchSize = mWidth / (ScanData.gMaxFoundColors + 2);
        int x = (int)(swatchSize * 1.5f);
        int swatchRad = swatchSize / 3;
        int y = h - (int)(swatchRad*2);
        int xinc = swatchSize;


        for(int i = 0; i < mSwatchBounds.length; i++)
        {
            mSwatchBounds[i] = new RectF(x - swatchRad, y - swatchRad, x+swatchRad, y+swatchRad);
            x += xinc;
        }

        x = (int)(swatchSize * 1.5f);
        int lastX = x + xinc * (mSwatchBounds.length-1);

        mSwatchBackground = new RectF(
                x,
                y - (int) (swatchRad * 1.5f),
                lastX,
                y + (int) (swatchRad * 1.5f));

        mSwatchBackCap0 = new RectF(
                x - swatchRad * 1.5f,
                y - (int) (swatchRad * 1.5f),
                x + (int)(swatchRad * 1.5f),
                y + (int) (swatchRad * 1.5f));

        x += xinc * (mSwatchBounds.length-1);

        mSwatchBackCap1 = new RectF(
                lastX - swatchRad * 1.5f,
                y - (int) (swatchRad * 1.5f),
                lastX + (int)(swatchRad * 1.5f),
                y + (int) (swatchRad * 1.5f));

        mHintPaint.setTextSize((int)(mWidth * 0.08));
        mProcessPaint.setTextSize((int)(mWidth * 0.1));



        int bestRad = mWidth / 8;
        int backRad = (int)(mWidth / 6.5f);
        x = (int)(mWidth * 0.5f);
        y = (int)(mHeight * 0.8f);

        mBestBounds = new RectF(x-bestRad, y-bestRad, x+bestRad, y+bestRad);
        mBestBackground = new RectF(x-backRad, y-backRad, x+backRad, y+backRad);
    }

    public synchronized void setSwatches(CviColor colors[], int foundColors)
    {
        if (colors != null)
        {
            if (foundColors > colors.length)
                foundColors = colors.length;

            for(int i = 0; i < foundColors; i++)
            {
                int color = 0xff000000;
                color |= ((int)(colors[i].r * 255) & 255) << 16;
                color |= ((int)(colors[i].g * 255) & 255) << 8;
                color |= ((int)(colors[i].b * 255) & 255);
                mSwatchColors[i].setColor(color);
            }
            for(int i = foundColors; i < mSwatchColors.length; i++)
            {
                mSwatchColors[i].setColor(gBackgroundColor);
            }
            strokeCount = foundColors;
            invalidate();
        }
    }

    public synchronized void setPreview(CviColor pColor) {

        previewLum = pColor.r*0.299f + pColor.g * 0.587f+ pColor.b* 0.114f;

    }

    public synchronized void setBestColor(CviColor pColor) {

        int color = 0xff000000;
        color |= ((int)(pColor.r * 255) & 255) << 16;
        color |= ((int)(pColor.g * 255) & 255) << 8;
        color |= ((int)(pColor.b * 255) & 255);
        mBestColor.setColor(color);
    }

    public synchronized void setHint(int hintId) {
        mHintId = hintId;
        invalidate();
    }

    public synchronized void setProcess(int processId) {
        mProcessId = processId;
        invalidate();
    }

}
