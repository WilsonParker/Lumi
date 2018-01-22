package com.graction.developer.lumi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Example local unit currentWeather, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class A2Test {
    @Test
    public void addition_isCorrect() throws Exception {
        test2();
    }

    private void test1(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        System.out.println(list.contains(2));
        System.out.println(list);
    }

    private void test2(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
    }
}