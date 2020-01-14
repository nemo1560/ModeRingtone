package nemo1560.moderingtone.Controller;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

import nemo1560.moderingtone.Service.OffRingTone;
import nemo1560.moderingtone.Service.OnRingTone;

public class AlarmUtil {
    private Intent intentService;
    private Context context;
    private BroadcastReceiver offRevice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    public AlarmUtil(Context context) {
        this.context = context;
    }

    public void OffRingtone(AlarmManager alarmManager, Calendar calendar) {
        intentService = new Intent(context, OffRingTone.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intentService, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
            Log.d("offTime",calendar.getTimeInMillis()+"");
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("offTime",calendar.getTimeInMillis()+"");
        }
    }

    public void OnRingtone(AlarmManager alarmManager, Calendar calendar) {
        intentService = new Intent(context, OnRingTone.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intentService, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
            Log.d("onTime",calendar.getTimeInMillis()+"");
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("onTime",calendar.getTimeInMillis()+"");
        }
    }

    public void OffRingtone(AlarmManager alarmManager, Long off) {
        intentService = new Intent(context, OffRingTone.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intentService, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, off,pendingIntent);
            Log.d("offTimeBackground",off+"");
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, off, pendingIntent);
            Log.d("offTimeBackground",off+"");
        }
    }

    public void OnRingtone(AlarmManager alarmManager, Long on) {
        intentService = new Intent(context, OnRingTone.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intentService, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, on,pendingIntent);
            Log.d("onTimeBackground",on+"");
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, on, pendingIntent);
            Log.d("onTimeBackground",on+"");
        }
    }
}
