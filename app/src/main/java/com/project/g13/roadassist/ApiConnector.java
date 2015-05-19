package com.project.g13.roadassist;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tobs on 2015-05-07.
 */
public class ApiConnector {

    @SuppressWarnings("unused")
    private static final String LOG_TAG = "ApiConnector";

    public JSONArray GetAllDrivers() {
        // URL for getting all customers
        String url = "http://group13.comxa.com/all_drivers2.php";

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();

            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());

            //Log Errors Here

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in http connection 2 " + e.toString());
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.d(LOG_TAG, "Entity Response  : " + entityResponse);

                jsonArray = new JSONArray(entityResponse);
                Log.d(LOG_TAG,  jsonArray.toString());
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 1 " + e.toString());
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 2 " + e.toString());
            }
        }

        return jsonArray;


    }
    
    public JSONArray GetTripData(String s) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getTripData.php");

        HttpEntity httpEntity = null;

        try
        {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("Dname", s));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Send data to the website and php code
            HttpResponse httpResponse = httpclient.execute(httppost);

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());

            //Log Errors Here

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in http connection 2 " + e.toString());
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.d(LOG_TAG, "Entity Response  : " + entityResponse);

                jsonArray = new JSONArray(entityResponse);
                Log.d(LOG_TAG,  jsonArray.toString());
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 1 " + e.toString());
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 2 " + e.toString());
            }
        }

        return jsonArray;


    }


}
