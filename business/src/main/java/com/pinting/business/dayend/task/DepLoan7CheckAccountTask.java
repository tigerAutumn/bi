package com.pinting.business.dayend.task;

import com.pinting.business.service.loan.DepLoan7CheckAccountService;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/12/13
 * Description: 7贷对账文件生成任务
 */
@Service
public class DepLoan7CheckAccountTask {

    @Autowired
    private DepLoan7CheckAccountService depLoan7CheckAccountService;

    /**
     * 任务执行
     */
    public void execute() {
    	Date time = DateUtil.addDays(new Date(), -1);
        depLoan7CheckAccountService.checkAccount(DateUtil.format(time));
    }
}
