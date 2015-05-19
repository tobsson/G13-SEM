package com.project.g13.roadassist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Christosgialias on 2015-05-19.
 */
public class StatisticsNew extends Activity{
    ListView tripListView;
    ArrayList<String> tripsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_new);
        tripListView = (ListView)findViewById(R.id.tripsList);
        String dName = "Nick";
        new GetTripsTask().execute(dName);

        //tripListView.setOnItemClickListener(this);

    }

    private class GetTripsTask extends AsyncTask<String,Long,ArrayList>
    {
        @Override
        protected ArrayList doInBackground(String... params) {
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.getTrips(params[0]);
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    try {
                        tripsList.add(jsonArray.get(i).toString());
                        Log.d("LOG_TAG", jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return tripsList;
        }
        @Override
        protected void onPostExecute(ArrayList tripsList) {

            //Updates the adapter with the values from the arraylist
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(StatisticsNew.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, tripsList);

            // Assign adapter to ListView, updates the listview with this info
            tripListView.setAdapter(adapter);


        }


    }


}
