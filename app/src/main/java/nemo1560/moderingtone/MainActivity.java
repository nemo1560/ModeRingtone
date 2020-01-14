package nemo1560.moderingtone;

import androidx.annotation.RequiresApi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import nemo1560.moderingtone.Controller.AlarmUtil;
import nemo1560.moderingtone.Core.BaseActivity;
import nemo1560.moderingtone.Core.ScheduleUtils;
import nemo1560.moderingtone.Service.Ground;
import nemo1560.moderingtone.Service.OnRingTone;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button on,off;
    private final static String TAG = "ModeRingTone";
    private AlarmManager alarmManager;
    private TelephonyManager telemamanger;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        on = findViewById(R.id.on);
        off = findViewById(R.id.off);
        final List<String> reqPermissions = Arrays.asList(
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE);

        listPermissionRequest(reqPermissions);

        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        init();
    }

    @SuppressLint({"HardwareIds", "NewApi", "MissingPermission"})
    private void init() {
        on.setOnClickListener(this);
        off.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        final int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        if(view.getId() == off.getId()){
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Calendar newTime = Calendar.getInstance();
                    if(selectedHour >= hour){
                        newTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                        newTime.set(Calendar.MINUTE, selectedMinute);
                        editor.putLong("offTime",newTime.getTimeInMillis()).commit();
                        editor.putInt("offHour",selectedHour).commit();
                        editor.putInt("offMinute",selectedMinute).commit();
                        editor.putInt("offDay",date).commit();
                        Log.d(TAG,date+" "+hour+" "+minute);
                        Toast.makeText(activity,"Bạn chọn tắt chuông lúc "+selectedHour+":"+selectedMinute,Toast.LENGTH_SHORT).show();
                        new AlarmUtil(activity).OffRingtone(alarmManager,newTime);
                    }else {
                        int tomorrow = date+1;
                        newTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                        newTime.set(Calendar.MINUTE, selectedMinute);
                        editor.putLong("offTime",newTime.getTimeInMillis()).commit();
                        editor.putInt("offHour",selectedHour).commit();
                        editor.putInt("offMinute",selectedMinute).commit();
                        editor.putInt("offDay",tomorrow).commit();
                        Log.d(TAG,tomorrow+" "+hour+" "+minute);
                        Toast.makeText(activity,"Bạn chọn tắt chuông lúc "+selectedHour+":"+selectedMinute,Toast.LENGTH_SHORT).show();
                        new AlarmUtil(activity).OffRingtone(alarmManager,newTime);
                    }

                }
            },hour,minute,true);
            timePickerDialog.show();
        }else {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Calendar newTime = Calendar.getInstance();
                    if(selectedHour >= hour){
                        newTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                        newTime.set(Calendar.MINUTE, selectedMinute);
                        editor.putLong("onTime",newTime.getTimeInMillis()).commit();
                        editor.putInt("onHour",selectedHour).commit();
                        editor.putInt("onMinute",selectedMinute).commit();
                        editor.putInt("onDay",date).commit();
                        Log.d(TAG,date+" "+hour+" "+minute);
                        Toast.makeText(activity,"Bạn chọn bật chuông lúc "+selectedHour+":"+selectedMinute,Toast.LENGTH_SHORT).show();
                        new AlarmUtil(activity).OnRingtone(alarmManager,newTime);
                    }else {
                        int tomorrow = date+1;
                        newTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                        newTime.set(Calendar.MINUTE, selectedMinute);
                        editor.putLong("onTime",newTime.getTimeInMillis()).commit();
                        editor.putInt("onHour",selectedHour).commit();
                        editor.putInt("onMinute",selectedMinute).commit();
                        editor.putInt("onDay",tomorrow).commit();
                        Log.d(TAG,tomorrow+" "+hour+" "+minute);
                        Toast.makeText(activity,"Bạn chọn bật chuông lúc "+selectedHour+":"+selectedMinute,Toast.LENGTH_SHORT).show();
                        new AlarmUtil(activity).OnRingtone(alarmManager,newTime);
                    }
                }
            },hour,minute,true);
            timePickerDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        Confirm("Thoát", "Bạn muốn thoát chương trình", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ScheduleUtils.ScheduleUtils(activity);
        }else {
            Intent intent = new Intent(activity,Ground.class);
            startService(intent);
        }
    }
}
