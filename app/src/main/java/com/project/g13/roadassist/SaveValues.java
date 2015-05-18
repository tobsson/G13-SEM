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

/**
 * Created by tobs on 2015-05-17.
 */
public class SaveValues implements Runnable {
    int speed;
    int fuel;
    int overSpeed;
    int brakeSwitch;
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
                                int speed = (int) tmpSpeed;
                                Log.i(LOG_TAG, "Speed Value: " + speed);
                            }
                            if (automotiveSignal.getSignalId() == 325) {
                                float tmpFuel = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                                int fuel = (int) tmpFuel;
                                Log.i(LOG_TAG, "Fuel Level: " + fuel);
                            }
                            if (automotiveSignal.getSignalId() == 285) {
                                overSpeed = ((Uint8) automotiveSignal.getData()).getIntValue();
                                Log.e(LOG_TAG, "OverSpeed: " + overSpeed);
                            }
                            if (automotiveSignal.getSignalId() == 317) {
                                brakeSwitch = ((Uint8) automotiveSignal.getData()).getIntValue();
                                Log.e(LOG_TAG, "BrakeSwitch: " + brakeSwitch);
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

}
