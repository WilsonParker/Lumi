package com.graction.developer.lumi.Model.Item;

import com.graction.developer.lumi.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Graction06 on 2018-01-16.
 */
public class AlarmData {
    private ArrayList<AlarmItem> items = new ArrayList<>();
    private ArrayList<Integer> indexList = new ArrayList<>();

    public ArrayList<AlarmItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<AlarmItem> items) {
        this.items = items;
    }

    public void addIndex() {
        for (AlarmItem item : items)
            indexList.add(item.getIndex());
    }

    public void addIndex(int index) {
        indexList.add(index);
    }

    public boolean containsIndex(int index) {
        return indexList.contains(index);
    }

    public void addItem(AlarmItem item) {
        items.add(item);
    }

    public void deleteItem(int index) {
        for (AlarmItem item : items) {
            if (item.getIndex() == index) {
                items.remove(item);
                break;
            }
        }
    }

    public class AlarmItem {
        private final String[] DayOfTheWeek = {"일", "월", "화", "수", "목", "금", "토"};
        private String address, memo;
        private int[] days;
        private boolean isMorning = true, isSpeaker;
        private int index, hour, minute, volume, img_phone, img_speaker;

/*
    *   Scalable
    * *//*

    private static final MathematicsManager mathematicsManager =  MathematicsManager.getInstance();
    private static final double RAT_WIDTH = UIFactory.getRatDeviseWidth()
                                , RAT_HEIGHT = UIFactory.getRatDeviseWidth()
                                , DIGIT = UIFactory.getRatDeviseWidth();
    private static final double height = mathematicsManager.rounds(172.6, RAT_HEIGHT, DIGIT)
                , text_address_size = mathematicsManager.rounds(15, RAT_HEIGHT, DIGIT)
                , text_timeinfo_size = mathematicsManager.rounds(12, RAT_HEIGHT, DIGIT)
                , text_time_size = mathematicsManager.rounds(25, RAT_HEIGHT, DIGIT)
                , text_days_size = mathematicsManager.rounds(13, RAT_HEIGHT, DIGIT)
                , text_memo_size = mathematicsManager.rounds(13, RAT_HEIGHT, DIGIT)
                ;
*/

        public AlarmItem(int index, String address, String memo, int[] days, int hour, int minute) {
            this(address, memo, days, hour, minute);
            this.index = index;
        }

        public AlarmItem(int index, String address, String memo, int[] days, int hour, int minute, int volume) {
            this(address, memo, days, hour, minute, volume);
            this.index = index;
        }

        public AlarmItem(String address, String memo, int[] days, int hour, int minute) {
            this.address = address;
            this.memo = memo;
            this.hour = hour;
            this.days = days;
            this.minute = minute;
            setMorning(hour);
            img_phone = R.drawable.phone_icon_on;
            img_speaker = R.drawable.sound_icon_off;
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
            img_phone = R.drawable.phone_icon_off;
            img_speaker = R.drawable.sound_icon_on;
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
                    sDay += DayOfTheWeek[i] + " ";
            }
            return sDay;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getImg_phone() {
            return img_phone;
        }

        public int getImg_speaker() {
            return img_speaker;
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
                    ", index=" + index +
                    ", hour=" + hour +
                    ", minute=" + minute +
                    ", volume=" + volume +
                    ", img_phone=" + img_phone +
                    ", img_speaker=" + img_speaker +
                    '}';
        }
    }
}
