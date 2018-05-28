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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TablesActivity extends AppCompatActivity {

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

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableA.teams[0]);
        m1.setText(ConfigTableA.matches[0]);
        gs1.setText(ConfigTableA.goals_scored[0]);
        gl1.setText(ConfigTableA.goals_lost[0]);
        balance1.setText(ConfigTableA.balances[0]);
        points1.setText(ConfigTableA.points[0]);

        team2.setText(ConfigTableA.teams[1]);
        m2.setText(ConfigTableA.matches[1]);
        gs2.setText(ConfigTableA.goals_scored[1]);
        gl2.setText(ConfigTableA.goals_lost[1]);
        balance2.setText(ConfigTableA.balances[1]);
        points2.setText(ConfigTableA.points[1]);

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

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableB.teams[0]);
        m1.setText(ConfigTableB.matches[0]);
        gs1.setText(ConfigTableB.goals_scored[0]);
        gl1.setText(ConfigTableB.goals_lost[0]);
        balance1.setText(ConfigTableB.balances[0]);
        points1.setText(ConfigTableB.points[0]);

        team2.setText(ConfigTableB.teams[1]);
        m2.setText(ConfigTableB.matches[1]);
        gs2.setText(ConfigTableB.goals_scored[1]);
        gl2.setText(ConfigTableB.goals_lost[1]);
        balance2.setText(ConfigTableB.balances[1]);
        points2.setText(ConfigTableB.points[1]);

        team3.setText(ConfigTableB.teams[2]);
        m3.setText(ConfigTableB.matches[2]);
        gs3.setText(ConfigTableB.goals_scored[2]);
        gl3.setText(ConfigTableB.goals_lost[2]);
        balance3.setText(ConfigTableB.balances[2]);
        points3.setText(ConfigTableB.points[2]);

        team4.setText(ConfigTableB.teams[3]);
        m4.setText(ConfigTableB.matches[3]);
        gs4.setText(ConfigTableB.goals_scored[3]);
        gl4.setText(ConfigTableB.goals_lost[3]);
        balance4.setText(ConfigTableB.balances[3]);
        points4.setText(ConfigTableB.points[3]);

        groupName.setText("GROUP B");

    }

    public void getTableC (View view) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableC.teams[0]);
        m1.setText(ConfigTableC.matches[0]);
        gs1.setText(ConfigTableC.goals_scored[0]);
        gl1.setText(ConfigTableC.goals_lost[0]);
        balance1.setText(ConfigTableC.balances[0]);
        points1.setText(ConfigTableC.points[0]);

        team2.setText(ConfigTableC.teams[1]);
        m2.setText(ConfigTableC.matches[1]);
        gs2.setText(ConfigTableC.goals_scored[1]);
        gl2.setText(ConfigTableC.goals_lost[1]);
        balance2.setText(ConfigTableC.balances[1]);
        points2.setText(ConfigTableC.points[1]);

        team3.setText(ConfigTableC.teams[2]);
        m3.setText(ConfigTableC.matches[2]);
        gs3.setText(ConfigTableC.goals_scored[2]);
        gl3.setText(ConfigTableC.goals_lost[2]);
        balance3.setText(ConfigTableC.balances[2]);
        points3.setText(ConfigTableC.points[2]);

        team4.setText(ConfigTableC.teams[3]);
        m4.setText(ConfigTableC.matches[3]);
        gs4.setText(ConfigTableC.goals_scored[3]);
        gl4.setText(ConfigTableC.goals_lost[3]);
        balance4.setText(ConfigTableC.balances[3]);
        points4.setText(ConfigTableC.points[3]);

        groupName.setText("GROUP C");

    }

    public void getTableD (View view) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableD.teams[0]);
        m1.setText(ConfigTableD.matches[0]);
        gs1.setText(ConfigTableD.goals_scored[0]);
        gl1.setText(ConfigTableD.goals_lost[0]);
        balance1.setText(ConfigTableD.balances[0]);
        points1.setText(ConfigTableD.points[0]);

        team2.setText(ConfigTableD.teams[1]);
        m2.setText(ConfigTableD.matches[1]);
        gs2.setText(ConfigTableD.goals_scored[1]);
        gl2.setText(ConfigTableD.goals_lost[1]);
        balance2.setText(ConfigTableD.balances[1]);
        points2.setText(ConfigTableD.points[1]);

        team3.setText(ConfigTableD.teams[2]);
        m3.setText(ConfigTableD.matches[2]);
        gs3.setText(ConfigTableD.goals_scored[2]);
        gl3.setText(ConfigTableD.goals_lost[2]);
        balance3.setText(ConfigTableD.balances[2]);
        points3.setText(ConfigTableD.points[2]);

        team4.setText(ConfigTableD.teams[3]);
        m4.setText(ConfigTableD.matches[3]);
        gs4.setText(ConfigTableD.goals_scored[3]);
        gl4.setText(ConfigTableD.goals_lost[3]);
        balance4.setText(ConfigTableD.balances[3]);
        points4.setText(ConfigTableD.points[3]);

        groupName.setText("GROUP D");

    }

    public void getTableE (View view) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableE.teams[0]);
        m1.setText(ConfigTableE.matches[0]);
        gs1.setText(ConfigTableE.goals_scored[0]);
        gl1.setText(ConfigTableE.goals_lost[0]);
        balance1.setText(ConfigTableE.balances[0]);
        points1.setText(ConfigTableE.points[0]);

        team2.setText(ConfigTableE.teams[1]);
        m2.setText(ConfigTableE.matches[1]);
        gs2.setText(ConfigTableE.goals_scored[1]);
        gl2.setText(ConfigTableE.goals_lost[1]);
        balance2.setText(ConfigTableE.balances[1]);
        points2.setText(ConfigTableE.points[1]);

        team3.setText(ConfigTableE.teams[2]);
        m3.setText(ConfigTableE.matches[2]);
        gs3.setText(ConfigTableE.goals_scored[2]);
        gl3.setText(ConfigTableE.goals_lost[2]);
        balance3.setText(ConfigTableE.balances[2]);
        points3.setText(ConfigTableE.points[2]);

        team4.setText(ConfigTableE.teams[3]);
        m4.setText(ConfigTableE.matches[3]);
        gs4.setText(ConfigTableE.goals_scored[3]);
        gl4.setText(ConfigTableE.goals_lost[3]);
        balance4.setText(ConfigTableE.balances[3]);
        points4.setText(ConfigTableE.points[3]);

        groupName.setText("GROUP E");

    }

    public void getTableF (View view) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableF.teams[0]);
        m1.setText(ConfigTableF.matches[0]);
        gs1.setText(ConfigTableF.goals_scored[0]);
        gl1.setText(ConfigTableF.goals_lost[0]);
        balance1.setText(ConfigTableF.balances[0]);
        points1.setText(ConfigTableF.points[0]);

        team2.setText(ConfigTableF.teams[1]);
        m2.setText(ConfigTableF.matches[1]);
        gs2.setText(ConfigTableF.goals_scored[1]);
        gl2.setText(ConfigTableF.goals_lost[1]);
        balance2.setText(ConfigTableF.balances[1]);
        points2.setText(ConfigTableF.points[1]);

        team3.setText(ConfigTableF.teams[2]);
        m3.setText(ConfigTableF.matches[2]);
        gs3.setText(ConfigTableF.goals_scored[2]);
        gl3.setText(ConfigTableF.goals_lost[2]);
        balance3.setText(ConfigTableF.balances[2]);
        points3.setText(ConfigTableF.points[2]);

        team4.setText(ConfigTableF.teams[3]);
        m4.setText(ConfigTableF.matches[3]);
        gs4.setText(ConfigTableF.goals_scored[3]);
        gl4.setText(ConfigTableF.goals_lost[3]);
        balance4.setText(ConfigTableF.balances[3]);
        points4.setText(ConfigTableF.points[3]);

        groupName.setText("GROUP F");

    }

    public void getTableG (View view) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableG.teams[0]);
        m1.setText(ConfigTableG.matches[0]);
        gs1.setText(ConfigTableG.goals_scored[0]);
        gl1.setText(ConfigTableG.goals_lost[0]);
        balance1.setText(ConfigTableG.balances[0]);
        points1.setText(ConfigTableG.points[0]);

        team2.setText(ConfigTableG.teams[1]);
        m2.setText(ConfigTableG.matches[1]);
        gs2.setText(ConfigTableG.goals_scored[1]);
        gl2.setText(ConfigTableG.goals_lost[1]);
        balance2.setText(ConfigTableG.balances[1]);
        points2.setText(ConfigTableG.points[1]);

        team3.setText(ConfigTableG.teams[2]);
        m3.setText(ConfigTableG.matches[2]);
        gs3.setText(ConfigTableG.goals_scored[2]);
        gl3.setText(ConfigTableG.goals_lost[2]);
        balance3.setText(ConfigTableG.balances[2]);
        points3.setText(ConfigTableG.points[2]);

        team4.setText(ConfigTableG.teams[3]);
        m4.setText(ConfigTableG.matches[3]);
        gs4.setText(ConfigTableG.goals_scored[3]);
        gl4.setText(ConfigTableG.goals_lost[3]);
        balance4.setText(ConfigTableG.balances[3]);
        points4.setText(ConfigTableG.points[3]);

        groupName.setText("GROUP G");

    }

    public void getTableH (View view) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        team1.setText(ConfigTableH.teams[0]);
        m1.setText(ConfigTableH.matches[0]);
        gs1.setText(ConfigTableH.goals_scored[0]);
        gl1.setText(ConfigTableH.goals_lost[0]);
        balance1.setText(ConfigTableH.balances[0]);
        points1.setText(ConfigTableH.points[0]);

        team2.setText(ConfigTableH.teams[1]);
        m2.setText(ConfigTableH.matches[1]);
        gs2.setText(ConfigTableH.goals_scored[1]);
        gl2.setText(ConfigTableH.goals_lost[1]);
        balance2.setText(ConfigTableH.balances[1]);
        points2.setText(ConfigTableH.points[1]);

        team3.setText(ConfigTableH.teams[2]);
        m3.setText(ConfigTableH.matches[2]);
        gs3.setText(ConfigTableH.goals_scored[2]);
        gl3.setText(ConfigTableH.goals_lost[2]);
        balance3.setText(ConfigTableH.balances[2]);
        points3.setText(ConfigTableH.points[2]);

        team4.setText(ConfigTableH.teams[3]);
        m4.setText(ConfigTableH.matches[3]);
        gs4.setText(ConfigTableH.goals_scored[3]);
        gl4.setText(ConfigTableH.goals_lost[3]);
        balance4.setText(ConfigTableH.balances[3]);
        points4.setText(ConfigTableH.points[3]);

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
