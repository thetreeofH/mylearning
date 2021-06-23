package com.wxiaoqi.rowauth.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ObjectUtils {
    /**
     * 将Object对象里面的属性和值转化成 objectToLinkedHashMap 对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static LinkedHashMap<String, Object> objectToLinkedHashMap(Object obj) throws IllegalAccessException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        List<Field> declaredFields = new ArrayList<>();
        declaredFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        /** 处理父类字段**/
        Class<?> superClass = clazz.getSuperclass();
        if(!superClass.equals(Object.class)) {
            declaredFields.addAll(Arrays.asList(superClass.getDeclaredFields()));
        }
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }

        return map;
    }



}
