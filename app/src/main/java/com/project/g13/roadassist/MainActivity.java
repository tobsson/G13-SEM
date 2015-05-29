package com.project.g13.roadassist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "MainActivity";

    private static String dusername;

    public static String getDusername() {
        return dusername;
    }

    public static void setDusername(String dusername) {
        MainActivity.dusername = dusername;
    }

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

                    /*
                     * Changing the boolean in the timer class to start running again
                     */
                    Timer timer = new Timer();
                    timer.resumeThread();
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
                    Toast.makeText(MainActivity.this, "No current destination stored...", Toast.LENGTH_LONG).show();
                }
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*
                Checks the Distraction level of the driver  and the Speed of the vehicle.
                If true the user will enter the imple statistics view. If false a toast
                saying that the user needs to stop the vehicle to look at statistics
                will appear.
                */
                if (Values.getdLevel() < 3 && Values.getSpeed() < 5) {
                    startActivity(new Intent(MainActivity.this, StatisticsSimple.class));
                }
                else{
                        Toast.makeText(MainActivity.this, "Stop the vehicle to use this function.", Toast.LENGTH_LONG).show();
                    }
            }
        });

        /*
        Start a new thread that listens for AGA-signals
         */
        SaveValues values = new SaveValues();
        Thread valuesThread = new Thread(values);
        valuesThread.start();

    }

    /*
    Adds buttons to the ActionBar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
    Depending on what the user presses in the actionbar different methods are called
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_help:
                openHelp();
                return true;
            case R.id.action_about:
                openAbout();
                return true;
            case R.id.action_logout:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    Opens the Plan_Route activity
     */
    private void openSearch() {
        Intent i = new Intent(MainActivity.this, Plan_Route.class);
        startActivity(i);
    }

    /*
    Opens the Help activity
     */
    private void openHelp() {
        Intent i = new Intent(MainActivity.this, Help.class);
        startActivity(i);
    }

    /*
    Opens the About activity
     */
    private void openAbout() {
        Intent i = new Intent(MainActivity.this, About.class);
        startActivity(i);
    }

    /*
    Logs out the user
     */
    private void logOut() {
        Intent i = new Intent(MainActivity.this, LogIn.class);
        setDusername(null);
        startActivity(i);
    }


}