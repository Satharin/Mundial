package com.example.rharasimiuk.mundial;

import android.annotation.SuppressLint;
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
import android.support.v7.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class PointsActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        buttonEffectApply();

        getNextMatch(loadLogin());

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

    }

    public void getNextMatch (String login) {

        final ProgressDialog loadingMatches = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigGetPoints.DATA_URL + login;

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
                        Toast.makeText(PointsActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String json) {

        ConfigGetPoints pj = new ConfigGetPoints(json);
        pj.ConfigGetPoints();

        if(ConfigGetPoints.users != null) {

            String[] matches = new String[ConfigGetPoints.users.length];

            for (int i = 0; i < ConfigGetPoints.users.length; i++) {

                matches[i] = ConfigGetPoints.users[i] + "   -   " + ConfigGetPoints.points[i] + "   -   " + ConfigGetPoints.exact_results[i];

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);

        }else{

            String[] matches = new String[1];
            matches[0] = "No bets yet.";
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.my_custom_layout, matches);
            getListView().setAdapter(adapter);

        }

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

    public String loadLogin() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        String login = loadGame.getString("login", "");

        return login;

    }

    public void goToHome(View view) {

        if(haveNetworkConnection()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(PointsActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

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

    public void goToCheckBets (){

        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), CheckBetsActivity.class));
            finish();

        }else {
            Toast.makeText(PointsActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void goToPoints () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), PointsActivity.class));
            finish();

        }else {
            Toast.makeText(PointsActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToTables () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), TablesActivity.class));
            finish();

        }else {
            Toast.makeText(PointsActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToYourBets () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), YourBetsActivity.class));
            finish();

        }else {
            Toast.makeText(PointsActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void logout() {

        saveLogin("", "");
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

    public void saveLogin(String login, String password) {

        SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor save = saveGame.edit();
        save.putString("login", login);
        save.putString("password", password);
        save.apply();

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
