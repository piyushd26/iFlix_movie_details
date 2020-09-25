package com.example.myappnew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.example.myappnew.Activities.LogedInActivity;

public class MyBroadCastReceiver extends BroadcastReceiver {
    public static final String TAG="MyBroadCastReceiver";
    @Override

    public void onReceive(Context context, Intent intent) {
        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN);

        switch(extraWifiState) {
            case WifiManager.WIFI_STATE_DISABLED:
                //do something
                Log.e(TAG, "WIFI_STATE_DISABLED");
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                //do something
                Log.e(TAG, "WIFI_STATE_ENABLED");

                break;
            case WifiManager.WIFI_STATE_ENABLING:
                //do something
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                //do something
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                //do something with data if you desire so, I found it unreliable until now so i've done nothing with it
        }
    }
}
