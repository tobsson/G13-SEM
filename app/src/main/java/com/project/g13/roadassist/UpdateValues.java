package com.project.g13.roadassist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Christosgialias on 2015-05-08.
 */
public class UpdateValues extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_update_test);

        NewTimer timer = new NewTimer();
        timer.myAsynchronousTask();
        System.out.println(timer.getcSpeed() + " " +timer.getDistLevel());
    }
}
