package com.example.Remainder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.example.Broadcast.MyReceiver;

import java.util.Calendar;

import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;

public class Remainder {

    public void setForMonday(AlarmManager alarmManager,Activity context) {
        Calendar calendar = Calendar.getInstance();
//         AlarmManager alarmManager = (AlarmManager) context.getSystemService(CATEGORY_ALARM);


        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("alarmRequestCode", 111);

        PendingIntent broadcast = PendingIntent.getBroadcast(context, 111, intent, Intent.FLAG_RECEIVER_NO_ABORT);

        calendar.set(Calendar.DAY_OF_WEEK,2);
        calendar.set(Calendar.HOUR,Integer.parseInt("09"));
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("Old is set@ :== " + calendar.getTime());


        long interval = calendar.getTimeInMillis() + 604800000L;
        System.out.println("Next Millis = " + interval);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY * 7, broadcast);
    }
}
