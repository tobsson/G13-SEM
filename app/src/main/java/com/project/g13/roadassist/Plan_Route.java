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


    private Button startNav;
    private Button button;
    private Button add_dest;
    private String destination;
    private AutoCompleteTextView destText;
    private RelativeLayout plan_layout;
    private static final String LOG_TAG = "PlanRouteActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private LinearLayout mLayout;


    private GoogleApiClient mGoogleApiClient;
    private AutoCompleteAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    public Plan_Route() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        plan_layout = (RelativeLayout) findViewById(R.id.plan);
        mLayout = (LinearLayout) findViewById(R.id.linearLayoutPlan);
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
                BOUNDS_MOUNTAIN_VIEW, null);
        destText.setAdapter(mPlaceArrayAdapter);

        add_dest = (Button)findViewById(R.id.add_dest_btn);
        startNav = (Button)findViewById(R.id.plnSrcbutton);

        // destText.setAdapter(new AutoCompleteAdapter(this));



        /*
        destText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(), "Position clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        */

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
                destination = destText.getText().toString();
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





        startNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination = destText.getText().toString();
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

    private AutoCompleteTextView createNewAutoCompleteTextView() {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final AutoCompleteTextView acTextView = new AutoCompleteTextView(this);
        acTextView.setLayoutParams(lparams);

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


    // Method for getting latitude and longtitude from an adress sent to google geocode webservice
    public static void getLatLongFromAddress(String youraddress) {


        double lng;
        double lat;
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                youraddress + "&sensor=false";
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}




// And the corresponding Adapter
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













/*
    private LayoutInflater mInflater;
    private Geocoder mGeocoder;
    private StringBuilder mSb = new StringBuilder();
    public AutoCompleteAdapter(final Context context) {
        super(context, -1);
        mInflater = LayoutInflater.from(context);
        mGeocoder = new Geocoder(context);
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final TextView tv;
        if (convertView != null) {
            tv = (TextView) convertView;
        } else {
            tv = (TextView) mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        tv.setText(createFormattedAddressFromAddress(getItem(position)));
        return tv;
    }
    private String createFormattedAddressFromAddress(final Address address) {
        mSb.setLength(0);
        final int addressLineSize = address.getMaxAddressLineIndex();
        for (int i = 0; i < addressLineSize; i++) {
            mSb.append(address.getAddressLine(i));
            if (i != addressLineSize - 1) {
                mSb.append(", ");
            }
        }
        return mSb.toString();
    }
    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                List<Address> addressList = null;
                if (constraint != null) {
                    try {
                        addressList = mGeocoder.getFromLocationName((String) constraint, 5);
                    } catch (IOException e) {
                    }
                }
                if (addressList == null) {
                    addressList = new ArrayList<Address>();
                }
                final FilterResults filterResults = new FilterResults();
                filterResults.values = addressList;
                filterResults.count = addressList.size();
                return filterResults;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(final CharSequence contraint, final FilterResults results) {
                clear();
                for (Address address : (List<Address>) results.values) {
                    add(address);
                }
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
            @Override
            public CharSequence convertResultToString(final Object resultValue) {
                return resultValue == null ? "" : ((Address) resultValue).getAddressLine(0);
            }
        };
        return myFilter;
    }
}
*/