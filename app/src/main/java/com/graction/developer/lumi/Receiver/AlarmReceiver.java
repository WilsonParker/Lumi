package com.graction.developer.lumi.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.Service.AlarmService;
import com.graction.developer.lumi.Util.Date.DateManager;
import com.graction.developer.lumi.Util.Log.HLogger;

import java.util.Arrays;

/**
 * Created by Graction06 on 2018-01-05.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private HLogger logger = new HLogger(AlarmReceiver.class);
    private PowerManager.WakeLock sCpuWakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "intent.getAction() : " + intent.getAction()); // com.graction.developer.lumi.ALARM_START
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "Ring Ring Ring");
        int[] week = intent.getIntArrayExtra(DataStorage.Intent.KEY_WEEK);
        AlarmItem item = (AlarmItem) intent.getSerializableExtra(DataStorage.Intent.KEY_ALARM_ITEM);
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "item : "+item);
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "week : "+ Arrays.toString(week));
        if (week != null && DateManager.getInstance().isDayInWeek(week)) {
            intent.setClass(context, AlarmService.class);
            context.startService(intent);
        }
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
