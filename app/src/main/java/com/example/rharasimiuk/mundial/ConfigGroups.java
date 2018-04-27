package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigGroups {
    public static String[] teams_a, teams_b, dates, times;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getGroups.php?stage=";
    public static final String KEY_TEAM_A = "team_a";
    public static final String KEY_TEAM_B = "team_b";
    public static final String JSON_ARRAY = "result";

    private JSONArray matches = null;

    private String json;

    public ConfigGroups(String json){
        this.json = json;
    }

    protected void ConfigGroups() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("null")) {
                matches = jsonObject.getJSONArray(JSON_ARRAY);
                teams_a = new String[matches.length()];
                teams_b = new String[matches.length()];


                for (int i = 0; i < matches.length(); i++) {
                    JSONObject jo = matches.getJSONObject(i);
                    teams_a[i] = jo.getString(KEY_TEAM_A);
                    teams_b[i] = jo.getString(KEY_TEAM_B);
                }
            } else {
                if (teams_a != null) {
                    Arrays.fill(teams_a, null);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


