package com.example.rharasimiuk.mundial;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog.Builder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BetActivity extends ListActivity {

    String[] matches, teams_a, teams_b, dates, times;

    String todayDate = "", localTime = "";

    private ProgressDialog loadingMatches;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        //Date date = new Date();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        todayDate = dateFormat.format(date);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat time = new SimpleDateFormat("HH:mm");
        time.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        localTime = time.format(currentLocalTime);

    }

    public void back(View view) {

        if(haveNetworkConnection()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void onBackPressed() {
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

    public void groups (View view) {

        if(haveNetworkConnection()) {
            getGroups();
        }else{
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void getGroups () {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigGroups.DATA_URL + todayDate + "&time_match=" + localTime;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONgroups(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BetActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONgroups(String json) {

        ConfigGroups pj = new ConfigGroups(json);
        pj.ConfigGroups();

        teams_a = new String[ConfigGroups.teams_a.length];
        teams_b = new String[ConfigGroups.teams_b.length];
        dates = new String[ConfigGroups.dates.length];
        times = new String[ConfigGroups.times.length];
        matches = new String[ConfigGroups.teams_a.length];

        for (int i = 0; i < ConfigGroups.teams_a.length; i++) {

            teams_a[i] = ConfigGroups.teams_a[i];
            teams_b[i] = ConfigGroups.teams_b[i];

        }

        for (int i = 0; i < ConfigGroups.teams_a.length; i++){

            matches[i] = teams_a[i] + " - " + teams_b[i] + " " + dates[i] + " " + times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

    }

    public void getKo () {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigKo.DATA_URL + todayDate + "&time_match=" + localTime;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONko(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BetActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONko(String json) {

        ConfigKo pj = new ConfigKo(json);
        pj.ConfigKo();

        teams_a = new String[ConfigKo.teams_a.length];
        teams_b = new String[ConfigKo.teams_b.length];
        dates = new String[ConfigKo.dates.length];
        times = new String[ConfigKo.times.length];
        matches = new String[ConfigKo.teams_a.length];

        for (int i = 0; i < ConfigKo.teams_a.length; i++) {

            teams_a[i] = ConfigKo.teams_a[i];
            teams_b[i] = ConfigKo.teams_b[i];

        }

        for (int i = 0; i < ConfigKo.teams_a.length; i++){

            matches[i] = teams_a[i] + " - " + teams_b[i] + " " + dates[i] + " " + times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

    }

    public void ko (View view) {

        if(haveNetworkConnection()) {
            getKo();
        }else{
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

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
