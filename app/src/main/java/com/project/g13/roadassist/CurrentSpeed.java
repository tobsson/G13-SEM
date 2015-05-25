package com.project.g13.roadassist;

import android.util.Log;

/**
 * Created by Per on 2015-05-22.
 */
public class CurrentSpeed {


        public static int speed;
        public static void setCurrentSpeed(int r) {
            CurrentSpeed.speed = r;
            Log.d("SPEED", "Speed is: " + speed);

        }
        public static int getCurrentSpeed() {
            return speed;
        }
    }




