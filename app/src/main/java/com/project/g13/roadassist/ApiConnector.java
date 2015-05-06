package com.project.g13.roadassist;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Christosgialias on 2015-05-06.
 */
public class ApiConnector {
    public JSONArray getAllDrivers(){

        String url = "http://group13.comxa.com/all_drivers.php";

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();

        } catch(ClientProtocolException e){
            e.printStackTrace();

        } catch(IOException e){
            e.printStackTrace();
        }

        JSONArray jsonArray = null;

        if(httpEntity != null){
            try
            {
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }
}
