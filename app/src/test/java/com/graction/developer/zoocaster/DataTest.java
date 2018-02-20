package com.graction.developer.lumi;


import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Graction06 on 2018-01-08.
 */

public class DataTest {

    @Test
    public void useAppContext(){
        // Context of the app under currentWeather.
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd HH : mm : ss");
        System.out.println(format.format(date));

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, 12);
        mCalendar.set(Calendar.MINUTE, 32);
        mCalendar.set(Calendar.SECOND, 10);

        System.out.println(format.format(mCalendar.getTimeInMillis()));
    }
}
