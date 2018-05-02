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
//import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog.Builder;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class BetActivity extends ListActivity {

    String[] matches, teams_a, teams_b, dates, times, id_matches;

    String todayDate = "", localTime = "", login, bet_a, bet_b, id_match, betLeft, betRight;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/saveBet.php";

    private EditText editTextLeft, editTextRight;

    RequestQueue requestQueue;

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

        loadLogin();

        final ListView grid = (ListView) findViewById(android.R.id.list);

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

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
                TextView current = (TextView) dialog.findViewById(R.id.textViewCurrent);
                EditText leftEdit = (EditText) dialog.findViewById(R.id.editTextLeft);
                EditText rightEdit = (EditText) dialog.findViewById(R.id.editTextRight);

                save.setText("Save");
                close.setText("Close");
                left.setText(teams_a[pos]);
                right.setText(teams_b[pos]);
                current.setText("Current bet: 3:1");

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText leftEdit = (EditText) dialog.findViewById(R.id.editTextLeft);
                        EditText rightEdit = (EditText) dialog.findViewById(R.id.editTextRight);
                        betLeft = leftEdit.getText().toString();
                        betRight = rightEdit.getText().toString();
                        saveBets(betLeft, betRight, id_matches[pos]);
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                return true;
            }
        });


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

    //Load game
    public void loadLogin() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        login = loadGame.getString("login", "");

    }

    private void showJSONgroups(String json) {

        ConfigGroups pj = new ConfigGroups(json);
        pj.ConfigGroups();

        teams_a = new String[ConfigGroups.teams_a.length];
        teams_b = new String[ConfigGroups.teams_b.length];
        dates = new String[ConfigGroups.dates.length];
        times = new String[ConfigGroups.times.length];
        id_matches = new String[ConfigGroups.id_matches.length];
        matches = new String[ConfigGroups.teams_a.length];

        for (int i = 0; i < ConfigGroups.teams_a.length; i++) {

            teams_a[i] = ConfigGroups.teams_a[i];
            teams_b[i] = ConfigGroups.teams_b[i];
            dates[i] = ConfigGroups.dates[i];
            times[i] = ConfigGroups.times[i];
            id_matches[i] = ConfigGroups.id_matches[i];

        }

        for (int i = 0; i < ConfigGroups.teams_a.length; i++){

            matches[i] = teams_a[i] + " - " + teams_b[i] + " " + dates[i] + " " + times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

    }

    public void saveBet(String id_match) {

        editTextLeft = (EditText) findViewById(R.id.editTextLeft);
        editTextRight = (EditText) findViewById(R.id.editTextRight);
        bet_a = betLeft;
        bet_b = betRight;


        //Check if fields are filled
        if(bet_a.equals("")){
            Toast.makeText(BetActivity.this, "Bet is empty.", Toast.LENGTH_LONG).show();
        }else if(bet_b.equals("")) {
            Toast.makeText(BetActivity.this, "Bet is empty.", Toast.LENGTH_LONG).show();
        }else{

            loadingMatches = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

            String url = ConfigBet.DATA_URL + login + "&bet_a=" + bet_a + "&bet_b=" + bet_b + "&id_match=" + id_match;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loadingMatches.dismiss();
                    showJSONbet(response);

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

    }

    private void showJSONbet(String json) {

        //bet_a = editTextLeft.getText().toString();
        //bet_b = editTextRight.getText().toString();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigBet.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            login = collegeData.getString(ConfigBet.KEY_LOGIN);
            bet_a = collegeData.getString(ConfigBet.KEY_BET_A);
            bet_b = collegeData.getString(ConfigBet.KEY_BET_B);
            id_match = collegeData.getString(ConfigBet.KEY_ID_MATCH);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(BetActivity.this, "Registration completed.", Toast.LENGTH_LONG).show();
        //saveBets(); //Player is added to database
        finish();


    }

    public void saveBets(final String bet_a, final String bet_b, final String id_match){

        StringRequest request = new StringRequest(Request.Method.POST, DATA_URL, new Response.Listener<String>() {
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

        if(ConfigKo.teams_a != null) {
            teams_a = new String[ConfigKo.teams_a.length];
            teams_b = new String[ConfigKo.teams_b.length];
            dates = new String[ConfigKo.dates.length];
            times = new String[ConfigKo.times.length];
            matches = new String[ConfigKo.teams_a.length];

            for (int i = 0; i < ConfigKo.teams_a.length; i++) {

                teams_a[i] = ConfigKo.teams_a[i];
                teams_b[i] = ConfigKo.teams_b[i];
                dates[i] = ConfigKo.dates[i];
                times[i] = ConfigKo.times[i];

            }

            for (int i = 0; i < ConfigKo.teams_a.length; i++) {

                matches[i] = teams_a[i] + " - " + teams_b[i] + " " + dates[i] + " " + times[i];

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);
        }else {
            matches = new String[1];
            matches[0] = "No KO stage yet";
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);
        }
    }

    public void ko (View view) {

        if(haveNetworkConnection()) {
            getKo();
        }else{
            Toast.makeText(BetActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void bet (View view){

        final Dialog dialog = new Dialog(BetActivity.this);
        dialog.setTitle("Bet");
        dialog.setContentView(R.layout.popup_bet);
        dialog.show();



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
