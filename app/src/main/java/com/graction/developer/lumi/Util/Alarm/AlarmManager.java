package com.graction.developer.lumi.Util.Alarm;

import android.util.Log;

import com.google.gson.Gson;
import com.graction.developer.lumi.Model.Item.AlarmData;
import com.graction.developer.lumi.Util.File.PreferenceManager;

import static com.graction.developer.lumi.Data.DataStorage.Preference.PREFERENCE_ALARM_DATA;

/**
 * Created by Graction06 on 2018-01-22.
 */

public class AlarmManager {
    private PreferenceManager preferenceManager = new PreferenceManager(PreferenceManager.PREFERENCE_ALARM);
    private AlarmData alarmData;
    private Gson gson = new Gson();
    private static final AlarmManager instance = new AlarmManager();

    public static AlarmManager getInstance() {
        return instance;
    }

    public void init() {
        alarmData = gson.fromJson(preferenceManager.getValue(PREFERENCE_ALARM_DATA, "").toString(), AlarmData.class);
        alarmData.addIndex();
    }

    public void addAlarm(AlarmData.AlarmItem item) {
        int i = 0;
        for (; i < Integer.MAX_VALUE; i++)
            if (!alarmData.containsIndex(i))
                break;
        item.setIndex(i);
        alarmData.addItem(item);
    }

    public void deleteAlarm(int index) {
        alarmData.deleteItem(index);
        Log.i(getClass().getCanonicalName(),"deleteAlarm");
    }

    public void saveAlarm(){
        preferenceManager.setValue(PREFERENCE_ALARM_DATA, gson.toJson(alarmData));
    }

    public void setAlarmData(AlarmData alarmData) {
        this.alarmData = alarmData;
    }

    public AlarmData getAlarmData() {
        return alarmData;
    }
}
