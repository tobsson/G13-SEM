package com.project.g13.roadassist;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by tobs on 2015-04-20.
 */
public class StatisticsSimple extends ListActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticssimple
        );

        Line l = new Line();
        LinePoint p = new LinePoint();
        p.setX(0);
        p.setY(5);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(8);
        p.setY(8);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(10);
        p.setY(4);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(12);
        p.setY(1);
        l.addPoint(p);
        l.setColor(Color.GREEN);

        LineGraph li = (LineGraph) findViewById(R.id.graph);
        li.addLine(l);
        li.setRangeY(0, 10);
        li.setLineToFill(0);
        li.showHorizontalGrid(true);
        li.showMinAndMaxValues(true);
        li.setGridColor(Color.RED);
        li.setTextColor(Color.RED);
        li.setTextSize(50);


        // Get ListView object from xml
        this.listView = (ListView) this.findViewById(android.R.id.list);

        // Loading products in Background Thread
        new GetAllDriversTask().execute(new ApiConnector());

        }


    //Async task for getting data from the mysql database
    private class GetAllDriversTask extends AsyncTask<ApiConnector,Long,ArrayList>
    {
        @Override
        protected ArrayList doInBackground(ApiConnector... params) {
            ArrayList<String> driversList = new ArrayList<String>();
            // it is executed on Background thread
            JSONArray jsonArray = params[0].GetAllDrivers();
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    try {
                        driversList.add(jsonArray.get(i).toString());
                        Log.d("MySQL_List LineArray", jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("MySQL_List", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return driversList;
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

}