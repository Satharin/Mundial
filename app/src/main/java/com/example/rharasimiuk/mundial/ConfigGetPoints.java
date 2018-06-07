package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigGetPoints {

    public static String[] users, points, exact_results;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getPointsRank.php?login=";
    public static final String KEY_LOGIN = "users";
    public static final String KEY_POINTS = "points";
    public static final String KEY_EXACT_RESULT = "exact_results";
    public static final String JSON_ARRAY = "result";

    private JSONArray table = null;

    private String json;

    public ConfigGetPoints(String json){
        this.json = json;
    }

    protected void ConfigGetPoints() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("")) {
                table = jsonObject.getJSONArray(JSON_ARRAY);

                users = new String[table.length()];
                points = new String[table.length()];
                exact_results = new String[table.length()];

                for (int i = 0; i < table.length(); i++) {
                    JSONObject jo = table.getJSONObject(i);
                    users[i] = jo.getString(KEY_LOGIN);
                    points[i] = jo.getString(KEY_POINTS);
                    exact_results[i] = jo.getString(KEY_EXACT_RESULT);

                }
            } else {
                if (users != null) {
                    Arrays.fill(users, null);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
