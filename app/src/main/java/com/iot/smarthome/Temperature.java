package com.iot.smarthome;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Temperature extends Activity {
    private static final String TAG ="MyService:";
    Handler handler= new Handler();
    Runnable runnable;
    int delay = 2000;

    String suhu ;
    TextView TV_Suhu, TV_Info ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        TV_Suhu =findViewById(R.id.tv_suhu);
        TV_Info = findViewById(R.id.tv_info);




        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable, delay);
                get_data();
                //Toast.makeText(MainActivity.this, "TAMPIL DATA", Toast.LENGTH_SHORT).show();
            }
        }, delay);
    }

    //method
    private void get_data(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://adnaniot.000webhostapp.com/suhu/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(MainActivity.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Response : "+response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String success = obj.getString("status");
                    //String pesan   = obj.getString("pesan");

                    if (success.equals("true")){
                        JSONArray jsonArray= obj.getJSONArray( "data");
                        JSONObject jsonObject= jsonArray.getJSONObject( 0);
                        suhu         = jsonObject.getString("suhu");
                        TV_Suhu.setText(suhu);


                        Integer cek_suhu;
                        cek_suhu= Integer.parseInt(suhu);
                        if (cek_suhu <= 28){
                            TV_Info.setText("Gunakan Pakaian tebal ");


                        }else {
                            TV_Info.setText("Minum Es agar segar");

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // JIKA MENGGUNAKAN SECURITY Authorizaiton Tambahkan disini skemanya
                //headers.put("Authorization", "Bearer " + token_key);
                return headers;
            }



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                // JIKA ADA NILAI POST YANG INGIN DIBERIKAN tambahkan disini Contoh :
                /*parmas.put("orderId", orderId);*/

                return parmas;
            }

        };

        int socketTimeOut = 10000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }
}