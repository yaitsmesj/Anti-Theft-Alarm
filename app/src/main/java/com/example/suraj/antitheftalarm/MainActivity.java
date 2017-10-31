package com.example.suraj.antitheftalarm;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    boolean chargeStatus = false;
    boolean motionStatus = false;
    boolean proxStatus = false;
    boolean status = false;

    Intent intent;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.settings){
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCharge:

                if(chargeStatus == false){

                    if(status==true){
                        sensorClose();
                    }

                    Boolean plugged = Utils.isConnected(this);
                    if(plugged){

                        Log.d(TAG, "onClick: ");
                        intent = new Intent(this, MyIntentService.class);
                        intent.putExtra("SERVICE_VALUE",Utils.CHARGING_CONSTANT);
                        startService(intent);
                        btnCharge.setBackgroundColor(Color.BLUE);

                        chargeStatus = true;
                        status = true;

                    }else{
                        Toast.makeText(this, "Connect the Charger first", Toast.LENGTH_SHORT).show();
                    }
                }else if(chargeStatus == true){
                    sensorClose();
                    btnCharge.setBackgroundColor(Color.parseColor("#d6d7d7"));
                }


                break;
            case R.id.btnMotion:

                if(motionStatus == false){
                    if(status == true){
                        sensorClose();
                    }

                    Log.d(TAG, "onClick: Motion Button");
                    intent = new Intent(this, MyIntentService.class);
                    intent.putExtra("SERVICE_VALUE",Utils.MOTION_CONSTANT);
                    startService(intent);
                    btnMotion.setBackgroundColor(Color.BLUE);

                    motionStatus =true;
                    status = true;
                }else{
                    sensorClose();
                    btnMotion.setBackgroundColor(Color.parseColor("#d6d7d7"));

                }
                break;

            case R.id.btnProximity:

                if(proxStatus == false){
                    if(status==true){
                        sensorClose();
                    }
                    Log.d(TAG, "onClick: Proximity Button");
                    intent = new Intent(this, MyIntentService.class);
                    intent.putExtra("SERVICE_VALUE",Utils.PROXIMITY_CONSTANT);
                    startService(intent);
                    btnProximity.setBackgroundColor(Color.BLUE);

                    proxStatus = true;
                    status = true;
                }else{
                    sensorClose();
                    btnProximity.setBackgroundColor(Color.parseColor("#d6d7d7"));

                }
                break;
            case R.id.btnSettings:
                Log.d(TAG, "onClick: Settings Button");
                break;
            default:
                Log.d(TAG, "onClick: Error");
        }
    }

    public void sensorClose(){

        stopService(intent);
        motionStatus = false;
        chargeStatus = false;
        proxStatus = false;

        status = false;
    }


}
