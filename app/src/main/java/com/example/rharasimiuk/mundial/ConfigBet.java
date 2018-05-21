package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigBet {

    public static String[] logins, bets_a, bets_b, id_matches;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/checkBet.php?login=";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_BET_A = "bet_a";
    public static final String KEY_BET_B = "bet_b";
    public static final String KEY_ID_MATCH = "id_match";
    public static final String JSON_ARRAY = "result";

    private JSONArray table = null;

    private String json;

    public ConfigBet(String json){
        this.json = json;
    }

    protected void ConfigBet() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("null")) {
                table = jsonObject.getJSONArray(JSON_ARRAY);

                logins = new String[table.length()];
                bets_a = new String[table.length()];
                bets_b = new String[table.length()];
                id_matches = new String[table.length()];



                for (int i = 0; i < table.length(); i++) {
                    JSONObject jo = table.getJSONObject(i);
                    logins[i] = jo.getString(KEY_LOGIN);
                    bets_a[i] = jo.getString(KEY_BET_A);
                    bets_b[i] = jo.getString(KEY_BET_B);
                    id_matches[i] = jo.getString(KEY_ID_MATCH);

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
