package com.project.g13.roadassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.skobbler.ngx.*;
import com.skobbler.ngx.SKCoordinate;
import com.skobbler.ngx.SKMaps;
import com.skobbler.ngx.map.SKAnnotation;
import com.skobbler.ngx.map.SKCoordinateRegion;
import com.skobbler.ngx.map.SKMapCustomPOI;
import com.skobbler.ngx.map.SKMapPOI;
import com.skobbler.ngx.map.SKMapSurfaceListener;
import com.skobbler.ngx.map.SKMapSurfaceView;
import com.skobbler.ngx.map.SKMapViewHolder;
import com.skobbler.ngx.map.SKMapViewStyle;
import com.skobbler.ngx.map.SKPOICluster;
import com.skobbler.ngx.map.SKScreenPoint;
import com.skobbler.ngx.navigation.SKAdvisorSettings;
import com.skobbler.ngx.navigation.SKNavigationSettings;
import com.skobbler.ngx.routing.SKRouteInfo;
import com.skobbler.ngx.routing.SKRouteJsonAnswer;
import com.skobbler.ngx.routing.SKRouteListener;
import com.skobbler.ngx.routing.SKRouteManager;
import com.skobbler.ngx.routing.SKRouteSettings;


import java.io.File;

public class MapsActivity extends Activity implements SKMapSurfaceListener, SKRouteListener {

    /**
     * Surface view for displaying the map
     */
    private SKMapSurfaceView mapView;

    /**
     * advisorsettings for navigation, voice and such
     */
    private SKAdvisorSettings advisorSettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SKMapViewHolder mapHolder = (SKMapViewHolder)
                findViewById(R.id.map_surface_holder);
        mapView = mapHolder.getMapSurfaceView();
        mapView.setMapSurfaceListener(this);

        //SKRouteManager.getInstance().setRouteListener(this);
        Toast.makeText(getApplicationContext(), "Loading Map...", Toast.LENGTH_SHORT).show();
        mapView.getMapSettings().setCurrentPositionShown(true);
        mapView.setZoom(20);


        /**
         * COMMENT OUT THIS LINE BELOW TO USE THE ROUTE CALCULATION WHICH CRASHES THE APP ATM
         */
        //launchRouteCalculation();



       /*
        Button navBtn = (Button)findViewById(R.id.navBtn);
        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You clicked the button...", Toast.LENGTH_SHORT).show();
            }
        });
        */

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    public void onActionPan() {

    }

    @Override
    public void onActionZoom() {

    }

    /**
     * When map surface has been created this method is called
     */
    @Override
    public void onSurfaceCreated() {

        Toast.makeText(getApplicationContext(), "Map loaded! Yeey!", Toast.LENGTH_SHORT).show();

        /**
         * Display message if the SKMaps are initalized or not
         */
        if (SKMaps.getInstance().isSKMapsInitialized()){
            Toast.makeText(getApplicationContext(),"SKMaps initialized!", Toast.LENGTH_SHORT).show();}
        else {
            Toast.makeText(getApplicationContext(),"SKMaps is NOT initialized!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onScreenOrientationChanged() {

    }

    @Override
    public void onMapRegionChanged(SKCoordinateRegion skCoordinateRegion) {

    }

    @Override
    public void onMapRegionChangeStarted(SKCoordinateRegion skCoordinateRegion) {

    }

    @Override
    public void onMapRegionChangeEnded(SKCoordinateRegion skCoordinateRegion) {

    }

    @Override
    public void onDoubleTap(SKScreenPoint skScreenPoint) {


        }

    /**
     * When you click the map this code happens
     */
    @Override
    public void onSingleTap(SKScreenPoint skScreenPoint) {


    Toast.makeText(getApplicationContext(), "You clicked the map! Yeey!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRotateMap() {

    }

    @Override
    public void onLongPress(SKScreenPoint skScreenPoint) {

    }

    @Override
    public void onInternetConnectionNeeded() {

    }

    @Override
    public void onMapActionDown(SKScreenPoint skScreenPoint) {

    }

    @Override
    public void onMapActionUp(SKScreenPoint skScreenPoint) {

    }

    @Override
    public void onPOIClusterSelected(SKPOICluster skpoiCluster) {

    }

    @Override
    public void onMapPOISelected(SKMapPOI skMapPOI) {

    }

    @Override
    public void onAnnotationSelected(SKAnnotation skAnnotation) {

    }

    @Override
    public void onCustomPOISelected(SKMapCustomPOI skMapCustomPOI) {

    }

    @Override
    public void onCompassSelected() {

    }

    @Override
    public void onCurrentPositionSelected() {

    }

    @Override
    public void onObjectSelected(int i) {

    }

    @Override
    public void onInternationalisationCalled(int i) {

    }

    @Override
    public void onBoundingBoxImageRendered(int i) {

    }

    @Override
    public void onGLInitializationError(String s) {

    }

    /**
     * this is the method for calculating routes.
     */
    public void launchRouteCalculation() {

        /**
         * Code to specify path to files on device
         */
        String mapResDirPath;
        File externalDir = getExternalFilesDir(null);

        if (externalDir != null) {
            mapResDirPath = externalDir + "/SKMaps/";
        } else {
            mapResDirPath = getFilesDir() + "/SKMaps/";
        }
        /**
         * According to some user on SO setting advisorsettings before launching route calculation is necessary
         */
        advisorSettings = new SKAdvisorSettings();
        advisorSettings.setLanguage(SKAdvisorSettings.SKAdvisorLanguage.LANGUAGE_EN);
        advisorSettings.setAdvisorConfigPath(mapResDirPath + "/SKMaps/Advisor/");
        advisorSettings.setResourcePath(mapResDirPath + "/SKMaps/Advisor/Languages/");
        advisorSettings.setAdvisorVoice("en");
        advisorSettings.setAdvisorType(SKAdvisorSettings.SKAdvisorType.AUDIO_FILES);
        SKRouteManager.getInstance().setAudioAdvisorSettings(advisorSettings);



        // get a route settings object and populate it with the desired properties
        SKRouteSettings route = new SKRouteSettings();

        // set start and destination points
        route.setStartCoordinate(new SKCoordinate(57.708266, 11.935341));
        route.setDestinationCoordinate(new SKCoordinate(57.715098, 11.945383));
        // set the number of routes to be calculated
        route.setNoOfRoutes(1);
        // set the route mode
        route.setRouteMode(SKRouteSettings.SKRouteMode.CAR_FASTEST);
        // set whether the route should be shown on the map after it's computed
        route.setRouteExposed(true);
        // set the route listener to be notified of route calculation
        // events
        SKRouteManager.getInstance().setRouteListener(this);
        // pass the route to the calculation routine, this it the line not working at the moment
        SKRouteManager.getInstance().calculateRoute(route);

    }


    @Override
    public void onRouteCalculationCompleted(SKRouteInfo skRouteInfo) {
        Toast.makeText(getApplicationContext(),"Route calculated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRouteCalculationFailed(SKRoutingErrorCode skRoutingErrorCode) {
        Toast.makeText(getApplicationContext(),"Route calculation failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAllRoutesCompleted() {

    }

    @Override
    public void onServerLikeRouteCalculationCompleted(SKRouteJsonAnswer skRouteJsonAnswer) {

    }

    @Override
    public void onOnlineRouteComputationHanging(int i) {

    }
}
