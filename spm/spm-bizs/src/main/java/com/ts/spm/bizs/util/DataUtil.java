package com.ts.spm.bizs.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:27
 * @Version 1.0
 */
public class DataUtil {

    /**
     * 计算百分比值，保留两位小数
     * @param num 分子
     * @param total 分母
     * @return
     */
    public static double getPercentValue(double num, double total){
        BigDecimal data1 = new BigDecimal(total);
        BigDecimal data2 = new BigDecimal(0);
        int result = data1.compareTo(data2);
        if(result==0){
            return 0.00;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        //可以设置精确几位小数
        df.setMaximumFractionDigits(2);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = (num*1.0) / (total*1.0) * 100;
        return Double.parseDouble(df.format(accuracy_num));
    }

    public static Map<String, String> nullToEmpty(Map<String, String> map) {
        Set<String> set = map.keySet();
        if(set != null && !set.isEmpty()) {
            for(String key : set) {
                if(map.get(key) == null) { map.put(key, ""); }
            }
        }
        return map;
    }

    public static Object checkNull(Object obj) {
        Class<? extends Object> clazz = obj.getClass();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 可访问私有变量
            field.setAccessible(true);
            // 获取属性类型
            String type = field.getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if ("class java.lang.String".equals(type)) {
                // 将属性的首字母大写
                String methodName = field.getName().replaceFirst(field.getName().substring(0, 1),
                        field.getName().substring(0, 1).toUpperCase());
                System.out.println(methodName);
                try {
                    Method methodGet = clazz.getMethod("get" + methodName);
                    // 调用getter方法获取属性值
                    String str = (String) methodGet.invoke(obj);
                    if ( StringUtils.isBlank(str)) {
                        // Method methodSet = clazz.getMethod("set" +
                        // methodName, new Class[] { String.class });
                        // methodSet.invoke(o, new Object[] { "" });
                        System.out.println(field.getType()); // class java.lang.String
                        // 如果为null的String类型的属性则重新复制为空字符串
                        field.set(obj, field.getType().getConstructor(field.getType()).newInstance(""));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    //stat use
    public static boolean hasValue(Object o){
        if(o==null||o.toString().trim().equals("")){
            return false;
        }
        return true;
    }
}
