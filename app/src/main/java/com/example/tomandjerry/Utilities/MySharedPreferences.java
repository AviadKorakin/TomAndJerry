package com.example.tomandjerry.Utilities;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private static MySharedPreferences mySharedPreferences;
    private final SharedPreferences prefs;

    private MySharedPreferences(Context context) {
        prefs = context.getSharedPreferences("MyPreference", MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (mySharedPreferences == null) {
            mySharedPreferences = new MySharedPreferences(context);
        }
    }

    public static MySharedPreferences getInstance() {
        return mySharedPreferences;
    }


    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public int readInt(String key, int def) {
        return prefs.getInt(key, def);
    }

    public String readString(String key, String def) {
        return prefs.getString(key, def);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


}
