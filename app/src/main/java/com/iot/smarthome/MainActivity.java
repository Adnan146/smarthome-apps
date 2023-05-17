package com.iot.smarthome;



import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Intent;

import androidx.cardview.widget.CardView;

public class MainActivity extends Activity {
    TextView smartID;
    CardView lampu;
    CardView pagar;
    CardView suhu;
    CardView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smartID = (TextView) findViewById(R.id.smart_id);
        lampu = (CardView) findViewById(R.id.cv_sl);
        pagar = (CardView) findViewById(R.id.cv_sd);
        suhu = (CardView) findViewById(R.id.cv_suhu);
        about = (CardView) findViewById(R.id.cv_about);

        activityOnStartState(getIntent().getStringExtra("smart_id"));

       menu_activty();

    }

    private void menu_activty() {
        lampu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SmartLightID.class));
            }
        });
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SmartDoorID.class));
            }
        });


        suhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Temperature.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });
    }

    protected void activityOnStartState(String id) {
        smartID.setText("Hello "+ id);
    }

}