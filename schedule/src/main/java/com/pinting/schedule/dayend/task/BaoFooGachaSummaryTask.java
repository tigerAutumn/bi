package com.pinting.schedule.dayend.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.schedule.mongodb.service.MongoStatisticsService;

/**
 * @author Gemma 2018-6-12 09:20:38
 * @description 对账汇总凭证定时 
 */
@Service
public class BaoFooGachaSummaryTask {
    
	@Autowired
	MongoStatisticsService mongoStatisticsService;
	
    // 定时任务执行方法
    public void execute() {
    	mongoStatisticsService.generateMainCheckGacha();
    	
    	mongoStatisticsService.generateAssistCheckGacha();
    }
}