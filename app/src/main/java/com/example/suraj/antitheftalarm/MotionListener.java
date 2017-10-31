package com.example.suraj.antitheftalarm;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Suraj on 22-Sep-17.
 */

public class MotionListener implements SensorEventListener {

    public static final String TAG = "Motion Listener";
    SensorManager sensorManager;
    private float mAccel = 0.00f;
    private float mAccelCurrent = SensorManager.GRAVITY_EARTH;
    private float mAccelLast = SensorManager.GRAVITY_EARTH;
    private float mGravity[];
    private int SENSOR_SENSITIVITY = 4;
    Context context;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       // Log.d(TAG, "onSensorChanged: ");


        if(sensorEvent.sensor.getType()== Sensor.TYPE_ACCELEROMETER){
            mGravity = sensorEvent.values.clone();

            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if(mAccel > 2.5){
                Log.d(TAG, "onSensorChanged: Motion Detected");
                stopSensor(context);
                EventDetectcionHandler eventDetectcionHandler = new EventDetectcionHandler();
                eventDetectcionHandler.activatingAction(context);
            }
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if (sensorEvent.values[0] >= -SENSOR_SENSITIVITY && sensorEvent.values[0] <= SENSOR_SENSITIVITY) {
                //near
                Log.d(TAG, "onSensorChanged: Inside Pocket");
               } else {
                //far
                Log.d(TAG, "onSensorChanged: Outside Pocket");
                stopSensor(context);
                EventDetectcionHandler eventDetectcionHandler = new EventDetectcionHandler();
                eventDetectcionHandler.activatingAction(context);
               }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void startSensor(Context context, int serviceValue){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.context = context;
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(serviceValue),SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void stopSensor(Context context){
        sensorManager.unregisterListener(this);
    }
}
