package com.example.rharasimiuk.mundial;

import android.annotation.SuppressLint;
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
import android.support.v7.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
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

public class BetActivity extends ListActivity {

    String[] checkBets = new String[2];

    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        buttonEffectApply();

        requestQueue = Volley.newRequestQueue(BetActivity.this);

        getGroups();

        final Button menuButton = (Button) findViewById(R.id.buttonMenu);
        @SuppressLint("RestrictedApi") final ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.CustomPopupTheme);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ctw, menuButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().equals("Your bets")){
                            goToYourBets();
                        }else if(item.getTitle().equals("Check bets")){
                            goToCheckBets();
                        }else if(item.getTitle().equals("Points")){
                            goToPoints();
                        }else if(item.getTitle().equals("Tables")){
                            goToTables();
                        }else if(item.getTitle().equals("Logout")){
                            logout();
                        }else if(item.getTitle().equals("Exit")){
                            exit();
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

        final String login = loadLogin();

        final ListView grid = (ListView) findViewById(android.R.id.list);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

                final String id_match = ConfigGroups.id_matches[pos];
                checkBet(login, id_match);
                checkBets[0] = "null";
                checkBets[1] = "null";
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    public void run() {

                    final Dialog dialog = new Dialog(BetActivity.this);
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

                        if (!checkBets[0].equals("-") && !checkBets[0].equals("null"))
                            current.setText("Current bet: " + checkBets[0] + ":" + checkBets[1]);
                        else if(checkBets[0].equals("-")){
                            current.setText("Current bet: No bet yet");
                        }else{
                            dialog.dismiss();
                            Toast.makeText(BetActivity.this, "Error while downloading data. Please try again.", Toast.LENGTH_LONG).show();
                        }

                    save.setText("Save");
                    close.setText("Close");

                    left.setText(ConfigGroups.teams_a[pos]);
                    right.setText(ConfigGroups.teams_b[pos]);

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (leftEdit.getText().toString().trim().length() > 0 && rightEdit.getText().toString().trim().length() > 0) {
                                Date matchTime = checkDate(ConfigGroups.dates[pos], ConfigGroups.times[pos]);

                                String bet_a = leftEdit.getText().toString();
                                String bet_b = rightEdit.getText().toString();

                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                String dateNow = ConfigGroups.current_dates[pos];
                                Date currentTime = null;
                                try {
                                    currentTime = format.parse(dateNow);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                assert currentTime != null;
                                if (currentTime.getTime() < matchTime.getTime()) {
                                    if (!checkBets[0].equals("null") && !checkBets[0].equals("-"))
                                        updateBets("https://mundial2018.000webhostapp.com/mundial/updateBet.php", login, bet_a, bet_b, id_match);
                                    else
                                        saveBets("https://mundial2018.000webhostapp.com/mundial/saveBet.php", login, bet_a, bet_b, id_match);
                                } else {
                                    Toast.makeText(BetActivity.this, "Match already started. You can't bet.", Toast.LENGTH_LONG).show();
                                }

                                current.setText("Current bet: " + bet_a + ":" + bet_b);
                                Toast.makeText(BetActivity.this, "Bet successfully added to data base.", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                leftEdit.setText("");
                                rightEdit.setText("");
                            }else{
                                Toast.makeText(BetActivity.this, "One of text fields is empty.", Toast.LENGTH_LONG).show();
                            }
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


    }

    public void buttonEffectApply() {
        Button back = (Button) findViewById(R.id.buttonHome);
        Button exit = (Button) findViewById(R.id.buttonMenu);

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

    public void goToHome(View view) {

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

    public String loadLogin() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        String login = loadGame.getString("login", "");

        return login;

    }

    public void saveLogin(String login, String password) {

        SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor save = saveGame.edit();
        save.putString("login", login);
        save.putString("password", password);
        save.apply();

    }

    public void getGroups () {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigGroups.DATA_URL;

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

        String[] matches = new String[ConfigGroups.teams_a.length];

        for (int i = 0; i < ConfigGroups.teams_a.length; i++){

            matches[i] = ConfigGroups.teams_a[i] + " - " + ConfigGroups.teams_b[i] + " " + ConfigGroups.dates[i] + " " + ConfigGroups.times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

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
                            Toast.makeText(BetActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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

        if(!ConfigBet.bets_a[0].equals("null")) {

            bet_aCheck = ConfigBet.bets_a[0];
            bet_bCheck = ConfigBet.bets_b[0];

        }else{

            bet_aCheck = "-";
            bet_bCheck = "-";
        }

        return new String[] {bet_aCheck, bet_bCheck};

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

    public void goToCheckBets (){

        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), CheckBetsActivity.class));
            finish();

        }else {
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void goToPoints () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), PointsActivity.class));
            finish();

        }else {
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToTables () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), TablesActivity.class));
            finish();

        }else {
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToYourBets () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), YourBetsActivity.class));
            finish();

        }else {
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void logout() {

        saveLogin("", "");
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

    public void exit () {

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
