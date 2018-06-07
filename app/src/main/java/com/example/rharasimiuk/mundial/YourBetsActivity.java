package com.example.rharasimiuk.mundial;

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
import android.view.MotionEvent;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class YourBetsActivity extends ListActivity {

    String[] checkBets;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_bets);

        requestQueue = Volley.newRequestQueue(YourBetsActivity.this);

        getYourBets(loadLogin());
        buttonEffectApply();

    }

    public void getYourBets(String login){

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigYourBets.DATA_URL + login;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONyourBets(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(YourBetsActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONyourBets(String json) {

        ConfigYourBets pj = new ConfigYourBets(json);
        pj.ConfigYourBets();

        if(ConfigYourBets.teams_a != null) {

            String[] matches = new String[ConfigYourBets.teams_a.length];

            for (int i = 0; i < ConfigYourBets.teams_a.length; i++){

                matches[i] = ConfigYourBets.teams_a[i] + " - " + ConfigYourBets.teams_b[i] + " " +
                        ConfigYourBets.dates[i] + " " + ConfigYourBets.times[i] + "\n Result: " +
                        ConfigYourBets.results_a[i] + ":" + ConfigYourBets.results_b[i] +
                        " Your bet: " + ConfigYourBets.bets_a[i] + ":" + ConfigYourBets.bets_b[i] +
                        " (" + ConfigYourBets.points[i] + " points)";

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);

            final String login = loadLogin();

            final ListView grid = (ListView) findViewById(android.R.id.list);

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

                    final String id_match = ConfigNextMatches.id_matches[pos];
                    checkBet(login, id_match);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        public void run() {

                            final Dialog dialog = new Dialog(YourBetsActivity.this);
                            dialog.setTitle("Bet");
                            dialog.setContentView(R.layout.popup_bet);
                            dialog.show();
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);

                            Button save = (Button) dialog.findViewById(R.id.buttonSave);
                            Button close = (Button) dialog.findViewById(R.id.buttonClose);
                            TextView left = (TextView) dialog.findViewById(R.id.textViewLeft);
                            TextView right = (TextView) dialog.findViewById(R.id.textViewRight);
                            final TextView current = (TextView) dialog.findViewById(R.id.textViewCurrent);
                            final EditText leftEdit = (EditText) dialog.findViewById(R.id.editTextLeft);
                            final EditText rightEdit = (EditText) dialog.findViewById(R.id.editTextRight);

                            checkBet(login, id_match);

                            if(checkBets[0] != null)
                                current.setText("Current bet: " + checkBets[0] + ":" + checkBets[1]);
                            else
                                current.setText("Current bet: No bet yet");

                            save.setText("Save");
                            close.setText("Close");

                            left.setText(ConfigNextMatches.teams_a[pos]);
                            right.setText(ConfigNextMatches.teams_b[pos]);

                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Date matchTime = checkDate(ConfigNextMatches.dates[pos], ConfigNextMatches.times[pos]);
                                    Date currentTime = checkDate(getDateToday(), getTimeToday());

                                    String bet_a = leftEdit.getText().toString();
                                    String bet_b = rightEdit.getText().toString();

                                    boolean isBefore = currentTime.before(matchTime);

                                    if (isBefore) {
                                        if (checkBets[0] != null)
                                            updateBets("https://mundial2018.000webhostapp.com/mundial/updateBet.php", login, bet_a, bet_b, id_match);
                                        else
                                            saveBets("https://mundial2018.000webhostapp.com/mundial/saveBet.php", login, bet_a, bet_b, id_match);
                                    } else {
                                        Toast.makeText(YourBetsActivity.this, "Match already started. You can't bet.", Toast.LENGTH_LONG).show();
                                    }

                                    current.setText("Current bet: " + bet_a + ":" + bet_b);
                                    Toast.makeText(YourBetsActivity.this, "Bet successfully added to data base.", Toast.LENGTH_LONG).show();
                                    leftEdit.setText("");
                                    rightEdit.setText("");
                                    getYourBets(loadLogin());

                                }
                            });

                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                        }
                    }, 1000);

                }
            });

        }else{

            String[] matches = new String[1];
            matches[0] = "You did not bet yet.";
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);

        }

    }

    public void buttonEffectApply() {
        Button back = (Button) findViewById(R.id.buttonBack);
        Button exit = (Button) findViewById(R.id.buttonExit);

        buttonEffect(back);
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

    public String loadLogin() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        String login = loadGame.getString("login", "");

        return login;

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

    public void checkBet(String login, String id_match) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = ConfigBet.DATA_URL + login + "&id_match=" + id_match;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                checkBets = showJSONbet(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(YourBetsActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private String[] showJSONbet(String json) {

        ConfigBet pj = new ConfigBet(json);
        pj.ConfigBet();

        String bet_aCheck;
        String bet_bCheck;

        if(ConfigBet.bets_a != null) {

            bet_aCheck = ConfigBet.bets_a[0];
            bet_bCheck = ConfigBet.bets_b[0];

        }else{

            bet_aCheck = null;
            bet_bCheck = null;
        }

        return new String[] {bet_aCheck, bet_bCheck};

    }

    public Date checkDate(String dateMatch, String timeMatch){

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date matchTime = format.parse(dateMatch + " " + timeMatch);
            return matchTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveBets(String url, final String login, final String bet_a, final String bet_b, final String id_match){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                parameters.put("login", login);
                parameters.put("bet_a", bet_a);
                parameters.put("bet_b", bet_b);
                parameters.put("id_match", id_match);

                return parameters;
            }
        };

        requestQueue.add(request);

    }

    public void updateBets(String url, final String login, final String bet_a, final String bet_b, final String id_match){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                parameters.put("login", login);
                parameters.put("bet_a", bet_a);
                parameters.put("bet_b", bet_b);
                parameters.put("id_match", id_match);

                return parameters;
            }
        };

        requestQueue.add(request);

    }

    public void back(View view) {

        if(haveNetworkConnection()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(YourBetsActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
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
