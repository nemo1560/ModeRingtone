package nemo1560.moderingtone.Core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import nemo1560.moderingtone.MainActivity;

public class BaseService extends Service {
    private final String CHANNEL_ID = "my_weather_01";
    private final String CHANNEL_NAME = "my_weather";
    private final static int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private Notification.Builder builder;
    private NotificationCompat.Builder cbuilder;
    public static SharedPreferences.Editor editor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        share();
    }

    private void share(){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        editor = preference.edit();
    }

    public void clear(){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        preference.edit().clear().commit();
    }

    public void clearName(String name){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        preference.edit().remove(name).commit();
    }

    public Long getLong(String name,Long def){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        return preference.getLong(name,def);
    }

    public int getInt(String name,int def){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        return preference.getInt(name,def);
    }

    public Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 1.5f); // round
        int height = (int) (baseline + paint.descent() + 1.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    public void setNotification(String title, String temp,int icon){
        if(!temp.isEmpty()){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            ////notification cho dong anroid 8.0
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

                notificationManager.createNotificationChannel(notificationChannel);

                builder = new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(temp)
                        .setSmallIcon(icon)
                        .setAutoCancel(true)
                        .setChannelId(CHANNEL_ID)
                        .setOngoing(false)
                        .setPriority(Notification.PRIORITY_HIGH);

                Intent notificationIntent = new Intent(getApplication(), MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent intent = PendingIntent.getActivity(getApplication(), 0, notificationIntent, 0);
                builder.setContentIntent(intent);

                Notification notification = builder.build();

                //bật startforcegound notification.
//                startForeground(1,notification);

                notificationManager.notify(NOTIFICATION_ID,notification);

            }
            else {
                //notification cho dong anroid thap hon 8.0
                cbuilder = new NotificationCompat.Builder(this);
                cbuilder.setContentTitle(title)
                        .setContentText(temp)
                        .setSmallIcon(icon)
                        .setLargeIcon(textAsBitmap(temp, (float) 30, Color.BLACK))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setLights(Color.parseColor("#eb0300"), 500, 2000)
                        .setAutoCancel(true);

                Intent notificationIntent = new Intent(getApplication(), MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent intent = PendingIntent.getActivity(getApplication(), 0, notificationIntent, 0);
                cbuilder.setContentIntent(intent);

                Notification notification = cbuilder.build();
                notificationManager.notify(NOTIFICATION_ID,notification);
            }
        }
    }
}
