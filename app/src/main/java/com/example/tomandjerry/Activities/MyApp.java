package com.example.tomandjerry.Activities;

import android.app.Application;

import com.example.tomandjerry.Utilities.MySharedPreferences;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySharedPreferences.init(this);
    }
}
