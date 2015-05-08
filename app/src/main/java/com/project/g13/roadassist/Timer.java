package com.project.g13.roadassist;

import android.os.Handler;
import android.util.Log;

/**
 * Created by Christosgialias on 2015-05-07.
 */
public class Timer {

    private String speedString;


    Handler mHandler;
    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            Log.e("Handlers", "Calls");
            /** Do something **/
            AgaValues values = new AgaValues();

            float cSpeed = values.getCSpeed();
            int distLevel = values.getDistLevel();
            boolean cOverspeed = values.getCOverspeed();

            speedString = Float.toString(cSpeed);


            mHandler.postDelayed(mRunnable, 1000);
        }



    };


    public String getSpeedString(){
        return speedString;
    }
}
