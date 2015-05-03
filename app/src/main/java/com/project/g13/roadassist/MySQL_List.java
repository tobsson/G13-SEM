package com.project.g13.roadassist;

/**
 * Created by tobs on 2015-04-27.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Background Async Task to Load all product by making HTTP Request
 * */
public class MySQL_List extends AsyncTask<String, Void, String> {
    private ListView listView;
    JSONObject jsonObject;
    ArrayList<String> driverslist = new ArrayList<String>();
    /**
     * Getting all names on drivers
     */
    protected String doInBackground(String... args) {
        String result = "";
        InputStream is = null;
        JSONArray jArray = null;
        HttpResponse response = null;
        Log.d("MySQL_List", "Do In Background");

        //http post
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://group13.comxa.com/all_drivers2.php");
            response = httpclient.execute(httppost);
            Log.d("MySQL_List", "HTTP Part Done");
        } catch (Exception e) {
            Log.e("MySQL_List", "Error in http connection " + e.toString());
        }

        //parse json data
        try {
            String jsonString = EntityUtils.toString(response.getEntity());
            Log.d("MySQL_List First Print",jsonString);
            //jsonObject = new JSONObject(jsonString);
            //Log.d("MySQL_List Second Print", jsonObject.toString());
            jArray = new JSONArray(jsonString);
            Log.d("MySQL_List Second Print", jArray.toString());
            if (jArray != null) {
                int len = jArray.length();
                for (int i = 0; i < len; i++) {
                    try {
                        driverslist.add(jArray.get(i).toString());
                        Log.d("MySQL_List LineArray", jArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("MySQL_List", "Error converting to ArrayList " + e.toString());
                    }
                }
            }
        } catch (IOException e) {
            Log.e("MySQL_List", "Error converting response to string " + e.toString());

        } catch (JSONException e) {
            Log.e("MySQL_List", "Error parsing data " + e.toString());
        }

        //parse json data to ArrayList

        return result;
    }


}
