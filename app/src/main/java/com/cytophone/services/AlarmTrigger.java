package com.cytophone.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.app.PendingIntent;
import android.app.AlarmManager;

import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Handler;

public class AlarmTrigger extends BroadcastReceiver {
    @Override
    public void onReceive (final Context context, Intent intent) {
        AlarmTrigger.scheduleExactAlarm(context
                ,(AlarmManager)context.getSystemService(Context.ALARM_SERVICE)
                ,90000);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock( PowerManager.PARTIAL_WAKE_LOCK
                ,"CellCom:Wakelock");
        wl.acquire();

        Handler handler = new Handler();
        Runnable periodicUpdate = new Runnable() {
            @Override
            public void run() {
                // whatever you want to do
            }
        };

        handler.post(periodicUpdate);
        wl.release();
    }

    public static void scheduleExactAlarm(Context context, AlarmManager alarms, int interval) {
        long triggerTime = SystemClock.elapsedRealtime()+10*1000-
                SystemClock.elapsedRealtime()%1000;
        int refreshInterval = interval;

        Intent i = new Intent(context, AlarmTrigger.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        alarms.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
    }

    public static void cancelAlarm(Context context, AlarmManager alarms) {
        Intent i = new Intent(context, AlarmTrigger.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        alarms.cancel(pi);
    }
}