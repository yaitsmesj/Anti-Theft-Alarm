package com.example.suraj.antitheftalarm.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.suraj.antitheftalarm.EventDetectcionHandler;
import com.example.suraj.antitheftalarm.Utils;

public class ChargingReceiver extends BroadcastReceiver {

    public static final String TAG = "Charging Receiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        if(Utils.alreadyFired == false){
            if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED));
            //Log.d(TAG, "onReceive: Battery Disconnected");
            Toast.makeText(context, "Battery Disconnected ", Toast.LENGTH_SHORT).show();
            Utils.alreadyFired = true;
            EventDetectcionHandler eventDetectcionHandler = new EventDetectcionHandler();
            eventDetectcionHandler.activatingAction(context);
        }
    }

}
