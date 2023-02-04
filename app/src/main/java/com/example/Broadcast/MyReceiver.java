package com.example.Broadcast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;
import com.example.Activity.DaysActivity;
import static com.example.Utils.shownotification;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {

        //after a call this Broadcast..

        shownotification(context,"Home Work Out","Continue our Exercise & Never Give Up! ",new Intent(context, DaysActivity.class));
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }
}
