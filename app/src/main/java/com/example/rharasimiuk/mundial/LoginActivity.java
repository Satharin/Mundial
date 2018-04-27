package com.example.rharasimiuk.mundial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText editName, editPassword;
    String login, password;
    private ProgressDialog loadingLogin;
    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        editName = (EditText) findViewById(R.id.editPassword);
        editPassword = (EditText) findViewById(R.id.editName);
    }

    public void login (View view) {

        if(haveNetworkConnection() == true) {

            login = editName.getText().toString();
            password = editPassword.getText().toString();

            loadingLogin = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

            String url = ConfigLogin.DATA_URL + login + "&password=" + password;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loadingLogin.dismiss();
                    showJSON(response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(LoginActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    private void showJSON(String json) {

        String login2 = "", password2 = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigLogin.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            login2 = collegeData.getString(ConfigLogin.KEY_LOGIN);
            password2 = collegeData.getString(ConfigLogin.KEY_PASSWORD);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Check if name and password exist in database
        if(login2.equals(login) && password2.equals(password)){
            //saveGame();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(LoginActivity.this, "Wrong name or password.", Toast.LENGTH_LONG).show();
            editName.setText("");
            editPassword.setText("");
        }

    }

    public void newAccount (View view) {

        if(haveNetworkConnection() == true) {
            startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
            finish();
        }else{
            Toast.makeText(LoginActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void about (View view) {

        AlertDialog.Builder about = new AlertDialog.Builder(this);

        about.setMessage("                     App made by Haraś.");

        about.show();

    }

    public void exit (View view) {

        AlertDialog.Builder exit = new AlertDialog.Builder(this);

        exit.setMessage("Are you sure you want to exit the application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        exit.show();
    }

    private boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;

    }

}

