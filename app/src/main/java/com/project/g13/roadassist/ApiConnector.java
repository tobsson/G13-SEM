package com.project.g13.roadassist;

import android.os.NetworkOnMainThreadException;
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
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tobs on 2015-05-07.
 */
public class ApiConnector {

    @SuppressWarnings("unused")
    private static final String LOG_TAG = "ApiConnector";

    
    public JSONArray GetTripData(String s) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getTripData.php");

        HttpEntity httpEntity = null;

        try
        {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("Dname", s));
            Log.e(LOG_TAG, "value pair " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

    public JSONArray GetGraphDataSpeed(String tid) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getGraphDataSpeed.php");

        HttpEntity httpEntity = null;

        try
        {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("TID", tid));
            //Log.e(LOG_TAG, "value pair " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e(LOG_TAG, "entity " + (new UrlEncodedFormEntity(nameValuePairs)).toString());

            Log.e(LOG_TAG, "a " + httppost.toString());
            //Send data to the website and php code
            HttpResponse httpResponse = httpclient.execute(httppost);
            Log.e(LOG_TAG, "b " + httpResponse.toString());

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());

            //Log Errors Here

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in http connection 2 " + e.toString());
        } catch (NetworkOnMainThreadException e){
            Log.e(LOG_TAG, "Error in http connection 3 " + e.toString());
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

    public JSONArray GetGraphDataDistraction(String tid) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getGraphDataDistraction.php");

        HttpEntity httpEntity = null;

        try
        {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("TID", tid));
            Log.e(LOG_TAG, "value pair " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpclient.execute(httppost);

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());

            //Log Errors Here

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in http connection 2 " + e.toString());
        } catch (NetworkOnMainThreadException e){
            Log.e(LOG_TAG, "Error in http connection 3 " + e.toString());
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

    public JSONArray GetTripDataOverspeed(String s) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getTripDataOverSpeed.php");

        HttpEntity httpEntity = null;

        try
        {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("TID", s));
            Log.e(LOG_TAG, "value pair " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

    public JSONArray GetTripDataBrakeswitch(String s) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getTripDataBrakeSwitch.php");

        HttpEntity httpEntity = null;

        try
        {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("TID", s));
            Log.e(LOG_TAG, "value pair " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

    public String insertNewUser(String[] credentials) {


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://group13.comxa.com/insert-db.php");
            try {

                String usern = credentials[0];
                String pass = credentials[1];
                String name = credentials[0];
                String surname = credentials[1];

                Log.e(LOG_TAG, "usern pass" + usern + " " + pass);

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Dusername", usern));
                nameValuePairs.add(new BasicNameValuePair("Dpassword", pass));
                nameValuePairs.add(new BasicNameValuePair("Dname", name));
                nameValuePairs.add(new BasicNameValuePair("Dsurname", surname));

                Log.e(LOG_TAG, "value pair" + nameValuePairs.toString());
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();


            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return "success";
        }

    public int GetMaxTid() {
        // URL for getting all customers
        String url = "http://group13.comxa.com/getMaxTid.php";
        int value = -1;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();

            Log.d(LOG_TAG, "HTTP Part Done MAX(tid)");

        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());
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

                //Convert JSONarray to JSONObject to int
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        value = json.getInt("MAX(tid)");
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to JSONObject " + e.toString());
                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 1 " + e.toString());
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 2 " + e.toString());
            }
        }

        return value;
    }

    public int GetMaxTidUser(String user) {
        // URL for getting all customers
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getMaxTidUser.php");
        int value = -1;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("User", user));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient

            HttpResponse httpResponse = httpClient.execute(httppost);

            httpEntity = httpResponse.getEntity();

            Log.d(LOG_TAG, "HTTP Part Done MAX(tid) User");

        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());
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

                //Convert JSONarray to JSONObject to int
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        value = json.getInt("MAX(tid)");
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to JSONObject " + e.toString());
                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 1 " + e.toString());
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 2 " + e.toString());
            }
        }

        return value;
    }

    public JSONArray GetTripTableData(String s) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getTripTable.php");

        HttpEntity httpEntity = null;

        try
        {
            int tid = GetMaxTidUser(s);
            String t = Integer.toString(tid);

            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("Dusername", s));
            nameValuePairs.add(new BasicNameValuePair("TID", t));
            Log.e(LOG_TAG, "TripTableData Post " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpclient.execute(httppost);

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());
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

    public JSONArray logIn(String username) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/login.php");

        HttpEntity httpEntity = null;

        try {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", username));
            Log.e(LOG_TAG, "value pair " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
                Log.d(LOG_TAG, jsonArray.toString());
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 1 " + e.toString());
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in converting string to jsonArray 2 " + e.toString());
            }
        }
        return jsonArray;
    }

    public JSONArray GetAverageSpeed(String s) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getTripDataAverageSpeed.php");

        HttpEntity httpEntity = null;

        try
        {
           //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TID", s));
            Log.e(LOG_TAG, "Trip Data Post " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpclient.execute(httppost);

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());
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

    public JSONArray GetAverageDistraction(String s) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getGraphDataDistractionAvarage.php");

        HttpEntity httpEntity = null;

        try
        {
            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TID", s));
            Log.e(LOG_TAG, "Trip Data Post " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpclient.execute(httppost);

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());
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

    public JSONArray GetTripTableData2(String t) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/getTripTable2.php");

        HttpEntity httpEntity = null;

        try
        {

            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TID", t));
            Log.e(LOG_TAG, "TripTableData Post " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpclient.execute(httppost);

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());
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

    //Method for posting data to the database with values for the trip table
    public void postDataTrip(){

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://group13.comxa.com/postToTrip.php");

        //Getting the highest value of TripID (TID)
        int tid = GetMaxTid() + 1;
        Log.d(LOG_TAG, "Sent TID to Graph: " + tid);


        try {
            //Create strings from the integer values so they can be used in the arraylist
            String tmpTid = Integer.toString(tid);
            String tmpBrakeSwitch = Integer.toString(Values.getBrakeSwitchTimes());
            String tmpOverSpeed = Integer.toString(Values.getOverSpeedTimes());
            String dUserName = "Nick";
            String start = Values.getRouteStart();
            String end = Values.getRouteEnd();


            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
            nameValuePairs.add(new BasicNameValuePair("TID", tmpTid));
            nameValuePairs.add(new BasicNameValuePair("BrakeSwitch", tmpBrakeSwitch));
            nameValuePairs.add(new BasicNameValuePair("OverSpeed", tmpOverSpeed));
            nameValuePairs.add(new BasicNameValuePair("Dusername", dUserName));
            nameValuePairs.add(new BasicNameValuePair("StartRoute", start));
            nameValuePairs.add(new BasicNameValuePair("EndRoute", end));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Send data to the website and php code
            httpclient.execute(httppost);
            //Print the values that are sent to the log
            Log.d(LOG_TAG, "postDataTrip run" + ", TID: " + tmpTid + ", BrakeSwitch: " + tmpBrakeSwitch
                    + ", OverSpeed: " + tmpOverSpeed + ", Username: " + dUserName
                    + ", StartRoute: " + start + ", EndRoute:" + end);
        } catch (Exception e) {
            Log.e(LOG_TAG, "postDataTrip Error:  " + e.toString());
        }

    }

    //Method for posting data to the database with values for the graph table
    public void postDataGraph(int time){

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://group13.comxa.com/postToGraph.php");

        //Getting the highest value of TripID (TID)
        ApiConnector ac = new ApiConnector();
        int tid = ac.GetMaxTid() + 1;
        //Log.d(LOG_TAG, "Sent TID to Graph: " + tid);

        try {
            //Create strings from the integer values so they can be used in the arraylist
            String tmpTime = Integer.toString(time);
            String tmpSpeed = Integer.toString(Values.getSpeed());
            String tmpdLevel = Integer.toString(Values.getdLevel());
            String tmpTid = Integer.toString(tid);

            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("Time", tmpTime));
            nameValuePairs.add(new BasicNameValuePair("CSpeed", tmpSpeed));
            nameValuePairs.add(new BasicNameValuePair("distLevel", tmpdLevel));
            nameValuePairs.add(new BasicNameValuePair("TID", tmpTid));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Send data to the website and php code
            httpclient.execute(httppost);
            //Print the values that are sent to the log
            Log.d(LOG_TAG, "postDatagraph run" + ", time: " + tmpTime + ", CSpeed: " + tmpSpeed + ", TID: " + tmpTid);
            time += 5;
        }
        catch(Exception e)
        {
            Log.e(LOG_TAG, "postDataGraph Error:  "+e.toString());
        }
    }

    public JSONArray checkIfUserExists(String t) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://group13.comxa.com/CheckUser.php");

        HttpEntity httpEntity = null;

        try
        {

            //ArrayList with post values for the graphtable
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("Dusername", t));
            Log.e(LOG_TAG, "TripTableData Post " + nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpclient.execute(httppost);

            httpEntity = httpResponse.getEntity();
            Log.d(LOG_TAG, "HTTP Part Done");

        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error in http connection 1 " + e.toString());
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
