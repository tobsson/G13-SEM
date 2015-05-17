package com.project.g13.roadassist;

import android.app.ActionBar;
import android.app.ListActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;
import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

import java.util.ArrayList;



/**
 * Created by tobs on 2015-04-20.
 */
public class StatisticsSimple extends ActionBarActivity {
    private ListView listView;
    private TextView ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticssimple);

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

        SaveValues values = new SaveValues();
        values.run();
        //new UpdateSpeed().execute();

        // Get a reference to the text field
        //ds = (TextView)findViewById(R.id.textView);
/**
        new AsyncTask() {
            protected Object doInBackground(Object... objects) {
                AgaValues av = new AgaValues();
                final Float speed;
                speed = av.truckSpeed();
                return speed;
            }
            @Override
            protected void onPostExecute(Float speed){
                ds.setText(String.valueOf(speed));
            }
        }.execute();
**/

      /**
        new AsyncTask() {
            @Override
            protected Object doInBackground (Object... objects) {
                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() {
                            @Override
                            public void receive (final AutomotiveSignal automotiveSignal) {
                                ds.post( new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        ds.setText(String.format("%.1f km/h", ((SCSFloat) automotiveSignal.getData()).getFloatValue()));
                                    }
                                });
                            }

                            @Override
                            public void timeout (int i) { }

                                @Override
                                public void notAllowed (int i) { }
                            },
                                    new DriverDistractionListener() {
                                @Override
                                public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                                    ds.post(new Runnable() { // Post the result back to the View/UI thread
                                        public void run() {
                                            ds.setTextSize(driverDistractionLevel.getLevel() * 10.0F + 12.0F);
                                        }
                                    });
                            }

                            @Override
                            public void lightModeChanged(LightMode lightMode) { }

                            @Override
                            public void stealthModeChanged(StealthMode stealthMode) { }
                        }
                ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED); // Register for the speed signal
                return null;
            }
        }.execute();
**/
        }
/**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_map:
                //composeMessage();
                return true;
            case R.id.action_help:
                openHelp();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_about:
                openAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearch() {
        Intent i = new Intent(StatisticsSimple.this, Plan_Route.class);
        startActivity(i);
    }

    private void openHelp() {
        Intent i = new Intent(StatisticsSimple.this, Help.class);
        startActivity(i);
    }

    private void openAbout() {
        Intent i = new Intent(StatisticsSimple.this, About.class);
        startActivity(i);
    }

    private void openSettings() {
        Intent i = new Intent(StatisticsSimple.this, Settings.class);
        startActivity(i);
    }
**/
    //Async task for getting data from the mysql database
    private class GetAllDriversTask extends AsyncTask<ApiConnector,Long,ArrayList>
    {
        @Override
        protected ArrayList doInBackground(ApiConnector... params) {
            ArrayList<String> driversList = new ArrayList<String>();
            // Put values in a JSONArray
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
/**
    private class UpdateSpeed extends AsyncTask<AgaValues, Void, Float> {
        @Override
        protected Float doInBackground(AgaValues... params) {
            Float speed = params[0].truckSpeed();
            Log.i("StatisticsSimple", "Speed Value: " + speed.toString());
            return speed;
        }
        @Override
        protected void onPostExecute (Float speed) {
            ds.setText(String.valueOf(speed));
        }
    }

    private class UpdateSpeed extends AsyncTask<Void,Void,Float>{
    public Float speed;
    @Override
    protected Float doInBackground(Void... test) {

        AutomotiveFactory.createAutomotiveManagerInstance(
                new AutomotiveCertificate(new byte[0]),
                new AutomotiveListener() {
                    @Override
                    public void receive(final AutomotiveSignal automotiveSignal) {
                        speed =((SCSFloat) automotiveSignal.getData()).getFloatValue();
                        Log.i("StatisticsSimple", "Speed Value: " + speed.toString());
                    }

                    @Override
                    public void timeout(int i) {
                    }

                    @Override
                    public void notAllowed(int i) {
                    }
                },
                new DriverDistractionListener() {
                    @Override
                    public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {

                    }

                    @Override
                    public void lightModeChanged(LightMode lightMode) {
                    }

                    @Override
                    public void stealthModeChanged(StealthMode stealthMode) {
                    }
                }
        ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED); // Register for the speed signal
        return speed;
    }
        @Override
        protected void onPostExecute (Float speed) {
            ds.setText(String.valueOf(speed));
            Log.i("onPostExecute", "Speed Value: " + speed.toString());
        }
    }
 **/
}