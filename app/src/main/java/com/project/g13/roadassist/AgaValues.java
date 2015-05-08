package com.project.g13.roadassist;

import android.content.Context;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.util.Log;
import android.os.AsyncTask;
import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.swedspot.automotiveapi.*;
import android.swedspot.automotiveapi.unit.*;
import android.swedspot.automotiveapi.*;
import android.swedspot.scs.data.*;
import android.widget.TextView;


import com.swedspot.vil.policy.*;
import com.swedspot.vil.distraction.*;

/**
 * Created by tobs on 2015-05-07.
 * Modifed by Chris on 2015-05-08
 */
public class AgaValues {
    Float cSpeed = null;
    int distLevel;
    int overspeedInt;
    boolean cOverspeed;

    protected void Signals() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... objects) {
                // Access to Automotive API

                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() { // Listener that observes the Signals
                            @Override
                            public void receive(final AutomotiveSignal automotiveSignal) {
                                new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        cSpeed =((SCSFloat) automotiveSignal.getData()).getFloatValue();
                                    }
                                };
                            }

                            @Override
                            public void timeout(int i) {
                            }

                            @Override
                            public void notAllowed(int i) {
                            }
                        },
                        new DriverDistractionListener() {       // Observe driver distraction level
                            @Override
                            public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                                new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        distLevel = driverDistractionLevel.getLevel();
                                    }
                                };
                            }

                            @Override
                            public void lightModeChanged(LightMode lightMode) {

                            }

                            @Override
                            public void stealthModeChanged(StealthMode stealthMode) {

                            }
                        }
                ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);

                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() { // Listener that observes the Signals
                            @Override
                            public void receive(final AutomotiveSignal automotiveSignal) {
                                new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        overspeedInt =((SCSInteger) automotiveSignal.getData()).getIntValue();
                                        if(overspeedInt == 1){
                                            cOverspeed = true;
                                        } else {
                                            cOverspeed = false;
                                        }
                                    }
                                };
                            }

                            @Override
                            public void timeout(int i) {
                            }

                            @Override
                            public void notAllowed(int i) {
                            }
                        },
                        new DriverDistractionListener() {       // Observe driver distraction level
                            @Override
                            public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                                /*new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        distLevel = driverDistractionLevel.getLevel();
                                    }
                                };*/
                            }

                            @Override
                            public void lightModeChanged(LightMode lightMode) {

                            }

                            @Override
                            public void stealthModeChanged(StealthMode stealthMode) {

                            }
                        }
                ).register(AutomotiveSignalId.FMS_VEHICLE_OVERSPEED);// Register for the speed signal
                return null;
            }
            // And go!
        }.execute();}


    public Float getCSpeed() {
        return cSpeed;
    }


    public int getDistLevel() {
        return distLevel;
    }

    public boolean getCOverspeed() {
        return cOverspeed;
    }
}

