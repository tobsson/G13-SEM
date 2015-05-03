package com.skobbler.ngx.sdktools.navigationui;

import android.app.Activity;
import com.skobbler.ngx.map.SKMapSurfaceView;


public class SKToolsNavigationManager {

    public SKToolsNavigationManager(Activity activity, int rootId) {
        SKToolsLogicManager.getInstance().setActivity(activity, rootId);
    }

    /**
     * Starts a route calculation.
     * @param configuration
     * @param mapView
     */
    public void launchRouteCalculation(SKToolsNavigationConfiguration configuration, SKMapSurfaceView mapView) {
        SKToolsLogicManager.getInstance().calculateRoute(configuration, mapView);
    }

    /**
     * Removes the screen with the route calculation.
     */
    public void removeRouteCalculationScreen(){
        SKToolsLogicManager.getInstance().removeRouteCalculationScreen();
    }

    /**
     * Starts the navigation
     * @param configuration
     * @param mapView
     */
    public void startNavigation(SKToolsNavigationConfiguration configuration, SKMapSurfaceView mapView) {
        SKToolsLogicManager.getInstance().startNavigation(configuration, mapView, false);
    }

    /**
     * Stops the navigation.
     */
    public void stopNavigation() {
        SKToolsLogicManager.getInstance().stopNavigation();
    }

    /**
     * Starts free drive.
     * @param configuration
     * @param mapView
     */
    public void startFreeDriveWithConfiguration(SKToolsNavigationConfiguration configuration,
                                                SKMapSurfaceView mapView) {
        SKToolsLogicManager.getInstance().startNavigation(configuration, mapView, true);
    }

    /**
     * Method that should be called when the orientation of the activity has changed.
     */
    public void notifyOrientationChanged() {
        SKToolsLogicManager.getInstance().notifyOrientationChanged();
    }

    /**
     * Sets the listener
     * @param navigationListener
     */
    public void setNavigationListener(SKToolsNavigationListener navigationListener) {
        SKToolsLogicManager.getInstance().setNavigationListener(navigationListener);
    }
}
