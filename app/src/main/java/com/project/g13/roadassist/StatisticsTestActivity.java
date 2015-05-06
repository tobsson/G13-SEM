package com.project.g13.roadassist;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Christosgialias on 2015-05-06.
 */
public class StatisticsTestActivity extends ActionBarActivity{
    private TextView responseView;


    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_test_activity);

        this.responseView = (TextView) this.findViewById(R.id.responseView);

        new GetAllDriversTask().execute(new ApiConnector());
    }

    public void setTextToTextView(JSONArray jsonArray){
        String s = "";
        for(int i=0; i<jsonArray.length();i++){
            JSONObject json = null;
            try{
                json = jsonArray.getJSONObject(i);
                s = s +
                        "Name: "+json.getString("First Name")+" "+json.getString("Last Name")+"\n"+
                        "Fleet Manager: "+json.getString("Fleet Manager")+"\n\n";

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private class GetAllDriversTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params){
            return params[0].getAllDrivers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){
            setTextToTextView(jsonArray);
        }
    }
}
