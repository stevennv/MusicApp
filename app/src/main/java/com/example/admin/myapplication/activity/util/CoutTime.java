package com.example.admin.myapplication.activity.util;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Admin on 11/1/2017.
 */

public class CoutTime extends CountDownTimer {
    private checkFinish checkFinish;

    public CoutTime(long millisInFuture, long countDownInterval, checkFinish checkFinish) {
        super(millisInFuture, countDownInterval);
        this.checkFinish = checkFinish;
    }

    @Override
    public void onTick(long l) {
        Log.d("onTick:", "onTick: " + l);
    }

    @Override
    public void onFinish() {
        checkFinish.onFinish();
    }

    public interface checkFinish {
        void onFinish();
    }
}
