package com.pinting.business.dayend.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.service.manage.BsSysBalanceDailyFileService;
import com.pinting.core.util.DateUtil;
/**
 * 系统余额快照日终
 * @author SHENGUOPING
 * @2017-11-14 上午11:35:43
 */
@Service
public class SysBalanceDailyFileTask {
	
	private Logger logger = LoggerFactory.getLogger(SysBalanceDailyFileTask.class);
	
	@Autowired
	private BsSysBalanceDailyFileService bsSysBalanceDailyFileService;
	
	public void execute() {
		logger.info("start SysBalanceDailyFileTask ");
		bsSysBalanceDailyFileService.generateSysBalanceDailyFile(DateUtil.formatYYYYMMDD(new Date()));
	}
	
}
