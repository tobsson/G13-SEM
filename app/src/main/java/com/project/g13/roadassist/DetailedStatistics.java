package com.project.g13.roadassist;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;

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

    private int brakeCount = 0;
    private int overspeedCount = 0;
    private LineChart speedChart;
    private BarChart distChart;
    private static final String LOG_TAG = "DetailedStatistics";
    private Typeface mTf;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticsdetailed);
        ApiConnector connector = new ApiConnector();
        //t = (TextView) findViewById(R.id.passedTID);


        {speedChart = (LineChart) findViewById(R.id.speedChart);
        speedChart.setDrawGridBackground(false);

        // no description text
        speedChart.setDescription("Speed History");
        speedChart.setNoDataTextDescription("You need to provide data for the chart.");
        // enable value highlighting
        speedChart.setHighlightEnabled(true);
        // enable touch gestures
        speedChart.setTouchEnabled(true);
        // enable scaling and dragging
        speedChart.setDragEnabled(true);
        speedChart.setScaleEnabled(true);
        // speedChart.setScaleXEnabled(true);
        // speedChart.setScaleYEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        speedChart.setPinchZoom(true);
        // set an alternative background color
        // speedChart.setBackgroundColor(Color.GRAY);
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        //speedChart.setMarkerView(mv);
        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        speedChart.setHighlightIndicatorEnabled(false);
        // x-axis limit line
            YAxis leftAxis = speedChart.getAxisLeft();
            LimitLine ll1 = new LimitLine(90f, "Vehicle Speed Limit");
            ll1.setLineWidth(1f);
            ll1.setLabelPosition(LimitLabelPosition.POS_RIGHT);
            ll1.setTextSize(8f);
            ll1.setTextColor(Color.RED);


            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
            leftAxis.addLimitLine(ll1);
            leftAxis.setStartAtZero(false);
            // reset all limit lines to avoid overlapping lines

        leftAxis.setAxisMaxValue(160f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(false);
        //leftAxis.enableGridDashedLine(10f, 10f, 0f);
        // limit lines are drawn behind data (and not on top)
        //leftAxis.setDrawLimitLinesBehindData(true);
        speedChart.getAxisRight().setEnabled(false);
        // get the legend (only possible after setting data)


        }



        {
            distChart = (BarChart) findViewById(R.id.distractionChart);

            distChart.setDrawBarShadow(true);
            distChart.setDrawValueAboveBar(true);

            distChart.setDescription("");
            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            distChart.setMaxVisibleValueCount(60);
            // scaling can now only be done on x- and y-axis separately
            distChart.setPinchZoom(false);
            // draw shadows for each bar that show the maximum value
            // distChart.setDrawBarShadow(true);
            // distChart.setDrawXLabels(false);
            distChart.setDrawGridBackground(false);
            // distChart.setDrawYLabels(false);

//
//        XAxis xAxis = speedChart.getXAxis();
//        xAxis.addLimitLine(llXAxis);

            XAxis xAxis = distChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTf);
            xAxis.setDrawGridLines(false);
            xAxis.setSpaceBetweenLabels(2);


            YAxis leftAxis = distChart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setLabelCount(7);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = distChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setTypeface(mTf);
            rightAxis.setLabelCount(8);
            rightAxis.setSpaceTop(15f);


        }



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("lTID");

            Log.d(LOG_TAG, "Passed from list" + value);
            //t.setText("Passed TID: " + value);

            //JSONArray jsonArray = connector.GetGraphDataSpeed("2");
            //Log.d(LOG_TAG, jsonArray.toString());
            new getGraphDataSpeedTask().execute(value);
            new getGraphDataDistractionTask().execute(value);
            //Log.e("LOG_TAG", "Bar Entry Array" + entries.toString());
            //Log.e("LOG_TAG", "Bar Entry Array" + speedGraphlist.toString());


            //setData(speedGraphlist);
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
                        speedTimeList.add(new Entry((float)parseInt(json.getString("CSpeed")), jsonArray.length()-1-i));
                        //speedTimeList.add(s);
                        time+=5;
                        Log.d(LOG_TAG, jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                    }
                }
                Log.d(LOG_TAG, "this shit"+ speedTimeList.toString());

            }
            return speedTimeList;
        }
        @Override
        protected void onPostExecute(ArrayList speedTimeList) {
            setSpeedData(speedTimeList, speedChart);
            speedChart.invalidate();
            Log.d(LOG_TAG, "code executed");
        }
    }

    private class getGraphDataDistractionTask extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<BarEntry> distractionTimeList = new ArrayList<>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.GetGraphDataDistraction(params[0]);

            if (jsonArray != null) {
                String s = "";
                int time = 0;
                for (int i = jsonArray.length()-1; i >= 0; i--) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        s = json.getString("distLevel") + " " +json.getString("Time") ;
                        distractionTimeList.add(new BarEntry((float)parseInt(json.getString("distLevel")), jsonArray.length()-1-i));
                        //speedTimeList.add(s);
                        time+=5;
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
            setDistractionData(distractionTimeList, distChart);
            distChart.invalidate();
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

    private void setSpeedData(ArrayList<Entry> yVals, LineChart chart) {


        ArrayList<String> xVals = new ArrayList<String>();
        int t = 0;
        for (int i = 0; i < yVals.size(); i++) {
            xVals.add((t) + "");
            t = t + 5;
        }
        Log.d(LOG_TAG, xVals.toString());

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "Speed");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //set1.enableDashedLine(10f, 5f, 0f);
        //set1.setColor(Color.BLACK);
        //set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        //set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        //set1.setFillColor(Color.BLACK);
        set1.setDrawFilled(true);
        // set1.setShader(new LinearGradient(0, 0, 0, speedChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        Log.d(LOG_TAG, data.toString());
        // set data
        chart.setData(data);
        Legend l = chart.getLegend();
        // modify the legend ...
        //l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        chart.animateXY(1000,1000);
    }



    private void setDistractionData(ArrayList<BarEntry> yVals, BarChart chart) {

        ArrayList<String> xVals = new ArrayList<String>();
        int t = 0;
        for (int i = 0; i < yVals.size(); i++) {
            xVals.add((t) + "");
            t = t + 5;
        }



        BarDataSet set1 = new BarDataSet(yVals, "Distraction Level");
        set1.setBarSpacePercent(10f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
//        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);
            set1.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(data);
        chart.animateY(1000);
    }


}
