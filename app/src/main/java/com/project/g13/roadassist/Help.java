package com.project.g13.roadassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by tobs on 2015-05-11.
 */
public class Help extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


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
        Intent i = new Intent(Help.this, Plan_Route.class);
        startActivity(i);
    }

    private void openHelp() {
        Intent i = new Intent(Help.this, Help.class);
        startActivity(i);
    }

    private void openAbout() {
        Intent i = new Intent(Help.this, About.class);
        startActivity(i);
    }

    private void openSettings() {
        Intent i = new Intent(Help.this, Settings.class);
        startActivity(i);
    }
}
