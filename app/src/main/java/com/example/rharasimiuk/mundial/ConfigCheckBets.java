package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigCheckBets {

    public static String[] teams_a, teams_b, dates, times, id_matches, results_a, results_b;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getCheckBets.php?date_match=";
    public static final String KEY_TEAM_A = "team_a";
    public static final String KEY_TEAM_B = "team_b";
    public static final String KEY_DATE_MATCH = "date_match";
    public static final String KEY_TIME_MATCH = "time_match";
    public static final String KEY_ID_MATCH = "id_match";
    public static final String KEY_RESULT_A = "result_a";
    public static final String KEY_RESULT_B = "result_b";
    public static final String JSON_ARRAY = "result";

    private JSONArray matches = null;

    private String json;

    public ConfigCheckBets(String json){
        this.json = json;
    }

    protected void ConfigCheckBets() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("null")) {
                matches = jsonObject.getJSONArray(JSON_ARRAY);

                teams_a = new String[matches.length()];
                teams_b = new String[matches.length()];
                dates = new String[matches.length()];
                times = new String[matches.length()];
                id_matches = new String[matches.length()];
                results_a = new String[matches.length()];
                results_b = new String[matches.length()];


                for (int i = 0; i < matches.length(); i++) {
                    JSONObject jo = matches.getJSONObject(i);
                    teams_a[i] = jo.getString(KEY_TEAM_A);
                    teams_b[i] = jo.getString(KEY_TEAM_B);
                    dates[i] = jo.getString(KEY_DATE_MATCH);
                    times[i] = jo.getString(KEY_TIME_MATCH);
                    id_matches[i] = jo.getString(KEY_ID_MATCH);
                    results_a[i] = jo.getString(KEY_RESULT_A);
                    results_b[i] = jo.getString(KEY_RESULT_B);
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
