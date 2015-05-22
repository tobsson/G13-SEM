package com.project.g13.roadassist;

/**
 * Created by Per on 2015-05-21.
 */
public class CurrentRoute {

    public static String route;
    public static void setCurrentRoute(String r ) {
        CurrentRoute.route = r;
    }
    public static String getCurrentRoute() {
        return route;
    }
}
