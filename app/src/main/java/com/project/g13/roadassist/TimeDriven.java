package com.project.g13.roadassist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.TimerTask;

/**
 * Created by tobs on 2015-05-26.
 */
public class TimeDriven extends Activity implements Runnable{

    /*
    Creating a boolean that is checked to see if the code shall run or not
    */
    private static volatile boolean running = true;

    private static final String LOG_TAG = "TimeDriven Class";


    public void run() {

        /*
        Creating a timer that is set to run it's code every 5000 milliseconds (5 seconds)
         */
        java.util.Timer timer2 = new java.util.Timer();

        /*
        Creating the task with the code that will run every time the timer tells it to
         */
        TimerTask doTask2 = new TimerTask() {
            @Override
            public void run() {

                /*
                Checks how long the driver has been driving and displays a warning at
                4 hours, 4.25 hours and 4.5 hours
                 *//*
                if (Values.timeDriven >= 16200) {
                    //Call alert service final warning
                } else if (Values.timeDriven >= 15300) {
                    //Call alert service 15 minute warning
                } else if (Values.timeDriven >= 14400) {
*/

                /*Test values for testing the notification feature, 20 seconds before suggesting rest
                 */
                    if (Values.timeDriven >= 16200) {
                        //Call alert service final warning
                    } else if (Values.timeDriven >= 15300) {
                        //Call alert service 15 minute warning
                    } else if (Values.timeDriven >= 20) {
                   /*
                    Intent intent = new Intent("com.project.g13.roadassist.android.Alert30");
                    this.startService(intent);
*/

                    startService(new Intent(getApplication(), Alert30.class));
                }

                /*
                Adds 5 seconds to the timeDriven variable as long as the current trip
                is less than 4.5 hours long and the vehicle has been standing still for less
                than 60 seconds
                 */
                if (Values.timeDriven < 16200 && Values.stopTime < 60) {
                    Values.setTimeDriven();
                }

                /*
                Adds 5 seconds to the stopTime variable that keeps track of how long the
                vehicle has been standing still.
                 */
                if (Values.speed < 5) {
                    Values.setStopTime();
                }

                /*
                Sets the stopTime variable to 0 if the vehicle is moving
                 */
                if (Values.speed >= 5){
                    Values.resetStopTime();
                }
            }
        };
        timer2.schedule(doTask2, 0, 5000); //Time between runs in milliseconds. 1000 is 1 second.
    }



    public void pauseThread() throws InterruptedException
    {
        running = false;
    }

    public void resumeThread()
    {
        running = true;
    }

}
