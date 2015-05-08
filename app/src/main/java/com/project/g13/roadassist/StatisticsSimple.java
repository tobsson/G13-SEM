package com.project.g13.roadassist;

import android.app.ActionBar;
import android.app.ListActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

import java.util.ArrayList;



/**
 * Created by tobs on 2015-04-20.
 */
public class StatisticsSimple extends ListActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticssimple);

        // get action bar
        ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
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