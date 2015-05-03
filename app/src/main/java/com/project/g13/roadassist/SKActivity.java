package com.project.g13.roadassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.skobbler.ngx.SKMaps;
import com.skobbler.ngx.SKMapsInitSettings;
import com.skobbler.ngx.SKPrepareMapTextureListener;
import com.skobbler.ngx.SKPrepareMapTextureThread;
import com.skobbler.ngx.map.SKMapViewStyle;
import com.skobbler.ngx.util.SKLogging;
import com.skobbler.ngx.versioning.SKMapUpdateListener;

import java.io.File;

/**
 * Created by Per on 2015-04-30.
 */
public class SKActivity extends Activity implements SKPrepareMapTextureListener {
    String mapResourcesDirPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skmaps_layout);

        SKLogging.enableLogs(true);
        File externalDir = getExternalFilesDir(null);

        // determine path where map resources should be copied on the device

        if (externalDir != null) {
            mapResourcesDirPath = externalDir + "/SKMaps/";
        } else {
            mapResourcesDirPath = getFilesDir() + "/SKMaps/";
        }

        //new SKPrepareMapTextureThread(this, mapResourcesDirPath, "SKMaps.zip", this).start();
        final SKPrepareMapTextureThread prepThread = new SKPrepareMapTextureThread(this, mapResourcesDirPath, "SKMaps.zip", this);
        prepThread.start();
        //Intent intent = new Intent(SKActivity.this,MainActivity.class);
        //startActivity(intent);
    }

    @Override
    public void onMapTexturesPrepared(boolean b) {
        if(b){
            Toast.makeText(SKActivity.this, "Map resources are prepared", Toast.LENGTH_SHORT).show();
            initializeLibrary();
            startActivity(new Intent(SKActivity.this, MainActivity.class));
        }else{
            Toast.makeText(SKActivity.this, "Map resources are NOT prepared", Toast.LENGTH_SHORT).show();

    }

    }
    /**
     * Initializes the SKMaps framework
     */
    private void initializeLibrary() {
        SKMapsInitSettings initMapSettings = new SKMapsInitSettings();
        // set path to map resources and initial map style
        initMapSettings.setMapResourcesPaths(mapResourcesDirPath,
                new SKMapViewStyle(mapResourcesDirPath + "/outdoorstyle/", "outdoorstyle.json"));

        // EXAMPLE OF ADDING PREINSTALLED MAPS
        initMapSettings.setPreinstalledMapsPath(mapResourcesDirPath + "/PreinstalledMaps");
        //initMapSettings.setConnectivityMode(SKMaps.CONNECTIVITY_MODE_OFFLINE);

        SKMaps.getInstance().initializeSKMaps(this, initMapSettings, "8d2d10e08df1f0f199fe3090f418f7298a25629722b1b5d15093880b1e35abc1");
    }






}