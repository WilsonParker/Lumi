package com.graction.developer.lumi;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Graction06 on 2018-01-08.
 */

@RunWith(AndroidJUnit4.class)
public class DataTest {
    private static final String TAG = "DataTest";
    /**
     * Instrumentation currentWeather, which will execute on an Android device.
     *
     * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
     */

    @Test
    public void useAppContext(){
        // Context of the app under currentWeather.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.graction.developer.lumi", appContext.getPackageName());

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd HH : mm : ss");
        System.out.println("DateTest");
        System.out.println(format.format(date));
        Log.d(TAG,"DateTest");
    }
}
