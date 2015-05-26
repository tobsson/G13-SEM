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
    private EditText editTextPass2;
    private EditText editName;
    private EditText editSurname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Button insertBtn = (Button)findViewById(R.id.insertBtn);

        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPass = (EditText) findViewById(R.id.editTextPassword);
        editTextPass2 = (EditText) findViewById(R.id.editTextPassword2);
        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern = editTextUser.getText().toString();

                new checkUsername().execute(usern);

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
            startActivity(new Intent(RegisterActivity.this, LogIn.class));
        }
    }

    private class checkUsername extends AsyncTask<String,Long,ArrayList>
    {

        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> result = new ArrayList<String>();
            // Put values in a JSONArray
            ApiConnector connector = new ApiConnector();
            JSONArray jsonArray = connector.checkIfUserExists(params[0]);


            if (jsonArray != null) {
                String s  = "";
                JSONObject json = null;
                try {
                    json = jsonArray.getJSONObject(0);
                    result.add(json.getString("total"));
                    Log.d(LOG_TAG, result.toString());
                } catch (JSONException e) {
                    Log.e("LOG_TAG", "Error converting to JSONObject " + e.toString());
                }

            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            Log.e("LOG_TAG", "total is " + result.toString());
            if(editTextUser.getText().toString().equals(null)||editTextPass.getText().toString().equals(null)|| editName.getText().toString().equals(null)||editSurname.getText().toString().equals(null)||editTextPass2.getText().toString().equals(null) ){
                if(result.toString().equals("[0]")){
                    String[] credentials = new String[4];
                    credentials[0] = editTextUser.getText().toString();
                    credentials[1] = editTextPass.getText().toString();
                    credentials[2] = editName.getText().toString();
                    credentials[3] = editSurname.getText().toString();
                    if(credentials[1].equals(editTextPass2.getText().toString())) {
                        new insertToDatabase().execute(credentials);
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords do not match. Please try again.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Username exists already. Please enter a different Username",
                            Toast.LENGTH_LONG).show();
                }} else {
                Toast.makeText(getApplicationContext(), "Please fill all the fields and try again.",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

}