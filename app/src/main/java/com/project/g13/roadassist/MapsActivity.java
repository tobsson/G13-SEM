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
import com.skobbler.ngx.map.SKPOICluster;
import com.skobbler.ngx.map.SKScreenPoint;
import com.skobbler.ngx.navigation.SKAdvisorSettings;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SKMapViewHolder mapHolder = (SKMapViewHolder)
                findViewById(R.id.map_surface_holder);
        mapView = mapHolder.getMapSurfaceView();
        mapView.setMapSurfaceListener(this);
        Toast.makeText(getApplicationContext(), "Loading Map...", Toast.LENGTH_SHORT).show();



        /**
         * Method to launch route calculation, I set predefined route inside the method for test
         * this crashes the app atm by a nullpointer exception from the method calculateRoute() method
         * located inside the SKMaps library. Uncomment the line below to see the error.
         */
        launchRouteCalculation();

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

    private void launchRouteCalculation() {
        // get a route settings object and populate it with the desired properties
        SKRouteSettings route = new SKRouteSettings();
        // set start and destination points
        route.setStartCoordinate(new SKCoordinate(57.708220, 11.935218));
        route.setDestinationCoordinate(new SKCoordinate(57.709699, 11.943752));
        // set the number of routes to be calculated
        route.setNoOfRoutes(1);
        // set the route mode
        route.setRouteMode(SKRouteSettings.SKRouteMode.CAR_FASTEST);
        // set whether the route should be shown on the map after it's computed
        route.setRouteExposed(true);
        // set the route listener to be notified of route calculation
        // events
        SKRouteManager.getInstance().setRouteListener(this);
        //Apparently you have to set advisorsettings before calculating route
        SKRouteManager.getInstance().setAudioAdvisorSettings(new SKAdvisorSettings());
        // pass the route to the calculation routine

        /**
         * Test if the route object is null or not.
         */
        if(route==null){
            Toast.makeText(getApplicationContext(), "Route is null!", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getApplicationContext(), "Route is not null! yeeey!", Toast.LENGTH_SHORT).show();
            /**
             * Try catch block only to make the app not crash
             */
            try {
                SKRouteManager.getInstance().calculateRoute(route);
            }
            catch (NullPointerException npe) {
            }


        }
    }


    @Override
    public void onActionPan() {

    }

    @Override
    public void onActionZoom() {

    }

    @Override
    public void onSurfaceCreated() {
        //System.out.print("Surface created");
        if(SKMaps.getInstance().isSKMapsInitialized()){
            Toast.makeText(getApplicationContext(), "SKMaps initialized!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "SKMaps is NOT initialized!", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onRouteCalculationCompleted(SKRouteInfo skRouteInfo) {
        Toast.makeText(getApplicationContext(), "Route Calculation completed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRouteCalculationFailed(SKRoutingErrorCode skRoutingErrorCode) {
        Toast.makeText(getApplicationContext(), "Route Calculation failed!", Toast.LENGTH_SHORT).show();
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
