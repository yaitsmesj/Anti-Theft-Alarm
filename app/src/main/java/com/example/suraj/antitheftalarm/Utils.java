package com.example.suraj.antitheftalarm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by Suraj on 22-Sep-17.
 */

public class Utils {

    public static final int CHARGING_CONSTANT = 1;
    public static final int MOTION_CONSTANT = 2;
    public static final int PROXIMITY_CONSTANT = 3;

    public static boolean isConnected(Context context){
        Intent intent = context.registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
        return plugged== BatteryManager.BATTERY_PLUGGED_AC|| plugged==BatteryManager.BATTERY_PLUGGED_USB;
    }
}
