package com.example.topog.blinkme;

/**
 * Created by topog on 06/01/2018.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;



/**
 * Created by topog on 06/01/2018.
 */

public class CallsAndSms extends BroadcastReceiver {
    private String ColorSms;
    private String ColorCalls;
    private String Ip;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Ip = prefs.getString("IP","");
        ColorCalls = prefs.getString("Calls Color","");
        ColorSms = prefs.getString("SMS Color","");
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
                Toast.makeText(context,"http://"+Ip+ColorCalls,Toast.LENGTH_SHORT).show();
                new Send().execute("http://" + Ip + ColorCalls);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Toast.makeText(context,"http://"+Ip+ColorSms,Toast.LENGTH_SHORT).show();
            new Send().execute( "http://"+Ip + ColorSms);

                    }
    }
}

