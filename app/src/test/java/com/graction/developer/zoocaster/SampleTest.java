package com.graction.developer.zoocaster;

import org.junit.Test;

/**
 * Example local unit currentWeather, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SampleTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String s = test1("i have a monkey, monkey have a money");
        System.out.print(s);
    }

    public String test1(String s){
        return s.replace('e','E');
    }
}