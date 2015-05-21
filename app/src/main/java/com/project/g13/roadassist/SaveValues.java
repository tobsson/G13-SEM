package com.project.g13.roadassist;

import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSData;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.SCSInteger;
import android.swedspot.scs.data.Uint8;
import android.util.Log;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tobs on 2015-05-17.
 */
public class SaveValues implements Runnable {
    int speed;
    int fuel;
    int overSpeed;
    int overSpeedTimes = 0;
    int brakeSwitch;
    int brakeSwitchTimes = 0;
    int dLevel;
    int time = 0;


    @SuppressWarnings("unused")
    private static final String LOG_TAG = "SaveValues";
        /*
         * Defines the code to run for this task.
         */
        @Override
        public void run() {

            // Moves the current Thread into the background
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            //Register the Automotive API
            AutomotiveFactory.createAutomotiveManagerInstance(
                    new AutomotiveCertificate(new byte[0]),
                    new AutomotiveListener() {
                        @Override
                        public void receive(final AutomotiveSignal automotiveSignal) {

                            //SCSData data = automotiveSignal.getData();
                            //Check the data for what it contains and set a value on the appropriate variable
                            if (automotiveSignal.getSignalId() == 320) {
                                float tmpSpeed = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                                speed = (int) tmpSpeed;
                            }
                            if (automotiveSignal.getSignalId() == 325) {
                                float tmpFuel = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                                fuel = (int) tmpFuel;
                            }
                            if (automotiveSignal.getSignalId() == 285) {
                                overSpeed = ((Uint8) automotiveSignal.getData()).getIntValue();
                                if(overSpeed == 1) {
                                    overSpeedTimes++;
                                }
                            }
                            if (automotiveSignal.getSignalId() == 317) {
                                brakeSwitch = ((Uint8) automotiveSignal.getData()).getIntValue();
                                if(brakeSwitch == 1) {
                                    brakeSwitchTimes++;
                                }
                            }
                        }

                        @Override
                        public void timeout(int i) {
                        }

                        @Override
                        public void notAllowed(int i) {
                        }
                    },
                    new DriverDistractionListener() {
                        @Override
                        public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                            dLevel = driverDistractionLevel.getLevel();
                            Log.i(LOG_TAG, "Distraction Value: " + dLevel);
                        }

                        @Override
                        public void lightModeChanged(LightMode lightMode) {
                        }

                        @Override
                        public void stealthModeChanged(StealthMode stealthMode) {
                        }
                    }
            ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED, AutomotiveSignalId.FMS_FUEL_LEVEL_1,
                    AutomotiveSignalId.FMS_VEHICLE_OVERSPEED, AutomotiveSignalId.FMS_BRAKE_SWITCH); // Register for the signals

        }

    //Timer task for running a method in a specified timer interval
    public void myTask(){

        Timer timer = new Timer();
        TimerTask doTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (speed > 5) {
                        postData();
                        Log.d("Timer", "postData executed");
                    }
                    Log.d("Timer", "Running");
                }catch (Exception e){
                    Log.e(LOG_TAG, "myTask" + e);
                }
            }
        };
     timer.schedule(doTask, 0, 5000); //Time between runs in milliseconds. 1000 is 1 second.
    }


    //Method for posting data to the database with values for graphtables
    public void postData(){

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://group13.comxa.com/postToTrip.php");

        //Getting the highest value of TripID (TID)
        ApiConnector ac = new ApiConnector();
        int tid = ac.GetMaxTid() + 1;
        Log.d(LOG_TAG, "Sent TID: " + tid);

        try {
            //Create strings from the integer values so they can be used in the arraylist
            String tmpTime = Integer.toString(time);
            String tmpSpeed = Integer.toString(speed);
            String tmpdLevel = Integer.toString(dLevel);
            String tmpTid = Integer.toString(tid);

            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("Time", tmpTime));
            nameValuePairs.add(new BasicNameValuePair("CSpeed", tmpSpeed));
            nameValuePairs.add(new BasicNameValuePair("distLevel", tmpdLevel));
            nameValuePairs.add(new BasicNameValuePair("TID", tmpTid));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Send data to the website and php code
            httpclient.execute(httppost);
            Log.d(LOG_TAG, "postData run" + ", time: " + tmpTime + ", CSpeed: " + tmpSpeed + ", TID: " + tmpTid);
            time += 5;
        }
        catch(Exception e)
        {
            Log.e(LOG_TAG, "postData Error:  "+e.toString());
        }
    }

}