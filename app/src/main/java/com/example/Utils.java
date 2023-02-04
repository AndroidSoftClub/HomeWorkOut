package com.example;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.service.autofill.SaveRequest;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.Activity.DayInnerActivity;
import com.example.Activity.DaysActivity;
import com.example.Activity.MainActivity;
import com.example.Activity.RestTimeActivity;
import com.example.Activity.StartActivity;
import com.example.Broadcast.MyReceiver;
import com.example.Data.Blog;
import com.example.Data.PageData;
import com.example.work.BuildConfig;
import com.example.work.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.joda.time.MutableDateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.service.controls.ControlsProviderService.TAG;
import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;

public class Utils {
    public static final int INTENT_CODE_INNER = 1;

    public static final int INTENT_CODE_1 = 1;
    public static final int INTENT_CODE_2= 2;
    public static final int INTENT_CODE_3 = 3;
    public static final int RESTDAY_CODE_1= 11;
    public static final int RESTDAY_CODE_2= 12;
    public static final int RESTDAY_CODE_3= 13;
    public static final String Language_code = "LANGUAGE";
    public static final String PREFRENCE_NAME = "name";
    public static final String PREFRENCE_NAME_DAY = "day";
    public static final String POSITION = "position";
    public static final String SEEKBAR= "seekbar";
    public static final String COUNTER_SEEKBAR = "counter_seek";
    public static final String C_SIZE = "size";
    public static final String DAY_CODE = "code";
    public static final String BOOEANCHEK = "booleanc";
    private static final String REMINDER_TIME = "reminderTime";
    private static final String MY_PREFRENCE = "userPref";
    private static final String IS_DAILY = "isDaily";


    public static void openTTSallEngin(Activity activity){
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        activity.startActivityForResult(intent, 0);

    }



    //local Helper..

    public static void updateResourse(Context activity,String code){
        Locale locale = new Locale(code);
        Locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        setLanguageCode(activity,code);
    }

    public static void setLanguageCode(Context activity,String code){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Language_code ,MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(Language_code,code);
        myEdit.apply();
        myEdit.commit();
    }

    public static String getLanguage(Context activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Language_code,MODE_PRIVATE);
        String s = sharedPreferences.getString(Language_code,"en");
        return s;
    }


    public static void triggerRebirth(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
    public static void sendEmail(String email,Activity activity) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        String aEmailList[] = {email};
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
        String feedback_msg = "FeedBack";
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<i><font color='your color'>" + feedback_msg + "</font></i>"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");

        PackageManager packageManager = activity.getPackageManager();
        boolean isIntentSafe = emailIntent.resolveActivity(packageManager) != null;
        if (isIntentSafe) {
            activity.startActivity(emailIntent);
        } else {
            Toast.makeText(activity, "App not Install", Toast.LENGTH_SHORT).show();
        }
    }

        public static void shareApplication(Activity activity) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Home WorkOut");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }




    public static SpannableString underlineTV(String s){
        SpannableString content = new SpannableString(s);
        content.setSpan(new UnderlineSpan(), 0, s.length(), 0);
        return content;
    }

    public static void setVideoUri(Activity activity,String url){
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(url));
        activity.startActivity(intent);
    }

    public static void selectTTsEngin(Activity activity){
        Intent intent = new Intent();
        intent.setAction("com.android.settings.TTS_SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void openttsSetting(Activity activity){
        ComponentName componentToLaunch = new ComponentName(
                "com.android.settings",
                "com.android.settings.TextToSpeechSettings");
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(componentToLaunch);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }
    public  static int getWindowHeight(Activity activity) {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }




    public static void showToast(Activity activity,String text){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    public static void hidestatusbar(Activity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public static void setRemainderTime(final Activity activity, final Calendar calendar, final TextView remindertime){
        int mHour;
        int mMinute;

        mHour = calendar.get(11);
        mMinute = calendar.get(12);
        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                calendar.set(11, i);
                calendar.set(12, i2);
                calendar.set(13, 0);
                if (i >= 12) {
                    TextView textView = remindertime;
                    StringBuilder sb = new StringBuilder();
                    sb.append(calendar.get(10) == 0 ? 12 : calendar.get(10));
                    sb.append(":");
                    sb.append(calendar.get(12) < 10 ? "0" : "");
                    sb.append(calendar.get(12));
                    sb.append(" PM");
                    textView.setText(sb.toString());
                    Log.d("AAA","AA_Time: "+textView.getText());

                } else {
                    TextView textView2 = remindertime;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(calendar.get(10) == 0 ? 12 : calendar.get(10));
                    sb2.append(":");
                    sb2.append(calendar.get(12) < 10 ? "0" : "");
                    sb2.append(calendar.get(12));
                    sb2.append(" AM");
                    textView2.setText(sb2.toString());
                    Log.d("AAA","Time: "+textView2.getText());
                }
                setReminderTime(activity,calendar.getTimeInMillis());
                setDAILY(activity, true);
            }
        }, mHour, mMinute, false).show();
    }
    public static void setAlarm(Activity activity,String choice,long time) {

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                activity.getApplicationContext(), 23432420, new Intent(activity,MyReceiver.class), 0);

        AlarmManager alarmManager = (AlarmManager) activity
                .getSystemService(Context.ALARM_SERVICE);

        if (choice.equals("One Time")) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time,pendingIntent);
            Toast.makeText(activity, "Meeting Reminder Set One Time",
                    Toast.LENGTH_LONG).show();
        } else if (choice.equals("Week Days (Mon-Fri)")) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.MONDAY:
                    alarmManager
                            .set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                case Calendar.TUESDAY:
                    alarmManager
                            .set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                case Calendar.WEDNESDAY:
                    alarmManager
                            .set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                case Calendar.THURSDAY:
                    alarmManager
                            .set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                case Calendar.FRIDAY:
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time * 3,
                            pendingIntent);
            }
        }
    }


    public static void shownotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    public static void openRestTimeActivityFromRV(int position,Activity context,List<Blog> blogList,int intent_Code) {
       Blog blog = blogList.get(position);
        context.startActivityForResult(new Intent(context, RestTimeActivity.class)
                        .putExtra("tool",blog.getDayName())
                        .putExtra("position",position)
                ,intent_Code);
    }

    public static void openDayInnerActivityFromRV(int position,Activity context,List<Blog> blogArrayList,int intent_code) {
        Blog blog =blogArrayList.get(position);
        context.startActivityForResult(new Intent(context, DayInnerActivity.class)
                        .putExtra("imagelist",blog.getImagelistID())
                        .putExtra("exerciseList",blog.getExerciseName())
                        .putExtra("toolbarName",blog.getDayName())
                        .putExtra("timer",blog.getTimer())
                        .putExtra("position",position)
                        .putExtra("size",blog.getExerciseName().size())
                        .putExtra("disciprion_exercise",blog.getDiscriptionlist()),
                intent_code);
    }


    public static void startAnimation(ProgressBar progressBar){
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 100, 0);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    public static List<PageData> getImageList(Activity activity){
        List<PageData> pageDataList = new ArrayList<>();

        pageDataList.clear();

        pageDataList.add(new PageData(R.drawable.cover_level_1,activity.getString(R.string.beginer),activity.getString(R.string.dd1),activity.getString(R.string.d1),"0%",R.drawable.ic_level_1,0));
        pageDataList.add(new PageData(R.drawable.cover_level_2,activity.getString(R.string.intermediat),activity.getString(R.string.dd2),activity.getString(R.string.d2),"0%",R.drawable.ic_level_2,0));
        pageDataList.add(new PageData(R.drawable.cover_level_3,activity.getString(R.string.advance),activity.getString(R.string.dd3),activity.getString(R.string.d3),"0%",R.drawable.ic_level_3,0));

        return pageDataList;
    }

    public static void setNightMode(Context target, boolean state){

        UiModeManager uiManager = (UiModeManager) target.getSystemService(Context.UI_MODE_SERVICE);

        if (state) {
            //uiManager.enableCarMode(0);
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        } else {
            // uiManager.disableCarMode(0);
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }
    }



    public static void openMulti_radio_Dialog(final Activity activity, final TextView textView){
        final CharSequence[] items = {"Male", "Female"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("My Gender");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(activity, items[item], Toast.LENGTH_SHORT).show();
                textView.setText(items[item]);
                dialog.dismiss();
                set_sharePrefre_Gender(activity, String.valueOf(items[item]));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public static void openDataPikerDialog(final Activity activity, final TextView textView){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textView.setText(i+"-"+i1+"-"+i2);
                            set_DOB_SP(activity, i+"-"+i1+"-"+i2); //set a date
                        }
                    }
                },
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    public static void set_DOB_SP(Activity activity,String s){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("DOB",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DOB",s);
        editor.commit();
        editor.apply();
    }

    public static String getDOB_SP(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("DOB",MODE_PRIVATE);
        String s = sharedPreferences.getString("DOB","0000-00-00");
        return s;
    }

    public static String get_shareprefre_Gender(Activity activity){

        SharedPreferences sharedPreferences = activity.getSharedPreferences("string",MODE_PRIVATE);
        String s = sharedPreferences.getString("string","Male");

        return s;
    }

    public static void set_sharePrefre_Gender(Activity activity,String s){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("string",MODE_PRIVATE);
        SharedPreferences.Editor preferences = sharedPreferences.edit();
        preferences.putString("string",s);
        preferences.apply();
        preferences.commit();
    }

    public static int getWidth(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }


    public static ArrayList<String> getBasicDiscription(){
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.clear();

        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");
        stringArrayList.add("Left side 10 time crunches and right side 10 time crunches start \n\n Basic Description Part");

        return stringArrayList;

    }

    public static ArrayList<Integer> getTimerlist1() {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);
        integerArrayList.add(30000);

        return integerArrayList;
    }

    public static ArrayList<String> getExercisename1(Activity activity) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(activity.getString(R.string.one));
        stringArrayList.add(activity.getString(R.string.two));
        stringArrayList.add(activity.getString(R.string.three));
        stringArrayList.add(activity.getString(R.string.four));
        stringArrayList.add(activity.getString(R.string.five));
        stringArrayList.add(activity.getString(R.string.six));
        stringArrayList.add(activity.getString(R.string.seven));
        stringArrayList.add(activity.getString(R.string.eight));
        stringArrayList.add(activity.getString(R.string.nine));
        stringArrayList.add(activity.getString(R.string.ten));
        stringArrayList.add(activity.getString(R.string.eleven));
        stringArrayList.add(activity.getString(R.string.twell));
        stringArrayList.add(activity.getString(R.string.thirteen));
        stringArrayList.add(activity.getString(R.string.fourtyn));
        stringArrayList.add(activity.getString(R.string.fiftyn));
        stringArrayList.add(activity.getString(R.string.sixtyn));
        stringArrayList.add(activity.getString(R.string.seventyn));
        return stringArrayList;
    }

    public static ArrayList<Integer> getImagelist1() {
        ArrayList<Integer> integers= new ArrayList<>();
        integers.add(R.drawable.day2e1);
        integers.add(R.drawable.day2e2);
        integers.add(R.drawable.day2e3);
        integers.add(R.drawable.day2e4);
        integers.add(R.drawable.day2e5);
        integers.add(R.drawable.day2e6);
        integers.add(R.drawable.day2e7);
        integers.add(R.drawable.day2e8);
        integers.add(R.drawable.day2e9);
        integers.add(R.drawable.day2e17);
        integers.add(R.drawable.day2e11);
        integers.add(R.drawable.day2e12);
        integers.add(R.drawable.day2e13);
        integers.add(R.drawable.day2e14);
        integers.add(R.drawable.day2e15);
        integers.add(R.drawable.day2e16);
        integers.add(R.drawable.day2e17);

        return integers;
    }

    public static void remind24(Context context) {
        @SuppressLint("WrongConstant") AlarmManager alarmManager = (AlarmManager) context.getSystemService(CATEGORY_ALARM);
        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("alarmRequestCode", 111);
        @SuppressLint("WrongConstant") PendingIntent broadcast = PendingIntent.getBroadcast(context, 111, intent, Intent.FLAG_RECEIVER_NO_ABORT);
        Calendar instance = Calendar.getInstance();
        instance.set(11, 24);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        if (Build.VERSION.SDK_INT < 23) {
            Log.i("`ww", "onReceive: 11111!");
            alarmManager.set(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            Log.i("`ww", "onReceive: 22222!");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 23) {
            Log.i("`ww", "onReceive: 33333!");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        }
    }

    public static void remind3hour(Context context) {
        @SuppressLint("WrongConstant") AlarmManager alarmManager = (AlarmManager) context.getSystemService(CATEGORY_ALARM);
        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("alarmRequestCode", 112);
        @SuppressLint("WrongConstant") PendingIntent broadcast = PendingIntent.getBroadcast(context, 112, intent, Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(getReminderTime(context));
        Calendar instance2 = Calendar.getInstance();
        instance2.set(11, instance.get(11));
        instance2.set(12, instance.get(12));
        instance2.set(13, 0);
        instance2.set(14, 0);
        if (System.currentTimeMillis() > instance2.getTimeInMillis()) {
            return;
        }
        if (Build.VERSION.SDK_INT < 19) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, instance2.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, instance2.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, instance2.getTimeInMillis(), broadcast);
        }
    }

    public static void setReminderTime(Context context, long j) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putLong(REMINDER_TIME, j);
        edit.commit();
    }

    public static long getReminderTime(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getLong(REMINDER_TIME, 1548207000000L);
    }

    public static void setDAILY(Context context, boolean z) {
        Log.d("isDaily", "setDaily" + z);
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_DAILY, z);
        edit.commit();
        if (z) {
            remind24(context);
            remind3hour(context);
        }
    }



    public static boolean isDAILY(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_DAILY, true);
    }



}
