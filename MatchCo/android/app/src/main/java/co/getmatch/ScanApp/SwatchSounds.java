package co.getmatch.ScanApp;
import android.content.Context;
import android.media.AsyncPlayer;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 Copyright (C) 2013-2017 MatchCo Inc
 All rights reserved.
 This document is proprietary information of MatchCo Inc.
 Do not distribute outside of the company without written authorization.
 Portions of this document may be protected by patent.
 Portions of this document may be covered by patent pending.
 */
/**
 * Created by young.harvill on 4/2/2017.
 */

public class SwatchSounds {
    AsyncPlayer mPlayer;
    AudioAttributes mAttributes;
    SampleSounds mSampleSounds[];
    int mLastHint;
    int mLastSample;
    double mLastTime;
    ScanSettings mSettings;

    SwatchSounds(){

        mLastHint = -1;
        mLastSample = -1;
        mLastTime = 0;
        mSettings = new ScanSettings(ProcessSettings.getPlatform());

        mPlayer =  new AsyncPlayer("ScanPlayer");
        if (null != mPlayer)
        {
            boolean result = false;

            AudioAttributes.Builder audioAttributesBuilder = new AudioAttributes.Builder();
            audioAttributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
            audioAttributesBuilder.setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION);
            mAttributes = audioAttributesBuilder.build();
            mSampleSounds = new SampleSounds[ProcessSample.stMax];

            if (mSampleSounds != null)
            {
                result = true;
                for(int i = 0; i < ProcessSample.stMax; i++)
                {
                    mSampleSounds[i] = new SampleSounds();
                    if (mSampleSounds[i] == null)
                    {
                        result = false;
                    }
                }
            }

            if (result)
            {
                mSampleSounds[ProcessSample.stWhitepoint].sampleURIs[MetricHints.mhCalibrateFail] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.fail_0);
                mSampleSounds[ProcessSample.stWhitepoint].sampleTimes[MetricHints.mhCalibrateFail]= 1.8f;


                mSampleSounds[ProcessSample.stWhitepoint].sampleURIs[MetricHints.mhKeepTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.keep_tapping_0);
                mSampleSounds[ProcessSample.stWhitepoint].sampleTimes[MetricHints.mhKeepTapping]= 1.0f;

                mSampleSounds[ProcessSample.stWhitepoint].sampleURIs[MetricHints.mhTapSuccess] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.success_0);
                mSampleSounds[ProcessSample.stWhitepoint].sampleTimes[MetricHints.mhTapSuccess]= 5.0f;

                mSampleSounds[ProcessSample.stUnderWrist].sampleURIs[MetricHints.mhStartTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.start_tapping_1);
                mSampleSounds[ProcessSample.stUnderWrist].sampleTimes[MetricHints.mhStartTapping]= 12.0f;

                mSampleSounds[ProcessSample.stUnderWrist].sampleURIs[MetricHints.mhKeepTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.keep_tapping_1);
                mSampleSounds[ProcessSample.stUnderWrist].sampleTimes[MetricHints.mhKeepTapping]= 3.0f;

                mSampleSounds[ProcessSample.stUnderWrist].sampleURIs[MetricHints.mhTapSuccess] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.success_1);
                mSampleSounds[ProcessSample.stUnderWrist].sampleTimes[MetricHints.mhTapSuccess]= 4.0f;

                mSampleSounds[ProcessSample.stForehead].sampleURIs[MetricHints.mhStartTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.start_tapping_2);
                mSampleSounds[ProcessSample.stForehead].sampleTimes[MetricHints.mhStartTapping]= 8.0f;

                mSampleSounds[ProcessSample.stForehead].sampleURIs[MetricHints.mhKeepTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.keep_tapping_2);
                mSampleSounds[ProcessSample.stForehead].sampleTimes[MetricHints.mhKeepTapping]= 2.0f;

                mSampleSounds[ProcessSample.stForehead].sampleURIs[MetricHints.mhTapSuccess] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.success_2);
                mSampleSounds[ProcessSample.stForehead].sampleTimes[MetricHints.mhTapSuccess]= 6.0f;

                mSampleSounds[ProcessSample.stRightCheek].sampleURIs[MetricHints.mhStartTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.start_tapping_3);
                mSampleSounds[ProcessSample.stRightCheek].sampleTimes[MetricHints.mhStartTapping]= 6.0f;

                mSampleSounds[ProcessSample.stRightCheek].sampleURIs[MetricHints.mhKeepTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.keep_tapping_3);
                mSampleSounds[ProcessSample.stRightCheek].sampleTimes[MetricHints.mhKeepTapping]= 3.0f;

                mSampleSounds[ProcessSample.stRightCheek].sampleURIs[MetricHints.mhTapSuccess] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.success_3);
                mSampleSounds[ProcessSample.stRightCheek].sampleTimes[MetricHints.mhTapSuccess]= 2;

                mSampleSounds[ProcessSample.stLeftCheek].sampleURIs[MetricHints.mhStartTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.start_tapping_5);
                mSampleSounds[ProcessSample.stLeftCheek].sampleTimes[MetricHints.mhStartTapping]= 6.0f;

                mSampleSounds[ProcessSample.stLeftCheek].sampleURIs[MetricHints.mhKeepTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.keep_tapping_5);
                mSampleSounds[ProcessSample.stLeftCheek].sampleTimes[MetricHints.mhKeepTapping]= 3.0f;

                mSampleSounds[ProcessSample.stLeftCheek].sampleURIs[MetricHints.mhTapSuccess] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.success_5);
                mSampleSounds[ProcessSample.stLeftCheek].sampleTimes[MetricHints.mhTapSuccess]= 3;

                mSampleSounds[ProcessSample.stRightJawline].sampleURIs[MetricHints.mhStartTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.start_tapping_4);
                mSampleSounds[ProcessSample.stRightJawline].sampleTimes[MetricHints.mhStartTapping]= 5.0f;

                mSampleSounds[ProcessSample.stRightJawline].sampleURIs[MetricHints.mhKeepTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.keep_tapping_4);
                mSampleSounds[ProcessSample.stRightJawline].sampleTimes[MetricHints.mhKeepTapping]= 6.0f;

                mSampleSounds[ProcessSample.stRightJawline].sampleURIs[MetricHints.mhTapSuccess] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.success_4);
                mSampleSounds[ProcessSample.stRightJawline].sampleTimes[MetricHints.mhTapSuccess]= 3;

                mSampleSounds[ProcessSample.stLeftJawline].sampleURIs[MetricHints.mhStartTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.start_tapping_6);
                mSampleSounds[ProcessSample.stLeftJawline].sampleTimes[MetricHints.mhStartTapping]= 8.0f;

                mSampleSounds[ProcessSample.stLeftJawline].sampleURIs[MetricHints.mhKeepTapping] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.keep_tapping_6);
                mSampleSounds[ProcessSample.stLeftJawline].sampleTimes[MetricHints.mhKeepTapping]= 1.0f;

                mSampleSounds[ProcessSample.stLeftJawline].sampleURIs[MetricHints.mhTapSuccess] =
                        Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.success_6);
                mSampleSounds[ProcessSample.stLeftJawline].sampleTimes[MetricHints.mhTapSuccess]= 10;

                if (mSettings.tapDistanceMM < 30.0f)
                {
                    mSampleSounds[ProcessSample.stUnderWrist].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_1);
                    mSampleSounds[ProcessSample.stUnderWrist].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stUnderWrist].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_1);
                    mSampleSounds[ProcessSample.stUnderWrist].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stForehead].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_2);
                    mSampleSounds[ProcessSample.stForehead].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stForehead].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_2);
                    mSampleSounds[ProcessSample.stForehead].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stWhitepoint].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.phonelevel);
                    mSampleSounds[ProcessSample.stWhitepoint].sampleTimes[MetricHints.mhFlatterToCalibrate]= 3.0f;

                    mSampleSounds[ProcessSample.stWhitepoint].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.flat_on_paper);
                    mSampleSounds[ProcessSample.stWhitepoint].sampleTimes[MetricHints.mhPlaceOverSurface]= 13.0f;

                    mSampleSounds[ProcessSample.stRightCheek].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_3);
                    mSampleSounds[ProcessSample.stRightCheek].sampleTimes[MetricHints.mhPlaceOverSurface]= 5.0f;

                    mSampleSounds[ProcessSample.stRightCheek].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_3);
                    mSampleSounds[ProcessSample.stRightCheek].sampleTimes[MetricHints.mhFlatterToCalibrate]= 5.0f;

                    mSampleSounds[ProcessSample.stLeftCheek].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_5);
                    mSampleSounds[ProcessSample.stLeftCheek].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stLeftCheek].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_5);
                    mSampleSounds[ProcessSample.stLeftCheek].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stRightJawline].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_4);
                    mSampleSounds[ProcessSample.stRightJawline].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stRightJawline].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_4);
                    mSampleSounds[ProcessSample.stRightJawline].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6f;

                    mSampleSounds[ProcessSample.stLeftJawline].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_6);
                    mSampleSounds[ProcessSample.stLeftJawline].sampleTimes[MetricHints.mhPlaceOverSurface]= 7.0f;

                    mSampleSounds[ProcessSample.stLeftJawline].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_6);
                    mSampleSounds[ProcessSample.stLeftJawline].sampleTimes[MetricHints.mhFlatterToCalibrate]= 7.0f;
                }
                else
                {
                    mSampleSounds[ProcessSample.stUnderWrist].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_1);
                    mSampleSounds[ProcessSample.stUnderWrist].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stUnderWrist].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_1);
                    mSampleSounds[ProcessSample.stUnderWrist].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stForehead].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_2);
                    mSampleSounds[ProcessSample.stForehead].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stForehead].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_2);
                    mSampleSounds[ProcessSample.stForehead].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stWhitepoint].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.phonelevel);
                    mSampleSounds[ProcessSample.stWhitepoint].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stWhitepoint].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.flat_on_paper_b);
                    mSampleSounds[ProcessSample.stWhitepoint].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stRightCheek].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_3);
                    mSampleSounds[ProcessSample.stRightCheek].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stRightCheek].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_3);
                    mSampleSounds[ProcessSample.stRightCheek].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stLeftCheek].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_4);
                    mSampleSounds[ProcessSample.stLeftCheek].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stLeftCheek].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_4);
                    mSampleSounds[ProcessSample.stLeftCheek].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stRightJawline].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_5);
                    mSampleSounds[ProcessSample.stRightJawline].sampleTimes[MetricHints.mhPlaceOverSurface]= 6.0f;

                    mSampleSounds[ProcessSample.stRightJawline].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_5);
                    mSampleSounds[ProcessSample.stRightJawline].sampleTimes[MetricHints.mhFlatterToCalibrate]= 6.0f;

                    mSampleSounds[ProcessSample.stLeftJawline].sampleURIs[MetricHints.mhPlaceOverSurface] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_6);
                    mSampleSounds[ProcessSample.stLeftJawline].sampleTimes[MetricHints.mhPlaceOverSurface]= 7.0f;

                    mSampleSounds[ProcessSample.stLeftJawline].sampleURIs[MetricHints.mhFlatterToCalibrate] =
                            Uri.parse("android.resource://co.getmatch.ScanApp/" + R.raw.place_over_surface_b_6);
                    mSampleSounds[ProcessSample.stLeftJawline].sampleTimes[MetricHints.mhFlatterToCalibrate]= 7.0f;
                }
            }
        }
    }

    void playHint(Context context, int processSampleId, int metricHintId)
    {
        if (!mSettings.calibrateAudio)
        {
            if (mSampleSounds != null &&  mSampleSounds[processSampleId] != null)
            {
                if (mSampleSounds[processSampleId].sampleURIs[metricHintId] != null)
                {
                    boolean playit = true;
                    double currentTime =  (double)android.os.SystemClock.elapsedRealtime() * 1.0/1000.0;

                    if (processSampleId == mLastSample && metricHintId == mLastHint)
                    {
                        playit = (((currentTime - mLastTime) * 0.5) >
                                (double)mSampleSounds[processSampleId].sampleTimes[metricHintId]);
                    }
                    if (playit)
                    {
                        mPlayer.play(context, mSampleSounds[processSampleId].sampleURIs[metricHintId],
                                false, mAttributes);

                        mLastHint = metricHintId;
                        mLastTime = currentTime;
                    }
                }

            }
        }
    }

    void pause()
    {
        if (mPlayer != null)
        {
            mPlayer.stop();
        }
    }

    float getHintTime(int processSampleId, int metricHintId)
    {
        float result = 0.0f;
        if (mSampleSounds != null &&  mSampleSounds[processSampleId] != null) {
            result = mSampleSounds[processSampleId].sampleTimes[metricHintId];
        }
        return(result);
    }


    public class SampleSounds{
        public Uri sampleURIs[];
        public float sampleTimes[];

        SampleSounds(){
            sampleURIs = new Uri[MetricHints.mhCount];
            sampleTimes = new float[MetricHints.mhCount];
            if (sampleURIs != null && sampleTimes != null)
            for(int i = 0; i < MetricHints.mhCount; i++)
            {
                sampleURIs[i] = null;
                sampleTimes[i] = 0.0f;
            }
        }
    }
}
