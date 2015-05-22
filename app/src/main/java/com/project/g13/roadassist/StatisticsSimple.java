package com.project.g13.roadassist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;



/**
 * Created by tobs on 2015-04-20.
 */
public class StatisticsSimple extends ActionBarActivity {
    private ListView listView;
    private TextView statsText;

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

        statsText = (TextView) findViewById(R.id.statsText);

        // Loading products in Background Thread
        new getTripTask().execute(new ApiConnector());

        new getTripTableTask().execute(new ApiConnector());
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

    private class getTripTableTask extends AsyncTask<ApiConnector,Long,ArrayList>
    {

        @Override
        protected ArrayList doInBackground(ApiConnector... params) {
            ArrayList<String> result = new ArrayList<String>();
            // Put values in a JSONArray
            JSONArray jsonArray = params[0].GetTripTableData("Nick");

            if (jsonArray != null) {
                String s  = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        result.add(json.getString("Dusername"));
                        result.add(json.getString("BrakeSwitch"));
                        result.add(json.getString("OverSpeed"));
                        result.add(json.getString("StartRoute"));
                        result.add(json.getString("EndRoute"));
                        Log.d(LOG_TAG, result.toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to JSONObject " + e.toString());
                    }
                }
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList result) {

            String name = result.get(0).toString();
            String brake = result.get(1).toString();
            String ospeed = result.get(2).toString();
            String start = result.get(3).toString();
            String end = result.get(4).toString();

            String a = "Welcome " + name + ", " +
                    "\nyour last trip started at " + start + " and ended at " + end +". " +
                    "\nYou went over your vehicles speed limit a total of " + ospeed + " times and used the brakes " + brake + " times. " +
                    "\n\nTo see more statistics and chars over your previous trips select " +
                    "which one you want to see from the list below.";

            statsText.setText(a);
        }
    }

}