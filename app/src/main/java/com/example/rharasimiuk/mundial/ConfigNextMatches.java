package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigNextMatches {

    public static String[] teams_a, teams_b, dates, times, bets_a, bets_b, id_matches, current_dates;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getNextMatches.php?login=";
    public static final String KEY_TEAM_A = "team_a";
    public static final String KEY_TEAM_B = "team_b";
    public static final String KEY_DATE_MATCH = "date_match";
    public static final String KEY_TIME_MATCH = "time_match";
    public static final String KEY_BET_A = "bet_a";
    public static final String KEY_BET_B = "bet_b";
    public static final String KEY_ID_MATCH = "id_match";
    public static final String KEY_CURRENT_DATE = "currentdate";
    public static final String JSON_ARRAY = "result";

    private JSONArray matches = null;

    private String json;

    public ConfigNextMatches(String json){
        this.json = json;
    }

    protected void ConfigNextMatches() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(String.valueOf(json));
            if (!JSON_ARRAY.equals("null")) {
                matches = jsonObject.getJSONArray(JSON_ARRAY);

                teams_a = new String[matches.length()];
                teams_b = new String[matches.length()];
                dates = new String[matches.length()];
                times = new String[matches.length()];
                bets_a = new String[matches.length()];
                bets_b = new String[matches.length()];
                id_matches = new String[matches.length()];
                current_dates = new String[matches.length()];


                for (int i = 0; i < matches.length(); i++) {
                    JSONObject jo = matches.getJSONObject(i);
                    teams_a[i] = jo.getString(KEY_TEAM_A);
                    teams_b[i] = jo.getString(KEY_TEAM_B);
                    dates[i] = jo.getString(KEY_DATE_MATCH);
                    times[i] = jo.getString(KEY_TIME_MATCH);
                    bets_a[i] = jo.getString(KEY_BET_A);
                    bets_b[i] = jo.getString(KEY_BET_B);
                    id_matches[i] = jo.getString(KEY_ID_MATCH);
                    current_dates[i] = jo.getString(KEY_CURRENT_DATE);
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

