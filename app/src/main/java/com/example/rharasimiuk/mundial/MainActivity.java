package com.example.rharasimiuk.mundial;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlertDialog.Builder;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends ListActivity {

    private ProgressDialog loadingMatches;

    RequestQueue requestQueue;

    String login, team_a, team_b, date, time, todayDate = "", localTime = "";

    String[] matches, teams_a, teams_b, dates, times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Date date = new Date();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        todayDate = dateFormat.format(date);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat time = new SimpleDateFormat("HH:mm");
        time.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        localTime = time.format(currentLocalTime);

        getNextMatch();
        loadLogin();



    }

    public void loadLogin() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        login = loadGame.getString("login", "");

    }

    public void bet (View view) {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), BetActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void admin (View view) {
        if(haveNetworkConnection()) {

            System.out.println(login);
            if(login.equals("Haras")) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
            }else{
                Toast.makeText(MainActivity.this,"Only Haras can access here.", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void checkBets (View view){

        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), CheckBetsActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void points (View view) {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), PointsActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void tables (View view) {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), TablesActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void getNextMatch () {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigNextMatches.DATA_URL + todayDate + "&time_match=" + localTime;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSON(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String json) {

        ConfigNextMatches pj = new ConfigNextMatches(json);
        pj.ConfigNextMatches();

        teams_a = new String[ConfigNextMatches.teams_a.length];
        teams_b = new String[ConfigNextMatches.teams_b.length];
        dates = new String[ConfigNextMatches.dates.length];
        times = new String[ConfigNextMatches.times.length];
        matches = new String[ConfigNextMatches.teams_a.length];

        for (int i = 0; i < ConfigNextMatches.teams_a.length; i++) {

            teams_a[i] = ConfigNextMatches.teams_a[i];
            teams_b[i] = ConfigNextMatches.teams_b[i];
            dates[i] = ConfigNextMatches.dates[i];
            times[i] = ConfigNextMatches.times[i];

        }

        for (int i = 0; i < ConfigNextMatches.teams_a.length; i++){

            matches[i] = teams_a[i] + " - " + teams_b[i] + " " + dates[i] + " " + times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

    }

    public void logout(View view) {

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

    public void exit (View view) {

        android.app.AlertDialog.Builder exit = new android.app.AlertDialog.Builder(this);

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

    public void onBackPressed() {
        android.app.AlertDialog.Builder exit = new android.app.AlertDialog.Builder(this);
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
