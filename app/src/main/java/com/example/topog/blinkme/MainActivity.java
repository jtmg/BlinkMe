package com.example.topog.blinkme;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private int mDefaultColor;
    private String r,g,b;
    private SharedPreferences pref;
    private EditText IP;
    private String ColorSms = "";
    private String ColorCalls = "";
    private String ColorMessenger = "";
    private String ColorWhatsApp = "";
    private String ColorInstagram = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDefaultColor = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        IP = findViewById(R.id.Ip);
        permissions();
    }

    void permissions()
    {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_PHONE_STATE))
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_PHONE_STATE},1);
            }else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_SMS))
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_SMS},1);
            }else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS},1);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
            {
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Permissions Granted",Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(this,"no permission granted",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 2:
            {
                if (grantResults.length > 0 && grantResults [1] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Permissions Granted",Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(this,"no permission granted",Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    public void Click(View view) {
        //openColorPicker();
        switch (view.getId())
        {
            case R.id.bSms:
            {
                openColorPicker("sms");
                break;
            }
            case R.id.bCalls:
            {
                openColorPicker("calls");
                break;
            }
            case R.id.bMessenger:
            {
                openColorPicker("messenger");
                break;
            }
            case R.id.bWhatsApps:
            {
                openColorPicker("whatsapp");
                break;
            }
            case R.id.bInstagram:
            {
                openColorPicker("instagram");

                break;
            }
            case R.id.bSave:
            {
                String aux = IP.getText().toString();
                pref.edit().putString("IP",aux).commit();

                pref.edit().putString("Calls Color",ColorCalls).commit();
                pref.edit().putString("SMS Color",ColorSms).commit();
                pref.edit().putString("Messenger Color",ColorMessenger).commit();
                pref.edit().putString("WhatsApp Color",ColorWhatsApp).commit();
                pref.edit().putString("Instagram Color",ColorInstagram).commit();

                break;
            }default:{break;}
        }


    }
    public  void  openColorPicker(String App)
    {
        final String aux = App;

        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                r = Integer.toString(Color.red(color));
                g = Integer.toString(Color.green(color));
                b = Integer.toString(Color.blue(color));
                switch (aux)
                {
                    case "sms":
                    {
                        ColorSms = "/r=" + r + "?g=" + g + "?b=" + b;
                    }
                    case "calls":
                    {
                        ColorCalls = "/r=" + r + "?g=" + g + "?b=" + b;
                    }
                    case "whatsapp":
                    {
                        ColorWhatsApp = "/r=" + r + "?g=" + g + "?b=" + b;
                    }
                    case "messenger":
                    {
                        ColorMessenger = "/r=" + r + "?g=" + g + "?b=" + b;
                    }
                    case "instagram":
                    {
                        ColorInstagram  = "/r=" + r + "?g=" + g + "?b=" + b;
                    }
                }

            }
        });
        colorPicker.show();


    }
}
