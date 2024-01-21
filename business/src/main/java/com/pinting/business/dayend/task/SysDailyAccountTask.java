package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.service.SysDailyBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 赞分期每日营收
 * Created by 剑钊 on 2016/9/7.
 */
@Service
public class SysDailyAccountTask {

    private Logger log = LoggerFactory.getLogger(SysDailyAccountTask.class);

    @Autowired
    private SysDailyBizService dailyBizService;

    public void execute() {


        log.info("============定时任务：每日合作方营收结算开始执行============");
        try {
            dailyBizService.checkAndGenerateRevenuePlan();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("============定时任务：每日合作方营收结算执行结束============");
    }
}
