package com.iot.smarthome;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Build;

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

public class SmartLightID extends Activity {
    public String smartID = "smart1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_light_id);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.purple_500));
        }

        EditText username = (EditText) findViewById(R.id.smart_id);


        findViewById(R.id.btn_masuk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://adnaniot.000webhostapp.com/iot/read_lampu.php?id=" + username.getText().toString();

                getString(new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (username.getText().toString().equals(jsonObject.getString("id")))
                                startActivity(new Intent(SmartLightID.this, SmartLight.class).putExtra("smart_id", smartID));

                            else throwDefaultAccount(username);
                        } catch (Exception e) {
                            throwDefaultAccount(username);
                        }
                    }

                    @Override
                    public void onError(String result) {
                    }
                }, url);
            }
        });
    }

    protected void throwDefaultAccount(EditText username) {
        if (!("smart1".equals(username.getText().toString()) ))
            Toast.makeText(SmartLightID.this, "Akun tidak ditemukan di server, " +
                    "akun default: username: smart1 dan password: 12345", Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(SmartLightID.this, SmartLight.class)
                    .putExtra("smart_id", smartID));
            Toast.makeText(SmartLightID.this, "Smart Home id: " + smartID, Toast.LENGTH_SHORT).show();
        }
    }

    protected void getString(final VolleyCallback callback, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(SmartLightID.this);
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