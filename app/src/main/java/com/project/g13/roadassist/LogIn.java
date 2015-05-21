package com.project.g13.roadassist;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.g13.roadassist.ApiConnector;
import com.project.g13.roadassist.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class LogIn extends ActionBarActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    public static final String USER_NAME = "username";

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        new logInTask().execute("Nick");
    }

    public void invokeLogin(View view){
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();

        //login(username, password);

    }



    private class logInTask extends AsyncTask<String, Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> userInfo = new ArrayList<String>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.logIn(params[0]);

            if (jsonArray != null) {
                JSONObject json = null;
                try {
                    json = jsonArray.getJSONObject(0);
                    userInfo.add(json.getString("Dusername"));
                    json = jsonArray.getJSONObject(1);
                    userInfo.add(json.getString("Dpassword"));
                    json = jsonArray.getJSONObject(2);
                    userInfo.add(json.getString("Dname"));
                    json = jsonArray.getJSONObject(3);
                    userInfo.add(json.getString("Dsurname"));
                } catch (JSONException e) {
                    Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                }


            }
            return userInfo;
        }

        @Override
        protected void onPostExecute(ArrayList userInfo) {
            Log.e("LOG_TAG", "Error converting to ArrayList " + userInfo.toString());
        }


    }
}