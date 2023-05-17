package com.iot.smarthome;

import android.app.Activity;
import android.os.Bundle;
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

public class SmartDoor extends Activity {
    Switch Depan_switch;
    Switch Garasi_switch;
    Switch Belakang_switch;
    Switch Samping_switch;
    Switch Pagar_switch;

    TextView smartID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_door);
        Depan_switch = (Switch) findViewById(R.id.depan_switch);
        Garasi_switch = (Switch) findViewById(R.id.garasi_switch);
        Belakang_switch = (Switch) findViewById(R.id.belakang_switch);
        Samping_switch = (Switch) findViewById(R.id.samping_switch);
        Pagar_switch = (Switch) findViewById(R.id.pagar_switch);


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
        Depan_switch.setEnabled(status);
        Garasi_switch.setEnabled(status);
        Belakang_switch.setEnabled(status);
        Samping_switch.setEnabled(status);
        Pagar_switch.setEnabled(status);

    }

    protected void
    selectRoomActivation(String id) {
        Depan_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //untuk dipanggil saat status diklik dari tambol kamar 1 berubah
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // dipanggil saat status button  di klik telah berubah
                getLampStatus("depan", Depan_switch.isChecked(), id); //
            }
        });
        Garasi_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //untuk dipanggil saat status diklik dari tambol kamar 1 berubah
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // dipanggil saat status button  di klik telah berubah
                getLampStatus("garasi", Garasi_switch.isChecked(), id); //
            }
        });
        Belakang_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //untuk dipanggil saat status diklik dari tambol kamar 1 berubah
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // dipanggil saat status button  di klik telah berubah
                getLampStatus("belakang", Belakang_switch.isChecked(), id); //
            }
        });
        Samping_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //untuk dipanggil saat status diklik dari tambol kamar 1 berubah
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // dipanggil saat status button  di klik telah berubah
                getLampStatus("samping", Samping_switch.isChecked(), id); //
            }
        });
        Pagar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //untuk dipanggil saat status diklik dari tambol kamar 1 berubah
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // dipanggil saat status button  di klik telah berubah
                getLampStatus("pagar", Pagar_switch.isChecked(), id); //
            }
        });





    }

    protected void activityOnStartState(String id) {
        smartID.setText("ID Smart: " + id); //mendeklarasi untuk menampilkan id dari pengguna



        RequestQueue requestQueue = Volley.newRequestQueue(SmartDoor.this); // Meminta permintaan antrian dari MainActivity
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://adnaniot.000webhostapp.com/iot/read_pintu.php?id=" + id, //mengambil data dari webservice
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String Depan = jsonObject.getString("depan");
                            String Garasi = jsonObject.getString("garasi");
                            String Belakang = jsonObject.getString("belakang");
                            String Samping = jsonObject.getString("Samping");
                            String Pagar = jsonObject.getString("pagar");



                            setFunction(true);

                            Depan_switch.setChecked(Depan.equals("1"));
                            Garasi_switch.setChecked(Garasi.equals("1"));
                            Belakang_switch.setChecked(Belakang.equals("1"));
                            Samping_switch.setChecked(Samping.equals("1"));
                            Pagar_switch.setChecked(Pagar.equals("1"));



                            selectRoomActivation(id);
                        } catch (Exception e) {
                            setFunction(false);
                            Toast.makeText(SmartDoor.this, "Nama rumah tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setFunction(false);
                        Toast.makeText(SmartDoor.this, "Nama rumah tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
        requestQueue.cancelAll(stringRequest);
    }

    public void getLampStatus(String room, Boolean status, String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(SmartDoor.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://smarthomeiot1.000webhostapp.com/iot/read_pintu.php?id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Depan = jsonObject.getString("depan");
                            String Garasi = jsonObject.getString("garasi");
                            String Belakang = jsonObject.getString("belakang");
                            String Samping = jsonObject.getString("Samping");
                            String Pagar = jsonObject.getString("pagar");

                            if (room.equals("depan")) {
                                Depan = (status) ? "1" : "0";
                            } if (room.equals("garasi")) {
                                Garasi = (status) ? "1" : "0";
                            } if (room.equals("belakang")) {
                                Belakang = (status) ? "1" : "0";
                            } if (room.equals("samping")) {
                                Samping = (status) ? "1" : "0";
                            } if (room.equals("pagar")) {
                                Pagar = (status) ? "1" : "0";
                            }




                            String state = "https://adnaniot.000webhostapp.com/iot/read_pintu.php?id=" + id +
                                    "&depan="  + Depan +
                                    "&garasi="  + Garasi+
                                    "&belakang="  + Belakang+
                                    "&Samping="  + Samping+
                                    "&pagar="+Pagar;

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
        RequestQueue requestQueue = Volley.newRequestQueue(SmartDoor.this);
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