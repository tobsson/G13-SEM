package com.project.g13.roadassist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by tobs on 2015-05-19.
 */
public class DetailedStatistics extends ActionBarActivity {
    private TextView t;

    private static final String LOG_TAG = "DetailedStatistics";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticsdetailed);

        t = (TextView)findViewById(R.id.passedTID);

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            String value = extras.getString("lTID");
            Log.d(LOG_TAG, "Passed from list" + value);
            t.setText("Passed TID: "+value);
        }
    }
}
