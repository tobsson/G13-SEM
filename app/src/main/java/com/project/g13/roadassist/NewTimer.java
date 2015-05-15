package com.project.g13.roadassist;


import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Christosgialias on 2015-05-15.
 */
public class NewTimer {


    private float cSpeed;
    private int distLevel;

    public void myAsynchronousTask() {
        final String TAG = "signals";
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            AgaValues3 values = new AgaValues3();
                            values.doInBackground();
                            cSpeed = values.getcSpeed();
                            distLevel = values.getDistLevel();
                            System.out.println(cSpeed + " " + distLevel);
                            values.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //this runs every 10 seconds. Feel free to change it
    }
    public int getDistLevel() {
        return distLevel;
    }

    public float getcSpeed() {
        return cSpeed;
    }
}
