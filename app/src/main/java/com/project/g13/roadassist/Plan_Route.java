package com.project.g13.roadassist;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.widget.Filter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Per on 2015-03-12.
 */
public class Plan_Route extends ActionBarActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {


    //Variable storing the current route when "Search.." is pressed, everytime search is pressed

    private Button startNav;
    private Button button;
    private Button add_dest;
    private String destination;
    private AutoCompleteTextView destText;
    private RelativeLayout plan_layout;
    private static final String LOG_TAG = "PlanRouteActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;

    //Layout that is used to add new search fields
    private LinearLayout mLayout;


    private GoogleApiClient mGoogleApiClient;
    private AutoCompleteAdapter mPlaceArrayAdapter;
    //Coordinate-boundaries from which the autocomplete-textfield for destination bases the suggestions from
    private static final LatLngBounds BOUNDS_LINDHOLMEN = new LatLngBounds(
            new LatLng(57.705793, 11.936084), new LatLng(57.707389, 11.937680));

    public Plan_Route() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        plan_layout = (RelativeLayout) findViewById(R.id.plan);
        mLayout = (LinearLayout) findViewById(R.id.linearLayoutPlan);

        //View for the extra search fields
        AutoCompleteTextView acTextView = new AutoCompleteTextView(this);


        mGoogleApiClient = new GoogleApiClient.Builder(Plan_Route.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        //TODO A view for input of route and such
        destText = (AutoCompleteTextView)findViewById(R.id.destination);
        destText.setThreshold(3);
        destText.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_LINDHOLMEN, null);
        destText.setAdapter(mPlaceArrayAdapter);

        add_dest = (Button)findViewById(R.id.add_dest_btn);
        startNav = (Button)findViewById(R.id.plnSrcbutton);

                add_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.addView(createNewAutoCompleteTextView());
                Log.d(LOG_TAG, "Add AutoCompleteTextView");
            }
        });

        startNav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*
                Create a new thread
                 */
                new Thread(new Runnable() {
                    public void run() {

                        /*
                        Get the calendar and save the time and date when the route starts
                         */
                        Calendar calendar = Calendar.getInstance();
                        String date = calendar.getTime().toString();
                        Values.setRouteStart(date);
                        Log.d(LOG_TAG, "Date/Time Start: " + date);

                        /*
                        Start the timer in a new thread
                         */
                        Timer timer = new Timer();
                        Thread timerThread = new Thread(timer);
                        timerThread.start();

                    }
                }).start();
                destination = destText.getText().toString();
                //Sets current route to the class CurrentRoute
                CurrentRoute.setCurrentRoute(destination);

                Toast.makeText(Plan_Route.this,destination, Toast.LENGTH_LONG).show();
                if(destination!=null){
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+destination+"&mode=w");
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
                            Toast.makeText(Plan_Route.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                        }
                    }}
                else {
                    Toast.makeText(Plan_Route.this, "Please input a destination...", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    //Method to create a new destination field
    private AutoCompleteTextView createNewAutoCompleteTextView() {
        //Create the field and set parameters for the layout
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final AutoCompleteTextView acTextView = new AutoCompleteTextView(this);

        //Apply the layout
        acTextView.setLayoutParams(lparams);

        //Set the autocomplete function on the new field
        acTextView.setThreshold(3);
        acTextView.setOnItemClickListener(mAutocompleteClickListener);
        acTextView.setAdapter(mPlaceArrayAdapter);

        return acTextView;
    }

    //NEW STUFF

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutoCompleteAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            /*
            mNameTextView.setText(Html.fromHtml(place.getName() + ""));
            mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
            mIdTextView.setText(Html.fromHtml(place.getId() + ""));
            mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
            mWebTextView.setText(place.getWebsiteUri() + "");*/
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }


    /*
     Method for getting latitude and longtitude from an adress sent to google geocode webservice
     @param youraddress input string search query for Google Places-address search
     @return string arrray with lat[0] and lng[1] coordinates
      */
    public static String[] getLatLongFromAddress(String youraddress) {

        String[] Coordinates = null;
        double lng;
        double lat;
        String formatted = youraddress.replace(" ", "+");
        formatted = formatted.replaceAll(",","");

        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                formatted + "&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
            String longtitude = String.valueOf(lng);
            String latitude = String.valueOf(lat);

            Log.d("latitude", latitude);
            Log.d("longitude", longtitude);

            Coordinates[0]= latitude;
            Coordinates[1]= longtitude;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Coordinates!=null){
return Coordinates;}
        else return null;
    }



}




// the Adapter for the Autocompletetextview and Google Places API
class AutoCompleteAdapter extends ArrayAdapter<AutoCompleteAdapter.PlaceAutocomplete> implements Filterable {




    private static final String TAG = "AutoCompleteAdapter";
    private GoogleApiClient mGoogleApiClient;
    private AutocompleteFilter mPlaceFilter;
    private LatLngBounds mBounds;
    private ArrayList<PlaceAutocomplete> mResultList;

    /**
     * Constructor
     *
     * @param context  Context
     * @param resource Layout resource
     * @param bounds   Used to specify the search bounds
     * @param filter   Used to specify place types
     */
    public AutoCompleteAdapter(Context context, int resource, LatLngBounds bounds,
                               AutocompleteFilter filter) {
        super(context, resource);
        mBounds = bounds;
        mPlaceFilter = filter;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            mGoogleApiClient = null;
        } else {
            mGoogleApiClient = googleApiClient;
        }
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    private ArrayList<PlaceAutocomplete> getPredictions(CharSequence constraint) {
        if (mGoogleApiClient != null) {
            Log.i(TAG, "Executing autocomplete query for: " + constraint);
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                                    mBounds, mPlaceFilter);
            // Wait for predictions, set the timeout.
            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Toast.makeText(getContext(), "Error: " + status.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error getting place predictions: " + status
                        .toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();
                resultList.add(new PlaceAutocomplete(prediction.getPlaceId(),
                        prediction.getDescription()));
            }
            // Buffer release
            autocompletePredictions.release();
            return resultList;
        }
        Log.e(TAG, "Google API client is not connected.");
        return null;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    // Query the autocomplete API for the entered constraint
                    mResultList = getPredictions(constraint);
                    if (mResultList != null) {
                        // Results
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    class PlaceAutocomplete {

        public CharSequence placeId;
        public CharSequence description;

        PlaceAutocomplete(CharSequence placeId, CharSequence description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }
    }
}

