package nemo1560.moderingtone.Core;

import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import nemo1560.moderingtone.Service.Ground;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

public class JobSchedule extends JobService {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (!isMyServiceRunning(Ground.class)) {
                getApplicationContext().stopService(new Intent(getApplicationContext(), Ground.class));
                getApplicationContext().startForegroundService(new Intent(getApplicationContext(), Ground.class));
            } else {
                getApplicationContext().startService(new Intent(getApplicationContext(), Ground.class));
            }
            ScheduleUtils.ScheduleUtils(getApplicationContext());
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

