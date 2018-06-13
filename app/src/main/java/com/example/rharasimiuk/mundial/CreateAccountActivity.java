package com.example.rharasimiuk.mundial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editName, editTextPassword, editTextConfirm;
    private Spinner spinner;

    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        buttonEffectApply();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        editName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirm = (EditText) findViewById(R.id.editTextConfirm);
        spinner = (Spinner)findViewById(R.id.spinnerGroup);

        Spinner dropdown = findViewById(R.id.spinnerGroup);
        String[] items = new String[]{"Twierdza", "MQA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void createAccount(View view) {

        final String login = editName.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String confirm = editTextConfirm.getText().toString();
        final String group_name = spinner.getSelectedItem().toString();

        //Check if fields are filled
        if(login.equals("")){
            Toast.makeText(CreateAccountActivity.this, "Field 'Name' is empty.", Toast.LENGTH_LONG).show();
        }else if(password.equals("")) {
            Toast.makeText(CreateAccountActivity.this, "Field 'Password' is empty.", Toast.LENGTH_LONG).show();
        }else if(confirm.equals("")) {
            Toast.makeText(CreateAccountActivity.this, "Field 'Confirm password' is empty.", Toast.LENGTH_LONG).show();
        }else{

            final ProgressDialog loadingCreate = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

            String url = ConfigCreate.DATA_URL + login;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loadingCreate.dismiss();
                    showJSON(response, login, password, confirm, group_name);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CreateAccountActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    private void showJSON(String json, String login, String password, String confirm, String group_name) {

        login = editName.getText().toString();
        password = editTextPassword.getText().toString();
        confirm = editTextConfirm.getText().toString();
        String login2 = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigCreate.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            login2 = collegeData.getString(ConfigCreate.KEY_LOGIN);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(login2.equals(login)) {
            Toast.makeText(CreateAccountActivity.this, "Login " + login + " already registered.", Toast.LENGTH_LONG).show();
            editName.setText("");
            editTextPassword.setText("");
            editTextConfirm.setText("");
        }else if(password.equals(confirm)&&!password.equals("")){
            Toast.makeText(CreateAccountActivity.this, "Registration completed.", Toast.LENGTH_LONG).show();
            savePlayer("https://mundial2018.000webhostapp.com/mundial/saveUser.php", login, password, group_name);
            saveLogin(login);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(CreateAccountActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
            editTextPassword.setText("");
            editTextConfirm.setText("");
        }

    }

    public void saveLogin(String login) {

        android.content.SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        android.content.SharedPreferences.Editor save = saveGame.edit();
        save.putString("login", login);
        save.apply();

    }

    public void buttonEffectApply() {
        Button back = (Button) findViewById(R.id.exit);
        Button create = (Button) findViewById(R.id.buttonCreate);

        buttonEffect(back);
        buttonEffect(create);

    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(Color.parseColor("#4c4cff"), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void savePlayer(String url, final String login, final String password, final String group_name){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("login", login);
                parameters.put("password", password);
                parameters.put("group_name", group_name);

                return parameters;
            }
        };

        requestQueue.add(request);

    }

    public void back(View view) {

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }
}

