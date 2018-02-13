package com.graction.developer.zoocaster;


import org.junit.Test;

/**
 * Created by Graction06 on 2018-01-08.
 */

public class PreferenceTest {

    @Test
    public void useAppContext(){
        test(0);
        test("");
        test(true);
    }

    private <T> void test(T t){
        System.out.println(t.getClass().getName());
        System.out.println(t.getClass().getCanonicalName());
        System.out.println(t.getClass().getTypeName());
    }
}
