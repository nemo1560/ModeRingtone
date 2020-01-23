package nemo1560.moderingtone.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import nemo1560.moderingtone.Core.BaseService;
import nemo1560.moderingtone.MainActivity;
import nemo1560.moderingtone.R;

public class OffRingTone extends BaseService {
    private final static String LOG = "OffRingTone";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        onHandleIntent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    protected void onHandleIntent() {
        Log.d(LOG,"-------OK Off---------");
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            send("OK");
        }else {
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            send("Had");
        }
    }

    private void send(String ok) {
        if(ok.equalsIgnoreCase("OK")){
            clearName("offTime");
            setNotification("Chế độ rung","Đã tắt chuông", R.mipmap.ic_icon);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onCreate();
    }

}
