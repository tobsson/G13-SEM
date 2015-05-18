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


    //@SuppressWarnings("unused")
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
                                //Log.i(LOG_TAG, "Speed Value: " + speed);
                            }
                            if (automotiveSignal.getSignalId() == 325) {
                                float tmpFuel = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                                fuel = (int) tmpFuel;
                                //Log.i(LOG_TAG, "Fuel Level: " + fuel);
                            }
                            if (automotiveSignal.getSignalId() == 285) {
                                overSpeed = ((Uint8) automotiveSignal.getData()).getIntValue();
                                overSpeedTimes++;
                                //Log.i(LOG_TAG, "OverSpeed: " + overSpeed);
                            }
                            if (automotiveSignal.getSignalId() == 317) {
                                brakeSwitch = ((Uint8) automotiveSignal.getData()).getIntValue();
                                brakeSwitchTimes++;
                                //Log.i(LOG_TAG, "BrakeSwitch: " + brakeSwitch);
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

    public int getSpeed(){
        return speed;
    }

    public int getFuel(){
        return fuel;
    }

    public int getOverSpeed() {
        return overSpeed;
    }

    public int getOverSpeedTimes() {
        return overSpeedTimes;
    }

    public int getBrakeSwitch() {
        return brakeSwitch;
    }

    public int getBrakeSwitchTimes() {
        return brakeSwitchTimes;
    }

    public int getdLevel() {
        return dLevel;
    }

    public void myTask(){

        Timer timer = new Timer();
        TimerTask doTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Log.i("Timer", "Speed Value: " + speed);
                    Log.i("Timer", "Fuel Level: " + fuel);
                }catch (Exception e){

                }
            }
        };
     timer.schedule(doTask, 0, 5000);
    }


/**
    public void postData(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://xxxxxxx.com/postdata.php");

        String tmpSpeed = "";
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("Time", ""));
            nameValuePairs.add(new BasicNameValuePair("Cspeed", tmpSpeed.valueOf(speed)));
            nameValuePairs.add(new BasicNameValuePair("", city));
            nameValuePairs.add(new BasicNameValuePair("Comment", comment));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error:  "+e.toString());
        }
    }
 **/
}