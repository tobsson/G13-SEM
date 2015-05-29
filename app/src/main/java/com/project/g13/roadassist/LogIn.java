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
import android.widget.Button;
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
import java.util.concurrent.ExecutionException;


public class LogIn extends ActionBarActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    public static final String USER_NAME = "username";

    String username;
    String password;
    String dbpassword;
    public static String name;
    public static String surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button loginBtn2 = (Button)findViewById(R.id.loginBtn2);
        Button signupBtn = (Button)findViewById(R.id.signupBtn);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        loginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new logInTask().execute(username = editTextUsername.getText().toString());
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, RegisterActivity.class));
            }
        });
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
                    json = jsonArray.getJSONObject(0);
                    userInfo.add(json.getString("Dpassword"));
                    json = jsonArray.getJSONObject(0);
                    userInfo.add(json.getString("Dname"));
                    json = jsonArray.getJSONObject(0);
                    userInfo.add(json.getString("Dsurname"));
                } catch (JSONException e) {
                    Log.e("LOG_TAG", "Error converting to ArrayList " + e.toString());
                }


            }
            return userInfo;
        }

        @Override
        protected void onPostExecute(ArrayList userInfo) {
            Log.e("LOG_TAG", "credentials functional" + userInfo.toString());
            //username=userInfo.get(1).toString();
            dbpassword=userInfo.get(1).toString();
            Log.e("LOG_TAG", "DBpassword on postexecute=" + dbpassword);
            name= userInfo.get(2).toString();
            surname= userInfo.get(3).toString();

            password = editTextPassword.getText().toString();
            Log.e("LOG_TAG", "input password=" + password);
            if(password.equals(dbpassword)){
                Toast.makeText(getApplicationContext(), "Welcome " + name,
                        Toast.LENGTH_LONG).show();
                MainActivity.setDusername(username);
                startActivity(new Intent(LogIn.this, MainActivity.class));
                Log.e("LOG_TAG", "this" + username + dbpassword + name + surname);
            } else {
                Toast.makeText(getApplicationContext(), "Username or Password is incorrect. Please try again.",
                        Toast.LENGTH_LONG).show();
            }
        }


    }
}