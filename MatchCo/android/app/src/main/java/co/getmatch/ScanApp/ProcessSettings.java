package co.getmatch.ScanApp;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.DngCreator;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 Copyright (C) 2013-2017 MatchCo Inc
 All rights reserved.
 This document is proprietary information of MatchCo Inc.
 Do not distribute outside of the company without written authorization.
 Portions of this document may be protected by patent.
 Portions of this document may be covered by patent pending.
 */

/**
 * Created by young.harvill on 3/9/2017.
 */

public class ProcessSettings {
    public static final int    psDeviceUnknown = 0;
    public static final int    psDeviceGalaxyS7 = 1;
    public static final int    psDeviceGalaxyS7Edge = 2;
    public static final int    psDeviceGooglePixel = 3;
    public static final int    psDeviceGooglePixItl = 4;
    public static final int    psDeviceGooglePixXL = 5;
    public static final int    psDeviceGooglePixXLItl = 6;
    //public static final int    psDeviceLGG4 = 4001; // NOTE(steve): this is a test device

    static public int getPlatform(){
        int result = psDeviceUnknown;
        String device =android.os.Build.MODEL;
        String _device = device.substring(0, device.length()-1);
        
        if (_device.contentEquals("SM-G930"))
        {
            result = psDeviceGalaxyS7;
        }
        else if (_device.contentEquals("SM-G935"))
        {
            result = psDeviceGalaxyS7Edge;
        }
        else if (device.contentEquals("Pixel"))
        {
            result = psDeviceGooglePixel;
        }
        else if (device.contentEquals("Pixel XL"))
        {
            result = psDeviceGooglePixXL;
        }
        // else if (device.contentEquals("LG-H811"))
        // {
        //     result = psDeviceLGG4;
        // }
        return(result);

    }
}
