package nemo1560.moderingtone.Core;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BaseActivity extends AppCompatActivity {
    private final static int REQUEST_CODE = 100;
    public static SharedPreferences.Editor editor;
    public Context activity;

    @Override
    protected void onStart() {
        super.onStart();
        activity = this;
        share();
    }

    public void Alert(String title, String massage) {
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setTitle(title);
        builder.setMessage(massage);
        builder.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        builder.show();
    }

    private void share(){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        editor = preference.edit();
    }

    public void clear(){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        preference.edit().clear().commit();
    }

    public Long getLong(String name,Long def){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        return preference.getLong(name,def);
    }

    public int getInt(String name,int def){
        SharedPreferences preference = getSharedPreferences("ModeRingTone",0);
        return preference.getInt(name,def);
    }

    public void Confirm(String title, String massage, DialogInterface.OnClickListener ok) {
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setTitle(title);
        builder.setMessage(massage);
        builder.setButton2("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        builder.setButton("OK", ok);
        builder.show();
    }

    /*final List<String> reqPermissions = Arrays.asList(Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE);*/

    public void listPermissionRequest(List<String> reqPermissions) {
        final ArrayList<String> permissionsNeeded = getPermissionNeeded(new ArrayList<>(reqPermissions));
        if (!permissionsNeeded.isEmpty()) {
            requestForPermission(permissionsNeeded.toArray(new String[permissionsNeeded.size()]));
        } else {
            //action
        }
    }

    private ArrayList<String> getPermissionNeeded(final ArrayList<String> reqPermissions) {
        final ArrayList<String> permissionNeeded = new ArrayList<>(reqPermissions.size());

        for (String reqPermission : reqPermissions) {
            if (ContextCompat.checkSelfPermission(BaseActivity.this, reqPermission) != PackageManager.PERMISSION_GRANTED) {
                permissionNeeded.add(reqPermission);
            }
        }

        return permissionNeeded;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                final int numOfRequest = grantResults.length;
                boolean isGranted = true;
                for (int i = 0; i < numOfRequest; i++) {
                    if (PackageManager.PERMISSION_GRANTED != grantResults[i]) {
                        isGranted = false;
                        break;
                    }
                }
                //action
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestForPermission(final String[] permissions) {
        ActivityCompat.requestPermissions(BaseActivity.this, permissions, REQUEST_CODE);
    }
}
