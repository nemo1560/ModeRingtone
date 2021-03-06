package nemo1560.moderingtone.Service;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.Calendar;

import nemo1560.moderingtone.Controller.AlarmUtil;
import nemo1560.moderingtone.Core.BaseService;

public class Ground extends BaseService {
    private Thread thread;
    private final static String TAG = "GroundService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"StartService");
        startHandler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void startHandler() {
        if (thread == null) {
            thread = new Thread(runnable);
            thread.start();
        } else if (thread.getState() == Thread.State.NEW) {
            thread.start();
        } else if (thread.getState() == Thread.State.TERMINATED) {
            thread = new Thread(runnable);
            thread.start();
        }else {
            thread = new Thread(runnable);
            thread.start();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while(true){
                AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
                Calendar current = Calendar.getInstance();
                int currentDay = current.get(Calendar.DAY_OF_MONTH);
                int offDay = getInt("offDay",-1);
                int offHour = getInt("offHour",-1);
                int offMinute = getInt("offMinute",-1);
                int onDay = getInt("onDay",-1);
                int onHour = getInt("onHour",-1);
                int onMinute = getInt("onMinute",-1);
                Log.d(TAG,currentDay+" "+offDay+" "+onDay+"");
                //repeat
                if(currentDay != offDay){
                    Calendar off = Calendar.getInstance();
                    offDay = currentDay;
                    off.set(Calendar.DAY_OF_MONTH,offDay);
                    off.set(Calendar.HOUR_OF_DAY,offHour);
                    off.set(Calendar.MINUTE, offMinute);
                    editor.putInt("offHour",offHour).commit();
                    editor.putInt("offMinute",offMinute).commit();
                    editor.putInt("offDay",offDay).commit();
                    editor.putLong("offTime",off.getTimeInMillis()).commit();
                }/*else if(getLong("offTime", (long) 0) == 0){
                    Calendar off = Calendar.getInstance();
                    off.set(Calendar.DAY_OF_MONTH,offDay);
                    off.set(Calendar.HOUR_OF_DAY,offHour);
                    off.set(Calendar.MINUTE, offMinute);
                    editor.putInt("offHour",offHour).commit();
                    editor.putInt("offMinute",offMinute).commit();
                    editor.putInt("offDay",offDay).commit();
                    editor.putLong("offTime",off.getTimeInMillis()).commit();
                }*/
                Long off = getLong("offTime", (long) 0);
                if(off != 0){
                    new AlarmUtil(getBaseContext()).OffRingtone(alarmManager,off);
                }

                if(currentDay != onDay){
                    Calendar on = Calendar.getInstance();
                    onDay = currentDay;
                    on.set(Calendar.DAY_OF_MONTH,onDay);
                    on.set(Calendar.HOUR_OF_DAY,onHour);
                    on.set(Calendar.MINUTE, onMinute);
                    editor.putInt("onHour",onHour).commit();
                    editor.putInt("onMinute",onMinute).commit();
                    editor.putInt("onDay",onDay).commit();
                    editor.putLong("onTime",on.getTimeInMillis()).commit();
                }/*else if(getLong("onTime", (long) 0) == 0){
                    Calendar on = Calendar.getInstance();
                    on.set(Calendar.DAY_OF_MONTH,onDay);
                    on.set(Calendar.HOUR_OF_DAY,onHour);
                    on.set(Calendar.MINUTE, onMinute);
                    editor.putInt("onHour",onHour).commit();
                    editor.putInt("onMinute",onMinute).commit();
                    editor.putInt("onDay",onDay).commit();
                    editor.putLong("onTime",on.getTimeInMillis()).commit();
                }*/
                Long on = getLong("onTime", (long) 0);
                if(on != 0){
                    new AlarmUtil(getBaseContext()).OnRingtone(alarmManager,on);
                }
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        onCreate();
    }
}
