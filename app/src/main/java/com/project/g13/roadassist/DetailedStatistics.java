package com.project.g13.roadassist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Created by tobs on 2015-05-19.
 */
public class DetailedStatistics extends ActionBarActivity {
    private TextView t;
    private ArrayList<String> speedGraphlist;
    private ArrayList<String> distractionGraphlist;
    private int brakeCount = 0;
    private int overspeedCount = 0;

    private static final String LOG_TAG = "DetailedStatistics";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticsdetailed);
        ApiConnector connector = new ApiConnector();
        t = (TextView) findViewById(R.id.passedTID);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("lTID");

            Log.d(LOG_TAG, "Passed from list" + value);
            t.setText("Passed TID: " + value);

            //JSONArray jsonArray = connector.GetGraphDataSpeed("2");
            //Log.d(LOG_TAG, jsonArray.toString());
            new getGraphDataSpeedTask().execute(value);
            new getGraphDataDistractionTask().execute(value);
            //brakeCount = parseInt(connector.GetTripDataBrakeswitch(value).toString());
            Log.d(LOG_TAG, "Brake count" + brakeCount);
            overspeedCount = parseInt(connector.GetTripDataOverspeed(value).toString());
            Log.d(LOG_TAG, "Overspeed count" + overspeedCount);
        }
    }

    private class getGraphDataSpeedTask extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> speedTimeList = new ArrayList<String>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.GetGraphDataSpeed(params[0]);

            if (jsonArray != null) {
                String s = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("CSpeed");
                        speedTimeList.add(s);
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return speedTimeList;
        }
        @Override
        protected void onPostExecute(ArrayList speedTimeList) {
            speedGraphlist = speedTimeList;
            Log.d(LOG_TAG, speedTimeList.toString());
        }
    }

    private class getGraphDataDistractionTask extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> distractionTimeList = new ArrayList<String>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.GetGraphDataDistraction(params[0]);

            if (jsonArray != null) {
                String s = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("distLevel");
                        distractionTimeList.add(s);
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return distractionTimeList;
        }
        @Override
        protected void onPostExecute(ArrayList distractionTimeList) {
            distractionGraphlist = distractionTimeList;
            Log.d(LOG_TAG, distractionTimeList.toString());
        }
    }

    private class getDataOverspeedCount extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> overspdCount = new ArrayList<String>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.GetTripDataOverspeed(params[0]);

            if (jsonArray != null) {
                String s = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("OverSpeed");
                        overspdCount.add(s);
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return overspdCount;
        }
        @Override
        protected void onPostExecute(ArrayList overspdCount) {
            overspeedCount = parseInt(overspdCount.toString());
            Log.d(LOG_TAG, overspdCount.toString());
        }
    }
}
