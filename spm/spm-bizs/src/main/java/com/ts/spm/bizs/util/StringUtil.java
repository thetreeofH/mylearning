package com.ts.spm.bizs.util;

/**
 * @Author luoyu
 * @Date 2020/8/31 14:40
 * @Version 1.0
 */
public class StringUtil {

    public static String getStringValue(Object obj){
        if(obj==null){
            return "";
        }
        return obj.toString().trim();
    }

    public static String getStringValue(Object obj,String defaultValue){
        if(obj==null){
            return defaultValue;
        }
        if("".equals(obj.toString().trim())){
            return defaultValue;
        }
        return obj.toString().trim();
    }

    public static int getStringLength(String str){
        if(str==null){
            return 0;
        }
        return str.getBytes().length;
    }

    public static boolean hasValue(Object o){
        if(o==null||o.toString().trim().equals("")){
            return false;
        }
        return true;
    }

    public static boolean isNumber(String str){
        if(!StringUtil.hasValue(str)){
            return false;
        }
        String reg="^[-|+]?\\d*([.]\\d+)?$";
        return str.matches(reg);
    }


    public static void main(String[] args) {
        String str="0123";
        System.out.println(isNumber(str));
    }

    public static String getDisplayValue(String obj){
        String tmp;
        if(obj==null || obj.length() == 0){
            return "";
        }
        if(obj.length() > 10){
            tmp = obj.substring(0, 7) + "...";
            return tmp;
        }
        return obj;
    }

    /**
     * 若字符串为null，或者为"",或者为"null" 返回true 否则，返回false
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj){
        return null == obj
                || "".equals(obj.toString().trim())
                || "null".endsWith(obj.toString().trim());
    }
}
