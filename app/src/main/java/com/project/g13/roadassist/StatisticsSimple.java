package com.project.g13.roadassist;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;


/**
 * Created by tobs on 2015-04-20.
 */
public class StatisticsSimple extends ActionBarActivity {
    private ListView listView;

    private static final String LOG_TAG = "StatisticsSimple";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticssimple);



        // Get ListView object from xml
        this.listView = (ListView) this.findViewById(android.R.id.list);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Find the value of clicked item
                String val = (String) parent.getItemAtPosition(position);
                Log.d(LOG_TAG, "Value is " + val);

                //Open a new activity and send the value of the clicked itema
                Intent i = new Intent(getApplicationContext(), DetailedStatistics.class);
                i.putExtra("lTID", val);
                startActivity(i);

            }

        });

        // Loading products in Background Thread
        new getTripTask().execute(new ApiConnector());
        }




    //Async task for getting data from the mysql database
    private class getTripTask extends AsyncTask<ApiConnector,Long,ArrayList>
    {

        @Override
        protected ArrayList doInBackground(ApiConnector... params) {
            ArrayList<String> tripList = new ArrayList<String>();
            // Put values in a JSONArray
            JSONArray jsonArray = params[0].GetTripData("Nick");

            if (jsonArray != null) {
                String s  = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("TID");
                        tripList.add(s);
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to JSONObject " + e.toString());
                    }
                }
            }
            return tripList;
        }
        @Override
        protected void onPostExecute(ArrayList driversList) {

            //Updates the adapter with the values from the arraylist
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(StatisticsSimple.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, driversList);

            // Assign adapter to ListView, updates the listview with this info
            listView.setAdapter(adapter);


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
            //overspeedCount = parseInt(overspdCount.toString());
            Log.d(LOG_TAG, overspdCount.toString());
        }
    }



}