package com.project.g13.roadassist;

import android.util.Log;

/**
 * Created by nti on 2015-05-21.
 */
public class GetValues {
    ins distLevel;

    public int getDist(){
        AutomotiveFactory.createAutomotiveManagerInstance(
                new AutomotiveCertificate(new byte[0]),
                new DriverDistractionListener() {
                    @Override
                    public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                        distLevel = driverDistractionLevel.getLevel();
                    }
                }
        );
        return distLevel;
    }
}
