package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigServerDate {

    public static String[] server_date;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/getServerDate.php";
    public static final String KEY_SERVERDATE = "serverdate";
    public static final String JSON_ARRAY = "result";

    private JSONArray matches = null;

    private String json;

    public ConfigServerDate(String json){
        this.json = json;
    }

    protected void ConfigServerDate() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("null")) {
                matches = jsonObject.getJSONArray(JSON_ARRAY);

                server_date = new String[matches.length()];

                for (int i = 0; i < matches.length(); i++) {
                    JSONObject jo = matches.getJSONObject(i);
                    server_date[i] = jo.getString(KEY_SERVERDATE);
                }
            } else {
                if (server_date != null) {
                    Arrays.fill(server_date, null);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
