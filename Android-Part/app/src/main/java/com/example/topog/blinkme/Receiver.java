package com.example.topog.blinkme;

/**
 * Created by topog on 07/01/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by topog on 07/01/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class Receiver extends NotificationListenerService {
    private static final class ApplicationPackageNames
    {
        public static final String Messenger = "com.facebook.orca";
        public static final String WhatsApp = "com.whatsapp";
        public static final String Instagram = "com.instagram.android";
    }

    private Context context;
    private SharedPreferences sp;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String pack = sbn.getPackageName();

        switch (pack)
        {
            case ApplicationPackageNames.Instagram:
            {
                String url = "http://";
                url += sp.getString("IP","");
                url += sp.getString("Instagram Color","");

                new Send().execute(url);
                break;
            }
            case ApplicationPackageNames.WhatsApp:
            {
                String url = "http://";
                url += sp.getString("IP","");
                url += sp.getString("WhatsApp Color","");

                new Send().execute(url);
                break;
            }
            case ApplicationPackageNames.Messenger:
            {
                String url = "http://";
                url += sp.getString("IP","");
                url += sp.getString("Messenger Color","");

                new Send().execute(url);
                break;
            }
        }

    }


    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification was removed");
    }

}