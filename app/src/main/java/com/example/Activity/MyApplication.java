package com.example.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.util.Util;
import com.example.Utils;

public class MyApplication extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean statusLocked = prefs.getBoolean("locked", false);

        if (statusLocked)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Log.d("AAA","First Activity Call Application Code: "+Utils.getLanguage(base));
    }
}
