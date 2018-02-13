package co.getmatch.ScanApp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;


public class ReactScanViewManager extends SimpleViewManager<FrameLayout> {

  public static final String REACT_CLASS = "ScanView";
  private static final int FRAME_ID = 10101010;
  private static final int VIEW_ID = 10101011;
  private static final int FRAGMENT_ID = 10101012;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public FrameLayout createViewInstance(ThemedReactContext context) {
    Activity activity = context.getCurrentActivity();
    SwatchView swatchView = new SwatchView(activity);
    // return swatchView;

    // View view = new View(activity);
    // view.setId(VIEW_ID);

    FrameLayout frame = new FrameLayout(activity);
    frame.setId(FRAME_ID);
    frame.addView(swatchView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    // ScanFragment scanFragment = ScanFragment.newInstance();

    // activity
    //   .getFragmentManager()
    //   .beginTransaction()
    //   // .add(FRAGMENT_ID, scanFragment)
    //   .replace(VIEW_ID, scanFragment)
    //   .commit();
            
    return frame;
    // return new View(context);
  }

}