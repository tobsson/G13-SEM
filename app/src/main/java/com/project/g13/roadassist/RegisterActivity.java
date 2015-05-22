package com.project.g13.roadassist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;


public class RegisterActivity extends ActionBarActivity {

    private static final String LOG_TAG = "Main Activity";
    private EditText editTextUser;
    private EditText editTextPass;
    private EditText editName;
    private EditText editSurname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Button insertBtn = (Button)findViewById(R.id.insertBtn);

        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPass = (EditText) findViewById(R.id.editTextPassword);
        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern = editTextUser.getText().toString();
                String pass = editTextPass.getText().toString();
                String name = editName.getText().toString();
                String surname = editSurname.getText().toString();
                String[] credentials = new String[4];
                credentials[0] = usern;
                credentials[1] = pass;
                credentials[2] = name;
                credentials[3] = surname;
                new insertToDatabase().execute(credentials);

            }
        });
    }


    private class insertToDatabase extends AsyncTask<String[], Long, ArrayList> {

        @Override
        protected ArrayList doInBackground(String[]... params) {
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            String insert= connector.insertNewUser(params[0]);


            return null;
        }
        @Override
        protected void onPostExecute(ArrayList overspdCount) {
            Toast.makeText(getApplicationContext(), "Registeration was successful",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }
    }



}
