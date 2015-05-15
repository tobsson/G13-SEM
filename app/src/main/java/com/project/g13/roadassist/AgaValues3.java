package com.project.g13.roadassist;

import android.os.AsyncTask;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSData;
import android.swedspot.scs.data.SCSFloat;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

/**
 * Created by Christosgialias on 2015-05-15.
 */
public class AgaValues3 extends AsyncTask<Void, Void, Void>
{
    private float cSpeed;
    private int distLevel;
    @Override
    protected Void doInBackground(Void... params)
    {
        System.out.println("some shit");
        AutomotiveFactory.createAutomotiveManagerInstance(
                new AutomotiveCertificate(new byte[0]),
                new AutomotiveListener() { // Listener that observes the Signals
                    @Override
                    public void receive(final AutomotiveSignal automotiveSignal) {
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
        return null;

    }


    protected Void doInBackground2(Void... params)
    {
        System.out.println("some shit");

        AutomotiveCertificate certificate = new AutomotiveCertificate(new byte[0]);

        AutomotiveListener automotiveListener = new AutomotiveListener() { // Listener that observes the Signals
            @Override
            public void receive(final AutomotiveSignal automotiveSignal) {
                        cSpeed = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                        System.out.println("speed is" + cSpeed );
            }

            @Override
            public void timeout(int i) {
            }

            @Override
            public void notAllowed(int i) {
            }
        };

        DriverDistractionListener driverDistractionListener = new DriverDistractionListener() {       // Observe driver distraction level
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
        };

        final AutomotiveManager api = AutomotiveFactory.createAutomotiveManagerInstance(certificate, automotiveListener, driverDistractionListener);
        api.setListener(automotiveListener);



        return null;
    }

    public float getcSpeed(){
        return cSpeed;
    }

    public int getDistLevel(){
        return distLevel;
    }
}
