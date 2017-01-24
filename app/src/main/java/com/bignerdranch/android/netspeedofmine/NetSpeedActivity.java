package com.bignerdranch.android.netspeedofmine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NetSpeedActivity extends AppCompatActivity {
    private static final String TAG = "NetSpeedActivity";
    public static final String RADIO_ON_CHECK = "com.bignerdranch.netspeed.radio_on_check";
    private static final int REQUEST_PERMISSION = 0;
    private Button mOpenNetSpeedButton;
    private Button mCloseNetSpeedButton;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_speed);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        init();

    }
//    private int getStatusBarHeight(){
//        Rect rectangle = new Rect();
//        Window window =getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        int statusBarHeight = rectangle.top;
//        return statusBarHeight;
//    }
    private void init() {
        mOpenNetSpeedButton = (Button) findViewById(R.id.open_net_speed_button);
        mCloseNetSpeedButton = (Button) findViewById(R.id.close_net_speed_button);
        mRadioGroup = (RadioGroup) findViewById(R.id.show_radio_group);
        mOpenNetSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(SpeedService.newIntent(NetSpeedActivity.this));
            }
        });
        mCloseNetSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(SpeedService.newIntent(NetSpeedActivity.this));
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NetSpeedActivity.this);
                preferences.edit().putInt(RADIO_ON_CHECK,checkedId).apply();
            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRadioGroup.check(preferences.getInt(RADIO_ON_CHECK,R.id.show_upload_download));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
//            Toast.makeText(this, "permission", Toast.LENGTH_SHORT).show();
//        }
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, R.string.permission_hint, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
