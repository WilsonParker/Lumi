package com.graction.developer.lumi.Model;

/**
 * Created by Graction06 on 2018-01-09.
 */

public class AlarmModel {

    public static final int DAY_OF_WEEK_MONDAY = 0X00000001
                            , DAY_OF_WEEK_TUESDAY = 0X00000010
                            , DAY_OF_WEEK_WEDNESDAY = 0X00000100
                            , DAY_OF_WEEK_THURSDAY = 0X00001000
                            , DAY_OF_WEEK_FRIDAY = 0X00010000
                            , DAY_OF_WEEK_SATURDAY = 0X00100000
                            , DAY_OF_WEEK_SUNDAY = 0X01000000
                            ;
    private int day_of_week;

}
