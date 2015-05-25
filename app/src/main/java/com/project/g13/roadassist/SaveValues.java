package com.project.g13.roadassist;

import android.os.AsyncTask;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSData;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.SCSInteger;
import android.swedspot.scs.data.Uint8;
import android.util.Log;
import android.widget.NumberPicker;

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
 * Class that runs code for the AGA-signals
 */

public class SaveValues implements Runnable {
    int speed;
    int speed2 = -1;
    int fuel;
    int overSpeed;
    int overSpeed2;
    int brakeSwitch;
    int brakeSwitch2;
    int dLevel;


    @SuppressWarnings("unused")
    private static final String LOG_TAG = "SaveValues";
        /*
         * Defines the code to run for this task.
         */
        @Override
        public void run() {
            /*
            Moves the current Thread into the background
             */
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            /*
            Register the Automotive API
             */
            AutomotiveFactory.createAutomotiveManagerInstance(
                    new AutomotiveCertificate(new byte[0]),
                    new AutomotiveListener() {
                        @Override
                        public void receive(final AutomotiveSignal automotiveSignal) {

                            /*
                            Check the data for what it contains and set a value on the appropriate variable
                             */
                            if (automotiveSignal.getSignalId() == 320) {
                                float tmpSpeed = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                                speed = (int) tmpSpeed;

                                /*
                                If the speed changes set the new value
                                 */
                                if (speed != speed2) {
                                    Values.setSpeed(speed);
                                    Log.d(LOG_TAG, "Speed : " + speed);
                                    speed2 = speed;
                                }
                            }

                            if (automotiveSignal.getSignalId() == 325) {
                                float tmpFuel = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                                fuel = (int) tmpFuel;
                            }

                            if (automotiveSignal.getSignalId() == 285) {
                                overSpeed = ((Uint8) automotiveSignal.getData()).getIntValue();

                                /*
                                If Overspeed is triggered increment the value by 1
                                 */
                                if (overSpeed == 1 && overSpeed2 == 1) {
                                    int ost = Values.getOverSpeedTimes();
                                    ost++;
                                    Log.d(LOG_TAG, "ost : " + ost);
                                    Values.setOverSpeedTimes(ost);
                                    overSpeed2++;
                                }
                                if (overSpeed == 0) {
                                    overSpeed2 = 1;
                                }
                            }

                            if (automotiveSignal.getSignalId() == 317) {
                                brakeSwitch = ((Uint8) automotiveSignal.getData()).getIntValue();

                                /*
                                If Brakeswitch is triggered increment the value by 1
                                 */
                                if (brakeSwitch == 1 && brakeSwitch2 == 1) {
                                    int bst = Values.getBrakeSwitchTimes();
                                    bst += brakeSwitch;
                                    Log.d(LOG_TAG, "bst: " + bst);
                                    Values.setBrakeSwitchTimes(bst);
                                    brakeSwitch2++;
                                }
                                if (brakeSwitch == 0) {
                                    brakeSwitch2 = 1;
                                }
                            }
                        }

                        @Override
                        public void timeout ( int i){
                        }

                        @Override
                        public void notAllowed ( int i){
                        }

                    }, new DriverDistractionListener() {
                        @Override
                        public void levelChanged (
                        final DriverDistractionLevel driverDistractionLevel){
                            dLevel = driverDistractionLevel.getLevel();
                            Values.setdLevel(dLevel);
                            Log.i(LOG_TAG, "Distraction Value: " + dLevel);
                        }

                        @Override
                        public void lightModeChanged (LightMode lightMode){
                        }

                        @Override
                        public void stealthModeChanged (StealthMode stealthMode){
                        }
                    }).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED, AutomotiveSignalId.FMS_FUEL_LEVEL_1,
                    AutomotiveSignalId.FMS_VEHICLE_OVERSPEED, AutomotiveSignalId.FMS_BRAKE_SWITCH); // Register the specific signals we want

        }
}