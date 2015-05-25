package com.project.g13.roadassist;

/**
 * Created by tobs on 2015-05-21.
 * Class for saving different values over the lifespan of the application. Prevents instantiation
 * so no values change unless the app is restarted.
 */
public class Values {
    public static int overSpeedTimes;
    public static int brakeSwitchTimes;
    public static String routeStart;
    public static String routeEnd;
    public static int speed;
    public static int overSpeed;
    public static int dLevel;
    public static int time = 0;

    public Values() {
    /*
    Prevent instantiation
     */
    }
    public static void setBrakeSwitchTimes(int brakeSwitchTimes) {
        Values.brakeSwitchTimes = brakeSwitchTimes;
    }

    public static void setOverSpeedTimes(int overSpeedTimes) {
        Values.overSpeedTimes = overSpeedTimes;
    }

    public static int getOverSpeedTimes() {
        return overSpeedTimes;
    }

    public static int getBrakeSwitchTimes() {
        return brakeSwitchTimes;
    }

    public static String getRouteStart() {
        return routeStart;
    }

    public static void setRouteStart(String routeStart) {
        Values.routeStart = routeStart;
    }

    public static String getRouteEnd() {
        return routeEnd;
    }

    public static void setRouteEnd(String routeEnd) {
        Values.routeEnd = routeEnd;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Values.speed = speed;
    }

    public static int getOverSpeed() {
        return overSpeed;
    }

    public static void setOverSpeed(int overSpeed) {
        Values.overSpeed = overSpeed;
    }

    public static int getdLevel() {
        return dLevel;
    }

    public static void setdLevel(int dLevel) {
        Values.dLevel = dLevel;
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        Values.time = time;
    }
}
