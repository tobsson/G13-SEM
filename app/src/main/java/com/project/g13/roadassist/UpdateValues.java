package com.project.g13.roadassist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Christosgialias on 2015-05-08.
 */
public class UpdateValues extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_update_test);

        final Timer timer = new Timer();

        final TextView ds = (TextView)findViewById(R.id.speedView);
        ds.setText("something");
        ds.post(new Runnable() { // Post the result back to the View/UI thread
            public void run() {
                ds.setText(timer.getSpeedString());
            }
        });
    }
}
