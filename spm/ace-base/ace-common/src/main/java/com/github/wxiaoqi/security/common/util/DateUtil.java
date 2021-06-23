package com.github.wxiaoqi.security.common.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Description: 日期时间工具类
 * @author
 * @date 2018-07-05
 * @version V1.0
 * @Copyright:
 */
public class DateUtil {

	public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddhhmmssSSS";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddhhmmss";

	public static String getCurrentDate() {
		String formatPattern_Short = "yyyyMMddhhmmss";
		SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
		return format.format(new Date());
	}
	public static String getCurrentDay() {
		String formatPattern_Short = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
		return format.format(new Date());
	}

	public static String getSeqString() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd G
		return fm.format(new Date());
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static void main(String[] args) {
		//System.out.println(DateUtil.date2Str(new Date(), "yyyyMMddHHmmssSSS"));
		String str="2021-05-31 17:45:38.698";
		System.out.println(str.indexOf(".")!=-1);
	}
	/**
	 * 获取当前时间，格式为 yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String getCurrentTimeStr(String format) {
		format = StringUtils.isBlank(format) ? FORMAT_YYYY_MM_DD_HH_MM_SS : format;
		Date now = new Date();
		return date2Str(now, format);
	}

	public static String date2Str(Date date) {
		return date2Str(date, FORMAT_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 时间转换成 Date 类型
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if ((format == null) || format.equals("")) {
			format = FORMAT_YYYY_MM_DD_HH_MM_SS;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date != null) {
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 获取批量付款预约时间
	 * 
	 * @return
	 */
	public static String getRevTime() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		String dateString = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDDHHMMSS).format(cal.getTime());
		System.out.println(dateString);
		return dateString;
	}

	/**
	 * 时间比较
	 * 
	 * @param date1
	 * @param date2
	 * @return DATE1>DATE2返回1，DATE1<DATE2返回-1,等于返回0
	 */
	public static int compareDate(String date1, String date2, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 把给定的时间减掉给定的分钟数
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date minusDateByMinute(Date date, int minute) {
		Date newDate = new Date(date.getTime() - (minute * 60 * 1000));
		return newDate;
	}

	public static Date stringToDate(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public static Date stringToDate2(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * function:获取当前时间，环比临近的N周
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]上周开始时间 Date[2]上周结束时间
	 *                                           Date[1]本周开始时间 Date[3]本周结束时间)
	 */
	public static Date[] getNearNWeek(int N) {
		// 获取当前
		Calendar cStart = Calendar.getInstance(Locale.CHINESE);
		Calendar cEnd = Calendar.getInstance(Locale.CHINESE);
		cEnd.add(Calendar.WEEK_OF_YEAR, 1);// 增加一个星期，才是我们中国人理解的本周日的日期
		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			cStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
			cStart.set(Calendar.HOUR_OF_DAY, 0);
			cStart.set(Calendar.MINUTE, 0);
			cStart.set(Calendar.SECOND, 0);
			cStart.set(Calendar.MILLISECOND, 0);
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.DAY_OF_YEAR, -7); // 向前7天

			cEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
			cEnd.set(Calendar.HOUR_OF_DAY, 23);
			cEnd.set(Calendar.MINUTE, 59);
			cEnd.set(Calendar.SECOND, 59);
			cEnd.set(Calendar.MILLISECOND, 0);
			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.DAY_OF_YEAR, -7); // //向前7天
		}

		return result;
	}

	/**
	 * function:获取当前时间，环比临近的N月
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]上月开始时间 Date[2]上月结束时间
	 *                                           Date[1]本月开始时间 Date[3]本月结束时间)
	 */
	public static Date[] getNearNMonth(int N) {
		// 获取当前
		Calendar cStart = Calendar.getInstance(Locale.CHINESE);
		Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			cStart.set(Calendar.DAY_OF_MONTH, 1); // 获取本月的第一天
			cStart.set(Calendar.HOUR_OF_DAY, 0);
			cStart.set(Calendar.MINUTE, 0);
			cStart.set(Calendar.SECOND, 0);
			cStart.set(Calendar.MILLISECOND, 0);
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.MONTH, -1); // 上一个月

			cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH)); // 获取本月的最后一天
			cEnd.set(Calendar.HOUR_OF_DAY, 23);
			cEnd.set(Calendar.MINUTE, 59);
			cEnd.set(Calendar.SECOND, 59);
			cEnd.set(Calendar.MILLISECOND, 0);
			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.MONTH, -1); // 上一个月
		}

		return result;
	}

	/**
	 * function:获取当前时间，环比临近的N个季度
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]上季度开始时间 Date[2]上季度结束时间
	 *                                           Date[1]本季度开始时间 Date[3]本季度结束时间)
	 */
	public static Date[] getNearNQuater(int N) {
		// 获取当前
		Calendar cStart = Calendar.getInstance(Locale.CHINESE);
		Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			cStart.set(Calendar.MONTH, getQuarterInMonth(cStart.get(Calendar.MONTH) + 1, true));
			cStart.set(Calendar.DAY_OF_MONTH, 1); // 设置为季度的第一天
			cStart.set(Calendar.HOUR_OF_DAY, 0);
			cStart.set(Calendar.MINUTE, 0);
			cStart.set(Calendar.SECOND, 0);
			cStart.set(Calendar.MILLISECOND, 0);
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.MONTH, -3); // 上一个季度

			cEnd.set(Calendar.MONTH, getQuarterInMonth(cEnd.get(Calendar.MONTH) + 1, false));
			cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH)); // 获取本月的最后一天
			cEnd.set(Calendar.HOUR_OF_DAY, 23);
			cEnd.set(Calendar.MINUTE, 59);
			cEnd.set(Calendar.SECOND, 59);
			cEnd.set(Calendar.MILLISECOND, 0);
			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.MONTH, -3); // 上一个月
		}

		return result;
	}

	/**
	 * 获取在季度中的月份
	 * 
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
			return months[0] - 1;
		else if (month >= 4 && month <= 6)
			return months[1] - 1;
		else if (month >= 7 && month <= 9)
			return months[2] - 1;
		else
			return months[3] - 1;
	}

	/**
	 * function:获取当前时间，同比临近的N周
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]去年本周开始时间 Date[2]去年本周结束时间
	 *                                           Date[1]今年本周开始时间 Date[3]今年本周结束时间)
	 * @return
	 */
	public static Date[] getYear2YearNWeek(int N) {
		// 获取当前
		Calendar cStart = Calendar.getInstance(Locale.CHINESE);
		Calendar cEnd = Calendar.getInstance(Locale.CHINESE);
		cEnd.add(Calendar.WEEK_OF_YEAR, 1);// 增加一个星期，才是我们中国人理解的本周日的日期
		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			cStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
			cStart.set(Calendar.HOUR_OF_DAY, 0);
			cStart.set(Calendar.MINUTE, 0);
			cStart.set(Calendar.SECOND, 0);
			cStart.set(Calendar.MILLISECOND, 0);
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.YEAR, -1); // 向前1年

			cEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
			cEnd.set(Calendar.HOUR_OF_DAY, 23);
			cEnd.set(Calendar.MINUTE, 59);
			cEnd.set(Calendar.SECOND, 59);
			cEnd.set(Calendar.MILLISECOND, 0);
			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.YEAR, -1); // 向前1年
		}

		return result;
	}

	/**
	 * function:获取当前时间，同比临近的N月
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]去年本月开始时间 Date[2]去年本月结束时间
	 *                                           Date[1]今年本月开始时间 Date[3]今年本月结束时间)
	 */
	public static Date[] getYear2YearNMonth(int N) {
		// 获取当前
		Calendar cStart = Calendar.getInstance(Locale.CHINESE);
		Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			cStart.set(Calendar.DAY_OF_MONTH, 1); // 获取本月的第一天
			cStart.set(Calendar.HOUR_OF_DAY, 0);
			cStart.set(Calendar.MINUTE, 0);
			cStart.set(Calendar.SECOND, 0);
			cStart.set(Calendar.MILLISECOND, 0);
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.YEAR, -1); // 向前1年

			cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH)); // 获取本月的最后一天
			cEnd.set(Calendar.HOUR_OF_DAY, 23);
			cEnd.set(Calendar.MINUTE, 59);
			cEnd.set(Calendar.SECOND, 59);
			cEnd.set(Calendar.MILLISECOND, 0);
			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.YEAR, -1); // 向前1年
		}

		return result;
	}

	/**
	 * function:获取当前时间，同比临近的N季度
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]去年本季度开始时间 Date[2]去年本季度结束时间
	 *                                           Date[1]今年本季度开始时间 Date[3]今年本季度结束时间)
	 */
	public static Date[] getYear2YearNQuater(int N) {
		// 获取当前
		Calendar cStart = Calendar.getInstance(Locale.CHINESE);
		Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			cStart.set(Calendar.MONTH, getQuarterInMonth(cStart.get(Calendar.MONTH) + 1, true));
			cStart.set(Calendar.DAY_OF_MONTH, 1); // 设置为季度的第一天
			cStart.set(Calendar.HOUR_OF_DAY, 0);
			cStart.set(Calendar.MINUTE, 0);
			cStart.set(Calendar.SECOND, 0);
			cStart.set(Calendar.MILLISECOND, 0);
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.YEAR, -1); // 向前1年

			cEnd.set(Calendar.MONTH, getQuarterInMonth(cStart.get(Calendar.MONTH) + 1, false));
			cEnd.set(Calendar.DAY_OF_MONTH, cEnd.getActualMaximum(Calendar.DAY_OF_MONTH)); // 获取本月的最后一天
			cEnd.set(Calendar.HOUR_OF_DAY, 23);
			cEnd.set(Calendar.MINUTE, 59);
			cEnd.set(Calendar.SECOND, 59);
			cEnd.set(Calendar.MILLISECOND, 0);
			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.YEAR, -1); // 向前1年
		}

		return result;
	}

	/**
	 * function:获取当前时间，同比/环比临近的N年
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]去年开始时间 Date[2]去年结束时间
	 *                                           Date[1]今年开始时间 Date[3]今年结束时间)
	 */
	public static Date[] getNearNYears(int N) {
		// 获取当前
		Calendar cStart = Calendar.getInstance(Locale.CHINESE);
		Calendar cEnd = Calendar.getInstance(Locale.CHINESE);

		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			cStart.set(Calendar.DAY_OF_YEAR, 1); // 设置为第一天
			cStart.set(Calendar.HOUR_OF_DAY, 0);
			cStart.set(Calendar.MINUTE, 0);
			cStart.set(Calendar.SECOND, 0);
			cStart.set(Calendar.MILLISECOND, 0);
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.YEAR, -1); // 向前1年

			cEnd.set(Calendar.DAY_OF_YEAR, cEnd.getActualMaximum(Calendar.DAY_OF_YEAR)); // 获取本年最后一天
			cEnd.set(Calendar.HOUR_OF_DAY, 23);
			cEnd.set(Calendar.MINUTE, 59);
			cEnd.set(Calendar.SECOND, 59);
			cEnd.set(Calendar.MILLISECOND, 0);
			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.YEAR, -1); // 向前1年
		}

		return result;
	}

	/**
	 * 两个时间相关的时间
	 * 
	 * @return
	 */
	public static long dayInterval(Date start, Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);

		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(end);

		long milis = calEnd.getTimeInMillis() - cal.getTimeInMillis();

		long days = milis / (1000 * 3600 * 24);

		return days;
	}

	/**
	 * 字符串类型时间按照一定规则转换为date型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return d;
	}

	/**
	 * function:获取start--stop时间段，环比临近的时间段 ------（时间倒叙）-------
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最晚到时间最早的顺序排列。比如N=2则
	 *                                           Date[0]本周期开始时间 Date[2]本个周期结束时间
	 *                                           Date[1]上周期开始时间 Date[3]上周期结束时间)
	 */
	public static Date[] getNearNTime(int N, Date start, Date end) {
		// 首先获取时间间隔
		int days = (int) dayInterval(start, end) + 1;

		// 将日历设置为参数时间
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(start);
		cStart.set(Calendar.HOUR_OF_DAY, 0);
		cStart.set(Calendar.MINUTE, 0);
		cStart.set(Calendar.SECOND, 0);
		cStart.set(Calendar.MILLISECOND, 0);

		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(end);
		cEnd.set(Calendar.HOUR_OF_DAY, 23);
		cEnd.set(Calendar.MINUTE, 59);
		cEnd.set(Calendar.SECOND, 59);
		cEnd.set(Calendar.MILLISECOND, 0);

		// 返回值
		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.DAY_OF_YEAR, -1 * days); // 向前一个周期

			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.DAY_OF_YEAR, -1 * days); // 向前一个周期
		}

		return result;
	}

	/**
	 * function:获取start--stop时间段，同比临近的时间段
	 * 
	 * @return(供返回2*N个数据，前N个是开始时间，后N个是结束时间，以此组队。 按照时间最早到时间最晚的顺序排列。比如N=2则
	 *                                           Date[0]上个周期开始时间 Date[2]上个周期结束时间
	 *                                           Date[1]本周期开始时间 Date[3]本周期结束时间)
	 */
	public static Date[] getNextNTime(int N, Date start, Date end) {
		// 首先获取时间间隔
		int days = (int) dayInterval(start, end);

		// 将日历设置为参数时间
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(start);
		cStart.set(Calendar.HOUR_OF_DAY, 0);
		cStart.set(Calendar.MINUTE, 0);
		cStart.set(Calendar.SECOND, 0);
		cStart.set(Calendar.MILLISECOND, 0);

		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(end);
		cEnd.set(Calendar.HOUR_OF_DAY, 23);
		cEnd.set(Calendar.MINUTE, 59);
		cEnd.set(Calendar.SECOND, 59);
		cEnd.set(Calendar.MILLISECOND, 0);

		// 返回值
		Date[] result = new Date[2 * N];

		// 计算开始时间
		for (int i = 0; i < N; i++) {
			result[N - 1 - i] = cStart.getTime();
			cStart.add(Calendar.YEAR, -1); // 向前1年

			result[2 * N - 1 - i] = cEnd.getTime();
			cEnd.add(Calendar.YEAR, -1); // 向前1年
		}

		return result;
	}

	/**
	 * 将字符串日期加一天
	 * 
	 * @param s ：为时间字符串（例如：“2019-05-30”）
	 * @param n ：如果日期加一天，那么n传1
	 * @return
	 */
	public static String addDay(String s, int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 构造格式化日期的格式
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cd.add(Calendar.DATE, n); // 当n=1代表是增加一天
		// cd.add(Calendar.YEAR, 1);//增加一年
		// cd.add(Calendar.DATE, -10);//减10天
		// cd.add(Calendar.MONTH, n);//n=1代表增加一个月
		return sdf.format(cd.getTime()); // format(Date date)方法：将制定的日期对象格式，化为指定格式的字符串（例如：“yyyy-MM-dd”）
	}

	/**
	 * 将字符串日期加一天
	 * 
	 * @param s ：为时间字符串（例如：“2019-05-30”）
	 * @param n ：如果日期加一天，那么n传1
	 * @return
	 */
	public static String deleteDay(String s, int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 构造格式化日期的格式
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		cd.add(Calendar.DATE, -n);//减10天
		return sdf.format(cd.getTime()); // format(Date date)方法：将制定的日期对象格式，化为指定格式的字符串（例如：“yyyy-MM-dd”）
	}
	
	/**
	 * 获取指定日期所在月份开始的时间
	 * 时间格式yyyyMMdd
	 * @param date 指定日期
	 * @return
	 */
	public static String getMonthBegin(Date date) {
		SimpleDateFormat aDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		//设置为1号,当前日期既为本月第一天  
		c.set(Calendar.DAY_OF_MONTH, 1);
		//将小时至0  
		c.set(Calendar.HOUR_OF_DAY, 0);  
		//将分钟至0  
		c.set(Calendar.MINUTE, 0);  
		//将秒至0  
		c.set(Calendar.SECOND,0);  
		//将毫秒至0  
		c.set(Calendar.MILLISECOND, 0);  
		// 获取本月第一天的时间
		return aDateFormat.format(c.getTime());  
	}
	
	/**
	 * 获取指定日期所在月份结束的时间
	 * 时间格式yyyyMMdd
	 * @param date 指定日期
	 * @return
	 */
	public static String getMonthEnd(Date date) {
		SimpleDateFormat aDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();  
		c.setTime(date);
		
		//设置为当月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
		//将小时至23
		c.set(Calendar.HOUR_OF_DAY, 23);  
		//将分钟至59
		c.set(Calendar.MINUTE, 59);  
		//将秒至59
		c.set(Calendar.SECOND,59);  
		//将毫秒至999
		c.set(Calendar.MILLISECOND, 999);  
		// 获取本月最后一天的时间 
		return aDateFormat.format(c.getTime());
	}
	public static int daysxiangcha(String dateStr1, String dateStr2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateStr1 = sdf.format(sdf2.parse(dateStr1));
			dateStr2 = sdf.format(sdf2.parse(dateStr2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int year1 = Integer.parseInt(dateStr1.substring(0, 4));
		int month1 = Integer.parseInt(dateStr1.substring(4, 6));
		int day1 = Integer.parseInt(dateStr1.substring(6, 8));
		int year2 = Integer.parseInt(dateStr2.substring(0, 4));
		int month2 = Integer.parseInt(dateStr2.substring(4, 6));
		int day2 = Integer.parseInt(dateStr2.substring(6, 8));
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, year1);
		c1.set(Calendar.MONTH, month1 - 1);
		c1.set(Calendar.DAY_OF_MONTH, day1);
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, year2);
		c2.set(Calendar.MONTH, month2 - 1);
		c2.set(Calendar.DAY_OF_MONTH, day2);
		long mills = c1.getTimeInMillis() > c2.getTimeInMillis() ? c1.getTimeInMillis() - c2.getTimeInMillis()
				: c2.getTimeInMillis() - c1.getTimeInMillis();
		return (int) (mills / 1000 / 3600 / 24);
	}
}
