package com.pinting.core.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil extends DateUtils {
	private static final String[] dayNames = { "星期日", "星期一", "星期二", "星期三",
			"星期四", "星期五", "星期六" };
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String HHMMSS_FORMAT = "HH:mm:ss";
	public static final String HHMM_FORMAT = "HH:mm";


	/**
	 * 返回一天最开始的时间
	 * @param date
	 * @return
	 */
	public static Date getDateBegin(Date date) throws ParseException {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}

	/**
	 * 返回一天最后的时间
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date date) throws ParseException {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND,999);
		return calendar.getTime();
	}

	public static Date parseDate(String dateTime) {
		return parse(dateTime, DATE_FORMAT);
	}

	public static Date parseDateTime(String dateTime) {
		return parse(dateTime, DATE_TIME_FORMAT);
	}

	public static Date parse(String dateTime, String format) {
		if (StringUtil.isBlank(dateTime))
			return null;

		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(dateTime);
		} catch (ParseException e) {
			throw new RuntimeException("format date error!", e);
		}
	}

	public static String format(Date date) {
		return formatDateTime(date, DATE_TIME_FORMAT);
	}

	public static String formatYYYYMMDD(Date date) {
		return formatDateTime(date, DATE_FORMAT);
	}

	public static String formatDateTime(Date date, String format) {
		if (date == null)
			return null;

		DateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(date);
	}

	public static String formatWeekInMonth(Date date) {
		if (date == null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return dayNames[(cal.get(7) - 1)];
	}

	public static int getDiffeSeconds(Date old) {
		if (old == null)
			return 0;

		return getDiffeMinute(null, old);
	}

	public static int getDiffeSeconds(Date now, Date old) {
		if (old == null)
			return 0;
		if (now == null)
			now = new Date();

		return (int) ((now.getTime() - old.getTime()) / 1000L);
	}

	public static int getDiffeMinute(Date old) {
		if (old == null)
			return 0;

		return getDiffeMinute(null, old);
	}

	public static int getDiffeMinute(Date now, Date old) {
		if (old == null)
			return 0;
		if (now == null)
			now = new Date();

		return (int) ((now.getTime() - old.getTime()) / 60000L);
	}

	public static int getDiffeHour(Date old) {
		if (old == null)
			return 0;

		return getDiffeMinute(null, old);
	}

	public static int getDiffeHour(Date now, Date old) {
		if (old == null)
			return 0;
		if (now == null)
			now = new Date();

		return (int) ((now.getTime() - old.getTime()) / 3600000L);
	}

	public static int getDiffeDay(Date old) {
		if (old == null)
			return 0;

		return getDiffeDay(null, old);
	}

	public static int getDiffeDay(Date now, Date old) {
		if (old == null)
			return 0;
		if (now == null)
			now = new Date();

		return (int) ((now.getTime() - old.getTime()) / 86400000L);
	}

	public static Date firstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(5, 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);

		return cal.getTime();
	}
	
	public static Long getLongTime(Date date){
		return date.getTime();
	}

}
