package com.bignerdranch.android.netspeedofmine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import java.text.NumberFormat;

/**
 * Created by xiazihao on 2016/12/31.
 */

public class WindowUtil {
    private Context mContext;
    private WindowManager mWindowManager;
    private SpeedView mSpeedView;
    private WindowManager.LayoutParams params;
    private long mPreRxBytes;
    private long mPreTxBytes;

    private Handler mHandler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            caculateSpeed();
            sendEmptyMessageDelayed(0,1000);
        }
    };
    public static int mStatusBarHeight = 0;


    public WindowUtil(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mSpeedView = new SpeedView(mContext);
        params = new WindowManager.LayoutParams();
        params.width = params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mPreRxBytes = TrafficStats.getTotalRxBytes();
        mPreTxBytes= TrafficStats.getTotalTxBytes();
    }
public void caculateSpeed(){
    long currentRxBytes = TrafficStats.getTotalRxBytes();
    long currentTxBytes = TrafficStats.getTotalTxBytes();
    long downloadSpeed = currentRxBytes - mPreRxBytes;
    long uploadSpeed = currentTxBytes - mPreTxBytes;
    mPreRxBytes = currentRxBytes;
    mPreTxBytes = currentTxBytes;
    String upSpeed=null;
    String downSpeed=null;

    NumberFormat df= java.text.NumberFormat.getNumberInstance();
    df.setMaximumFractionDigits(2);

    if(downloadSpeed>1024*1024){
        downloadSpeed/=(1024*1024);
        downSpeed=df.format(downloadSpeed)+"MB/s";
    }else if(downloadSpeed>1024){
        downloadSpeed/=(1024);
        downSpeed=df.format(downloadSpeed)+"B/s";
    }else{
        downSpeed=df.format(downloadSpeed)+"B/s";
    }

    if(uploadSpeed>1024*1024){
        uploadSpeed/=(1024*1024);
        upSpeed=df.format(uploadSpeed)+"MB/s";
    }else if(uploadSpeed>1024){
        uploadSpeed/=(1024);
        upSpeed=df.format(uploadSpeed)+"kB/s";
    }else{
        upSpeed=df.format(uploadSpeed)+"B/s";
    }

    updateSpeed("↓ "+downSpeed,"↑ "+upSpeed);
}
    private void updateSpeed(String download, String upload) {

        mSpeedView.setUpload(upload);
        mSpeedView.setDownload(download);


    }

    public void showSpeedView() {
        mWindowManager.addView(mSpeedView, params);
        mHandler.sendEmptyMessage(0);
    }

    public void closeSpeedView() {
        mWindowManager.removeView(mSpeedView);
    }
}
