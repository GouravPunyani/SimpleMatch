package co.getmatch.ScanApp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

public class ReactScanModule extends ReactContextBaseJavaModule {

  private static final int SCAN_REQUEST = 8675309;
  private Promise launchPromise = null;
  private final ReactApplicationContext reactContext;

  public ReactScanModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    // let React Native know we want to listen for activity events
    reactContext.addActivityEventListener(activityEventListener);
  }

  // listens for returning from our scan activity and send the result to JavaScript land
  private final ActivityEventListener activityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      if (requestCode != SCAN_REQUEST || launchPromise == null) {
        return;
      }

      WritableMap map = Arguments.createMap();
      if (resultCode == Activity.RESULT_OK) {
        map.putBoolean("success", true);
        map.putString("filename", intent.getStringExtra("SCAN_FILENAME"));
      } else {
        map.putBoolean("success", false);
      }

      // resolve the promise
      launchPromise.resolve(map);
      launchPromise = null;
    }
  };

  @Override
  public String getName() {
    return "Scan";
  }
  
  @ReactMethod
  public void launch(Promise promise) {
    if (launchPromise != null) {
      promise.reject("already launched");
      return;
    }

    // remember this promise for when we return later
    launchPromise = promise;

    // spin up a new activity
    Activity currentActivity = getCurrentActivity();
    Intent intent = new Intent(currentActivity, ScanActivity.class);
    currentActivity.startActivityForResult(intent, SCAN_REQUEST);
  }

  @ReactMethod
  public void checkReadExternalStoragePermission(Promise promise) {
    String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    int result = PermissionChecker.checkSelfPermission(this.reactContext, permission);
    if (result == PermissionChecker.PERMISSION_GRANTED) {
      promise.resolve("granted");
    } else if (result == PermissionChecker.PERMISSION_DENIED) {
      boolean deniedBefore = ActivityCompat.shouldShowRequestPermissionRationale(getCurrentActivity(), permission);
      if (deniedBefore) {
        promise.resolve("denied");
      } else {
        promise.resolve("undetermined");
      }
    } else if (result == PermissionChecker.PERMISSION_DENIED_APP_OP) {
      promise.resolve("denied-app-op");
    } else {
      promise.resolve("unknown");
    }
  }

}