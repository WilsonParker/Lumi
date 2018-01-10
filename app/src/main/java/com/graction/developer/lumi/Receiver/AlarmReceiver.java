package com.graction.developer.lumi.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.graction.developer.lumi.Service.AlarmService;
import com.graction.developer.lumi.Util.Log.HLogger;

/**
 * Created by Graction06 on 2018-01-05.
 */

public class AlarmReceiver extends BroadcastReceiver{
    private HLogger logger = new HLogger(AlarmReceiver.class);
    private PowerManager.WakeLock sCpuWakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        logger.log(HLogger.LogType.INFO, "AlarmReceiver","intent.getAction() : "+intent.getAction()); // com.graction.developer.lumi.ALARM_START
        logger.log(HLogger.LogType.INFO, "AlarmReceiver","Ring Ring Ring");
        intent.setClass(context, AlarmService.class);
        context.startService(intent);
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
}
