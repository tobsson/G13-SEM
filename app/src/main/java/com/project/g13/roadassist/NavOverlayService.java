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
    import android.widget.ImageView;

    public class NavOverlayService extends Service {

        public WindowManager windowManager;
        public ImageView overLayMenu;
        public WindowManager.LayoutParams params;

        @Override
        public void onCreate() {
            super.onCreate();

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            overLayMenu = new ImageView(this);
            overLayMenu.setImageResource(R.drawable.overlaymenu);
            //overLayMenu.setAlpha(0);
            params= new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            params.x = 0;
            params.y = 0;

            //this code is for when overlay is pressed
            overLayMenu.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //windowManager.removeView(overLayMenu);


                            //Removes the overLayMenu-button by stopping the service
                            stopService(new Intent(getApplication(), NavOverlayService.class));
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.setComponent(new ComponentName("com.project.g13.roadassist", "com.project.g13.roadassist.menuNav"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                            return true;

                    }
                    return false;
                }


            });
            windowManager.addView(overLayMenu, params);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (overLayMenu != null)
                windowManager.removeView(overLayMenu);
        }



        @Override
        public IBinder onBind(Intent intent) {
            // TODO Auto-generated method stub
            return null;
        }
    }

