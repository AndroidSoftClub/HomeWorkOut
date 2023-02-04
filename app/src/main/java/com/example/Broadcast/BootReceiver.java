package com.example.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.Utils;


public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action;
        Log.e("Boot", "SystemBootReceiver");
        if (intent != null && (action = intent.getAction()) != null && !action.isEmpty()) {
            char c = 65535;

            switch (action.hashCode()) {
                case -810471698:
                    if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
                        c = 3;
                        break;
                    }
                    break;
                case -757780528:
                    if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        c = 2;
                        break;
                    }
                    break;
                case 505380757:
                    if (action.equals("android.intent.action.TIME_SET")) {
                        c = 1;
                        break;
                    }
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 5;
                        break;
                    }
                    break;
                case 798292259:
                    if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1544582882:
                    if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1737074039:
                    if (action.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                        c = 6;
                        break;
                    }
                    break;

                default:
                    return;
            }


            switch (c) {
                case 0:

                case 1:

                case 6:
                    if (Utils.isDAILY(context)) {
                        Log.d("DM","Yess  In there Once More");
                        Utils.remind24(context);
                        Utils.remind3hour(context);
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
