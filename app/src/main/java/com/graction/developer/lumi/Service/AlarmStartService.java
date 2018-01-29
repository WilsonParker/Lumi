package com.graction.developer.lumi.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.graction.developer.lumi.Activity.AlarmActivity;
import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.Util.Date.DateManager;
import com.graction.developer.lumi.Util.Log.HLogger;

/**
 * Created by Graction06 on 2018-01-05.
 */

public class AlarmStartService extends Service {
    private HLogger logger = new HLogger(AlarmStartService.class);
    private final IBinder binder = new AlarmBinder();
    private PowerManager.WakeLock sCpuWakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmItem item = (AlarmItem) intent.getBundleExtra(DataStorage.Intent.KEY_BUNDLE).getSerializable(DataStorage.Intent.KEY_ALARM_ITEM);
        logger.log(HLogger.LogType.INFO, "AlarmStartService", "Service Ring Ring Ring");
        logger.log(HLogger.LogType.INFO, "AlarmStartService", "item : "+item);
        int[] week = item.getDays();
        if (week != null && DateManager.getInstance().isDayInWeek(week)) {
            intent.setClass(this, AlarmActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            wakeLock(this);
        }
        return START_NOT_STICKY;
    }

    private void wakeLock(Context context) {
        if (sCpuWakeLock == null) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            sCpuWakeLock = pm.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                            PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.ON_AFTER_RELEASE
                    , "ALARM"
            );
        }
        sCpuWakeLock.acquire();

        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }

    public class AlarmBinder extends Binder {
        public AlarmStartService getService() {
            return AlarmStartService.this;
        }
    }
}
