package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsHolidayMapper;
import com.pinting.business.model.BsHoliday;
import com.pinting.business.service.site.HolidayService;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.in.util.MethodRole;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private BsHolidayMapper bsHolidayMapper;
	
	@Override
	@MethodRole("APP")
	public boolean isHolidayTimeList(Date date) {
		List<BsHoliday> bsHolidays = bsHolidayMapper.findNotInHolidayTimeList(DateUtil.formatDateTime(date, DateUtil.DATE_TIME_FORMAT));
		if (CollectionUtils.isEmpty(bsHolidays)) {
			return false;
		}
		return true;
	}

}
