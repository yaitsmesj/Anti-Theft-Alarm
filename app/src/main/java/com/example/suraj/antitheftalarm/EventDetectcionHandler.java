package com.example.suraj.antitheftalarm;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Suraj on 01-Nov-17.
 */

public class EventDetectcionHandler {

    public static final String TAG = "Event Detection";

    public void activatingAction(final Context context) {
        Log.d(TAG, "activatingAction: Event Detected");
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String timeValue = sharedPreferences.getString("time_list_key", "4s");
        long waitTime = 4000;

        if (timeValue.equals("2s"))
            waitTime = 2000;
        else if (timeValue.equals("4s"))
            waitTime = 4000;
        else if (timeValue.equals("6s"))
            waitTime = 6000;
        else if (timeValue.equals("8s"))
            waitTime = 8000;
        else if (timeValue.equals("10s"))
            waitTime = 10000;

        // Activate Alarm
        Log.d(TAG, "activatingAction: Wait Time" + waitTime);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(Utils.patternEntered==false)
                    Log.d(TAG, "run: Playing Music");
                Utils.playMusic(context);
            }
        };
        handler.postDelayed(r,5000);
        // Activate Screen
        Log.d(TAG, "activatingAction: Activating Lock Screen");
        Intent intent = new Intent(context,PatternConfirmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("set_password",false);
        context.startActivity(intent);
    }
}
