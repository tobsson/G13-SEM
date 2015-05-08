package com.project.g13.roadassist;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by tobs on 2015-05-07.
 */
public class ApiConnector {

    public JSONArray GetAllDrivers()
    {
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

            Log.d("ApiConnector", "HTTP Part Done");

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            Log.e("ApiConnector", "Error in http connection 1 " + e.toString());

            //Log Errors Here

        } catch (IOException e) {
            Log.e("ApiConnector", "Error in http connection 2 " + e.toString());
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
                Log.e("ApiConnector",  jsonArray.toString());
            } catch (JSONException e) {
                Log.e("ApiConnector", "Error in converting string to jsonArray 1 " + e.toString());
            } catch (IOException e) {
                Log.e("ApiConnector", "Error in converting string to jsonArray 2 " + e.toString());
            }
        }

        return jsonArray;


    }

    public JSONArray GetCustomerDetails(int CustomerID)
    {
        // URL for getting all customers
        String url = "http://192.168.0.2/php/getCustomer.php?CustomerID="+CustomerID;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        } catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }



}
