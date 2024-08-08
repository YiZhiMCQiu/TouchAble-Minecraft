package cn.yizhimcqiu.util;

import java.lang.reflect.Field;

public class ObfRefHelper {
    public static Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }
}
