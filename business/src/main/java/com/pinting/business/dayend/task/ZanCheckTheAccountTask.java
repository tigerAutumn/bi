package com.pinting.business.dayend.task;

import com.pinting.business.service.loan.ZanCheckTheAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:      cyb
 * Date:        2016/9/24
 * Description: 币港湾-赞分期对账（服务方：币港湾）
 */
@Service
public class ZanCheckTheAccountTask {

    private Logger logger = LoggerFactory.getLogger(ZanCheckTheAccountTask.class);

    @Autowired
    private ZanCheckTheAccountService zanCheckTheAccountService;

    /**
     * 任务执行
     */
    public void execute() {
        zanCheckTheAccount();
    }

    private void zanCheckTheAccount() {
        logger.info("借还款、营销对账明细文件生成开始");
        zanCheckTheAccountService.checkTheAccount();
        logger.info("借还款、营销对账明细文件生成结束");
    }

}
