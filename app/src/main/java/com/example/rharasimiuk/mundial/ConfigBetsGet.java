package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigBetsGet {

    public static String[] logins, id_matches, bets_a, bets_b, results_a, results_b, id_bets;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getBetsWithScores.php?id_match=";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_ID_MATCH = "id_match";
    public static final String KEY_BET_A = "bet_a";
    public static final String KEY_BET_B = "bet_b";
    public static final String KEY_RESULT_A = "result_a";
    public static final String KEY_RESULT_B = "result_b";
    public static final String KEY_ID_BET = "id_bet";
    public static final String JSON_ARRAY = "result";

    private JSONArray matches = null;

    private String json;

    public ConfigBetsGet(String json){
        this.json = json;
    }

    protected void ConfigBetsGet() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("null")) {
                matches = jsonObject.getJSONArray(JSON_ARRAY);

                logins = new String[matches.length()];
                id_matches = new String[matches.length()];
                bets_a = new String[matches.length()];
                bets_b = new String[matches.length()];
                results_a = new String[matches.length()];
                results_b = new String[matches.length()];
                id_bets = new String[matches.length()];


                for (int i = 0; i < matches.length(); i++) {
                    JSONObject jo = matches.getJSONObject(i);
                    logins[i] = jo.getString(KEY_LOGIN);
                    id_matches[i] = jo.getString(KEY_ID_MATCH);
                    bets_a[i] = jo.getString(KEY_BET_A);
                    bets_b[i] = jo.getString(KEY_BET_B);
                    results_a[i] = jo.getString(KEY_RESULT_A);
                    results_b[i] = jo.getString(KEY_RESULT_B);
                    id_bets[i] = jo.getString(KEY_ID_BET);

                }

                for (int i = 0; i < bets_a.length; i++) {
                    System.out.print(bets_a[i] + " ");
                }

            } else {
                if (bets_a != null) {
                    Arrays.fill(bets_a, null);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

