package com.graction.developer.zoocaster.file;


import org.junit.Test;

/**
 * Created by Graction06 on 2018-01-08.
 */

public class FileTest {

    @Test
    public void useAppContext(){
        String file = "image/background/sunny.png";
        String[] result = new String[2];
        result[0] = file.substring(0, file.lastIndexOf("/")+1);
        result[1] = file.substring(file.lastIndexOf("/")+1);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }
}
