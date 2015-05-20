package com.project.g13.roadassist;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Created by tobs on 2015-05-19.
 */
public class DetailedStatistics extends ActionBarActivity {
    private TextView t;
    private ArrayList<Entry> speedGraphlist;
    private ArrayList<String> distractionGraphlist;
    private int brakeCount = 0;
    private int overspeedCount = 0;
    private LineChart mChart;
    private static final String LOG_TAG = "DetailedStatistics";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticsdetailed);
        ApiConnector connector = new ApiConnector();
        //t = (TextView) findViewById(R.id.passedTID);
        mChart = (LineChart)findViewById(R.id.chart1);

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable value highlighting
        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        //mChart.setMarkerView(mv);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mChart.setHighlightIndicatorEnabled(false);

        // x-axis limit line
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLabelPosition.POS_RIGHT);
//        llXAxis.setTextSize(10f);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.addLimitLine(llXAxis);



        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setAxisMaxValue(110f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(false);
        //leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        //leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        //l.setForm(Legend.LegendForm.LINE);





        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("lTID");

            Log.d(LOG_TAG, "Passed from list" + value);
            //t.setText("Passed TID: " + value);

            //JSONArray jsonArray = connector.GetGraphDataSpeed("2");
            //Log.d(LOG_TAG, jsonArray.toString());
            new getGraphDataSpeedTask().execute(value);
            new getGraphDataDistractionTask().execute(value);
            this.setData(speedGraphlist);
            //brakeCount = parseInt(connector.GetTripDataBrakeswitch(value).toString());
            Log.d(LOG_TAG, "Brake count" + brakeCount);
            //overspeedCount = parseInt(connector.GetTripDataOverspeed(value).toString());
            Log.d(LOG_TAG, "Overspeed count" + overspeedCount);
        }
    }

    private class getGraphDataSpeedTask extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<Entry> speedTimeList = new ArrayList<Entry>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.GetGraphDataSpeed(params[0]);

            if (jsonArray != null) {
                String s = "";
                int time = 0;
                for (int i = jsonArray.length()-1; i >= 0; i--) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("CSpeed") + " " +json.getString("Time") ;
                        speedTimeList.add(new Entry(parseInt(json.getString("CSpeed")), time));
                        //speedTimeList.add(s);
                        time+=5;
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return speedTimeList;
        }
        @Override
        protected void onPostExecute(ArrayList speedTimeList) {
            speedGraphlist = speedTimeList;
            Log.d(LOG_TAG, speedTimeList.toString());
        }
    }

    private class getGraphDataDistractionTask extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> distractionTimeList = new ArrayList<String>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.GetGraphDataDistraction(params[0]);

            if (jsonArray != null) {
                String s = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("distLevel");
                        distractionTimeList.add(s);
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return distractionTimeList;
        }
        @Override
        protected void onPostExecute(ArrayList distractionTimeList) {
            distractionGraphlist = distractionTimeList;
            Log.d(LOG_TAG, distractionTimeList.toString());
        }
    }

    private class getDataOverspeedCount extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> overspdCount = new ArrayList<String>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.GetTripDataOverspeed(params[0]);

            if (jsonArray != null) {
                String s = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("OverSpeed");
                        overspdCount.add(s);
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }

            }
            return overspdCount;
        }
        @Override
        protected void onPostExecute(ArrayList overspdCount) {
            overspeedCount = parseInt(overspdCount.toString());
            Log.d(LOG_TAG, overspdCount.toString());
        }
    }

    private void setData(ArrayList<Entry> yVals) {



        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 25; i+=5) {
            xVals.add((i) + "");
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //set1.enableDashedLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        //set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);
        set1.setDrawFilled(true);
        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }

}
