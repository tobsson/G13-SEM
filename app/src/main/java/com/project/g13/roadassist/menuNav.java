package com.project.g13.roadassist;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;


/**
 * Created by Per on 2015-05-04.
 */
public class menuNav extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

    // LogCat tag
    private static final String TAG = menuNav.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    //Rebound variables
    private static double TENSION = 800;
    private static double DAMPER = 20; //friction

    private TextView gasStationText;
    private TextView parkingText;
    private TextView restText;
    private TextView endRouteText;



    private ImageView gasBtnRb;
    private ImageView restaurantBtnRb;
    private ImageView parkingBtnRb;
    private ImageView closeMenuBtnRb;
    private ImageView endRouteBtnRb;
    private SpringSystem mSpringSystem;
    private Spring gasSpring, parkSpring, restaurantSpring, endRouteSpring;

    // JSON Node names
    private static final String TAG_LOCATION = "location";
    private static final String TAG_LAT = "latitude";
    private static final String TAG_LNG = "longtitude";

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private Button parking;
    private Button endRoute;
    private Button restaurant;
    private Button gas;
    private Button closeMenu;

    private String lat;
    private String lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menunav_layout);

/*
        parking = (Button) findViewById(R.id.restStopBtn);
        endRoute = (Button) findViewById(R.id.endRouteBtn);
        restaurant = (Button)findViewById(R.id.restaurantBtn);
        gas = (Button)findViewById(R.id.gasBtn);
        closeMenu = (Button)findViewById(R.id.returnBtn);
        */
        gasStationText = (TextView)findViewById(R.id.gasStnText);
        parkingText = (TextView)findViewById(R.id.parkingTextView);
        restText = (TextView)findViewById(R.id.parkingTextView);
        endRouteText = (TextView) findViewById(R.id.endRouteTextView);

        gasBtnRb = (ImageView) findViewById(R.id.gasBtnRb);
        restaurantBtnRb = (ImageView) findViewById(R.id.restaurantBtnRb);
        parkingBtnRb = (ImageView) findViewById(R.id.parkingBtnRb);
        closeMenuBtnRb = (ImageView) findViewById(R.id.closeMenuBtnRb);
        endRouteBtnRb = (ImageView) findViewById(R.id.endRouteBtnRb);

        endRouteBtnRb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        endRouteSpring.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        endRouteSpring.setEndValue(0f);
                        endRoute();
                        return true;


                }

                return false;
            }
        });


        gasBtnRb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        gasSpring.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        gasSpring.setEndValue(0f);
                        findGasStn();
                        return true;


                }

                return false;
            }
        });

        restaurantBtnRb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        restaurantSpring.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        restaurantSpring.setEndValue(0f);
                        findRestaurant();
                        return true;


                }

                return false;
            }
        });

        parkingBtnRb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        parkSpring.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        parkSpring.setEndValue(0f);
                        findParking();
                        return true;


                }

                return false;
            }
        });

        closeMenuBtnRb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        gasSpring.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        gasSpring.setEndValue(0f);
                        closeOverLayMenu();
                        return true;


                }

                return false;
            }
        });

        mSpringSystem = SpringSystem.create();

        gasSpring = mSpringSystem.createSpring();
        restaurantSpring = mSpringSystem.createSpring();
        parkSpring = mSpringSystem.createSpring();
        endRouteSpring = mSpringSystem.createSpring();

        gasSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                gasBtnRb.setScaleX(scale);
                gasBtnRb.setScaleY(scale);

            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
        parkSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                parkingBtnRb.setScaleX(scale);
                parkingBtnRb.setScaleY(scale);

            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
        restaurantSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                restaurantBtnRb.setScaleX(scale);
                restaurantBtnRb.setScaleY(scale);

            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
        endRouteSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                endRouteBtnRb.setScaleX(scale);
                endRouteBtnRb.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });

        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        gasSpring.setSpringConfig(config);
        parkSpring.setSpringConfig(config);
        restaurantSpring.setSpringConfig(config);
        endRouteSpring.setSpringConfig(config);

        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }


/*
        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        //String request that can be sent to google places api
                        String nearbyGas = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                                "location=" + displayLocation() + "&" +
                                "rankby=distance" + "&" +
                                "type=parking&" +
                                "key=AIzaSyDP44XNtbKmVlSxIKLpNqW5HO0PGqsydHI";


                        StringBuilder placesBuilder = new StringBuilder();
                        HttpClient placesClient = new DefaultHttpClient();
                        try {
                            //try to fetch the data
                            HttpGet placesGet = new HttpGet(nearbyGas);
                            HttpResponse placesResponse = placesClient.execute(placesGet);
                            StatusLine placeSearchStatus = placesResponse.getStatusLine();

                            if (placeSearchStatus.getStatusCode() == 200) {
                                //we have an OK response
                                HttpEntity placesEntity = placesResponse.getEntity();
                                InputStream placesContent = placesEntity.getContent();
                                InputStreamReader placesInput = new InputStreamReader(placesContent);
                                BufferedReader placesReader = new BufferedReader(placesInput);
                                String lineIn;
                                while ((lineIn = placesReader.readLine()) != null) {
                                    placesBuilder.append(lineIn);
                                }
                                Log.d(TAG, placesBuilder.toString());
                                String resultsRestStop = placesBuilder.toString();
                                try {
                                    JSONObject jObject = new JSONObject(resultsRestStop);
                                    JSONArray jResults = jObject.getJSONArray("results");
                                    JSONObject jPlaceObj = jResults.getJSONObject(0);
                                    JSONObject jGeometry = jPlaceObj.getJSONObject("geometry");
                                    JSONObject jLocation = jGeometry.getJSONObject("location");

                                    lat = jLocation.getString("lat");
                                    lng = jLocation.getString("lng");
                                    Log.d(TAG, lat + "," + lng);

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                                //Start navigation to nearest gas-station
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng + "&mode=w");
                                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                intent.setPackage("com.google.android.apps.maps");


                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                try {
                                    startActivity(intent);
                                    startService(new Intent(getApplication(), NavOverlayService.class));
                                } catch (ActivityNotFoundException ex) {
                                    try {
                                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        startActivity(unrestrictedIntent);
                                    } catch (ActivityNotFoundException innerEx) {
                                        Toast.makeText(menuNav.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                                    }
                                }


                                Thread.interrupted();


                            } else {
                                Toast.makeText(menuNav.this, "Response failed", Toast.LENGTH_SHORT);
                                Thread.interrupted();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });

        endRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menuNav.this, MainActivity.class));
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread (new Runnable() {
                    public void run() {
                        //String request that can be sent to google places api
                        String nearbyFood = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                                "location="+displayLocation()+"&" +
                                "rankby=distance" + "&" +
                                "type=restaurant&" +
                                "key=AIzaSyDP44XNtbKmVlSxIKLpNqW5HO0PGqsydHI";


                        StringBuilder placesBuilder = new StringBuilder();
                        HttpClient placesClient = new DefaultHttpClient();
                        try {
                            //try to fetch the data
                            HttpGet placesGet = new HttpGet(nearbyFood);
                            HttpResponse placesResponse = placesClient.execute(placesGet);
                            StatusLine placeSearchStatus = placesResponse.getStatusLine();

                            if (placeSearchStatus.getStatusCode() == 200) {
                                //we have an OK response
                                HttpEntity placesEntity = placesResponse.getEntity();
                                InputStream placesContent = placesEntity.getContent();
                                InputStreamReader placesInput = new InputStreamReader(placesContent);
                                BufferedReader placesReader = new BufferedReader(placesInput);
                                String lineIn;
                                while ((lineIn = placesReader.readLine()) != null) {
                                    placesBuilder.append(lineIn);
                                }
                                Log.d(TAG, placesBuilder.toString());
                                String resultsGas = placesBuilder.toString();
                                try {
                                    JSONObject jObject = new JSONObject(resultsGas);
                                    JSONArray jResults = jObject.getJSONArray("results");
                                    JSONObject jPlaceObj = jResults.getJSONObject(0);
                                    JSONObject jGeometry =  jPlaceObj.getJSONObject("geometry");
                                    JSONObject jLocation = jGeometry.getJSONObject("location");

                                    lat = jLocation.getString("lat");
                                    lng = jLocation.getString("lng");
                                    Log.d(TAG, lat+","+lng);

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                                //Start navigation to nearest gas-station
                                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng+"&mode=w");
                                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                intent.setPackage("com.google.android.apps.maps");


                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                try
                                {
                                    startActivity(intent);
                                    startService(new Intent(getApplication(), NavOverlayService.class));
                                }
                                catch(ActivityNotFoundException ex)
                                {
                                    try
                                    {
                                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        startActivity(unrestrictedIntent);
                                    }
                                    catch(ActivityNotFoundException innerEx)
                                    {
                                        Toast.makeText(menuNav.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                                    }
                                }


                                Thread.interrupted();


                            }
                            else {
                                Toast.makeText(menuNav.this, "Response failed", Toast.LENGTH_SHORT);
                                Thread.interrupted();
                            }



                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread (new Runnable() {
                    public void run() {
                        //String request that can be sent to google places api
                        String nearbyGas = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                                "location="+displayLocation()+"&" +
                                "rankby=distance" + "&" +
                                "keyword=bensinstation&" +
                                "key=AIzaSyDP44XNtbKmVlSxIKLpNqW5HO0PGqsydHI";


                        // do something
                        StringBuilder placesBuilder = new StringBuilder();
                        HttpClient placesClient = new DefaultHttpClient();
                        try {
                            //try to fetch the data
                            HttpGet placesGet = new HttpGet(nearbyGas);
                            HttpResponse placesResponse = placesClient.execute(placesGet);
                            StatusLine placeSearchStatus = placesResponse.getStatusLine();

                            if (placeSearchStatus.getStatusCode() == 200) {
                            //we have an OK response
                                HttpEntity placesEntity = placesResponse.getEntity();
                                InputStream placesContent = placesEntity.getContent();
                                InputStreamReader placesInput = new InputStreamReader(placesContent);
                                BufferedReader placesReader = new BufferedReader(placesInput);
                                String lineIn;
                                while ((lineIn = placesReader.readLine()) != null) {
                                    placesBuilder.append(lineIn);
                                }
                                Log.d(TAG, placesBuilder.toString());
                                String resultsGas = placesBuilder.toString();
                                try {
                                    JSONObject jObject = new JSONObject(resultsGas);
                                    JSONArray jResults = jObject.getJSONArray("results");
                                    JSONObject jPlaceObj = jResults.getJSONObject(0);
                                    JSONObject jGeometry =  jPlaceObj.getJSONObject("geometry");
                                    JSONObject jLocation = jGeometry.getJSONObject("location");

                                    lat = jLocation.getString("lat");
                                    lng = jLocation.getString("lng");
                                    Log.d(TAG, lat+","+lng);

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                                //Start navigation to nearest gas-station
                                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng+"&mode=w");
                                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                intent.setPackage("com.google.android.apps.maps");


                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                try
                                {
                                    startActivity(intent);
                                    startService(new Intent(getApplication(), NavOverlayService.class));
                                }
                                catch(ActivityNotFoundException ex)
                                {
                                    try
                                    {
                                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        startActivity(unrestrictedIntent);
                                    }
                                    catch(ActivityNotFoundException innerEx)
                                    {
                                        Toast.makeText(menuNav.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                                    }
                                }


                                Thread.interrupted();


                            }
                            else {
                                Toast.makeText(menuNav.this, "Response failed", Toast.LENGTH_SHORT);
                                Thread.interrupted();
                            }



                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        closeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(getApplication(), NavOverlayService.class));
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });

*/
    }
    // Method for finding gas-station coordinates and sendind intent to navigation app
    private void findGasStn() {
        new Thread (new Runnable() {
            public void run() {
                //String request that can be sent to google places api
                String nearbyGas = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location="+displayLocation()+"&" +
                        "rankby=distance" + "&" +
                        "keyword=bensinstation&" +
                        "key=AIzaSyDP44XNtbKmVlSxIKLpNqW5HO0PGqsydHI";

                String nearbyGas2 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location="+displayLocation()+"&" +
                        "radius=5000&" +
                        "types=gas_station&" +
                        "key=AIzaSyDP44XNtbKmVlSxIKLpNqW5HO0PGqsydHI";

                // do something
                StringBuilder placesBuilder = new StringBuilder();
                HttpClient placesClient = new DefaultHttpClient();
                try {
                    //try to fetch the data
                    HttpGet placesGet = new HttpGet(nearbyGas);
                    HttpResponse placesResponse = placesClient.execute(placesGet);
                    StatusLine placeSearchStatus = placesResponse.getStatusLine();

                    if (placeSearchStatus.getStatusCode() == 200) {
                        //we have an OK response
                        HttpEntity placesEntity = placesResponse.getEntity();
                        InputStream placesContent = placesEntity.getContent();
                        InputStreamReader placesInput = new InputStreamReader(placesContent);
                        BufferedReader placesReader = new BufferedReader(placesInput);
                        String lineIn;
                        while ((lineIn = placesReader.readLine()) != null) {
                            placesBuilder.append(lineIn);
                        }
                        Log.d(TAG, placesBuilder.toString());
                        String resultsGas = placesBuilder.toString();
                        try {
                            JSONObject jObject = new JSONObject(resultsGas);
                            JSONArray jResults = jObject.getJSONArray("results");
                            JSONObject jPlaceObj = jResults.getJSONObject(0);
                            JSONObject jGeometry =  jPlaceObj.getJSONObject("geometry");
                            JSONObject jLocation = jGeometry.getJSONObject("location");

                            lat = jLocation.getString("lat");
                            lng = jLocation.getString("lng");
                            Log.d(TAG, lat+","+lng);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        //Start navigation to nearest gas-station
                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng+"&mode=w");
                        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        intent.setPackage("com.google.android.apps.maps");


                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        try
                        {
                            startActivity(intent);
                            startService(new Intent(getApplication(), NavOverlayService.class));
                        }
                        catch(ActivityNotFoundException ex)
                        {
                            try
                            {
                                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                startActivity(unrestrictedIntent);
                            }
                            catch(ActivityNotFoundException innerEx)
                            {
                                Toast.makeText(menuNav.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                            }
                        }


                        Thread.interrupted();


                    }
                    else {
                        Toast.makeText(menuNav.this, "Response failed", Toast.LENGTH_SHORT);
                        Thread.interrupted();
                    }



                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void findRestaurant(){
        new Thread (new Runnable() {
            public void run() {
                //String request that can be sent to google places api
                String nearbyFood = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location="+displayLocation()+"&" +
                        "rankby=distance" + "&" +
                        "type=restaurant&" +
                        "key=AIzaSyDP44XNtbKmVlSxIKLpNqW5HO0PGqsydHI";


                StringBuilder placesBuilder = new StringBuilder();
                HttpClient placesClient = new DefaultHttpClient();
                try {
                    //try to fetch the data
                    HttpGet placesGet = new HttpGet(nearbyFood);
                    HttpResponse placesResponse = placesClient.execute(placesGet);
                    StatusLine placeSearchStatus = placesResponse.getStatusLine();

                    if (placeSearchStatus.getStatusCode() == 200) {
                        //we have an OK response
                        HttpEntity placesEntity = placesResponse.getEntity();
                        InputStream placesContent = placesEntity.getContent();
                        InputStreamReader placesInput = new InputStreamReader(placesContent);
                        BufferedReader placesReader = new BufferedReader(placesInput);
                        String lineIn;
                        while ((lineIn = placesReader.readLine()) != null) {
                            placesBuilder.append(lineIn);
                        }
                        Log.d(TAG, placesBuilder.toString());
                        String resultsGas = placesBuilder.toString();
                        try {
                            JSONObject jObject = new JSONObject(resultsGas);
                            JSONArray jResults = jObject.getJSONArray("results");
                            JSONObject jPlaceObj = jResults.getJSONObject(0);
                            JSONObject jGeometry =  jPlaceObj.getJSONObject("geometry");
                            JSONObject jLocation = jGeometry.getJSONObject("location");

                            lat = jLocation.getString("lat");
                            lng = jLocation.getString("lng");
                            Log.d(TAG, lat+","+lng);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        //Start navigation to nearest gas-station
                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng+"&mode=w");
                        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        intent.setPackage("com.google.android.apps.maps");


                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        try
                        {
                            startActivity(intent);
                            startService(new Intent(getApplication(), NavOverlayService.class));
                        }
                        catch(ActivityNotFoundException ex)
                        {
                            try
                            {
                                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                startActivity(unrestrictedIntent);
                            }
                            catch(ActivityNotFoundException innerEx)
                            {
                                Toast.makeText(menuNav.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                            }
                        }


                        Thread.interrupted();


                    }
                    else {
                        Toast.makeText(menuNav.this, "Response failed", Toast.LENGTH_SHORT);
                        Thread.interrupted();
                    }



                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

   private void findParking() {
       new Thread(new Runnable() {
           public void run() {
               //String request that can be sent to google places api
               String nearbyGas = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                       "location=" + displayLocation() + "&" +
                       "rankby=distance" + "&" +
                       "type=parking&" +
                       "key=AIzaSyDP44XNtbKmVlSxIKLpNqW5HO0PGqsydHI";


               StringBuilder placesBuilder = new StringBuilder();
               HttpClient placesClient = new DefaultHttpClient();
               try {
                   //try to fetch the data
                   HttpGet placesGet = new HttpGet(nearbyGas);
                   HttpResponse placesResponse = placesClient.execute(placesGet);
                   StatusLine placeSearchStatus = placesResponse.getStatusLine();

                   if (placeSearchStatus.getStatusCode() == 200) {
                       //we have an OK response
                       HttpEntity placesEntity = placesResponse.getEntity();
                       InputStream placesContent = placesEntity.getContent();
                       InputStreamReader placesInput = new InputStreamReader(placesContent);
                       BufferedReader placesReader = new BufferedReader(placesInput);
                       String lineIn;
                       while ((lineIn = placesReader.readLine()) != null) {
                           placesBuilder.append(lineIn);
                       }
                       Log.d(TAG, placesBuilder.toString());
                       String resultsRestStop = placesBuilder.toString();
                       try {
                           JSONObject jObject = new JSONObject(resultsRestStop);
                           JSONArray jResults = jObject.getJSONArray("results");
                           JSONObject jPlaceObj = jResults.getJSONObject(0);
                           JSONObject jGeometry = jPlaceObj.getJSONObject("geometry");
                           JSONObject jLocation = jGeometry.getJSONObject("location");

                           lat = jLocation.getString("lat");
                           lng = jLocation.getString("lng");
                           Log.d(TAG, lat + "," + lng);

                       } catch (JSONException e) {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }


                       //Start navigation to nearest gas-station
                       Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng + "&mode=w");
                       Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                       intent.setPackage("com.google.android.apps.maps");


                       intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                       try {
                           startActivity(intent);
                           startService(new Intent(getApplication(), NavOverlayService.class));
                       } catch (ActivityNotFoundException ex) {
                           try {
                               Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                               startActivity(unrestrictedIntent);
                           } catch (ActivityNotFoundException innerEx) {
                               Toast.makeText(menuNav.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                           }
                       }


                       Thread.interrupted();


                   } else {
                       Toast.makeText(menuNav.this, "Response failed", Toast.LENGTH_SHORT);
                       Thread.interrupted();
                   }


               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }).start();



   }

    private void endRoute(){

        startActivity(new Intent(menuNav.this, MainActivity.class));
    }
    private void closeOverLayMenu(){
        startService(new Intent(getApplication(), NavOverlayService.class));
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * Method to display the location on UI
     * */
    private String displayLocation() {


        String currLoc;
        Location loc = FusedLocationApi
                .getLastLocation(mGoogleApiClient);


            double lat = loc.getLatitude();
            double lng = loc.getLongitude();
            currLoc = Double.toString(lat) + "," + Double.toString(lng);

            return currLoc;

    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }




    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }



    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }





}