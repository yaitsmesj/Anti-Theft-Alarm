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

    // TAG variable for Testing
    public static final String TAG = "MainActivity";

    // Buttons
    ImageButton btnCharge;
    ImageButton btnMotion;
    ImageButton btnProximity;
    ImageButton btnSettings;

    // Status Variables
    boolean chargeStatus = false;
    boolean motionStatus = false;
    boolean proxStatus = false;
    boolean status = false;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //    Log.d(TAG, "onCreate: Oncreate method starting");
        //Getting Reference of Views
        btnCharge = (ImageButton) findViewById(R.id.btnCharge);
        btnMotion = (ImageButton) findViewById(R.id.btnMotion);
        btnProximity = (ImageButton) findViewById(R.id.btnProximity);
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);

        //Setting Listener on Buttons
        btnCharge.setOnClickListener(this);
        btnMotion.setOnClickListener(this);
        btnProximity.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
      //  Log.d(TAG, "onCreate: Oncreate method ending");

    }


    //Method for creating Options Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflating Pop-up Menu
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    //Method for Menu Item Selection

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // Settings Button clicked Condition
        if(itemId == R.id.settings){
            Log.d(TAG, "onOptionsItemSelected: Settings Clicked");
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            return true;
        }
        if(itemId == R.id.share){
            Log.d(TAG, "onOptionsItemSelected: Share Clicked");
            
        }
        if (itemId==R.id.about){
            Log.d(TAG, "onOptionsItemSelected: About Clicked");
            
        }
        if(itemId==R.id.rate){
            Log.d(TAG, "onOptionsItemSelected: Rate us Clicked");
        }   

        return super.onOptionsItemSelected(item);
    }


    // OnClick method for Button Click Action

    @Override
    public void onClick(View view) {

     //   Log.d(TAG, "onClick: Button Clicked");
        switch (view.getId()){

            case R.id.btnCharge:

     //           Log.d(TAG, "onClick: Charge Button Clicked");
                if(chargeStatus == false){

                    if(status==true){
                        sensorClose();
                    }

                    Boolean plugged = Utils.isConnected(this);
                    if(plugged){

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
       //         Log.d(TAG, "onClick: Motion Button clicked");

                if(motionStatus == false){
                    if(status == true){
                        sensorClose();
                    }

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
         //       Log.d(TAG, "onClick: Proximity Button clicked");

                if(proxStatus == false){
                    if(status==true){
                        sensorClose();
                    }

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

    // Method for Closing Sensors

    public void sensorClose(){

        stopService(intent);
        motionStatus = false;
        chargeStatus = false;
        proxStatus = false;

        status = false;
    }

}
