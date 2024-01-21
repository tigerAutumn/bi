package com.pinting.mall.util;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间格式操作类
 * @author Hanley
 *
 */
public class DateUtil {

	/**
	 * 日期格式
	 */
	public static final String PAT_DATE = "yyyy-MM-dd";

	/**
	 * 字符串日期格式
	 */
	public static final String SIMPLE_DATE = "yyyyMMdd";
	/**
	 * 时间格式
	 */
	public static final String PAT_TIME = "HH:mm:ss";
	/**
	 * 字符串时间格式
	 */
	public static final String SIMPLE_TIME = "HHmmss";
	/**
	 * 完整格式
	 */
	public static final String SIMPLE = "yyyyMMddHHmmss";
	/**
	 * 14位24小时格式
	 */
	public static final String PAT_24 = "YYYYMMDD24HHMMSS";

	/**
	 * 时分格式
	 */
	public static final String HHmm = "HH:mm";

	/**
	 * 日期格式
	 */
	public static final String DOT_DATE = "yyyy.MM.dd";
	
	/**
	 * 返回日期格式
	 * @return
	 */
	public static String getDate(Date date) {
		return format(date, PAT_DATE);
	}
	
	/**
	 * 返回日期格式
	 * @return
	 */
	public static String getDotDate(Date date) {
		return format(date, DOT_DATE);
	}
	
	/**
	 * 返回时间格式
	 * @return
	 */
	public static String getTime(Date date) {
		return format(date, PAT_TIME);
	}
	
	/**
	 * 返回完整格式
	 * @return
	 */
	public static String getSimpleTime(Date date) {
		return format(date, SIMPLE_TIME);
	}
	public static String getSimpleDate(Date date) {
		return format(date, SIMPLE_DATE);
	}
	
	/**
	 * 返回完整格式
	 * @return
	 */
	public static String getDateFormatPatter(Date date) {
		return format(date, SIMPLE);
	}
	public static String getSIMPLEDATE(Date date) {
		return format(date, SIMPLE_DATE);
	}
	
	/**
	 * 将字符串格式转换为日期格式
	 * @param strDate
	 * @return
	 * @throws ParseException 
	 * @throws Exception 
	 */
	public static Date strToDate(String strDate) throws ParseException {
		return parse(strDate, PAT_DATE);
	}
	/**
	 * 将字符串格式转换为时间格式
	 * @param strTime
	 * @return
	 * @throws ParseException
	 */
	public static Date strToTime(String strTime) throws ParseException {
		return parse(strTime, PAT_TIME);
	}
	
	/**
	 * 使用参数格式化date为字符串
	 * @param date
	 * @param patter
	 * @return
	 */
	public static String format(Date date, String patter) {
		return null == date ? "" : new SimpleDateFormat(patter).format(date);
	}
	
	/**
	 * 严格按照patter参数格式检验数据
	 * @param obj
	 * @param patter
	 * @return
	 */
	public static boolean checkDate(String obj, String patter) {
		if (obj != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(patter);
				sdf.setLenient(false);
				sdf.parse(obj);
			} catch (ParseException e) { return false; }
			return true;
		}
		return false;
	}
	
	/**
	 * 将字符串转换为预设日期格式
	 * @param strDate
	 * @param patter
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String strDate, String patter) throws ParseException {
		return strDate == null ? null : new SimpleDateFormat(patter).parse(strDate);
	}
	
	/**
	 * 
	 * @Title: compareTo 
	 * @Description: 时间比较(>0表示x晚于y,<0表示x早于y,=0表示x等于y)
	 * @param x
	 * @param y
	 * @return
	 * @throws
	 */
	public static int compareTo(Date x, Date y) {
		Calendar cx = Calendar.getInstance();
		Calendar cy = Calendar.getInstance();
		cx.setTime(x);
		cy.setTime(y);
		return cx.compareTo(cy);
	}
	
	/**
	 * 返回自然月几个月后时间，若传入date =2016-3-31，amount =1，则返回,2016-5-1
	 * @param date 时间
	 * @param amount 增加月数时间
	 * @return
	 */
	public static Date addMonthAfter(Date date, int amount) {
		Date afterMonth = com.pinting.core.util.DateUtil.addMonths(date,amount);
		Calendar beforeC = Calendar.getInstance();
		Calendar afterC = Calendar.getInstance();
		beforeC.setTime(date);
		afterC.setTime(afterMonth);
		int beforeDay = beforeC.get(Calendar.DAY_OF_MONTH)+1;
		
		int afterDay = afterC.get(Calendar.DAY_OF_MONTH)+1;
		
		if(afterDay < beforeDay){
			afterMonth = com.pinting.core.util.DateUtil.addDays(afterMonth, 1);
		}
		return afterMonth;
	}
	
	/**
	 * 获取指定日期所在周的星期一基础上加减天数
	 * @param date 指定日期
	 * @param days 所在周星期一基础上加减天数
	 */
	public static Date getDateByWeek(Date date, int days) {
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, days);
        return cal.getTime();
	}
	
	/**
	 * 返回一天最开始的时间
	 * @param date
	 * @return
	 */
	public static Date getDateBegin(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 返回一天最后的时间
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 返回一天最后的时间
	 * @param date
	 * @return  yyyy-MM-dd 23:59:59
	 */
	public static Date getDateEnd2S(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 000);
		return calendar.getTime();
	}
	/**
	 * 根据传入时间，查询某月的天数
	 * @param date
	 * @return
	 */
	public static int mothDays(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		return day;
	}

	/**
	 * 比较在 HH:mm - HH:mm 时间内
	 * @param startHHmm			开始时间
	 * @param endHHmm			结束时间
	 * @param now				当前时间
	 * @param moreThanOneDay	是否跨天
     * @return
     */
	public static boolean betweenHHMM(String startHHmm, String endHHmm, Date now, boolean moreThanOneDay) {
		SimpleDateFormat sdf = new SimpleDateFormat(HHmm);
		boolean isBetween = false;
		if(moreThanOneDay) {
			// 时间间隔跨天
			try {
				isBetween = isInZone(sdf.parse(startHHmm).getTime(), sdf.parse("23:59").getTime(), getLong(sdf.format(now)));
				if(!isBetween) {
					isBetween = isInZone(sdf.parse("00:00").getTime(), sdf.parse(endHHmm).getTime(), getLong(sdf.format(now)));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			// 时间间隔不跨天
			try {
				isBetween = isInZone(sdf.parse(startHHmm).getTime(), sdf.parse(endHHmm).getTime(), getLong(sdf.format(now)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return isBetween;
	}

	private static boolean isInZone(long tStart,long tEnd,long t) throws ParseException {
		return tStart <= t && t <= tEnd;
	}

	private static long getLong(String timeStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(HHmm);
		return sdf.parse(timeStr).getTime();
	}

	/**
     * 获得当前年份
     * 
     * @return int
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 获得上月月份
     * @return
     */
    public static int getPreMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }
    
	/**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     * 
     * @param date
     *          日期 为null时表示当天
     * @param day
     *          相加(相减)的天数
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

	/**
	 * 获取[startDate, endDate]相隔天数，包头包尾。如：
	 * 	2017年1月1日到2017年1月1日，返回1
	 * 	2017年1月1日到2017年1月2日，返回2
	 *  2017年1月1日到2017年1月3日，返回3
	 * @param startDate	开始时期
	 * @param endDate	截止日期
     * @return
     */
	public static int getBetweenDays(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = null;
		Date secondDate = null;
		firstDate = df.parse(DateUtil.format(startDate, "yyyy-MM-dd"));
		secondDate = df.parse(DateUtil.format(endDate, "yyyy-MM-dd"));
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
		return nDay;
	}

	public static void main(String[] args) throws ParseException {
		/*System.out.println(DateUtil.checkDate("20140620", DateUtil.SIMPLE_DATE));
		Date date = com.pinting.core.util.DateUtil.parseDate("2018-6-3 23:00:00");
		Date date2 = com.pinting.core.util.DateUtil.parseDate("2016-2-25 1:00:00");
		System.out.println(mothDays(date));
		System.out.println(com.pinting.core.util.DateUtil.format(DateUtil.addMonthAfter(date,1)));
		System.out.println(com.pinting.core.util.DateUtil.getDiffeDay(date, date2));*/

		Date date1 = com.pinting.core.util.DateUtil.parseDate("2018-5-28");
		Date date2 = com.pinting.core.util.DateUtil.parseDate("2018-5-29");//发生债权转让
		Date date3 = com.pinting.core.util.DateUtil.parseDate("2018-6-28");
		System.out.println("借款成功日="+date1);
		System.out.println("债权转让日="+date2);
		System.out.println("还款日="+date3);
		
		System.out.println(com.pinting.core.util.DateUtil.getDiffeDay(date2,date1)); //债转，前包括，后不包括
		System.out.println(com.pinting.core.util.DateUtil.getDiffeDay(date3, date2)+1);//还款，前后包括
		
	}
}