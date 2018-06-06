package com.example.rharasimiuk.mundial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigResult {

    public static String[] results_a, results_b, id_matches;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/mundial/checkResult.php?id_match=";
    public static final String KEY_RESULT_A = "result_a";
    public static final String KEY_RESULT_B = "result_b";
    public static final String KEY_ID_MATCH = "id_match";
    public static final String JSON_ARRAY = "result";

    private JSONArray table = null;

    private String json;

    public ConfigResult(String json){
        this.json = json;
    }

    protected void ConfigResult() {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if (!JSON_ARRAY.equals("")) {
                table = jsonObject.getJSONArray(JSON_ARRAY);

                results_a = new String[table.length()];
                results_b = new String[table.length()];
                id_matches = new String[table.length()];

                for (int i = 0; i < table.length(); i++) {
                    JSONObject jo = table.getJSONObject(i);
                    results_a[i] = jo.getString(KEY_RESULT_A);
                    results_b[i] = jo.getString(KEY_RESULT_B);
                    id_matches[i] = jo.getString(KEY_ID_MATCH);

                }
            } else {
                if (results_a != null) {
                    Arrays.fill(results_a, null);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
