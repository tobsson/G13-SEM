package com.project.g13.roadassist;

import android.content.Context;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.util.Log;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

/**
 * Created by tobs on 2015-05-07.
 */

public class AgaValues {
    float speed;
    int dLevel;
    private static final String LOG_TAG = "AgaValues";

    public Float truckSpeed() {

        AutomotiveFactory.createAutomotiveManagerInstance(
                new AutomotiveCertificate(new byte[0]),
                new AutomotiveListener() {
                    @Override
                    public void receive(final AutomotiveSignal automotiveSignal) {
                        speed =((SCSFloat) automotiveSignal.getData()).getFloatValue();
                        Log.i(LOG_TAG, "Speed Value: " + speed);
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
        ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED); // Register for the speed signal
        return speed;
    }

}