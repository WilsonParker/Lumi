package com.graction.developer.lumi;

import com.graction.developer.lumi.Model.DataBase.AlarmTable;
import com.graction.developer.lumi.Util.Parser.ObjectParserManager;
import com.graction.developer.lumi.Util.StringUtil;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        test7();
    }

    private void test1() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        System.out.println(list.contains(2));
        System.out.println(list);
    }

    private void test2() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
    }

    private void test3() {
//        String[] s1 = {"1","2", "3"};
//        System.out.println(Arrays.toString(s1));

        try {
            /*String[] res = ObjectParserManager.getInstance().fieldValueToString(new AddressModel("address", "do", "si","gu"), true);
            System.out.println(res[0]);
            System.out.println(res[1]);*/

            /*int days[] = {1, 2, 3, 4};
            AlarmData.AlarmItem item = new AlarmData().new AlarmItem("place_name", "place_address", "memo", days, 17, 38);
            String[] result = ObjectParserManager.getInstance().fieldValueToString(item, true);
            System.out.println(result[0]);
            System.out.println(result[1]);*/

            AlarmTable table = new AlarmTable(17, 52, "Deoksugung", "99 Sejong-daero, Jeong-dong, Jung-gu, Seoul, South Korea", "memo", "1,1,0,0,0,0,0");
            String[] result = ObjectParserManager.getInstance().fieldValueToString(table, true);
            System.out.println(result[0]);
            System.out.println(result[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test4() {
        String s = "money";
        System.out.println(StringUtil.convertCase(s, 2, true));
        System.out.println(StringUtil.convertFirstUpperOrLower(s, true));
    }

    private void test5() {
        String s = "abc";
        Integer i = 1;
        Integer[] is = {1, 2, 3};
        try {
            System.out.println(s.getClass().getName().toLowerCase());
            System.out.println(i.getClass().getName().toLowerCase());
            System.out.println(is.getClass().getName().toLowerCase());
            System.out.println(is.getClass().getName().toLowerCase());
            System.out.println(ObjectParserManager.getInstance().attachData(s));
            System.out.println(ObjectParserManager.getInstance().attachData(i));
            System.out.println(ObjectParserManager.getInstance().attachData(is));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void test6() {
        try {
            AlarmTable table = new AlarmTable(17, 52, "Deoksugung", "99 Sejong-daero, Jeong-dong, Jung-gu, Seoul, South Korea", "memo", "1,1,0,0,0,0,0");
            /*Field field = table.getClass().getDeclaredField("");
            System.out.println(field.getName());*/
            System.out.println(table.getClass().getDeclaredField("alarm_place_name").getName());
            String[] result = ObjectParserManager.getInstance().fieldValueToString(table, true);
            System.out.println(result[0]);
            System.out.println(result[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test7() {
        try {
            AlarmTable table = new AlarmTable(17, 52, "Deoksugung", "99 Sejong-daero, Jeong-dong, Jung-gu, Seoul, South Korea", "memo", "1,1,0,0,0,0,0");
            for(Method method : table.getClass().getDeclaredMethods()){
                if(method.getName().contains("set")) {
                    System.out.println(method.getParameterTypes()[0].getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}