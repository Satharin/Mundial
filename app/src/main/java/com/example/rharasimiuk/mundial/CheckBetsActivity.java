package com.example.rharasimiuk.mundial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class CheckBetsActivity extends ListActivity {

    String[] matches,matchesBet, teams_a, teams_b, dates, times, id_matches, result_a, result_b, bets_a, bets_b, betsUser_a, betsUser_b, users, matchesUsers, results_a, results_b;

    String todayDate = "", localTime = "", login, id_match, bet_aCheck;

    public ListView listView1, listView2;

    RequestQueue requestQueue;

    private ProgressDialog loadingMatches;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bets);

        //Date date = new Date();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        todayDate = dateFormat.format(date);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat time = new SimpleDateFormat("HH:mm");
        time.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        localTime = time.format(currentLocalTime);

        requestQueue = Volley.newRequestQueue(CheckBetsActivity.this);

        getGroups();

        final ListView grid = (ListView) findViewById(android.R.id.list);
        final ListView grid2 = (ListView) findViewById(R.id.listView2);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

                if(id_matches != null) {
                    id_match = id_matches[pos];

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds

                            TextView score = (TextView) findViewById(R.id.textViewScore);


                            checkBets(id_match);

                            score.setText(teams_a[pos] + " " + results_a[pos] + " : " + results_b[pos] + " " + teams_b[pos]);

                            if(ConfigCheckUserBets.logins != null) {

                                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(grid2.getContext(), R.layout.my_custom_layout, matchesUsers);
                                //grid2.setAdapter(adapter);
                            }else{
                                matches = new String[1];
                                matches[0] = "Not started or finished matches yet.";
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(grid2.getContext(), R.layout.my_custom_layout, matches);
                                grid2.setAdapter(adapter);

                            }

                        }
                    }, 1000);
                }

            }
        });


    }

    public void checkBets(String idMatch){

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigCheckUserBets.DATA_URL + idMatch;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONuserBets(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CheckBetsActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONuserBets(String json) {

        ConfigCheckUserBets pj = new ConfigCheckUserBets(json);
        pj.ConfigCheckUserBets();

        if(ConfigCheckUserBets.logins != null) {

            users = new String[ConfigCheckUserBets.logins.length];
            betsUser_a = new String[ConfigCheckUserBets.bets_a.length];
            betsUser_b = new String[ConfigCheckUserBets.bets_b.length];
            matchesUsers = new String[ConfigCheckUserBets.logins.length];

            for (int i = 0; i < ConfigCheckUserBets.logins.length; i++) {

                users[i] = ConfigCheckUserBets.logins[i];
                betsUser_a[i] = ConfigCheckUserBets.bets_a[i];
                betsUser_b[i] = ConfigCheckUserBets.bets_b[i];

            }

            for (int i = 0; i < ConfigCheckUserBets.logins.length; i++) {

                matchesUsers[i] = users[i] + " " + betsUser_a[i] + " : " + betsUser_b[i];

            }
            final ListView grid2 = (ListView) findViewById(R.id.listView2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(grid2.getContext(), R.layout.my_custom_layout, matchesUsers);
            grid2.setAdapter(adapter);

        }else{
            matches = new String[1];
            matches[0] = "Not started or finished matches yet.";
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);
        }

    }

    public String getDateToday(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        todayDate = dateFormat.format(date);

        return todayDate;
    }

    public String getTimeToday(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat time = new SimpleDateFormat("HH:mm");
        time.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        localTime = time.format(currentLocalTime);

        return localTime;
    }

    public void getGroups () {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        todayDate = getDateToday();
        localTime = getTimeToday();

        String url = ConfigCheckBets.DATA_URL + todayDate + "&time_match=" + localTime;

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
                        Toast.makeText(CheckBetsActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONgroups(String json) {

        ConfigCheckBets pj = new ConfigCheckBets(json);
        pj.ConfigCheckBets();

        if(ConfigCheckBets.teams_a != null) {

            teams_a = new String[ConfigCheckBets.teams_a.length];
            teams_b = new String[ConfigCheckBets.teams_b.length];
            dates = new String[ConfigCheckBets.dates.length];
            times = new String[ConfigCheckBets.times.length];
            id_matches = new String[ConfigCheckBets.id_matches.length];
            results_a = new String[ConfigCheckBets.results_a.length];
            results_b = new String[ConfigCheckBets.results_b.length];
            matches = new String[ConfigCheckBets.teams_a.length];

            for (int i = 0; i < ConfigCheckBets.teams_a.length; i++) {

                teams_a[i] = ConfigCheckBets.teams_a[i];
                teams_b[i] = ConfigCheckBets.teams_b[i];
                dates[i] = ConfigCheckBets.dates[i];
                times[i] = ConfigCheckBets.times[i];
                id_matches[i] = ConfigCheckBets.id_matches[i];
                results_a[i] = ConfigCheckBets.results_a[i];
                results_b[i] = ConfigCheckBets.results_b[i];

            }

            for (int i = 0; i < ConfigCheckBets.teams_a.length; i++) {

                matches[i] = teams_a[i] + " - " + teams_b[i] + " " + dates[i] + " " + times[i];

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);

        }else{
            matches = new String[1];
            matches[0] = "Not started or finished matches yet.";
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);
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

    //Load game
    public void loadLogin() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        login = loadGame.getString("login", "");

    }

    public void back(View view) {

        if(haveNetworkConnection()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(CheckBetsActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
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
}
