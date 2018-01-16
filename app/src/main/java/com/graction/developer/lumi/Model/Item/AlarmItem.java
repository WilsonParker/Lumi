package com.graction.developer.lumi.Model.Item;

import java.util.Arrays;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class AlarmItem {
    private final String[] DayOfTheWeek = {"일", "월", "화", "수", "목", "금", "토"};
    private String address, memo;
    private int[] days;
    private boolean isMorning, isSpeaker;
    private int hour, minute, volume;

    public AlarmItem(String address, String memo, int[] days, int hour, int minute) {
        this.address = address;
        this.memo = memo;
        this.days = days;
        this.minute = minute;
        setMorning(hour);
    }

    public AlarmItem(String address, String memo, int[] days, int hour, int minute, int volume) {
        this.address = address;
        this.memo = memo;
        this.days = days;
        this.hour = hour;
        this.minute = minute;
        this.volume = volume;
        this.isSpeaker = true;
        setMorning(hour);
    }

    private void setMorning(int hour) {
        if (hour > 12) {
            this.isMorning = false;
            this.hour = hour - 12;
        }
    }

    public String getTimeInfo() {
        return isMorning ? "오전" : "오후";
    }

    public String getStringDays() {
        String sDay = "";
        for (int i = 0; i < days.length; i++) {
            if (days[i] == 1)
                sDay += DayOfTheWeek[i];
        }
        return sDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int[] getDays() {
        return days;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public boolean isSpeaker() {
        return isSpeaker;
    }

    public void setSpeaker(boolean speaker) {
        isSpeaker = speaker;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "AlarmItem{" +
                "DayOfTheWeek=" + Arrays.toString(DayOfTheWeek) +
                ", address='" + address + '\'' +
                ", memo='" + memo + '\'' +
                ", days=" + Arrays.toString(days) +
                ", isMorning=" + isMorning +
                ", isSpeaker=" + isSpeaker +
                ", hour=" + hour +
                ", minute=" + minute +
                ", volume=" + volume +
                '}';
    }
}
