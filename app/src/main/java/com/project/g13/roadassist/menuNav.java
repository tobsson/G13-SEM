package com.project.g13.roadassist;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Per on 2015-05-04.
 */
public class menuNav extends Activity {

    private Button restStop;
    private Button sleep;
    private Button restaurant;
    private Button gas;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menunav_layout);


        restStop = (Button) findViewById(R.id.restStopBtn);
        sleep = (Button) findViewById(R.id.sleepBtn);
        restaurant = (Button)findViewById(R.id.restaurantBtn);
        gas = (Button)findViewById(R.id.gasBtn);
        exit = (Button)findViewById(R.id.returnBtn);

        restStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(getApplication(), NavOverlayService.class));
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.google.android.apps.maps","com.google.android.maps.MapsActivity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });


    }

}