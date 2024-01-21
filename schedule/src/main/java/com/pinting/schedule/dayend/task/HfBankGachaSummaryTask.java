package com.pinting.schedule.dayend.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.schedule.mongodb.service.MongoStatisticsService;

/**
 * 恒丰对账汇总凭证定时 
 * @author SHENGUOPING
 * @date  2018年7月6日 下午4:17:14
 */
@Service
public class HfBankGachaSummaryTask {

	@Autowired
	MongoStatisticsService mongoStatisticsService;
	
    // 定时任务执行方法
    public void execute() {
    	mongoStatisticsService.generateHfBankCheckGacha();
    }
    
}
