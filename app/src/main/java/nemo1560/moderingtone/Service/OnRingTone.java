package nemo1560.moderingtone.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

import nemo1560.moderingtone.Core.BaseService;
import nemo1560.moderingtone.R;

public class OnRingTone extends BaseService {
    private final static String LOG = "OnRingTone";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void send(String ok){
        if(ok != null){
            clearName("onTime");
            setNotification("Chế độ chuông","Đã bật chuông", R.mipmap.ic_icon);
        }
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
        Log.d(LOG,"-------OK On---------");
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }else {
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        send("OK");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onCreate();
    }
}
