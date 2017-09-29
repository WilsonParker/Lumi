package com.graction.developer.lumi.Util.Parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by graction03 on 2017-09-28.
 */

public class ObjectParserManager {
    private static final ObjectParserManager instance = new ObjectParserManager();

    public static ObjectParserManager getInstance() {
        return instance;
    }

    public ArrayList<String> createFieldList(Class cls) {
        ArrayList<String> list = new ArrayList<>();
        for (Field field : cls.getDeclaredFields())
            list.add(field.getName().toLowerCase());
        return list;
    }

    public Map<String, Method> createMethodMap(Class cls, ParserCompareAction parserCompareAction) {
        Map<String, Method> map = new HashMap<>();
        for (Method method : cls.getDeclaredMethods()) {
            if (parserCompareAction.compare(method.getName())) {
                parserCompareAction.add(map, method);
            }
        }
        return map;
    }

    public Object invoke(Method method, Object obj, Object... value) throws InvocationTargetException, IllegalAccessException {
        Object result;
        Type[] types = method.getParameterTypes();
        Object[] o = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            String t = types[i].toString().toLowerCase();
            String v = String.valueOf(value[i]);
            if (t.equals(value[i].getClass().toString())) {
                o[i] = value[i];
            } else if (t.contains("string")) {
                o[i] = String.valueOf(value[i]);
            } else if (t.contains("double")) {
                o[i] = Double.parseDouble(v);
            } else if (t.contains("long")) {
                o[i] = Long.parseLong(v);
            } else if (t.contains("float")) {
                o[i] = Float.parseFloat(v);
            } else if (t.contains("int")) {
                o[i] = Integer.parseInt(v);
            } else if (t.contains("boolean")) {
                o[i] = Boolean.parseBoolean(v);
            } else if (t.contains("string")) {
                o[i] = v;
            } else if (t.contains("byte")) {
                o[i] = Byte.parseByte(v);
            } else if (t.contains("char")) {
                o[i] = v.charAt(0);
            }
        }
        result = method.invoke(obj, o);
        return result;
    }


    public interface ParserCompareAction {
        boolean compare(String s);

        void add(Map<String, Method> map, Method method);
    }
}
