package com.project.g13.roadassist;

/**
 * Created by Christosgialias on 2015-05-15.
 */
import android.content.Context;
import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;

import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.scs.data.SCSData;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.Uint32;
import android.util.Log;

import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutomotiveManagerWrapper {

    public static final int DRIVER_DISTRACTION_SIGNAL_ID = 6666;
    private static final String TAG = AutomotiveManagerWrapper.class.getName();

    private static final AutomotiveCertificate amc = new AutomotiveCertificate(new byte[0]);
    public static AutomotiveManagerWrapper instance = null;
    public static AutomotiveManager automotiveManager;
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private float cSpeed;

    @SuppressWarnings("ResourceType")
    private AutomotiveManagerWrapper(final Context context) {
        AutomotiveListener aml = new AutomotiveListener() {
            @Override
            public void receive(AutomotiveSignal automotiveSignal) {
                final SCSData data = automotiveSignal.getData();
                cSpeed = ((SCSFloat) data).getFloatValue();
                System.out.println("speed is" + cSpeed );
            }

            @Override
            public void timeout(int i) {
            }

            @Override
            public void notAllowed(int i) {
            }
        };

        DriverDistractionListener ddl = new DriverDistractionListener() {
            @Override
            public void levelChanged(DriverDistractionLevel driverDistractionLevel) {
                int ddl = driverDistractionLevel.getLevel();

            }

            @Override
            public void lightModeChanged(LightMode lightMode) {
            }

            @Override
            public void stealthModeChanged(StealthMode stealthMode) {
            }
        };
        automotiveManager = AutomotiveFactory.createAutomotiveManagerInstance(amc, aml, ddl);
        automotiveManager.setListener(aml);
    }
}
