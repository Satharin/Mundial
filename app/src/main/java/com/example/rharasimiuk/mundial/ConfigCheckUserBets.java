package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigCheckUserBets {

    public static String[] logins, bets_a, bets_b;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getCheckUserBets.php?id_match=";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_BET_A = "bet_a";
    public static final String KEY_BET_B = "bet_b";
    public static final String JSON_ARRAY = "result";

    private JSONArray matches = null;

    private String json;

    public ConfigCheckUserBets(String json){
        this.json = json;
    }

    protected void ConfigCheckUserBets() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("null")) {
                matches = jsonObject.getJSONArray(JSON_ARRAY);

                logins = new String[matches.length()];
                bets_a = new String[matches.length()];
                bets_b = new String[matches.length()];


                for (int i = 0; i < matches.length(); i++) {
                    JSONObject jo = matches.getJSONObject(i);
                    logins[i] = jo.getString(KEY_LOGIN);
                    bets_a[i] = jo.getString(KEY_BET_A);
                    bets_b[i] = jo.getString(KEY_BET_B);
                }
            } else {
                if (logins != null) {
                    Arrays.fill(logins, null);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
