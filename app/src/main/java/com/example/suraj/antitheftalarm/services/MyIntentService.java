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
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        serviceValue = intent.getIntExtra("SERVICE_VALUE",0);

        if(serviceValue== Utils.CHARGING_CONSTANT) {
            IntentFilter intentFilter = new IntentFilter();
            receiver = new ChargingReceiver();
            intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
            registerReceiver(receiver, intentFilter);

        }else if(serviceValue==Utils.MOTION_CONSTANT) {
            motionListener = new MotionListener();
            motionListener.startMotionSensor(this, Sensor.TYPE_ACCELEROMETER);
        }else if(serviceValue==Utils.PROXIMITY_CONSTANT){
            motionListener = new MotionListener();
            motionListener.startMotionSensor(this,Sensor.TYPE_PROXIMITY);
        }

        while (true){

        }
    }

    @Override
    public void onDestroy() {
        if(serviceValue==Utils.CHARGING_CONSTANT) {
            unregisterReceiver(receiver);
        }else if(serviceValue==Utils.MOTION_CONSTANT){
            motionListener.stopMotionSensor(this);
        }else if(serviceValue==Utils.PROXIMITY_CONSTANT){

        }
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
