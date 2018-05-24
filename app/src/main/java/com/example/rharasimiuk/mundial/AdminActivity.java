package com.example.rharasimiuk.mundial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
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
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class AdminActivity extends ListActivity {

    String[] matches, teams_a, teams_b, dates, times, id_matches, bets_aCheck, bets_bCheck, id_betsBets, bets_aBets, bets_bBets, results_aBets, results_bBets;
    String[] bets_a, bets_b, results_a, results_b, id_matchesBets;
    String[] points, exactResult;

    String todayDate = "", localTime = "", login, bet_a, bet_b, id_match, betLeft, betRight, bet_aCheck, bet_bCheck;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/saveScore.php";
    public static final String DATA_URL_UPDATE = "https://mundial2018.000webhostapp.com/mundial/updateScore.php";
    public static final String DATA_URL_SEND_POINTS = "https://mundial2018.000webhostapp.com/mundial/sendPoints.php";

    private ProgressDialog loadingMatches;

    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Date date = new Date();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        todayDate = dateFormat.format(date);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat time = new SimpleDateFormat("HH:mm");
        time.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        localTime = time.format(currentLocalTime);

        requestQueue = Volley.newRequestQueue(AdminActivity.this);

        getGroups();
        loadLogin();

        final ListView grid = (ListView) findViewById(android.R.id.list);

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

                bet_aCheck = null;
                id_match = id_matches[pos];

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds


                        final Dialog dialog = new Dialog(AdminActivity.this);
                        dialog.setTitle("Bet");
                        dialog.setContentView(R.layout.popup_admin);
                        dialog.show();
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);

                        Button save = (Button) dialog.findViewById(R.id.buttonSave);
                        Button close = (Button) dialog.findViewById(R.id.buttonClose);
                        final Button update = (Button) dialog.findViewById(R.id.buttonUpdate);
                        TextView left = (TextView) dialog.findViewById(R.id.textViewLeft);
                        TextView right = (TextView) dialog.findViewById(R.id.textViewRight);
                        final TextView current = (TextView) dialog.findViewById(R.id.textViewCurrent);
                        EditText leftEdit = (EditText) dialog.findViewById(R.id.editTextLeft);
                        EditText rightEdit = (EditText) dialog.findViewById(R.id.editTextRight);

                        if(bet_aCheck != null)
                            current.setText("Score already sent");
                        else
                            current.setText("Score not sent yet");

                        save.setText("Save");
                        close.setText("Close");
                        update.setText("Update");
                        left.setText(teams_a[pos]);
                        right.setText(teams_b[pos]);


                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText leftEdit = (EditText) dialog.findViewById(R.id.editTextLeft);
                                EditText rightEdit = (EditText) dialog.findViewById(R.id.editTextRight);
                                bet_a = leftEdit.getText().toString();
                                bet_b = rightEdit.getText().toString();
                                id_match = id_matches[pos];


                                updateScore();

                                //dialog.dismiss();
                            }
                        });

                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                id_match = id_matches[pos];
                                updatePoints(id_match);
                                //dialog.dismiss();
                                update.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                                update.setEnabled(false);
                            }
                        });

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                current.setText("Current bet: No bet yet");
                                bet_aCheck = null;
                                bet_bCheck = null;
                                id_match = null;
                                dialog.dismiss();
                                System.out.println("--------------------------------------");
                                System.out.println(bet_aCheck);
                            }
                        });



                    }
                }, 1000);
                return true;
            }
        });

    }

    public void updateScore(){

            StringRequest request = new StringRequest(Request.Method.POST, DATA_URL_UPDATE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("bet_a", bet_a);
                    parameters.put("bet_b", bet_b);
                    parameters.put("id_match", id_match);

                    return parameters;
                }
            };

            requestQueue.add(request);


    }

    public void sendPoints(final String id_bet_to_send, final String points_to_send, final String exactResult_to_send){

        StringRequest request = new StringRequest(Request.Method.POST, DATA_URL_SEND_POINTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("id_bet", id_bet_to_send);
                parameters.put("points", points_to_send);
                parameters.put("exact_result", exactResult_to_send);

                return parameters;
            }
        };

        requestQueue.add(request);


    }

    public void updatePoints(String idMatch) {
        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigBetsGet.DATA_URL+idMatch;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONp(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSONp(String json) {

        ConfigBetsGet pj = new ConfigBetsGet(json);
        pj.ConfigBetsGet();

        if(ConfigBetsGet.bets_a != null) {

            bets_aBets = new String[ConfigBetsGet.bets_a.length];
            bets_bBets = new String[ConfigBetsGet.bets_b.length];
            results_aBets = new String[ConfigBetsGet.results_a.length];
            results_bBets = new String[ConfigBetsGet.results_b.length];
            id_betsBets = new String[ConfigBetsGet.id_bets.length];
            id_matchesBets = new String[ConfigBetsGet.id_matches.length];
            //matchesBets = new String[ConfigBetsGet.bets_a.length];
            points = new String[ConfigBetsGet.bets_a.length];
            exactResult = new String[ConfigBetsGet.bets_a.length];

            for (int i = 0; i < ConfigBetsGet.bets_a.length; i++) {

                bets_aBets[i] = ConfigBetsGet.bets_a[i];
                bets_bBets[i] = ConfigBetsGet.bets_b[i];
                results_aBets[i] = ConfigBetsGet.results_a[i];
                results_bBets[i] = ConfigBetsGet.results_b[i];
                id_betsBets[i] = ConfigBetsGet.id_bets[i];
                id_matchesBets[i] = ConfigBetsGet.id_matches[i];

            }

            for (int i = 0; i < ConfigBetsGet.bets_a.length; i++){

                if(Integer.parseInt(bets_aBets[i]) == Integer.parseInt(results_aBets[i]) && Integer.parseInt(bets_bBets[i]) == Integer.parseInt(results_bBets[i])){
                    points[i] = "5";
                    exactResult[i] = "1";
                }else if(Integer.parseInt(bets_aBets[i]) > Integer.parseInt(bets_bBets[i]) && Integer.parseInt(results_aBets[i]) > Integer.parseInt(results_bBets[i])){
                    points[i] = "2";
                    exactResult[i] = "0";
                }else if(Integer.parseInt(bets_aBets[i]) < Integer.parseInt(bets_bBets[i]) && Integer.parseInt(results_aBets[i]) < Integer.parseInt(results_bBets[i])){
                    points[i] = "2";
                    exactResult[i] = "0";
                }else if(Integer.parseInt(bets_aBets[i]) == Integer.parseInt(bets_bBets[i]) && Integer.parseInt(results_aBets[i]) == Integer.parseInt(results_bBets[i])){
                    points[i] = "2";
                    exactResult[i] = "0";
                }else{
                    points[i] = "0";
                    exactResult[i] = "0";
                }

            }

            for (int i = 0; i < points.length; i++){

                String id_betsSend = id_betsBets[i];
                String pointsSend = points[i];
                String exactResultSend = exactResult[i];

                sendPoints(id_betsSend, pointsSend, exactResultSend);

            }


        }else {
            matches = new String[1];
            matches[0] = "NULL";
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);
        }

    }

    public void getGroups () {

        loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigMatches.DATA_URL;

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
                        Toast.makeText(AdminActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONgroups(String json) {

        ConfigMatches pj = new ConfigMatches(json);
        pj.ConfigMatches();

        teams_a = new String[ConfigMatches.teams_a.length];
        teams_b = new String[ConfigMatches.teams_b.length];
        dates = new String[ConfigMatches.dates.length];
        times = new String[ConfigMatches.times.length];
        id_matches = new String[ConfigMatches.id_matches.length];
        matches = new String[ConfigMatches.teams_a.length];

        for (int i = 0; i < ConfigMatches.teams_a.length; i++) {

            teams_a[i] = ConfigMatches.teams_a[i];
            teams_b[i] = ConfigMatches.teams_b[i];
            dates[i] = ConfigMatches.dates[i];
            times[i] = ConfigMatches.times[i];
            id_matches[i] = ConfigMatches.id_matches[i];

        }

        for (int i = 0; i < ConfigMatches.teams_a.length; i++){

            matches[i] = teams_a[i] + " - " + teams_b[i] + " " + dates[i] + " " + times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

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
            Toast.makeText(AdminActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
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
