package com.project.g13.roadassist;

import android.util.Log;

import java.util.TimerTask;

/**
 * Created by tobs on 2015-05-25.
 */
public class Timer implements Runnable {

    /*
    Creating a boolean that is checked to see if the code shall run or not
     */
    private static volatile boolean running = true;
    private int stopTime = 0;

    private static final String LOG_TAG = "Timer Class";


    public void run(){

        /*
        Creating a timer that is set to run it's code every 5000 milliseconds (5 seconds)
         */
        java.util.Timer timer = new java.util.Timer();

        /*
        Creating the task with the code that will run every time the timer tells it to
         */
        TimerTask doTask = new TimerTask() {
            @Override
            public void run() {

                /*
                Checking the boolean to see if the code shall run or not
                 */
                if (running){
                    try {

                        /*
                        Sending values to the database as long as the timer is running
                         */
                        int time = Values.getTime();
                        ApiConnector apiConnector = new ApiConnector();
                        apiConnector.postDataGraph(time);
                        Log.d("Timer", "postDataGraph executed");

                        /*
                        Increase the "time" values that is saved and used to know where
                        in the trip the user is
                         */
                        time += 5;
                        Values.setTime(time);
                        Log.d("Timer", "Running");
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "myTask" + e);
                    }
                }
            }
        };
        timer.schedule(doTask, 0, 5000); //Time between runs in milliseconds. 1000 is 1 second.
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
