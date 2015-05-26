package com.project.g13.roadassist;



import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by tobs on 2015-05-26.
 */
public class Alert30 extends Service {

    public WindowManager windowManager;
    public WindowManager.LayoutParams params;
    public Button alertButton;

    private static final String LOG_TAG = "Alert30";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "onCreate: ");
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        alertButton = new Button(this);
        alertButton.setText("This is the text i want to be visible");

        //overLayMenu.setAlpha(0);
        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER | Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        //this code is for when overlay is pressed
        alertButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(LOG_TAG, "onTouch: Pressing the button!!!!!11");
                        //Removes the overLayMenu-button by stopping the service
                        stopService(new Intent(getApplication(), Alert30.class));

                        return true;

                }
                return false;
            }


        });
        windowManager.addView(alertButton, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (alertButton != null)
            windowManager.removeView(alertButton);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}