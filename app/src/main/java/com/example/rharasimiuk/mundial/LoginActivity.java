package com.example.rharasimiuk.mundial;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        editName = (EditText) findViewById(R.id.editPassword);
        editPassword = (EditText) findViewById(R.id.editName);

        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    handled = true;
                }
                return handled;
            }
        });

        buttonEffectApply();
    }

    public void buttonEffectApply() {
        Button login = (Button) findViewById(R.id.login);
        Button newAccount = (Button) findViewById(R.id.newAccount);
        Button about = (Button) findViewById(R.id.about);
        Button exit = (Button) findViewById(R.id.exit);


        buttonEffect(login);
        buttonEffect(newAccount);
        buttonEffect(about);
        buttonEffect(exit);

    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(Color.parseColor("#4c4cff"), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void login () {

        if(haveNetworkConnection() == true) {

            final String login = editName.getText().toString();
            final String password = editPassword.getText().toString();

            final ProgressDialog loadingLogin = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

            String url = ConfigLogin.DATA_URL + login + "&password=" + password;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loadingLogin.dismiss();
                    showJSON(response, login, password);

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

    public void loginButton (View view) {

        if(haveNetworkConnection() == true) {

            final String login = editName.getText().toString();
            final String password = editPassword.getText().toString();

            final ProgressDialog loadingLogin = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

            String url = ConfigLogin.DATA_URL + login + "&password=" + password;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loadingLogin.dismiss();
                    showJSON(response, login, password);

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

    private void showJSON(String json, String login, String password) {

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
            saveLogin(login);
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
        about.setTitle("About");
        about.setPositiveButton("OK", null);
        LinearLayout diagLayout = new LinearLayout(this);
        diagLayout.setOrientation(LinearLayout.VERTICAL);
        TextView text = new TextView(this);
        text.setText("\nWorld Cup 2018" +
                "\nApp developed by Haraś.");
        text.setPadding(5, 5, 5, 5);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(20);
        diagLayout.addView(text);
        about.setView(diagLayout);
        about.show();

    }

    //Save player_name
    public void saveLogin(String login) {

        SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor save = saveGame.edit();
        save.putString("login", login);
        save.apply();

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

