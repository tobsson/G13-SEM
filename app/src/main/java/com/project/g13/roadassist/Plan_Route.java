package com.project.g13.roadassist;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Per on 2015-03-12.
 */
public class Plan_Route extends ActionBarActivity {


    private Button startNav;
    private Button button;
    private String destination;
    private EditText destText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        //TODO A view for input of route and such

        button = (Button)findViewById(R.id.button);
        startNav = (Button)findViewById(R.id.plnSrcbutton);
        destText = (EditText)findViewById(R.id.destination);

        button.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          destination = destText.getText().toString();
                                          Toast.makeText(Plan_Route.this,destination, Toast.LENGTH_LONG).show();
                                      }
                                  });


        startNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination = destText.getText().toString();
                Toast.makeText(Plan_Route.this,destination, Toast.LENGTH_LONG).show();
                if(destination!=null){

                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+destination+"&mode=w");
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
                        Toast.makeText(Plan_Route.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }}
                else {
                    Toast.makeText(Plan_Route.this, "Please input a destination...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
