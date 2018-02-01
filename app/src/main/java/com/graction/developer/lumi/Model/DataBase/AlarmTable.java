package com.graction.developer.lumi.Model.DataBase;

import com.graction.developer.lumi.DataBase.AutoIncreament;

/**
 * Created by Graction06 on 2018-01-25.
 */

public class AlarmTable {
    @AutoIncreament
    private int alarm_index;
    private int alarm_hourofday, alarm_minute, alarm_volume;
    private String alarm_address, alarm_memo, alarm_days;
    private int alarm_running_state;

    public AlarmTable() {
    }

    public AlarmTable(int alarm_hourofday, int alarm_minute, String alarm_address, String alarm_memo, String alarm_days, int alarm_running_state, int alarm_volume) {
        this.alarm_running_state = alarm_running_state;
        this.alarm_hourofday = alarm_hourofday;
        this.alarm_minute = alarm_minute;
        this.alarm_address = alarm_address;
        this.alarm_memo = alarm_memo;
        this.alarm_days = alarm_days;
        this.alarm_volume = alarm_volume;
    }

    public int getAlarm_index() {
        return alarm_index;
    }

    public void setAlarm_index(int alarm_index) {
        this.alarm_index = alarm_index;
    }

    public int getAlarm_hourofday() {
        return alarm_hourofday;
    }

    public void setAlarm_hourofday(int alarm_hourofday) {
        this.alarm_hourofday = alarm_hourofday;
    }

    public int getAlarm_minute() {
        return alarm_minute;
    }

    public void setAlarm_minute(int alarm_minute) {
        this.alarm_minute = alarm_minute;
    }

    public int getAlarm_volume() {
        return alarm_volume;
    }

    public void setAlarm_volume(int alarm_volume) {
        this.alarm_volume = alarm_volume;
    }

    public String getAlarm_memo() {
        return alarm_memo;
    }

    public void setAlarm_memo(String alarm_memo) {
        this.alarm_memo = alarm_memo;
    }

    public String getAlarm_days() {
        return alarm_days;
    }

    public void setAlarm_days(String alarm_days) {
        this.alarm_days = alarm_days;
    }

    public int getAlarm_running_state() {
        return alarm_running_state;
    }

    public void setAlarm_running_state(int alarm_running_state) {
        this.alarm_running_state = alarm_running_state;
    }

    public String getAlarm_address() {
        return alarm_address;
    }

    public void setAlarm_address(String alarm_address) {
        this.alarm_address = alarm_address;
    }

    @Override
    public String toString() {
        return "AlarmTable{" +
                "alarm_index=" + alarm_index +
                ", alarm_hourofday=" + alarm_hourofday +
                ", alarm_minute=" + alarm_minute +
                ", alarm_volume=" + alarm_volume +
                ", alarm_address='" + alarm_address + '\'' +
                ", alarm_memo='" + alarm_memo + '\'' +
                ", alarm_days='" + alarm_days + '\'' +
                ", alarm_running_state=" + alarm_running_state +
                '}';
    }
}
