package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.HFBankCheckAccountService;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/5/10
 * Description: 恒丰出入金对账
 */
@Service("hFBankCheckAccountTask")
public class HFBankCheckAccountTask {

    @Autowired
    private HFBankCheckAccountService hfBankCheckAccountService;

    /**
     * 任务执行
     */
    public void execute() {
    	Date now = new Date();
        Date yesterday = DateUtil.addDays(now, -1);
        String time = DateUtil.formatDateTime(yesterday, "yyyyMMdd");
        Date checkTime = DateUtil.parse(time, "yyyyMMdd");
        hfBankCheckAccountService.checkAccount(checkTime);
    }

}
