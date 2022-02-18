package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.Local.LocalManager;
import com.example.Utils;
import com.example.work.R;

import java.util.Locale;
import java.util.Set;

import static com.example.Database.MyDataBase_1.DATABASE_NAME;
import static com.example.Database.MyDataBase_1.TABLE_NAME_1;
import static com.example.Database.MyDataBase_1.TABLE_NAME_CATAGORY_1;
import static com.example.Utils.openttsSetting;
import static com.example.Utils.selectTTsEngin;
import static com.example.Utils.sendEmail;
import static com.example.Utils.showToast;
import static com.example.Utils.triggerRebirth;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    LinearLayout linearLayout_tts,linearLayout_sound_option,select_tts_ll,download_tts_ll,voice_language_id,
    device_setting_tts_id,synce_google_fit_ll,helth_data_ll,remaindme_workout_ll,metric_ll,language_ll,clear_data_ll,rate_us_ll,
    share_us_ll,feedback_ll,privcy_policy_ll,dark_mode_ll;
    TextView tv_language_dd;
    TextToSpeech tts;
    Switch aSwitch;
    Spinner spinner;
    boolean isDarkmode = true;
    boolean isChek_spinner = true;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Utils.hidestatusbar(this); //hide status bar

        toolbar = findViewById(R.id.toolbar_settingID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        findView();     //first all finding & Register view

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean statusLocked = prefs.getBoolean("locked", false);

        if (statusLocked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            aSwitch.setChecked(true);
            isDarkmode = false;
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            aSwitch.setChecked(false);
            isDarkmode = true;
        }
        //set a tts
         tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {

                    }
                }
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.darkmode_id:
                showToast(this, "Dark mode");

                if (isDarkmode) {
                    aSwitch.setChecked(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    prefs.edit().putBoolean("locked", true).apply();
                    isDarkmode = !isDarkmode;
                }
                else {
                    aSwitch.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    prefs.edit().putBoolean("locked", false).apply();
                    isDarkmode = !isDarkmode;
                }

                break;
            case R.id.policy_id:
                showToast(this,"Privacy Policy");
                break;
            case R.id.feedback_id:
                sendEmail("lathiyafenil518@gmail.com",this);
                showToast(this,"FeedBack");
                break;
            case R.id.share_us_id:
                Utils.shareApplication(this);
                showToast(this,"Share US");
                break;
            case R.id.rate_us_ID:
                showToast(this,"Rate Us");
                break;
            case R.id.clear_data_id:
                this.deleteDatabase(DATABASE_NAME);
                prefs.edit().putBoolean("locked", false).apply();
                Utils.set_DOB_SP(this,"0000-00-00");
                Utils.set_sharePrefre_Gender(this,"Male");
                Utils.updateResourse(this,"en");
                Utils.triggerRebirth(this);
                showToast(this,"Clear All Data");

                break;

            case R.id.language_id:
                showToast(this,"Language id");
                break;
            case R.id.metric_id:
                showToast(this,"Metric id");
                break;
            case R.id.remaindme_workout_id:
                startActivity(new Intent(SettingActivity.this,ReminderActivity.class));
                showToast(this,"Remaind Me workout");
                break;
            case R.id.helth_data_id:
                startActivity(new Intent(SettingActivity.this,HelthDataActivity.class));
                showToast(this,"Health Data");
                break;
            case R.id.tts_setting_id:
                setvoiceSpeech("TTS Hello World Voice Testing ");

                new AlertDialog.Builder(this)
                        .setTitle("TTS Testing")
                        .setMessage("TTS Hello World Voice Testing")
                        .setCancelable(false)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                tts.stop();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create().show();

                showToast(this,"Tts Test ID");

                break;
            case R.id.sound_option_id:
                break;

            case R.id.select_tts_engin_id:
                Utils.openTTSallEngin(this);
                showToast(this,"Selecrt tts id");
                break;

            case R.id.download_ttsID:
                Utils.setVideoUri(this,"https://play.google.com/store/apps/details?id=com.google.android.tts");
                showToast(this,"Download TTS");
                break;

            case R.id.voice_language_id:
                showToast(this,"Voice Language ");
                break;

            case R.id.device_setting_tts_id:
                selectTTsEngin(this);
                showToast(this,"Device Setting tts");
                break;

            case R.id.synce_google_fit_id:
                showToast(this,"synce_google_fit_id");
                break;


        }

    }

    private void setvoiceSpeech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void findView() {

        aSwitch = findViewById(R.id.switchID);
        tv_language_dd = findViewById(R.id.language_tbdd_ID);
        privcy_policy_ll = findViewById(R.id.policy_id);
        feedback_ll = findViewById(R.id.feedback_id);
        linearLayout_tts = findViewById(R.id.tts_setting_id);
        linearLayout_sound_option = findViewById(R.id.sound_option_id);
        select_tts_ll = findViewById(R.id.select_tts_engin_id);
        download_tts_ll = findViewById(R.id.download_ttsID);
        voice_language_id = findViewById(R.id.voice_language_id);
        device_setting_tts_id = findViewById(R.id.device_setting_tts_id);
        synce_google_fit_ll = findViewById(R.id.synce_google_fit_id);
        helth_data_ll = findViewById(R.id.helth_data_id);
        remaindme_workout_ll = findViewById(R.id.remaindme_workout_id);
        metric_ll = findViewById(R.id.metric_id);
        language_ll = findViewById(R.id.language_id);
        clear_data_ll = findViewById(R.id.clear_data_id);
        rate_us_ll = findViewById(R.id.rate_us_ID);
        share_us_ll = findViewById(R.id.share_us_id);
        spinner = findViewById(R.id.spinnerID);
        dark_mode_ll = findViewById(R.id.darkmode_id);


        if (Utils.getLanguage(this).endsWith("en")) {
            tv_language_dd.setText("English");
            spinner.setSelection(1,true);
        }
        else if (Utils.getLanguage(this).endsWith("fr")) {
            tv_language_dd.setText("Spain");
            spinner.setSelection(3,true);
        }
        else if (Utils.getLanguage(this).endsWith("hi")) {
            tv_language_dd.setText("Hindi");
            spinner.setSelection(2,true);
        }
        else if (Utils.getLanguage(this).endsWith("zh")) {
            tv_language_dd.setText("Chinese");
            spinner.setSelection(4,true);
        }
        else
            spinner.setSelection(0,true);


        ArrayAdapter<CharSequence> arrayAdapter_spinner = ArrayAdapter.createFromResource(this,R.array.spinner_array,android.R.layout.simple_spinner_item);
        arrayAdapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("English") && isChek_spinner) {
                    Log.d("AAA","English Selected");
                    Utils.updateResourse(SettingActivity.this,"en");
                    spinner.setSelection(i,true);
                    isChek_spinner=false;
                    tv_language_dd.setText("English");
                    triggerRebirth(SettingActivity.this);
                }
                else if (adapterView.getItemAtPosition(i).equals("Hindi")) {
                    Log.d("AAA","Hindi Selected");
                    Utils.updateResourse(SettingActivity.this,"hi");
                    isChek_spinner=false;
                    tv_language_dd.setText("Hindi");
                    triggerRebirth(SettingActivity.this);
                }

                else if (adapterView.getItemAtPosition(i).equals("Spain")) {
                    Log.d("AAA","Spain Selected");
                    Utils.updateResourse(SettingActivity.this,"fr");
                    isChek_spinner=false;
                    tv_language_dd.setText("Spain");
                    triggerRebirth(SettingActivity.this);
                }
                else if (adapterView.getItemAtPosition(i).equals("Chinese")){
                    Log.d("AAA","Chinese Selected");
                    Utils.updateResourse(SettingActivity.this,"zh");
                    isChek_spinner=false;
                    tv_language_dd.setText("Chinese");
                    triggerRebirth(SettingActivity.this);
                }
                else
                    Log.d("AAA","Not Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //regester all view
        privcy_policy_ll.setOnClickListener(this);
        linearLayout_sound_option.setOnClickListener(this);
        linearLayout_tts.setOnClickListener(this);
        select_tts_ll.setOnClickListener(this);
        dark_mode_ll.setOnClickListener(this);
        download_tts_ll.setOnClickListener(this);
        voice_language_id.setOnClickListener(this);
        device_setting_tts_id.setOnClickListener(this);
        synce_google_fit_ll.setOnClickListener(this);
        helth_data_ll.setOnClickListener(this);
        remaindme_workout_ll.setOnClickListener(this);
        metric_ll.setOnClickListener(this);
        language_ll.setOnClickListener(this);
        clear_data_ll.setOnClickListener(this);
        rate_us_ll.setOnClickListener(this);
        share_us_ll.setOnClickListener(this);
        feedback_ll.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        tts.stop();
        tts.shutdown();
        super.onDestroy();
    }

}
