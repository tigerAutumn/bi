package com.pinting.business.dayend.task;

import com.pinting.business.service.loan.DafyLoanCheckAccountService;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/6
 * Description: 达飞云贷自主放款对账文件生成任务
 */
@Service
public class DafyLoanCheckAccountTask {

    @Autowired
    private DafyLoanCheckAccountService dafyLoanCheckAccountService;

    /**
     * 任务执行
     */
    public void execute() {
        Date time = DateUtil.addDays(new Date(), -1);
        dafyLoanCheckAccountService.checkAccount(DateUtil.format(time));
    }

}
