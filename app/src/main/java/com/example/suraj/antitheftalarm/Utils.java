package com.example.suraj.antitheftalarm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Suraj on 22-Sep-17.
 */

public class Utils {

    public static final int CHARGING_CONSTANT = 1;
    public static final int MOTION_CONSTANT = 2;
    public static final int PROXIMITY_CONSTANT = 3;

    public static boolean alreadyFired = false;
    public static String save_pattern_key = "pattern_code";

    public static int setPatternRequestCode = 111;
    public static int settingsPatternRequestCode = 222;

    public static MediaPlayer mediaPlayer;

    public static boolean patternEntered = false;
    public static boolean isConnected(Context context){
        Intent intent = context.registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
        return plugged== BatteryManager.BATTERY_PLUGGED_AC|| plugged==BatteryManager.BATTERY_PLUGGED_USB;
    }

    public static void playMusic(Context context){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

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
        Log.d(MainActivity.TAG, "playMusic: Before Sleep");
            mediaPlayer = MediaPlayer.create(context,resId);
            mediaPlayer.setLooping(true);

        if(mediaPlayer!=null)
            mediaPlayer.start();
    }

    public static void stopMusic(){

        if(mediaPlayer!=null){
            //mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
