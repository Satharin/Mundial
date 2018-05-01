package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigTableE {

    public static String[] teams, matches, goals_scored, goals_lost, balances, points;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getTableE.php";
    public static final String KEY_TEAM = "team";
    public static final String KEY_MATCHES = "matches";
    public static final String KEY_GOALS_SCORED = "goals_scored";
    public static final String KEY_GOALS_LOST = "goals_lost";
    public static final String KEY_BALANCE = "balance";
    public static final String KEY_POINTS = "points";
    public static final String JSON_ARRAY = "result";

    private JSONArray table = null;

    private String json;

    public ConfigTableE(String json){
        this.json = json;
    }

    protected void ConfigTableE() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("null")) {
                table = jsonObject.getJSONArray(JSON_ARRAY);

                teams = new String[table.length()];
                matches = new String[table.length()];
                goals_scored = new String[table.length()];
                goals_lost = new String[table.length()];
                balances = new String[table.length()];
                points = new String[table.length()];


                for (int i = 0; i < table.length(); i++) {
                    JSONObject jo = table.getJSONObject(i);
                    teams[i] = jo.getString(KEY_TEAM);
                    matches[i] = jo.getString(KEY_MATCHES);
                    goals_scored[i] = jo.getString(KEY_GOALS_SCORED);
                    goals_lost[i] = jo.getString(KEY_GOALS_LOST);
                    balances[i] = jo.getString(KEY_BALANCE);
                    points[i] = jo.getString(KEY_POINTS);
                }
            } else {
                if (teams != null) {
                    Arrays.fill(teams, null);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}