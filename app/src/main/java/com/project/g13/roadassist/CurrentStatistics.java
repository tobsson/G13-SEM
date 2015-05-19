package com.project.g13.roadassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Christosgialias on 2015-05-19.
 */
public class CurrentStatistics extends Activity{
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        TextView driverName = (TextView)findViewById(R.id.driverName);
        Button historyBtn = (Button)findViewById(R.id.historyBtn);


        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentStatistics.this, History.class));
            }
        });
    }




}
