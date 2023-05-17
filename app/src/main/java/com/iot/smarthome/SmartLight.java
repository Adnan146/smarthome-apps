package com.iot.smarthome;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Build;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
public class SmartLight extends Activity {
    Switch kamar1_switch;
    Switch kamar2_switch;
    Switch ruangtengah_switch;
    Switch ruangbelakang_switch;
    Switch dapur_switch;
    Switch garasi_switch;
    Switch ruangtamu_switch;
    Switch teras_switch;


    TextView smartID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_light);
        garasi_switch = (Switch) findViewById(R.id.garasi_switch);
        dapur_switch = (Switch) findViewById(R.id.dapur_switch);
        kamar1_switch = (Switch) findViewById(R.id.kamar1_switch);
        kamar2_switch = (Switch) findViewById(R.id.kamar2_switch);
        ruangtamu_switch = (Switch) findViewById(R.id.ruangtamu_switch);
        ruangtengah_switch = (Switch) findViewById(R.id.ruangtengah_switch);
        ruangbelakang_switch = (Switch) findViewById(R.id.ruangbelakang_switch);
        teras_switch = (Switch) findViewById(R.id.teras_switch);

        smartID = (TextView) findViewById(R.id.smart_id);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.purple_200));
        }

//        starting point
        setFunction(false);
        activityOnStartState(getIntent().getStringExtra("smart_id"));
    }

    protected void setFunction(Boolean status) {
        kamar1_switch.setEnabled(status);
        kamar2_switch.setEnabled(status);
        garasi_switch.setEnabled(status);
        ruangtengah_switch.setEnabled(status);
        ruangbelakang_switch.setEnabled(status);
        dapur_switch.setEnabled(status);
        ruangtamu_switch.setEnabled(status);
        teras_switch.setEnabled(status);

    }

    protected void
    selectRoomActivation(String id) {
        garasi_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLampStatus("garasi", garasi_switch.isChecked(), id);
            }
        });
        teras_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLampStatus("teras", teras_switch.isChecked(), id);
            }
        });
        ruangtamu_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLampStatus("ruangtamu", ruangtamu_switch.isChecked(), id);
            }
        });
        ruangtengah_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLampStatus("ruangtengah", ruangtengah_switch.isChecked(), id);
            }
        });
        ruangbelakang_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLampStatus("ruangbelakang", ruangbelakang_switch.isChecked(), id);
            }
        });
        dapur_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLampStatus("dapur", dapur_switch.isChecked(), id);
            }
        });
        kamar1_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //untuk dipanggil saat status diklik dari tambol kamar 1 berubah
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // dipanggil saat status button  di klik telah berubah
                getLampStatus("kamar1", kamar1_switch.isChecked(), id); //
            }
        });
        kamar2_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLampStatus("kamar2", kamar2_switch.isChecked(), id);
            }
        });






    }

    protected void activityOnStartState(String id) {
        smartID.setText("ID Smart: " + id); //mendeklarasi untuk menampilkan id dari pengguna



        RequestQueue requestQueue = Volley.newRequestQueue(SmartLight.this); // Meminta permintaan antrian dari MainActivity
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://adnaniot.000webhostapp.com/iot/read_lampu.php?id=" + id, //mengambil data dari webservice
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String garasi = jsonObject.getString("garasi");
                            String teras = jsonObject.getString("teras");
                            String ruangTamu = jsonObject.getString("ruangtamu");
                            String ruangTengah = jsonObject.getString("ruangtengah");
                            String ruangBelakang = jsonObject.getString("ruangbelakang");
                            String dapur = jsonObject.getString("dapur");
                            String kamar1 = jsonObject.getString("kamar1");
                            String kamar2 = jsonObject.getString("kamar2");


                            setFunction(true);

                            garasi_switch.setChecked(garasi.equals("1"));
                            teras_switch.setChecked(teras.equals("1"));
                            ruangtamu_switch.setChecked(ruangTamu.equals("1"));
                            ruangtengah_switch.setChecked(ruangTengah.equals("1"));
                            ruangbelakang_switch.setChecked(ruangBelakang.equals("1"));
                            dapur_switch.setChecked(dapur.equals("1"));
                            kamar1_switch.setChecked(kamar1.equals("1"));
                            kamar2_switch.setChecked(kamar2.equals("1"));


                            selectRoomActivation(id);
                        } catch (Exception e) {
                            setFunction(false);
                            Toast.makeText(SmartLight.this, "Nama rumah tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setFunction(false);
                        Toast.makeText(SmartLight.this, "Nama rumah tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
        requestQueue.cancelAll(stringRequest);
    }

    public void getLampStatus(String room, Boolean status, String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(SmartLight.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://adnaniot.000webhostapp.com/iot/read_lampu.php?id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String garasi = jsonObject.getString("garasi");
                            String teras = jsonObject.getString("teras");
                            String ruangTamu = jsonObject.getString("ruangtamu");
                            String ruangTengah = jsonObject.getString("ruangtengah");
                            String ruangBelakang = jsonObject.getString("ruangbelakang");
                            String dapur = jsonObject.getString("dapur");
                            String kamar1 = jsonObject.getString("kamar1");
                            String kamar2 = jsonObject.getString("kamar2");


                            if (room.equals("garasi")) {
                                garasi = (status) ? "1" : "0";
                            }
                            if (room.equals("teras")) {
                                teras = (status) ? "1" : "0";
                            }
                            if (room.equals("ruangtamu")) {
                                ruangTamu = (status) ? "1" : "0";
                            }
                            if (room.equals("ruangtengah")) {
                                ruangTengah = (status) ? "1" : "0";
                            }
                            if (room.equals("ruangbelakang")) {
                                ruangBelakang = (status) ? "1" : "0";
                            }
                            if (room.equals("dapur")) {
                                dapur = (status) ? "1" : "0";
                            }
                            if (room.equals("kamar1")) {
                                kamar1 = (status) ? "1" : "0";
                            }
                            if (room.equals("kamar2")) {
                                kamar2 = (status) ? "1" : "0";
                            }



                            String state = "https://adnaniot.000webhostapp.com/iot/read_lampu.php?id=" + id +
                                    "&garasi=" + garasi +
                                    "&teras=" + teras +
                                    "&ruangtamu=" + ruangTamu +
                                    "&ruangtengah=" + ruangTengah +
                                    "&ruangbelakang=" + ruangBelakang +
                                    "&dapur=" + dapur +
                                    "&kamar1=" + kamar1+
                                    "&kamar2=" + kamar2 ;

                            setLamp(state);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(stringRequest);
        requestQueue.cancelAll(stringRequest);
    }

    protected void setLamp(String value) {
        RequestQueue requestQueue = Volley.newRequestQueue(SmartLight.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(stringRequest);
        requestQueue.cancelAll(stringRequest);
    }



}