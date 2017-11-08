package com.example.suraj.antitheftalarm.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.util.Log;

import com.example.suraj.antitheftalarm.MotionListener;
import com.example.suraj.antitheftalarm.Utils;
import com.example.suraj.antitheftalarm.receivers.ChargingReceiver;

public class MyIntentService extends IntentService {

    public static final String TAG = "My Intent Service";

    ChargingReceiver receiver;
    MotionListener motionListener;
    int serviceValue = 0;
    public static boolean motionServiceStatus = true;
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        serviceValue = intent.getIntExtra("SERVICE_VALUE",0);

        Log.d(TAG, "onHandleIntent: Intent Service Started with Service Value " +serviceValue);

        if(serviceValue== Utils.CHARGING_CONSTANT) {
            Log.d(TAG, "onHandleIntent: Service Value is equal to Charging Constant");
            IntentFilter intentFilter = new IntentFilter();
            receiver = new ChargingReceiver();
            intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
            registerReceiver(receiver, intentFilter);

        }else if(serviceValue==Utils.MOTION_CONSTANT) {
            Log.d(TAG, "onHandleIntent: Service Value is equal to Motion Constant");

            motionListener = new MotionListener();
            motionListener.startSensor(this, Sensor.TYPE_ACCELEROMETER);
        }else if(serviceValue==Utils.PROXIMITY_CONSTANT){
            Log.d(TAG, "onHandleIntent: Service Value is equal to Proximity Constant");

            motionListener = new MotionListener();
            motionListener.startSensor(this,Sensor.TYPE_PROXIMITY);
        }

        while (motionServiceStatus){

        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Destroying Intent Service");
        if(serviceValue==Utils.CHARGING_CONSTANT) {
            unregisterReceiver(receiver);
        }else if(serviceValue==Utils.MOTION_CONSTANT){
            motionListener.stopSensor(this);
        }else if(serviceValue==Utils.PROXIMITY_CONSTANT){
            motionListener.stopSensor(this);
        }
        Utils.stopMusic();
       // Log.d(TAG, "onDestroy: Destroyed ");
        super.onDestroy();
    }


}

