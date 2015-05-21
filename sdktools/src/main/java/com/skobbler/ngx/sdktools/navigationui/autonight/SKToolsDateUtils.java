package com.skobbler.ngx.sdktools.navigationui.autonight;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class used to manipulate date and time values
 */
final class SKToolsDateUtils {

    /**
     * the sunset hour (in 24h format) used for auto day / night mode option
     */
    protected static int AUTO_NIGHT_SUNSET_HOUR;

    /**
     * the sunrise minute (in 24h format) used for auto day / night mode option
     */
    protected static int AUTO_NIGHT_SUNRISE_MINUTE;

    /**
     * the sunset minute (in 24h format) used for auto day / night mode option
     */
    protected static int AUTO_NIGHT_SUNSET_MINUTE;

    /**
     * the sunrise hour (in 24h format) used for auto day / night mode option
     */
    protected static int AUTO_NIGHT_SUNRISE_HOUR;

    /**
     * Returns true if it is day time, false otherwise
     * @return
     */
    public static boolean isDaytime() {
        return isCurrentTimeInSunriseSunsetLimit();
    }

    private SKToolsDateUtils() {}

    /**
     * Checks if the current time of user's device is in sunrise sunset limit.
     * @return
     */
    public static boolean isCurrentTimeInSunriseSunsetLimit() {
        int currentMinutes = getMinuteOfDay() + getHourOfDay() * 60;
        int lowerMinutes = AUTO_NIGHT_SUNRISE_HOUR * 60 + AUTO_NIGHT_SUNRISE_MINUTE;
        int upperMinutes = AUTO_NIGHT_SUNSET_HOUR * 60 + AUTO_NIGHT_SUNSET_MINUTE;
        return currentMinutes >= lowerMinutes && currentMinutes < upperMinutes;
    }

    /**
     * Returns the current hour of the day as set on the device.
     * @return
     */
    public static int getHourOfDay() {
        SimpleDateFormat format = new SimpleDateFormat("H");
        return Byte.parseByte(format.format(new Date()));
    }

    /**
     * Returns the current hour of the day as set on the device.
     * @return
     */
    public static int getMinuteOfDay() {
        SimpleDateFormat format = new SimpleDateFormat("m");
        return Byte.parseByte(format.format(new Date()));
    }

}
