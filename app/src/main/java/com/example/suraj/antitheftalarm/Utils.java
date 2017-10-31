package com.example.suraj.antitheftalarm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.preference.PreferenceManager;

/**
 * Created by Suraj on 22-Sep-17.
 */

public class Utils {

    public static final int CHARGING_CONSTANT = 1;
    public static final int MOTION_CONSTANT = 2;
    public static final int PROXIMITY_CONSTANT = 3;

    public static boolean alreadyFired = false;

    public static MediaPlayer mediaPlayer;

    public static boolean isConnected(Context context){
        Intent intent = context.registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
        return plugged== BatteryManager.BATTERY_PLUGGED_AC|| plugged==BatteryManager.BATTERY_PLUGGED_USB;
    }

    public static void playMusic(Context context){

            SharedPreferences  sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String  ring_value = sharedPreferences.getString("ring_list_key","2");
            int resId = R.raw.police_siren_1;
            switch (ring_value){
                case "1":
                    resId = R.raw.dog_bark;
                    break;
                case "2":
                    resId = R.raw.police_siren_1;
                    break;
                case "3":
                    resId = R.raw.police_siren_2;
                    break;
                case "4":
                    resId = R.raw.police_siren_3;
                    break;
                default:
            }

            mediaPlayer = MediaPlayer.create(context,resId);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
    }

    public static void stopMusic(){

        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
