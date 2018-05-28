package com.example.rharasimiuk.mundial;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getNextMatch();

    }

    public String loadLogin() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        String login = loadGame.getString("login", "");

        return login;

    }

    public void goToBet (View view) {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), BetActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToAdmin (View view) {
        if(haveNetworkConnection()) {

            if(loadLogin().equals("Haras")) {
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

    public void goToPoints (View view) {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), PointsActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToTables (View view) {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), TablesActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void getNextMatch () {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigNextMatches.DATA_URL + getDateToday() + "&time_match=" + getTimeToday();

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

        String[] matches = new String[ConfigNextMatches.teams_a.length];

        for (int i = 0; i < ConfigNextMatches.teams_a.length; i++){

            matches[i] = ConfigNextMatches.teams_a[i] + " - " + ConfigNextMatches.teams_b[i] + " " + ConfigNextMatches.dates[i] + " " + ConfigNextMatches.times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

    }

    public String getDateToday(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String todayDate = dateFormat.format(date);

        return todayDate;
    }

    public String getTimeToday(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat time = new SimpleDateFormat("HH:mm");
        time.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        String localTime = time.format(currentLocalTime);

        return localTime;
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
