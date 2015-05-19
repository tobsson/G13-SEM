package com.project.g13.roadassist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by Christosgialias on 2015-05-19.
 */
public class History extends Activity{
    ListView tripListView;
    ArrayList<String> tripsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tripsList = new ArrayList<String>();
        setContentView(R.layout.activity_statistics_new);
        tripListView = (ListView)findViewById(R.id.tripsList);
        String dName = "Nick";
        new GetTripsTask().execute(dName);

        
        tripListView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {

                // selected item
                String product = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent intent = new Intent(getApplicationContext(), PreviousTripStatistics.class);
                // sending data to new activity
                Bundle b = new Bundle();
                b.putInt("key", 1); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();

            }
        });
    }

    private class GetTripsTask extends AsyncTask<String,Long,ArrayList>
    {
        @Override
        protected ArrayList doInBackground(String... params) {
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.getTrips(params[0]);
            if (jsonArray != null) {
                String s  = "";
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("TID");
                        tripsList.add(s);
                        //tripsList.add(jsonArray.get(i).toString());
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(History.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, tripsList);

            // Assign adapter to ListView, updates the listview with this info
            tripListView.setAdapter(adapter);


        }


    }



}
