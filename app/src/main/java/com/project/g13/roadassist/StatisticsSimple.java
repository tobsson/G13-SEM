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
        listView = (ListView) findViewById(android.R.id.list);

        // Loading products in Background Thread
        new MySQL_List().execute();
        }


    class MySQL_List extends AsyncTask<String, Void, String> {
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

            return result;
        }

        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {
                    List<String> values = new ArrayList<String>();
                    values = driverslist;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(StatisticsSimple.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, values);

                    // Assign adapter to ListView
                    listView.setAdapter(adapter);
                }
            });
            }
        }
}