package com.graction.developer.zoocaster;

import android.content.ContentValues;

import com.graction.developer.zoocaster.DataBase.DataBaseParserManager;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.DataBase.AlarmTable;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.StringUtil;

import org.junit.Test;

/**
 * Example local unit currentWeather, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class A3Test {
    private final HLogger logger = new HLogger(A3Test.class);

    @Test
    public void addition_isCorrect() throws Exception {
        test1();
    }

    private void test1() {
        int[] selectedWeek = {0, 1, 1, 0, 0, 0, 0, 0};
        AlarmItem item = new AlarmItem(new AlarmTable(1, 1, "address", "memo", StringUtil.arrayToString(selectedWeek), AlarmTable.ENABLED, 1, AlarmTable.ENABLED));
        ContentValues values = null;
        try {
            values = DataBaseParserManager.getInstance().bindContentValues(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String whereClause = DataBaseStorage.Column.COLUMN_ALARM_INDEX + " = ?";
        String[] args = {item.getIndex() + ""};
        System.out.println(whereClause + " : " + args);
//        DataBaseStorage.alarmDataBaseHelper.update(DataBaseStorage.Table.TABLE_ALARM, values, whereClause, args);
    }

   /* boolean isRunning = true;
    private void test2() {
        Net.getInstance().getAddressFactoryIm().searchAddress(AddressModelResult.getParameter("안양시", 1)).enqueue(new Callback<AddressModelResult>() {
            @Override
            public void onResponse(Call<AddressModelResult> call, Response<AddressModelResult> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    isRunning =false;
                }
            }

            @Override
            public void onFailure(Call<AddressModelResult> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onSearch(View view) - onFailure(Call<PostcodifyModel> call, Throwable t)", t);
            }
        });
        while (isRunning) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/
}