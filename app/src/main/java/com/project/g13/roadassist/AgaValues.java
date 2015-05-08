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
/**
public class AgaValues {
    Float speed = null;

    public Float truckSpeed() {

        AutomotiveFactory.createAutomotiveManagerInstance(
                new AutomotiveCertificate(new byte[0]),
                new AutomotiveListener() {
                    @Override
                    public void receive (final AutomotiveSignal automotiveSignal) {
                        ds.post( new Runnable() { // Post the result back to the View/UI thread
                            public void run() {
                                ds.setText(String.format("%.1f km/h", ((SCSFloat) automotiveSignal.getData()).getFloatValue()));
                            }
                        });
                    }

                    @Override
                    public void timeout (int i) { }

                    @Override
                    public void notAllowed (int i) { }
                }
        ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED); // Register for the speed signal
        return speed;

    }

}

**/