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
import java.util.ArrayList;



/**
 * Created by tobs on 2015-04-20.
 * This class shows some of the statistics of the latest trip and a list of all previous trips
 */
public class StatisticsSimple extends ActionBarActivity {

    private ListView listView;
    private TextView statsText;
    private TextView statsText2, statsText3;

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
        statsText2 = (TextView) findViewById(R.id.statsText2);
        statsText3 = (TextView) findViewById(R.id.statsText3);

        // Loading products in Background Thread

        new getTripTask().execute(MainActivity.getDusername());
        new getTripTableTask().execute(MainActivity.getDusername());
        }




    //Async task for getting data from the mysql database
    private class getTripTask extends AsyncTask<String,Long,ArrayList> {



        @Override
        protected ArrayList doInBackground(String... params) {

            JSONArray jsonArray = null;
            ArrayList<String> tripList = new ArrayList<String>();

            try {
                // Put values in a JSONArray
                ApiConnector connector = new ApiConnector();
                jsonArray = connector.GetTripData(params[0]);
            } catch(Exception e) {
                Log.d(LOG_TAG, "getTripTask 1: " + e);
            }
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
        protected void onPostExecute(ArrayList tripsList) {
            try {
                new getAverageSpeed().execute(tripsList.get(0).toString());
                new getAverageDistraction().execute(tripsList.get(0).toString());

                //Updates the adapter with the values from the arraylist
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StatisticsSimple.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, tripsList);

                // Assign adapter to ListView, updates the listview with this info
                listView.setAdapter(adapter);
            } catch(Exception e) {
                Log.d(LOG_TAG, "getTripTask onPostExecute: " + e);
            }


        }
    }

    private class getTripTableTask extends AsyncTask<String,Long,ArrayList>
    {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> result = new ArrayList<String>();
            JSONArray jsonArray = null;

            try {
                // Put values in a JSONArray
                ApiConnector connector = new ApiConnector();
                jsonArray = connector.GetTripTableData(params[0]);
            } catch(Exception e){
                Log.d(LOG_TAG, "getTripTableTask 1: " + e);
            }


            if (jsonArray != null) {
                String s  = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json;
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

            try {
                String name = result.get(0).toString();
                String brake = result.get(1).toString();
                String ospeed = result.get(2).toString();
                String start = result.get(3).toString();
                String end = result.get(4).toString();

                String a = "Welcome " + name + ", " +
                        "\nyour last trip started at " + start + " and ended at " + end + ". " +
                        "\nYou went over your vehicles speed limit a total of " + ospeed + " times and used the brakes " + brake + " times. ";

                statsText.setText(a);
            } catch(Exception e) {
                Log.d(LOG_TAG, "getTripTableTask onPostExecute: " + e);
            }
        }
    }

     class getAverageSpeed extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> avgSpeed = new ArrayList<String>();
            JSONArray jsonArray = null;

            try {
                // Put values in a JSONArray
                ApiConnector connector = new ApiConnector();
                jsonArray = connector.GetAverageSpeed(params[0]);
            } catch(Exception e) {
                Log.d(LOG_TAG, "getAvarageSpeed 1:  + e");
            }

            if (jsonArray != null) {
                String s = "";

                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(0);
                        s = json.getString("AverageSpeed");
                        avgSpeed.add(s);
                        Log.d(LOG_TAG, jsonArray.get(0).toString());
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "Error converting to ArrayList " + e.toString());
                    }


            }
            return avgSpeed;
        }
        @Override
        protected void onPostExecute(ArrayList avgSpeed) {
            try {
                //overspeedCount = parseInt(overspdCount.toString());
                statsText2.setText("Your average speed was " + avgSpeed.get(0).toString() + "km/h");
            } catch(Exception e) {
                Log.d(LOG_TAG, "getAvarageSpeed onPostExecute" + e);
            }


        }
    }

    class getAverageDistraction extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> avgDist = new ArrayList<String>();
            JSONArray jsonArray = null;

            try {
                // Put values in a JSONArray
                ApiConnector connector = new ApiConnector();
                jsonArray = connector.GetAverageDistraction(params[0]);
            } catch(Exception e){
                Log.d(LOG_TAG, "getAvarageDistraction 1: " + e);
            }

            if (jsonArray != null) {
                String s = "";

                JSONObject json = null;
                try {
                    json = jsonArray.getJSONObject(0);
                    s = json.getString("AverageDist");
                    avgDist.add(s);
                    Log.d(LOG_TAG, jsonArray.get(0).toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error converting to ArrayList " + e.toString());
                }


            }
            return avgDist;
        }
        @Override
        protected void onPostExecute(ArrayList avgDist) {
            try {
                statsText3.setText("Your average Distraction Level was " + avgDist.get(0).toString() + "\n\nTo see more statistics and charts over your previous trips select " +
                        "the one you want to view from the list below.");
                Log.e(LOG_TAG, "average Dist " + avgDist.toString());
            } catch(Exception e){
                Log.d(LOG_TAG, "getAvarageSpeed onPostExecute: " + e);
            }

        }
    }

}