package com.iot.smarthome;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class  LoginActivity extends Activity {
    public String smartID = "smart1";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_login1);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.purple_500));
        }

        EditText username = (EditText) findViewById(R.id.username_id);
        EditText password = (EditText) findViewById(R.id.password_id);

        findViewById(R.id.login_btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://adnaniot.000webhostapp.com/iot/read_lampu.php?id=" + username.getText().toString();

                getString(new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (username.getText().toString().equals(jsonObject.getString("id"))
                                    && password.getText().toString().equals(jsonObject.getString("password")))
                                startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("smart_id", smartID));

                            else throwDefaultAccount(username, password);
                        } catch (Exception e) {
                            throwDefaultAccount(username, password);
                        }
                    }

                    @Override
                    public void onError(String result) {
                    }
                }, url);
            }
        });
    }

    protected void throwDefaultAccount(EditText username, EditText password) {
        if (!("smart1".equals(username.getText().toString()) && "12345".equals(password.getText().toString())))
            Toast.makeText(LoginActivity.this, "Akun tidak ditemukan di server, " +
                    "akun default: username: smart1 dan password: 12345", Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .putExtra("smart_id", smartID));
            Toast.makeText(LoginActivity.this, "Smart Home id: " + smartID, Toast.LENGTH_SHORT).show();
        }
    }

    protected void getString(final VolleyCallback callback, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
