package com.example.suraj.antitheftalarm;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.suraj.antitheftalarm.MainActivity.TAG;

/**
 * Created by Suraj on 01-Nov-17.
 */

public class EventDetectcionHandler {

    public void activatingAction(final Context context) {

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

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.playMusic(context);
            }
        },waitTime);

        // Activate Screen
        Log.d(TAG, "activatingAction: Activating Screen");
    }
}
