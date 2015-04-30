package com.project.g13.roadassist;

/**
 * Created by tobs on 2015-04-27.
 */

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Background Async Task to Load all product by making HTTP Request
 * */
public class MySQL_List extends AsyncTask<String, String, String> {

    /**
     * getting All products from url
     * */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = StatisticsSimple.jParser.makeHttpRequest(StatisticsSimple.url_all_drivers, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(StatisticsSimple.TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                StatisticsSimple.products = json.getJSONArray(StatisticsSimple.TAG_DUSERNAME);

                // looping through All Products
                for (int i = 0; i < StatisticsSimple.products.length(); i++) {
                    JSONObject c = StatisticsSimple.products.getJSONObject(i);

                    // Storing each json item in variable
                    String dpassword = c.getString(StatisticsSimple.TAG_DPASSWORD);
                    String firstname = c.getString(StatisticsSimple.TAG_DNAMES);
                    String surname = c.getString(StatisticsSimple.TAG_DSURNAME);
                    String musername = c.getString(StatisticsSimple.TAG_MUSERNAME);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(StatisticsSimple.TAG_DPASSWORD, dpassword);
                    map.put(StatisticsSimple.TAG_DNAMES, firstname);
                    map.put(StatisticsSimple.TAG_DSURNAME, surname);
                    map.put(StatisticsSimple.TAG_MUSERNAME, musername);

                    // adding HashList to ArrayList
                    StatisticsSimple.driversList.add(map);
                }
            } else {
                    //Nothing found
                    //TO-DO
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // updating UI from Background Thread
        runOnUiThread(new Runnable() {
            public void run() {
                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        StatisticsSimple.this, driversList,
                        R.layout.list_item, new String[] { TAG_PID,
                        TAG_NAME},
                        new int[] { R.id.pid, R.id.name });
                // updating listview
                StatisticsSimple.setListAdapter(adapter);
            }
        });

    }

}
