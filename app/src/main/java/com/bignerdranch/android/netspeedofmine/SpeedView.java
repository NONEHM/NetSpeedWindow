package com.bignerdranch.android.netspeedofmine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xiazihao on 2016/12/31.
 */

public class SpeedView extends FrameLayout {
    private static final String TAG = "SpeedView";
    WindowManager mWindowManager;
    private float preX, preY, x, y;
    private TextView mSpeedUpTextView;
    private TextView mSpeedDownTextView;

    public SpeedView(Context context) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View view = inflate(context, R.layout.speed_layout, this);
        mSpeedDownTextView = (TextView) view.findViewById(R.id.speed_down);
        mSpeedUpTextView = (TextView) view.findViewById(R.id.speed_up);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "touch down x:" + event.getRawX() + " y:" + event.getRawY());
                preX = event.getRawX();
                preY = event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "touch move x:" + event.getRawX() + " y:" + event.getRawY());

                x = event.getRawX();
                y = event.getRawY();
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
                params.x += x - preX;
                params.y += y - preY;
                mWindowManager.updateViewLayout(this, params);
                preX = x;
                preY = y;
                return true;
            default:
                return super.onTouchEvent(event);

        }
    }

    public void setUpload(String upload) {
        mSpeedUpTextView.setText(upload);
    }

    public void setDownload(String download) {
        mSpeedDownTextView.setText(download);
    }
}
