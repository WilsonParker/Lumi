package com.graction.developer.lumi.Util.Parser;

import android.database.Cursor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by graction03 on 2017-09-28.
 */

public class DataBaseParserManager extends ObjectParserManager {
    private static final DataBaseParserManager instance = new DataBaseParserManager();

    public static DataBaseParserManager getInstance() {
        return instance;
    }

    public <T> T parseObject(Cursor cursor, Class cls) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T obj = (T) cls.newInstance();
        Map<String, Method> map = getMethods(cls, new ParserCompareActionMap() {
            @Override
            public void add(Map<String, Method> map, Method method) {
                map.put(method.getName().replace("set", "").toLowerCase(), method);
            }

            @Override
            public boolean compare(String s) {
                return s.toLowerCase().contains("set");
            }
        });
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Method method = map.get(cursor.getColumnName(i).toLowerCase());
            method.invoke(obj, getData(cursor, i, method));
        }
        return obj;
    }

    public Object getData(Cursor cursor, int index, Method method) {
        String typeC = method.getParameterTypes()[0].getName();
        String type = typeC;
        if (typeC.contains(".")) {
            String s[] = typeC.split("\\.");
            type = s[s.length - 1].toLowerCase();
        }
        switch (type) {
            case "int":
            case "integer":
                return cursor.getInt(index);
            case "string":
                return cursor.getString(index);
            case "float":
                return cursor.getFloat(index);
            case "long":
                return cursor.getLong(index);
            case "double":
                return cursor.getDouble(index);
            case "short":
                return cursor.getShort(index);
            default:
                return null;

        }
    }
}
