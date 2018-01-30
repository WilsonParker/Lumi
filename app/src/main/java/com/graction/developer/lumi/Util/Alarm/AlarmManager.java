package com.graction.developer.lumi.Util.Alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.Service.AlarmService;
import com.graction.developer.lumi.Util.Date.DateManager;
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
    private final int interval = 1000 * 60 * 60 * 24; // a day
    private android.app.AlarmManager alarmManager;
    private ArrayList<AlarmItem> alarmList;
    private Context context;

    public static AlarmManager getInstance() {
        return instance;
    }

    // need to init
    public void init(Context context) {
        this.context = context;
    }

    public void init() {
        for (AlarmItem item : alarmList)
            setAlarmService(item);
    }

    public void setAlarm(Context context, AlarmItem item) {
        alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.set(Calendar.HOUR_OF_DAY, item.isMorning() ? item.getHour() : item.getHour() + 12);
        mCalendar.set(Calendar.MINUTE, item.getMinute());

        Intent alarmIntent = new Intent(DataStorage.Action.RECEIVE_ACTION_ALARM_START);
//        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataStorage.Intent.KEY_ALARM_ITEM, item);
        alarmIntent.putExtra(DataStorage.Intent.KEY_BUNDLE, bundle);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = getPendingIntent(context, item.getIndex(), alarmIntent);

//        long time = System.currentTimeMillis() > mCalendar.getTimeInMillis() ? mCalendar.getTimeInMillis() + interval : mCalendar.getTimeInMillis();
//        long time = System.currentTimeMillis() + 5000;
        long time = System.currentTimeMillis() > mCalendar.getTimeInMillis() ? mCalendar.getTimeInMillis() + interval : mCalendar.getTimeInMillis();

//        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, time, pendingIntent);
//        alarmManager.setRepeating(android.app.AlarmManager.ELAPSED_REALTIME_WAKEUP, time, 1000 * 60, pendingIntent);
//        alarmManager.setInexactRepeating(android.app.AlarmManager.ELAPSED_REALTIME_WAKEUP, time, 1000 * 60, pendingIntent);
//        alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, time, 1000 * 60, pendingIntent);
        alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, time, interval, pendingIntent);
        logger.log(INFO, "setAlarm(AlarmItem)", "AlarmItem : " + DateManager.getInstance().formatDate(time, "yyyy-MM-dd HH-mm"));
        logger.log(INFO, "setAlarm(AlarmItem)", "AlarmItem : " + item);
    }

    public void setAlarmService(AlarmItem item) {
        setAlarmService(context, item);
    }

    public void setAlarmService(Context context, AlarmItem item) {
        Intent alarmIntent = new Intent(context, AlarmService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataStorage.Intent.KEY_ALARM_ITEM, item);
        alarmIntent.putExtra(DataStorage.Intent.KEY_BUNDLE, bundle);
        context.startService(alarmIntent);
    }

        public PendingIntent getPendingIntent(Context context, int index, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                index,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return pendingIntent;
    }

    /*public void setAlarm(AlarmItem item) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, item.isMorning() ? item.getHour() : item.getHour() + 12);
        mCalendar.set(Calendar.MINUTE, item.getMinute());

        Intent alarmIntent = new Intent(DataStorage.Action.RECEIVE_ACTION_SINGLE_ALARM);
//        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataStorage.Intent.KEY_ALARM_ITEM, item);
        alarmIntent.putExtra(DataStorage.Intent.KEY_BUNDLE,bundle);
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
    }*/

    public void cancelAlarm(AlarmItem item) {
        Intent alarmIntent = new Intent(DataStorage.Action.RECEIVE_ACTION_ALARM_START);
        PendingIntent pendingIntent = getPendingIntent(context, item.getIndex(), alarmIntent);
        alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
