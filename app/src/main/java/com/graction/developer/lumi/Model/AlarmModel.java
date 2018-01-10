package com.graction.developer.lumi.Model;

import com.graction.developer.lumi.Util.Date.DateManager;

import java.util.Calendar;

/**
 * Created by Graction06 on 2018-01-09.
 */

public class AlarmModel {
    private DateManager dateManager = DateManager.getInstance();

    public static final int DAY_OF_WEEK_MONDAY = 0X00000001
                            , DAY_OF_WEEK_TUESDAY = 0X00000010
                            , DAY_OF_WEEK_WEDNESDAY = 0X00000100
                            , DAY_OF_WEEK_THURSDAY = 0X00001000
                            , DAY_OF_WEEK_FRIDAY = 0X00010000
                            , DAY_OF_WEEK_SATURDAY = 0X00100000
                            , DAY_OF_WEEK_SUNDAY = 0X01000000
                            ;
    private int day_of_week, hour_of_day, minute;
    private Calendar calendar;

    public AlarmModel(int day_of_week, int hour_of_day, int minute) {
        this.day_of_week = day_of_week;
        this.hour_of_day = hour_of_day;
        this.minute = minute;
    }

    private void init(){
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour_of_day);
        calendar.set(Calendar.MINUTE, minute);

    }

    public boolean isDay(){
        return false;
    }

    public long getTimeMillis(){
        return calendar.getTimeInMillis();
    }

    public class AlarmModelItem{

    }
}
