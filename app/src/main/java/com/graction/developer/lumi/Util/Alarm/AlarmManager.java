package com.graction.developer.lumi.Util.Alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.Receiver.AlarmReceiver;
import com.graction.developer.lumi.Util.Log.HLogger;

import java.util.ArrayList;
import java.util.Calendar;

import static com.graction.developer.lumi.Util.Log.HLogger.LogType.INFO;

/**
 * Created by Graction06 on 2018-01-22.
 */

public class AlarmManager {
    private static final AlarmManager instance = new AlarmManager();
    private final HLogger logger = new HLogger(AlarmManager.class);
    private android.app.AlarmManager alarmManager;
    private ArrayList<AlarmItem> alarmList;
    private Context context;

    public static AlarmManager getInstance() {
        return instance;
    }

    public void setAlarmList(ArrayList<AlarmItem> alarmList) {
        this.alarmList = alarmList;
        init();
    }

    // need to init
    public void init(Context context) {
        this.context = context;
        alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void init() {
        for (AlarmItem item : alarmList)
            setAlarm(item);
    }

    private void setAlarm(AlarmItem item) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, item.isMorning() ? item.getHour() : item.getHour() + 12);
        mCalendar.set(Calendar.MINUTE, item.getMinute());

//        Intent alarmIntent = new Intent(DataStorage.Action.RECEIVE_ACTION_ALARM);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra(DataStorage.Intent.KEY_WEEK, item.getDays());
        alarmIntent.putExtra(DataStorage.Intent.KEY_ALARM_ITEM, item);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                item.getIndex(),
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        item.setPendingIntent(pendingIntent);
        alarmManager.set(
                android.app.AlarmManager.RTC_WAKEUP,
                mCalendar.getTimeInMillis(),
                pendingIntent
        );

        logger.log(INFO, "setAlarm(AlarmItem)", "AlarmItem : " + item);
    }

    public void deleteAlarm(AlarmItem item) {
        alarmManager.cancel(item.getPendingIntent());
    }
}
