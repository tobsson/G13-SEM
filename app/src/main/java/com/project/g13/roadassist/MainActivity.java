package com.project.g13.roadassist;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button plnRtBtn = (Button)findViewById(R.id.plnRtBtn);
        Button vwRtBtn = (Button)findViewById(R.id.vwRtBtn);
        Button statsBtn = (Button)findViewById(R.id.statsBtn);


        plnRtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Plan_Route.class));
            }
        });
        vwRtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentRoute.getCurrentRoute() !=null){
                    String destination = CurrentRoute.getCurrentRoute();
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination + "&mode=d");
                    Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    intent.setPackage("com.google.android.apps.maps");


                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    try
                    {
                        startActivity(intent);
                        startService(new Intent(getApplication(), NavOverlayService.class));
                    }
                    catch(ActivityNotFoundException ex)
                    {
                        try
                        {
                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            startActivity(unrestrictedIntent);
                        }
                        catch(ActivityNotFoundException innerEx)
                        {
                            Toast.makeText(MainActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                        }
                    }}
                else {
                    Toast.makeText(MainActivity.this, "No previous destination stored", Toast.LENGTH_LONG).show();
                }
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatisticsSimple.class));
            }
        });

        //new Thread(new SaveValues()).start();
        new Thread(new Runnable() {
            public void run() {
                //Start task that runs every 5 seconds
                SaveValues values = new SaveValues();
                values.run();
                values.myTask();

                //Get the calendar and save the time and date when the route starts
                Calendar calendar = Calendar.getInstance();
                String date = calendar.getTime().toString();
                Values.setRouteStart(date);
                Log.d(LOG_TAG, "Date/Time Start: " + date);
            }
        }).start();
    }




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
        Intent i = new Intent(MainActivity.this, Plan_Route.class);
        startActivity(i);
    }

    private void openHelp() {
        Intent i = new Intent(MainActivity.this, Help.class);
        startActivity(i);
    }

    private void openAbout() {
        Intent i = new Intent(MainActivity.this, About.class);
        startActivity(i);
    }

    private void openSettings() {
        Intent i = new Intent(MainActivity.this, Settings.class);
        startActivity(i);
    }


}
