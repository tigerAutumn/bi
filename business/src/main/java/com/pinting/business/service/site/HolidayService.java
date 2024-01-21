package com.pinting.business.service.site;

import java.util.Date;


public interface HolidayService {
	/**
	 * 查询某时间是否在节假日 
	 * 开始时间的前一天（提前一天+半小时   24*60 + 30 =1470  ）和
	 * 结束时间的前一天（提前一天+半小时   24*60  =1440  ）时间段内
	 * @param date  时间
	 * @return  true 在假期内  false不在时间段内
	 */
	public boolean isHolidayTimeList(Date date);
}
