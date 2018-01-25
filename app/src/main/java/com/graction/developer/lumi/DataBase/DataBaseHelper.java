package com.graction.developer.lumi.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.Util.Parser.DataBaseParserManager;
import com.graction.developer.lumi.Util.Parser.ObjectParserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Graction06 on 2018-01-25.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final HLogger logger = new HLogger(DataBaseHelper.class);

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(
                    String.format(
                            "CREATE TABLE %s (" +
                                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "%s TEXT," +
                                    "%s TEXT," +
                                    "%s TEXT," +
                                    "%s TEXT," +
                                    "%s INTEGER UNSIGNED," +
                                    "%s INTEGER UNSIGNED," +
                                    "%s INTEGER UNSIGNED" +
                                    ");"
                            , DataBaseStorage.Table.TABLE_ALARM
                            , DataBaseStorage.Column.COLUMN_ALARM_INDEX
                            , DataBaseStorage.Column.COLUMN_ALARM_PLACE_NAME
                            , DataBaseStorage.Column.COLUMN_ALARM_PLACE_ADDRESS
                            , DataBaseStorage.Column.COLUMN_ALARM_MEMO
                            , DataBaseStorage.Column.COLUMN_ALARM_DAYS
                            , DataBaseStorage.Column.COLUMN_ALARM_HOUROFDAY
                            , DataBaseStorage.Column.COLUMN_ALARM_MINUTE
                            , DataBaseStorage.Column.COLUMN_ALARM_VOLUME
                    )
            );
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "onCreate(SQLiteDatabase)", e);
        }
    }

    // DB 업그레이드를 위해 Version 이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String query) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(query);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "insert(String)", e);
        }
    }

    public void insert(String table, Object obj) {
        try {
            String[] val = ObjectParserManager.getInstance().fieldValueToString(obj, true);
            String query = String.format("INSERT INTO %s(%s) values(%s)", table, val[0], val[1]);
            logger.log(HLogger.LogType.INFO, "insert(String, Object)", query);
            insert(query);
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "insert(String, Object)", e);
        }
    }

    public <T> T select(String query, Class cls) {
        return null;
    }

    public <T extends Object> List<T> selectList(String query, Class cls) {
        List<T> list = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                list.add(DataBaseParserManager.getInstance().parseObject(cursor, cls));
            }
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "selectList(String query, Class cls)", e);
        }
        return list;
    }
}
