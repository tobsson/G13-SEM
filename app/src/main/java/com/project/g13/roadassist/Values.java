package com.project.g13.roadassist;

/**
 * Created by tobs on 2015-05-21.
 */
public class Values {
    public static int overSpeedTimes;
    public static int brakeSwitchTimes;
    public static String routeStart;
    public static String routeEnd;

    public Values() {
    //Prevent instantiation
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
}
