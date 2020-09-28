package com.chang.mvvmsample;

import android.app.Application;

import com.chang.mvvmsample.model.database.AppDB;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //数据库初始化
        AppDB.getInstance(this);
    }
}
