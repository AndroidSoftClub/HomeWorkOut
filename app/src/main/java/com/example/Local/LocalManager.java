package com.example.Local;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class LocalManager {
    SharedPreferences sharedPreferences;
    Activity activity;

    public LocalManager(Activity context){
        activity = context;
        sharedPreferences = context.getSharedPreferences("LANG",MODE_PRIVATE);
    }

    public void updateResourse(Activity activity,String code){
        sharedPreferences = activity.getSharedPreferences("LANG", MODE_PRIVATE);
        Locale locale = new Locale(code);
        Locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        setLang(code);
    }

    public String getLang(){
        return sharedPreferences.getString("LANG","en");
    }

    public void setLang(String code){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LANG",code);
        editor.apply();
        editor.commit();
    }

}
