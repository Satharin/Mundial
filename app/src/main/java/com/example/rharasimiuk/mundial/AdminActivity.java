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
import android.view.MenuItem;
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

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends ListActivity {

    String[] checkResults;

    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        requestQueue = Volley.newRequestQueue(AdminActivity.this);

        getGroups();

        final Button menuButton = (Button) findViewById(R.id.buttonMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(AdminActivity.this, menuButton);
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

        final ListView grid = (ListView) findViewById(android.R.id.list);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

                final String id_match = ConfigMatches.id_matches[pos];
                checkResult(id_match);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

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
                        final EditText leftEdit = (EditText) dialog.findViewById(R.id.editTextLeft);
                        final EditText rightEdit = (EditText) dialog.findViewById(R.id.editTextRight);

                        save.setText("Save");
                        close.setText("Close");
                        update.setText("Update");
                        left.setText(ConfigMatches.teams_a[pos]);
                        right.setText(ConfigMatches.teams_b[pos]);

                        if(!checkResults[0].equals("null"))
                            current.setText("Score: " + checkResults[0] + ":" + checkResults[1]);
                        else
                            current.setText("Score not sent yet.");


                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(leftEdit.getText().toString().trim().length() > 0 && rightEdit.getText().toString().trim().length() > 0) {

                                    EditText leftEdit = (EditText) dialog.findViewById(R.id.editTextLeft);
                                    EditText rightEdit = (EditText) dialog.findViewById(R.id.editTextRight);
                                    String result_a = leftEdit.getText().toString();
                                    String result_b = rightEdit.getText().toString();
                                    updateScore("https://mundial2018.000webhostapp.com/mundial/updateScore.php", result_a, result_b, id_match);
                                    Toast.makeText(AdminActivity.this, "Score sent to database.", Toast.LENGTH_LONG).show();
                                    leftEdit.setText("");
                                    rightEdit.setText("");
                                    current.setText("Score: " + result_a + ":" + result_b);

                                }else{

                                    Toast.makeText(AdminActivity.this, "One of EditText fields is empty", Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(!current.getText().equals("Score not sent yet.")) {
                                    updatePoints(id_match);
                                    Toast.makeText(AdminActivity.this, "Bets analyzed and points updated.", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(AdminActivity.this, "Nothing to update yet. Please send score first.", Toast.LENGTH_LONG).show();
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

    public void updateScore(String url, final String bet_a, final String bet_b, final String id_match){

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
                    parameters.put("bet_a", bet_a);
                    parameters.put("bet_b", bet_b);
                    parameters.put("id_match", id_match);

                    return parameters;
                }
            };

            requestQueue.add(request);


    }

    public void sendPoints(String url, final String id_bet_to_send, final String points_to_send, final String exactResult_to_send){

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
                parameters.put("id_bet", id_bet_to_send);
                parameters.put("points", points_to_send);
                parameters.put("exact_result", exactResult_to_send);

                return parameters;
            }
        };

        requestQueue.add(request);


    }

    public void updatePoints(String idMatch) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigBetsGet.DATA_URL+idMatch;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                showJSONpoints(response);

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

    private void showJSONpoints(String json) {

        ConfigBetsGet pj = new ConfigBetsGet(json);
        pj.ConfigBetsGet();

        String[] points = new String[ConfigBetsGet.bets_a.length];
        String[] exactResult = new String[ConfigBetsGet.bets_a.length];

        if(ConfigBetsGet.bets_a != null) {

            for (int i = 0; i < ConfigBetsGet.bets_a.length; i++){

                if(Integer.parseInt(ConfigBetsGet.bets_a[i]) == Integer.parseInt(ConfigBetsGet.results_a[i]) && Integer.parseInt(ConfigBetsGet.bets_b[i]) == Integer.parseInt(ConfigBetsGet.results_b[i])){
                    points[i] = "5";
                    exactResult[i] = "1";
                }else if(Integer.parseInt(ConfigBetsGet.bets_a[i]) > Integer.parseInt(ConfigBetsGet.bets_b[i]) && Integer.parseInt(ConfigBetsGet.results_a[i]) > Integer.parseInt(ConfigBetsGet.results_b[i])){
                    points[i] = "2";
                    exactResult[i] = "0";
                }else if(Integer.parseInt(ConfigBetsGet.bets_a[i]) < Integer.parseInt(ConfigBetsGet.bets_b[i]) && Integer.parseInt(ConfigBetsGet.results_a[i]) < Integer.parseInt(ConfigBetsGet.results_b[i])){
                    points[i] = "2";
                    exactResult[i] = "0";
                }else if(Integer.parseInt(ConfigBetsGet.bets_a[i]) == Integer.parseInt(ConfigBetsGet.bets_b[i]) && Integer.parseInt(ConfigBetsGet.results_a[i]) == Integer.parseInt(ConfigBetsGet.results_b[i])){
                    points[i] = "2";
                    exactResult[i] = "0";
                }else{
                    points[i] = "0";
                    exactResult[i] = "0";
                }

                sendPoints("https://mundial2018.000webhostapp.com/mundial/sendPoints.php", ConfigBetsGet.id_bets[i], points[i], exactResult[i]);

            }

        }else {
            Toast.makeText(AdminActivity.this, "No results yet.", Toast.LENGTH_LONG).show();
        }

    }

    public void getGroups () {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

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

        String[] matches = new String[ConfigMatches.teams_a.length];

        for (int i = 0; i < ConfigMatches.teams_a.length; i++){

            matches[i] = ConfigMatches.teams_a[i] + " - " + ConfigMatches.teams_b[i] + " " + ConfigMatches.dates[i] + " " + ConfigMatches.times[i];

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
        getListView().setAdapter(adapter);

    }

    public void checkResult(String id_match) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = ConfigResult.DATA_URL + id_match;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingMatches.dismiss();
                checkResults = showJSONresult(response);

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

    private String[] showJSONresult(String json) {

        ConfigResult pj = new ConfigResult(json);
        pj.ConfigResult();

        String result_aCheck;
        String result_bCheck;

        if(ConfigResult.results_a != null) {

            result_aCheck = ConfigResult.results_a[0];
            result_bCheck = ConfigResult.results_b[0];

        }else{

            result_aCheck = null;
            result_bCheck = null;
        }

        return new String[] {result_aCheck, result_bCheck};

    }

    public void goToHome(View view) {

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

    public void goToCheckBets (){

        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), CheckBetsActivity.class));
            finish();

        }else {
            Toast.makeText(AdminActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void goToPoints () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), PointsActivity.class));
            finish();

        }else {
            Toast.makeText(AdminActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToTables () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), TablesActivity.class));
            finish();

        }else {
            Toast.makeText(AdminActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToYourBets () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), YourBetsActivity.class));
            finish();

        }else {
            Toast.makeText(AdminActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void logout() {

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

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
