package com.chailijun.baselib.base;

import android.app.Application;

import com.chailijun.baselib.utils.C;

public abstract class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        C.setContext(getApplicationContext());
        init();
    }

    protected abstract void init();
}
