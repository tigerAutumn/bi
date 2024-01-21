package com.pinting.business.dayend.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:      cyb
 * Date:        2016/8/25
 * Description: 定时查询宝付充值订单处理中状态，并置为成功
 */
@Deprecated
public class BaofooTopUpOrderStautsTask {

    private Logger logger = LoggerFactory.getLogger(BaofooTopUpOrderStautsTask.class);

    /*@Autowired
    private UserBuyProductService userBuyProductService;*/

    public void execute() {
        logger.info("定时查询宝付充值订单处理中状态开始");
        try {
            //userBuyProductService.baofooSyncQuickPayStauts();
        } catch (Exception e) {
            logger.error("定时查询宝付充值订单处理中状态异常", e.getMessage());
        }
        logger.info("定时查询宝付充值订单处理中状态结束");
    }

}
