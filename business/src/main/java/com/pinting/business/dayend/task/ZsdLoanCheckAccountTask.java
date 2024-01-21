package com.pinting.business.dayend.task;

import com.pinting.business.service.loan.ZsdLoanCheckAccountService;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/9/4
 * Description: 赞时贷对账文件生成任务
 */
@Service
public class ZsdLoanCheckAccountTask {

    @Autowired
    private ZsdLoanCheckAccountService zsdLoanCheckAccountService;

    /**
     * 任务执行
     */
    public void execute() {
    	Date time = DateUtil.addDays(new Date(), -1);
        zsdLoanCheckAccountService.checkAccount(DateUtil.format(time));
    }
}
