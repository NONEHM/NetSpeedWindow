package com.bignerdranch.android.netspeedofmine;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xiazihao on 2016/12/31.
 */

public class SpeedService extends Service {
    private static final String TAG = "SpeedService";
    private WindowUtil mWindow;
    private static final String SETTING = "setting";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String UPDOWN = "up_down";

    public static Intent newIntent(Context context) {
        return new Intent(context, SpeedService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mWindow == null) {
            mWindow = new WindowUtil(this);
            mWindow.showSpeedView();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "service stoped");
        mWindow.closeSpeedView();
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "service started");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
