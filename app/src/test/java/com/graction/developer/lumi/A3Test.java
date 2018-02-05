package com.graction.developer.lumi;

import android.content.ContentValues;

import com.graction.developer.lumi.DataBase.DataBaseParserManager;
import com.graction.developer.lumi.DataBase.DataBaseStorage;
import com.graction.developer.lumi.Model.DataBase.AlarmTable;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.Util.StringUtil;

import org.junit.Test;

/**
 * Example local unit currentWeather, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class A3Test {
    @Test
    public void addition_isCorrect() throws Exception {
        test1();
    }

    private void test1() {
        int[] selectedWeek = {0,1,1,0,0,0,0,0};
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
        DataBaseStorage.alarmDataBaseHelper.update(DataBaseStorage.Table.TABLE_ALARM, values, whereClause, args);
    }
}