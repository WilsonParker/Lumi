package com.graction.developer.lumi;

import com.graction.developer.lumi.Util.Parser.MathematicsManager;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Example local unit currentWeather, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class A1Test {
    @Test
    public void addition_isCorrect() throws Exception {
        test5();
    }

    private void test1() {
        System.out.println(7 % 1);
        System.out.println(7 % 3);
        System.out.println(7 % 4);

        System.out.println(1 % 7);
        System.out.println(4 % 7);
        System.out.println(7 % 7);
        System.out.println(7 % 8);
        System.out.println(7 % 10);

        System.out.println(8 / 7);
        System.out.println(8 / 10);
        System.out.println(7 / 8);
        System.out.println(7 / 10);
    }

    private void test2() {
        String s = "java.lang.String";
        System.out.println(s.contains("\\."));
        System.out.println(s.contains("."));
    }

    private void test3() {
        String str = "\\uc548\\uc591\\ud310\\uad50\\ub85c 23-456", text = "";
        try {
            String strs[] = str.split(" ");
            for (String a : strs) {
                if (a.contains("\\u")) {
                    String arr[] = a.split("\\\\u");
                    for (int i = 1; i < arr.length; i++) {
                        int hexVal = Integer.parseInt(arr[i], 16);
                        text += (char) hexVal;
                    }
                } else {
                    text += " "+a;
                }
            }
            System.out.println(URLEncoder.encode(str, "utf-8"));
            System.out.println(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void test4(){
        String str = "\\uc548\\uc591\\ud310\\uad50\\ub85c 23-456", text = "";
        String arr[] = str.split(" ");
        for(String s : arr){
            if(s.contains("\\u")){
                int index = s.indexOf("\\u");
                while(index > -1){
                    if(index > (s.length()-6)) break;
                    int snum = index + 2
                            , fnum = snum + 4;
                    String subStr = s.substring(snum, fnum);
                    text += (char) Integer.parseInt(subStr, 16);
                    index = str.indexOf("\\u", index+1);
                }
            }else{
                text += " "+s;
            }
        }

        System.out.println(text);
    }

    private void test5(){
        System.out.println(5 / 4);
        System.out.println(5 / 10);
        System.out.println(MathematicsManager.getInstance().rounds(5/4,2));
        System.out.println(MathematicsManager.getInstance().rounds(1/10,3));
        System.out.println(MathematicsManager.getInstance().rounds(-1/3,3));
        System.out.println(MathematicsManager.getInstance().rounds(-1*3,3));
        System.out.println(Math.pow(2,22));
    }
}