package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.service.SysDailyBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 赞分期宝付营销代付对账
 *
 * @author liujz
 * @Project: business
 * @Title: CheckAccountJob.java
 * @date 2016-10-09 下午2:45:33
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class BaoFooCheckZanAccountTask {
    private Logger log = LoggerFactory.getLogger(BaoFooCheckZanAccountTask.class);

    @Autowired
    private SysDailyBizService sysDailyBizService;


    /**
     * 任务执行
     */
    public void execute() {
        //赞分期宝付营销代付对账
        checkAccountJob();
    }


    /**
     * 赞分期宝付营销代付对账
     */
    private void checkAccountJob() {
        sysDailyBizService.downLoadZanCheckAccountFile();
    }

}
