package com.sayed.servicesex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Views - no data bindin as it simple sample
    Button btnStart,btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        btnStart=findViewById(R.id.btnStartService);
        btnStop=findViewById(R.id.btnStopService);

        //ON CLICKS
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use this to start and trigger a service
                Intent i= new Intent(getBaseContext(), MyService.class);
                // potentially add data to the intent
                i.putExtra("KEY1", "Value to be used by the service");
                startService(i);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getBaseContext(), MyService.class);
                stopService(i);
            }
        });
    }
}
