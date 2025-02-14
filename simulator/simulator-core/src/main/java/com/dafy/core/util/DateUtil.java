/**
 * @project
 * @author yanwl
 * @version 2.0
 * @date 2015-11-20
 */
package com.dafy.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 日期处理的工具类
 * @author yanwl
 *
 */
public class DateUtil extends DateUtils {
	
	private static final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	
	/**
	 * 日期+时间的格式
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期的格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 小时:分秒的格式
	 */
	public static final String HHMMSS_FORMAT = "HH:mm:ss";
	
	/**
	 * 小时:分的格式
	 */
	public static final String HHMM_FORMAT = "HH:mm";
	
	/**
	 * 将日期字符串解析成"yyyy-MM-dd"格式的Date对象
	 * @param dateTime
	 * @return
	 */
	public static Date parseDate(String dateTime) {
		
		return parse(dateTime, DATE_FORMAT);
	}
	
	/**
	 * 将日期字符串解析成"yyyy-MM-dd HH:mm:ss"格式的Date对象
	 * @param dateTime
	 * @return
	 */
	public static Date parseDateTime(String dateTime) {
		
		return parse(dateTime, DATE_TIME_FORMAT);
	}
	
	/**
	 * 将日期字符串解析成指定格式的Date对象
	 * @param dateTime
	 * @param format
	 * @return
	 */
	public static Date parse(String dateTime, String format) {
		if (StringUtil.isBlank(dateTime)) return null;
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(dateTime);
		} catch (ParseException e) {
			
			throw new RuntimeException("format date error!", e);
		}
	}
	
	/**
	 * 将日期类解析成"yyyy-MM-dd HH:mm:ss"格式的日期字符串
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		
		return formatDateTime(date, DATE_TIME_FORMAT);
	}
	
	/**
	 * 将日期类解析成"yyyy-MM-dd"格式的日期字符串
	 * @param date
	 * @return
	 */
	public static String formatYYYYMMDD(Date date) {
		
		return formatDateTime(date, DATE_FORMAT);
	}
	
	/**
	 * 将日期类解析成指定格式的日期字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDateTime(Date date, String format) {
		if (date == null) return null;
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(date);
	}
	
	/**
	 * 返回当天日期
	 * @param date
	 * @return
	 */
	public static Integer getCurrentDate(Date date) {
		if (date == null) return null;
		
		Calendar cal = Calendar.getInstance();
		int day=cal.get(Calendar.DATE);
		
		return day;
	}
	
	/**
	 * 返回当月的星期几
	 * @param date
	 * @return
	 */
	public static String formatWeekInMonth(Date date) {
		if (date == null) return null;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return dayNames[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	/**
	 * 返回时间差(秒)
	 * @param old
	 * @return
	 */
	public static int getDiffeSeconds(Date old) {
		if (old == null) return 0;
		
		return getDiffeMinute(null, old);
	}
	
	/**
	 * 返回时间差(秒)
	 * @param now
	 * @param old
	 * @return
	 */
	public static int getDiffeSeconds(Date now, Date old) {
		if (old == null) return 0;
		if (now == null) now = new Date();
		
		return (int) ((now.getTime() - old.getTime())/1000);
	}
	
	/**
	 * 返回时间差(分钟)
	 * @param old
	 * @return
	 */
	public static int getDiffeMinute(Date old) {
		if (old == null) return 0;
		
		return getDiffeMinute(null, old);
	}
	
	/**
	 * 返回时间差(分钟)
	 * @param now
	 * @param old
	 * @return
	 */
	public static int getDiffeMinute(Date now, Date old) {
		if (old == null) return 0;
		if (now == null) now = new Date();
		
		return (int) ((now.getTime() - old.getTime())/(1000 * 60));
	}
	
	/**
	 * 返回时间差(小时)
	 * @param old
	 * @return
	 */
	public static int getDiffeHour(Date old) {
		if (old == null) return 0;
		
		return getDiffeHour(null, old);
	}
	
	/**
	 * 返回时间差(小时)
	 * @param now
	 * @param old
	 * @return
	 */
	public static int getDiffeHour(Date now, Date old) {
		if (old == null) return 0;
		if (now == null) now = new Date();
		
		return (int) ((now.getTime() - old.getTime())/(1000 * 60 * 60));
	}
	
	/**
	 * 返回时间差(天)
	 * @param old
	 * @return
	 */
	public static int getDiffeDay(Date old) {
		if (old == null) return 0;
		
		return getDiffeDay(null, old);
	}
	
	/**
	 * 返回时间差(天)
	 * @param now
	 * @param old
	 * @return
	 */
	public static int getDiffeDay(Date now, Date old) {
		if (old == null) return 0;
		if (now == null) now = new Date();
		
		return (int) ((now.getTime() - old.getTime())/(1000 * 60 * 60 * 24));
	}
	
	/**
	 * 返回当月的第一天
	 * @return
	 */
	public static Date firstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		Date d = cal.getTime();
		if(cal != null) {
			cal = null;
		}
		return d;
	}
	
	
	/**
	 * 获取昨天时间（23:59:59）
	 * @return
	 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date d = cal.getTime();
		if(cal != null) {
			cal = null;
		}
		return d;
	}
	
	/**
	 * 获取某天的最后时间（23:59:59）
	 * @return
	 */
	public static Date getLastTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date d = cal.getTime();
		if(cal != null) {
			cal = null;
		}
		return d;
	}
	
	/**
	 * 获取某天的开始时间（00:00:00）
	 * @return
	 */
	public static Date getFirstTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date d = cal.getTime();
		if(cal != null) {
			cal = null;
		}
		return d;
	}
	
	/**
	 * 获取某年月的天数
	 * @return
	 */
	public static int getMaxDay(int year,int month){
		Calendar c = Calendar.getInstance();
		c.set(year, month,1);
		c.add(Calendar.DAY_OF_YEAR, -1);
		int days = c.get(Calendar.DAY_OF_MONTH);
		if(c != null) {
			c = null;
		}
		return days;
	}
	
	/**
	 * 返回某年月第一天日期
	 * @return
	 */
	public static Date firstDayOfYearMonth(int year,int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE,1);
		
		Date d = cal.getTime();
		if(cal != null) {
			cal = null;
		}
		return d;
	}
	
	/**
	 * 返回某年月最后一天日期
	 * @return
	 */
	public static Date lastDayOfYearMonth(int year,int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE,0);
		
		Date d = cal.getTime();
		if(cal != null) {
			cal = null;
		}
		return d;
	}
	
	/**
	 * 返回某年月日的日期
	 * @return
	 */
	public static Date dayOfYearMonth(int year,int month,int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE,day);
		
		Date d = cal.getTime();
		if(cal != null) {
			cal = null;
		}
		return d;
	}
	
	public static void main(String[] args) {
		
		System.out.println(DateUtil.parseDate("20151107"));
	}
}