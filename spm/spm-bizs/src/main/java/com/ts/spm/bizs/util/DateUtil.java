package com.ts.spm.bizs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/4 14:51
 * @Version 1.0
 */
public class DateUtil {

    /**
     * function:获取当前时间，环比临近的N周
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则   Date[0]上周开始时间  Date[2]上周结束时间
     *                              Date[1]本周开始时间  Date[3]本周结束时间)
     */
    public static Date[] getNearNWeek(int N){
        //获取当前
        Calendar cStart = Calendar.getInstance(Locale.CHINESE);
        Calendar cEnd = Calendar.getInstance(Locale.CHINESE);
        cEnd.add(Calendar.WEEK_OF_YEAR, 1);// 增加一个星期，才是我们中国人理解的本周日的日期
        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            cStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  //获取本周一的日期
            cStart.set(Calendar.HOUR_OF_DAY, 0);
            cStart.set(Calendar.MINUTE, 0);
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.DAY_OF_YEAR, -7);  //向前7天

            cEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
            cEnd.set(Calendar.HOUR_OF_DAY, 23);
            cEnd.set(Calendar.MINUTE, 59);
            cEnd.set(Calendar.SECOND, 59);
            cEnd.set(Calendar.MILLISECOND, 0);
            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.DAY_OF_YEAR, -7); // //向前7天
        }

        return result;
    }

    /**
     * function:获取当前时间，环比临近的N月
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则   Date[0]上月开始时间  Date[2]上月结束时间
     *                              Date[1]本月开始时间  Date[3]本月结束时间)
     */
    public static Date[] getNearNMonth(int N){
        //获取当前
        Calendar cStart = Calendar.getInstance(Locale.CHINESE);
        Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            cStart.set(Calendar.DAY_OF_MONTH, 1);  //获取本月的第一天
            cStart.set(Calendar.HOUR_OF_DAY, 0);
            cStart.set(Calendar.MINUTE, 0);
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.MONTH, -1);    //上一个月

            cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH));  //获取本月的最后一天
            cEnd.set(Calendar.HOUR_OF_DAY, 23);
            cEnd.set(Calendar.MINUTE, 59);
            cEnd.set(Calendar.SECOND, 59);
            cEnd.set(Calendar.MILLISECOND, 0);
            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.MONTH, -1); //上一个月
        }

        return result;
    }

    /**
     * function:获取当前时间，环比临近的N个季度
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则   Date[0]上季度开始时间  Date[2]上季度结束时间
     *                              Date[1]本季度开始时间  Date[3]本季度结束时间)
     */
    public static Date[] getNearNQuater(int N){
        //获取当前
        Calendar cStart = Calendar.getInstance(Locale.CHINESE);
        Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            cStart.set(Calendar.MONTH, getQuarterInMonth(cStart.get(Calendar.MONTH)+1, true));
            cStart.set(Calendar.DAY_OF_MONTH, 1);  //设置为季度的第一天
            cStart.set(Calendar.HOUR_OF_DAY, 0);
            cStart.set(Calendar.MINUTE, 0);
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.MONTH, -3);    //上一个季度

            cEnd.set(Calendar.MONTH, getQuarterInMonth(cEnd.get(Calendar.MONTH)+1, false));
            cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH));  //获取本月的最后一天
            cEnd.set(Calendar.HOUR_OF_DAY, 23);
            cEnd.set(Calendar.MINUTE, 59);
            cEnd.set(Calendar.SECOND, 59);
            cEnd.set(Calendar.MILLISECOND, 0);
            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.MONTH, -3); //上一个月
        }

        return result;
    }

    /**
     * 获取在季度中的月份
     * @param month
     * @param isQuarterStart
     * @return
     */
    private static int getQuarterInMonth(int month, boolean isQuarterStart) {
        int months[] = { 1, 4, 7, 10 };
        if (!isQuarterStart) {
            months = new int[] { 3, 6, 9, 12 };
        }
        if (month >= 1 && month <= 3)
            return months[0]-1;
        else if (month >= 4 && month <= 6)
            return months[1]-1;
        else if (month >= 7 && month <= 9)
            return months[2]-1;
        else
            return months[3]-1;
    }

    /**
     * function:获取当前时间，同比临近的N周
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则  Date[0]去年本周开始时间   Date[2]去年本周结束时间
     * 							    Date[1]今年本周开始时间  Date[3]今年本周结束时间)
     * @return
     */
    public static Date[] getYear2YearNWeek(int N){
        //获取当前
        Calendar cStart = Calendar.getInstance(Locale.CHINESE);
        Calendar cEnd = Calendar.getInstance(Locale.CHINESE);
        cEnd.add(Calendar.WEEK_OF_YEAR, 1);// 增加一个星期，才是我们中国人理解的本周日的日期
        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            cStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  //获取本周一的日期
            cStart.set(Calendar.HOUR_OF_DAY, 0);
            cStart.set(Calendar.MINUTE, 0);
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.YEAR, -1);  //向前1年

            cEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
            cEnd.set(Calendar.HOUR_OF_DAY, 23);
            cEnd.set(Calendar.MINUTE, 59);
            cEnd.set(Calendar.SECOND, 59);
            cEnd.set(Calendar.MILLISECOND, 0);
            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.YEAR, -1); // 向前1年
        }

        return result;
    }

    /**
     * function:获取当前时间，同比临近的N月
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则  Date[0]去年本月开始时间   Date[2]去年本月结束时间
     * 							    Date[1]今年本月开始时间  Date[3]今年本月结束时间)
     */
    public static Date[] getYear2YearNMonth(int N){
        //获取当前
        Calendar cStart = Calendar.getInstance(Locale.CHINESE);
        Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            cStart.set(Calendar.DAY_OF_MONTH, 1);  //获取本月的第一天
            cStart.set(Calendar.HOUR_OF_DAY, 0);
            cStart.set(Calendar.MINUTE, 0);
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.YEAR, -1);  //向前1年

            cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH));  //获取本月的最后一天
            cEnd.set(Calendar.HOUR_OF_DAY, 23);
            cEnd.set(Calendar.MINUTE, 59);
            cEnd.set(Calendar.SECOND, 59);
            cEnd.set(Calendar.MILLISECOND, 0);
            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.YEAR, -1);  //向前1年
        }

        return result;
    }

    /**
     * function:获取当前时间，同比临近的N季度
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则  Date[0]去年本季度开始时间   Date[2]去年本季度结束时间
     * 							    Date[1]今年本季度开始时间  Date[3]今年本季度结束时间)
     */
    public static Date[] getYear2YearNQuater(int N){
        //获取当前
        Calendar cStart = Calendar.getInstance(Locale.CHINESE);
        Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            cStart.set(Calendar.MONTH, getQuarterInMonth(cStart.get(Calendar.MONTH)+1, true));
            cStart.set(Calendar.DAY_OF_MONTH, 1);  //设置为季度的第一天
            cStart.set(Calendar.HOUR_OF_DAY, 0);
            cStart.set(Calendar.MINUTE, 0);
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.YEAR, -1);  //向前1年

            cEnd.set(Calendar.MONTH, getQuarterInMonth(cStart.get(Calendar.MONTH)+1, false));
            cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH));  //获取本月的最后一天
            cEnd.set(Calendar.HOUR_OF_DAY, 23);
            cEnd.set(Calendar.MINUTE, 59);
            cEnd.set(Calendar.SECOND, 59);
            cEnd.set(Calendar.MILLISECOND, 0);
            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.YEAR, -1);  //向前1年
        }

        return result;
    }



    /**
     * function:获取当前时间，同比/环比临近的N年
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则  Date[0]去年开始时间   Date[2]去年结束时间
     * 							    Date[1]今年开始时间  Date[3]今年结束时间)
     */
    public static Date[] getNearNYears(int N){
        //获取当前
        Calendar cStart = Calendar.getInstance(Locale.CHINESE);
        Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            cStart.set(Calendar.DAY_OF_YEAR, 1);  //设置为第一天
            cStart.set(Calendar.HOUR_OF_DAY, 0);
            cStart.set(Calendar.MINUTE, 0);
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.YEAR, -1);  //向前1年

            cEnd.set(Calendar.DAY_OF_YEAR, cEnd.getActualMaximum(Calendar.DAY_OF_YEAR));  //获取本年最后一天
            cEnd.set(Calendar.HOUR_OF_DAY, 23);
            cEnd.set(Calendar.MINUTE, 59);
            cEnd.set(Calendar.SECOND, 59);
            cEnd.set(Calendar.MILLISECOND, 0);
            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.YEAR, -1);  //向前1年
        }

        return result;
    }


    /**
     * 两个时间相关的时间
     * @return
     */
    public static long dayInterval(Date start, Date end){
        Calendar cal=Calendar.getInstance();
        cal.setTime(start);

        Calendar calEnd=Calendar.getInstance();
        calEnd.setTime(end);

        long milis = calEnd.getTimeInMillis() - cal.getTimeInMillis();

        long days = milis / (1000 * 3600 * 24);

        return days;
    }

    /**
     * 字符串类型时间按照一定规则转换为date型
     * @param date
     * @param format
     * @return
     */
    public static Date parse(String date,String format){
        DateFormat df=new SimpleDateFormat(format);
        Date d=null;
        try {
            d= df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return d;
    }

    /**
     * function:获取start--stop时间段，环比临近的时间段 ------（时间倒叙）-------
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最晚到时间最早的顺序排列。比如N=2则   Date[0]本周期开始时间  Date[2]本个周期结束时间
     *                              Date[1]上周期开始时间  Date[3]上周期结束时间)
     */
    public static Date[] getNearNTime(int N, Date start, Date end){
        //首先获取时间间隔
        int days = (int)dayInterval(start, end) + 1;

        //将日历设置为参数时间
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(start);
        cStart.set(Calendar.HOUR_OF_DAY, 0);
        cStart.set(Calendar.MINUTE, 0);
        cStart.set(Calendar.SECOND, 0);
        cStart.set(Calendar.MILLISECOND, 0);

        Calendar cEnd=Calendar.getInstance();
        cEnd.setTime(end);
        cEnd.set(Calendar.HOUR_OF_DAY, 23);
        cEnd.set(Calendar.MINUTE, 59);
        cEnd.set(Calendar.SECOND, 59);
        cEnd.set(Calendar.MILLISECOND, 0);

        //返回值
        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.DAY_OF_YEAR, -1 * days);  //向前一个周期

            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.DAY_OF_YEAR, -1 * days);    //向前一个周期
        }

        return result;
    }

    /**
     * function:获取start--stop时间段，同比临近的时间段
     * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。
     * 按照时间最早到时间最晚的顺序排列。比如N=2则   Date[0]上个周期开始时间  Date[2]上个周期结束时间
     *                              Date[1]本周期开始时间      Date[3]本周期结束时间)
     */
    public static Date[] getNextNTime(int N, Date start, Date end){
        //首先获取时间间隔
        int days = (int)dayInterval(start, end);

        //将日历设置为参数时间
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(start);
        cStart.set(Calendar.HOUR_OF_DAY, 0);
        cStart.set(Calendar.MINUTE, 0);
        cStart.set(Calendar.SECOND, 0);
        cStart.set(Calendar.MILLISECOND, 0);

        Calendar cEnd=Calendar.getInstance();
        cEnd.setTime(end);
        cEnd.set(Calendar.HOUR_OF_DAY, 23);
        cEnd.set(Calendar.MINUTE, 59);
        cEnd.set(Calendar.SECOND, 59);
        cEnd.set(Calendar.MILLISECOND, 0);

        //返回值
        Date[] result = new Date[2*N];

        //计算开始时间
        for (int i=0; i<N; i++){
            result[N-1-i] = cStart.getTime();
            cStart.add(Calendar.YEAR, -1);  //向前1年

            result[2*N-1-i] = cEnd.getTime();
            cEnd.add(Calendar.YEAR, -1);  //向前1年
        }

        return result;
    }

    public static List<String> findDaysStr(String begintTime, String endTime) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Date dBegin = null;
         Date dEnd = null;
         try {
                   dBegin = sdf.parse(begintTime);
                   dEnd = sdf.parse(endTime);
               } catch (ParseException e) {
                   e.printStackTrace();
          }
          List<String> daysStrList = new ArrayList<String>();
          daysStrList.add(sdf.format(dBegin));
          Calendar calBegin = Calendar.getInstance();
          calBegin.setTime(dBegin);
          Calendar calEnd = Calendar.getInstance();
          calEnd.setTime(dEnd);
          while (dEnd.after(calBegin.getTime())) {
                  calBegin.add(Calendar.DAY_OF_MONTH, 1);
                  String dayStr = sdf.format(calBegin.getTime());
                  daysStrList.add(dayStr);
              }
          return daysStrList;
    }


    /**
     * 计算两个日期之间的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);

        if (year1 != year2)  //不同年
        {
            int timeDistance = 0;
            for (int i = year1 ; i < year2 ;i++){ //闰年
                if (i%4==0 && i%100!=0||i%400==0){
                    timeDistance += 366;
                }else { // 不是闰年
                    timeDistance += 365;
                }
            }
            return  timeDistance + (day2-day1);
        }else{// 同年
            return day2-day1;
        }

    }

    /**
     * 返回日时分秒
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days){
            return days + "天"+hours+"小时"+minutes+"分"+second+"秒";
        }else {
            return hours+"小时"+minutes+"分"+second+"秒";
        }
    }

    public static Long dateDiff(String startTime, String endTime,
                                String format, String str) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 24 * 60 * 60;// 一天的秒数
        long nh = 60 * 60;// 一小时的秒数
        long nm =  60;// 一分钟的秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
//            day = diff / nd;// 计算差多少天
//            hour = diff % nd / nh + day * 24;// 计算差多少小时
//            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
//            sec = diff % nd % nh % nm / ns;// 计算差多少秒

            day = diff / nd;// 计算差多少天
            hour = diff/ nh; // 计算差多少小时
            min = diff/ nm ;// 计算差多少分钟
            sec = diff / ns;// 计算差多少秒
            sec = diff / ns;// 计算差多少秒

            if (str.equalsIgnoreCase("day")) {
                return day;
            }
            if (str.equalsIgnoreCase("hour")) {
                return hour;
            }
            if (str.equalsIgnoreCase("min")) {
                return min;
            }
            if (str.equalsIgnoreCase("sec")) {
                return sec;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (str.equalsIgnoreCase("h")) {
            return hour;
        } else {
            return min;
        }
    }

    public static void main(String[] args) throws Exception{
        Long date=dateDiff("2020-12-16 06:36:09.0", "2020-12-16 14:02:36.0","yyyy-MM-dd HH:mm:ss","sec");
        System.out.println("sec=" +date);

        String time=secondToTime(date);
        System.out.println(time);
    }

}
