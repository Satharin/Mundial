package com.example.rharasimiuk.mundial;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TablesActivity extends AppCompatActivity {

    public TextView team1, m1, gs1, gl1, balance1, points1,
            team2, m2, gs2, gl2, balance2, points2,
            team3, m3, gs3, gl3, balance3, points3,
            team4, m4, gs4, gl4, balance4, points4,
            groupName;

    TextView[] textViewTeams, textViewMatches, textViewGoalsScored, textViewGoalsLost, textViewBalances, textViewPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        buttonEffectApply();

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

        textViewTeams = new TextView[]{team1, team2, team3, team4};
        textViewMatches= new TextView[]{m1, m2, m3, m4};
        textViewGoalsScored = new TextView[]{gs1, gs2, gs3, gs4};
        textViewGoalsLost = new TextView[]{gl1, gl2, gl3, gl4};
        textViewBalances = new TextView[]{balance1, balance2, balance3, balance4};
        textViewPoints = new TextView[]{points1, points2, points3, points4};

        groupName = (TextView) findViewById(R.id.textViewGroupName);

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

        groupName.setText("GROUP A");

        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableA.teams[i]);
            textViewMatches[i].setText(ConfigTableA.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableA.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableA.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableA.balances[i]);
            textViewPoints[i].setText(ConfigTableA.points[i]);
        }

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

        groupName.setText("GROUP B");

        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableB.teams[i]);
            textViewMatches[i].setText(ConfigTableB.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableB.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableB.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableB.balances[i]);
            textViewPoints[i].setText(ConfigTableB.points[i]);
        }

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

        groupName.setText("GROUP C");
        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableC.teams[i]);
            textViewMatches[i].setText(ConfigTableC.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableC.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableC.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableC.balances[i]);
            textViewPoints[i].setText(ConfigTableC.points[i]);
        }

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

        groupName.setText("GROUP D");

        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableD.teams[i]);
            textViewMatches[i].setText(ConfigTableD.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableD.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableD.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableD.balances[i]);
            textViewPoints[i].setText(ConfigTableD.points[i]);
        }

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

        groupName.setText("GROUP E");

        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableE.teams[i]);
            textViewMatches[i].setText(ConfigTableE.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableE.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableE.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableE.balances[i]);
            textViewPoints[i].setText(ConfigTableE.points[i]);
        }

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

        groupName.setText("GROUP F");

        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableF.teams[i]);
            textViewMatches[i].setText(ConfigTableF.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableF.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableF.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableF.balances[i]);
            textViewPoints[i].setText(ConfigTableF.points[i]);
        }

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

        groupName.setText("GROUP G");

        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableG.teams[i]);
            textViewMatches[i].setText(ConfigTableG.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableG.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableG.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableG.balances[i]);
            textViewPoints[i].setText(ConfigTableG.points[i]);
        }

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

        groupName.setText("GROUP H");

        for(int i = 0; i < 4; i++){
            textViewTeams[i].setText(ConfigTableH.teams[i]);
            textViewMatches[i].setText(ConfigTableH.matches[i]);
            textViewGoalsScored[i].setText(ConfigTableH.goals_scored[i]);
            textViewGoalsLost[i].setText(ConfigTableH.goals_lost[i]);
            textViewBalances[i].setText(ConfigTableH.balances[i]);
            textViewPoints[i].setText(ConfigTableH.points[i]);
        }

    }

    public void buttonEffectApply() {
        Button groupA = (Button) findViewById(R.id.buttonA);
        Button groupB = (Button) findViewById(R.id.buttonB);
        Button groupC = (Button) findViewById(R.id.buttonC);
        Button groupD = (Button) findViewById(R.id.buttonD);
        Button groupE = (Button) findViewById(R.id.buttonE);
        Button groupF = (Button) findViewById(R.id.buttonF);
        Button groupG = (Button) findViewById(R.id.buttonG);
        Button groupH = (Button) findViewById(R.id.buttonH);
        Button back = (Button) findViewById(R.id.buttonHome);
        Button exit = (Button) findViewById(R.id.buttonMenu);

        buttonEffect(groupA);
        buttonEffect(groupB);
        buttonEffect(groupC);
        buttonEffect(groupD);
        buttonEffect(groupE);
        buttonEffect(groupF);
        buttonEffect(groupG);
        buttonEffect(groupH);
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

    /*public void resetTable(View view) {

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

    }*/

    public void goToHome(View view) {

        if(haveNetworkConnection()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(TablesActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
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

    public void goToCheckBets (){

        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), CheckBetsActivity.class));
            finish();

        }else {
            Toast.makeText(TablesActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    public void goToPoints () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), PointsActivity.class));
            finish();

        }else {
            Toast.makeText(TablesActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToTables () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), TablesActivity.class));
            finish();

        }else {
            Toast.makeText(TablesActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToYourBets () {
        if(haveNetworkConnection()) {

            startActivity(new Intent(getApplicationContext(), YourBetsActivity.class));
            finish();

        }else {
            Toast.makeText(TablesActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
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
