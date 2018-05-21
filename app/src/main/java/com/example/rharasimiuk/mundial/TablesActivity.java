package com.example.rharasimiuk.mundial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TablesActivity extends AppCompatActivity {

    private ProgressDialog loadingMatches;

    String group;

    RequestQueue requestQueue;

    private TextView team1, m1, gs1, gl1, balance1, points1,
            team2, m2, gs2, gl2, balance2, points2,
            team3, m3, gs3, gl3, balance3, points3,
            team4, m4, gs4, gl4, balance4, points4,
            groupName;

    String[] table, teams, matches, goals_scored, goals_lost, balances, points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        team1 = (TextView) findViewById(R.id.textViewTeam1);
        m1 = (TextView) findViewById(R.id.textViewM1);
        gs1 = (TextView) findViewById(R.id.textViewGS1);
        gl1 = (TextView) findViewById(R.id.textViewGL1);
        balance1 = (TextView) findViewById(R.id.textViewBalance1);
        points1 = (TextView) findViewById(R.id.textViewPoints1);

        team2 = (TextView) findViewById(R.id.textViewTeam2);
        m2 = (TextView) findViewById(R.id.textViewM2);
        gs2 = (TextView) findViewById(R.id.textViewGS2);
        gl2 = (TextView) findViewById(R.id.textViewGL2);
        balance2 = (TextView) findViewById(R.id.textViewBalance2);
        points2 = (TextView) findViewById(R.id.textViewPoints2);

        team3 = (TextView) findViewById(R.id.textViewTeam3);
        m3 = (TextView) findViewById(R.id.textViewM3);
        gs3 = (TextView) findViewById(R.id.textViewGS3);
        gl3 = (TextView) findViewById(R.id.textViewGL3);
        balance3 = (TextView) findViewById(R.id.textViewBalance3);
        points3 = (TextView) findViewById(R.id.textViewPoints3);

        team4 = (TextView) findViewById(R.id.textViewTeam4);
        m4 = (TextView) findViewById(R.id.textViewM4);
        gs4 = (TextView) findViewById(R.id.textViewGS4);
        gl4 = (TextView) findViewById(R.id.textViewGL4);
        balance4 = (TextView) findViewById(R.id.textViewBalance4);
        points4 = (TextView) findViewById(R.id.textViewPoints4);

        groupName = (TextView) findViewById(R.id.textViewGroupName);

    }

    public void getTableA (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableA.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONa(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONa(String json) {

        ConfigTableA pj = new ConfigTableA(json);
        pj.ConfigTableA();

        teams = new String[ConfigTableA.teams.length];
        matches = new String[ConfigTableA.matches.length];
        goals_scored = new String[ConfigTableA.goals_scored.length];
        goals_lost = new String[ConfigTableA.goals_lost.length];
        balances = new String[ConfigTableA.balances.length];
        points = new String[ConfigTableA.points.length];

        for (int i = 0; i < ConfigTableA.teams.length; i++) {

            teams[i] = ConfigTableA.teams[i];
            matches[i] = ConfigTableA.matches[i];
            goals_scored[i] = ConfigTableA.goals_scored[i];
            goals_lost[i] = ConfigTableA.goals_lost[i];
            balances[i] = ConfigTableA.balances[i];
            points[i] = ConfigTableA.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP A");

    }

    public void getTableB (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableB.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONb(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONb(String json) {

        ConfigTableB pj = new ConfigTableB(json);
        pj.ConfigTableB();

        teams = new String[ConfigTableB.teams.length];
        matches = new String[ConfigTableB.matches.length];
        goals_scored = new String[ConfigTableB.goals_scored.length];
        goals_lost = new String[ConfigTableB.goals_lost.length];
        balances = new String[ConfigTableB.balances.length];
        points = new String[ConfigTableB.points.length];

        for (int i = 0; i < ConfigTableB.teams.length; i++) {

            teams[i] = ConfigTableB.teams[i];
            matches[i] = ConfigTableB.matches[i];
            goals_scored[i] = ConfigTableB.goals_scored[i];
            goals_lost[i] = ConfigTableB.goals_lost[i];
            balances[i] = ConfigTableB.balances[i];
            points[i] = ConfigTableB.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP B");

    }

    public void getTableC (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableC.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONc(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONc(String json) {

        ConfigTableC pj = new ConfigTableC(json);
        pj.ConfigTableC();

        teams = new String[ConfigTableC.teams.length];
        matches = new String[ConfigTableC.matches.length];
        goals_scored = new String[ConfigTableC.goals_scored.length];
        goals_lost = new String[ConfigTableC.goals_lost.length];
        balances = new String[ConfigTableC.balances.length];
        points = new String[ConfigTableC.points.length];

        for (int i = 0; i < ConfigTableC.teams.length; i++) {

            teams[i] = ConfigTableC.teams[i];
            matches[i] = ConfigTableC.matches[i];
            goals_scored[i] = ConfigTableC.goals_scored[i];
            goals_lost[i] = ConfigTableC.goals_lost[i];
            balances[i] = ConfigTableC.balances[i];
            points[i] = ConfigTableC.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP C");

    }

    public void getTableD (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableD.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONd(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONd(String json) {

        ConfigTableD pj = new ConfigTableD(json);
        pj.ConfigTableD();

        teams = new String[ConfigTableD.teams.length];
        matches = new String[ConfigTableD.matches.length];
        goals_scored = new String[ConfigTableD.goals_scored.length];
        goals_lost = new String[ConfigTableD.goals_lost.length];
        balances = new String[ConfigTableD.balances.length];
        points = new String[ConfigTableD.points.length];

        for (int i = 0; i < ConfigTableD.teams.length; i++) {

            teams[i] = ConfigTableD.teams[i];
            matches[i] = ConfigTableD.matches[i];
            goals_scored[i] = ConfigTableD.goals_scored[i];
            goals_lost[i] = ConfigTableD.goals_lost[i];
            balances[i] = ConfigTableD.balances[i];
            points[i] = ConfigTableD.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP D");

    }

    public void getTableE (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableE.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONe(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONe(String json) {

        ConfigTableE pj = new ConfigTableE(json);
        pj.ConfigTableE();

        teams = new String[ConfigTableE.teams.length];
        matches = new String[ConfigTableE.matches.length];
        goals_scored = new String[ConfigTableE.goals_scored.length];
        goals_lost = new String[ConfigTableE.goals_lost.length];
        balances = new String[ConfigTableE.balances.length];
        points = new String[ConfigTableE.points.length];

        for (int i = 0; i < ConfigTableE.teams.length; i++) {

            teams[i] = ConfigTableE.teams[i];
            matches[i] = ConfigTableE.matches[i];
            goals_scored[i] = ConfigTableE.goals_scored[i];
            goals_lost[i] = ConfigTableE.goals_lost[i];
            balances[i] = ConfigTableE.balances[i];
            points[i] = ConfigTableE.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP E");

    }

    public void getTableF (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableF.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONf(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONf(String json) {

        ConfigTableF pj = new ConfigTableF(json);
        pj.ConfigTableF();

        teams = new String[ConfigTableF.teams.length];
        matches = new String[ConfigTableF.matches.length];
        goals_scored = new String[ConfigTableF.goals_scored.length];
        goals_lost = new String[ConfigTableF.goals_lost.length];
        balances = new String[ConfigTableF.balances.length];
        points = new String[ConfigTableF.points.length];

        for (int i = 0; i < ConfigTableF.teams.length; i++) {

            teams[i] = ConfigTableF.teams[i];
            matches[i] = ConfigTableF.matches[i];
            goals_scored[i] = ConfigTableF.goals_scored[i];
            goals_lost[i] = ConfigTableF.goals_lost[i];
            balances[i] = ConfigTableF.balances[i];
            points[i] = ConfigTableF.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP F");

    }

    public void getTableG (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableG.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONg(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONg(String json) {

        ConfigTableG pj = new ConfigTableG(json);
        pj.ConfigTableG();

        teams = new String[ConfigTableG.teams.length];
        matches = new String[ConfigTableG.matches.length];
        goals_scored = new String[ConfigTableG.goals_scored.length];
        goals_lost = new String[ConfigTableG.goals_lost.length];
        balances = new String[ConfigTableG.balances.length];
        points = new String[ConfigTableG.points.length];

        for (int i = 0; i < ConfigTableG.teams.length; i++) {

            teams[i] = ConfigTableG.teams[i];
            matches[i] = ConfigTableG.matches[i];
            goals_scored[i] = ConfigTableG.goals_scored[i];
            goals_lost[i] = ConfigTableG.goals_lost[i];
            balances[i] = ConfigTableG.balances[i];
            points[i] = ConfigTableG.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP G");

    }

    public void getTableH (View view) {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigTableH.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONh(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TablesActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONh(String json) {

        ConfigTableH pj = new ConfigTableH(json);
        pj.ConfigTableH();

        teams = new String[ConfigTableH.teams.length];
        matches = new String[ConfigTableH.matches.length];
        goals_scored = new String[ConfigTableH.goals_scored.length];
        goals_lost = new String[ConfigTableH.goals_lost.length];
        balances = new String[ConfigTableH.balances.length];
        points = new String[ConfigTableH.points.length];

        for (int i = 0; i < ConfigTableH.teams.length; i++) {

            teams[i] = ConfigTableH.teams[i];
            matches[i] = ConfigTableH.matches[i];
            goals_scored[i] = ConfigTableH.goals_scored[i];
            goals_lost[i] = ConfigTableH.goals_lost[i];
            balances[i] = ConfigTableH.balances[i];
            points[i] = ConfigTableH.points[i];

        }

        team1.setText(teams[0]);
        m1.setText(matches[0]);
        gs1.setText(goals_scored[0]);
        gl1.setText(goals_lost[0]);
        balance1.setText(balances[0]);
        points1.setText(points[0]);

        team2.setText(teams[1]);
        m2.setText(matches[1]);
        gs2.setText(goals_scored[1]);
        gl2.setText(goals_lost[1]);
        balance2.setText(balances[1]);
        points2.setText(points[1]);

        team3.setText(teams[2]);
        m3.setText(matches[2]);
        gs3.setText(goals_scored[2]);
        gl3.setText(goals_lost[2]);
        balance3.setText(balances[2]);
        points3.setText(points[2]);

        team4.setText(teams[3]);
        m4.setText(matches[3]);
        gs4.setText(goals_scored[3]);
        gl4.setText(goals_lost[3]);
        balance4.setText(balances[3]);
        points4.setText(points[3]);

        groupName.setText("GROUP H");

    }

    public void resetTable(View view) {

        team1.setText("");
        m1.setText("");
        gs1.setText("");
        gl1.setText("");
        balance1.setText("");
        points1.setText("");

        team2.setText("");
        m2.setText("");
        gs2.setText("");
        gl2.setText("");
        balance2.setText("");
        points2.setText("");

        team3.setText("");
        m3.setText("");
        gs3.setText("");
        gl3.setText("");
        balance3.setText("");
        points3.setText("");

        team4.setText("");
        m4.setText("");
        gs4.setText("");
        gl4.setText("");
        balance4.setText("");
        points4.setText("");

        groupName.setText("GROUP");

    }

    public void back(View view) {

        if(haveNetworkConnection()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(TablesActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

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
