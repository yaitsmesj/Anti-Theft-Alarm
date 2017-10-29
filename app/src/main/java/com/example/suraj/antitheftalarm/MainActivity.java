package com.example.suraj.antitheftalarm;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.suraj.antitheftalarm.receivers.ChargingReceiver;
import com.example.suraj.antitheftalarm.services.MyIntentService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MainActivity";
    ImageButton btnCharge;
    ImageButton btnMotion;
    ImageButton btnProximity;
    ImageButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCharge = (ImageButton) findViewById(R.id.btnCharge);
        btnMotion = (ImageButton) findViewById(R.id.btnMotion);
        btnProximity = (ImageButton) findViewById(R.id.btnProximity);
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);

        btnCharge.setOnClickListener(this);
        btnMotion.setOnClickListener(this);
        btnProximity.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCharge:
                Boolean plugged = Utils.isConnected(this);
                if(plugged){

                    Log.d(TAG, "onClick: ");
                    Intent intent = new Intent(this, MyIntentService.class);
                    intent.putExtra("SERVICE_VALUE",Utils.CHARGING_CONSTANT);
                    startService(intent);
                    btnCharge.setBackgroundColor(Color.BLUE);
                }else{
                    Toast.makeText(this, "Connect the Charger first", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnMotion:{
                Log.d(TAG, "onClick: Motion Button");
                Intent intent = new Intent(this, MyIntentService.class);
                intent.putExtra("SERVICE_VALUE",Utils.MOTION_CONSTANT);
                startService(intent);
                btnMotion.setBackgroundColor(Color.BLUE);

            }
                break;
            case R.id.btnProximity:
                Log.d(TAG, "onClick: Proximity Button");
                Intent intent = new Intent(this, MyIntentService.class);
                intent.putExtra("SERVICE_VALUE",Utils.PROXIMITY_CONSTANT);
                startService(intent);
                btnProximity.setBackgroundColor(Color.BLUE);
                break;
            case R.id.btnSettings:
                Log.d(TAG, "onClick: Settings Button");
                break;
            default:
                Log.d(TAG, "onClick: Error");
        }
    }
}
