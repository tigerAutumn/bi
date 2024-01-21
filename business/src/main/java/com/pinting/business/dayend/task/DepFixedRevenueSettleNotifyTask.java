package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:      cyb
 * Date:        2017/4/8
 * Description: 每日营销结算通知定时
 */
@Service
public class DepFixedRevenueSettleNotifyTask {

    private final Logger logger = LoggerFactory.getLogger(DepFixedRevenueSettleNotifyTask.class);

    @Autowired
    private DepFixedRevenueSettleService depFixedRevenueSettleService;

    /**
     * 任务执行
     */
    public void execute() {
    	// 每日借款手续费、还款营收结算
    	try {
            yunDaiRevenueSettle();
		} catch (Exception e) {
			logger.error("云贷每日借款手续费、还款营收结算异常:{}", e.getMessage());
		}
    }

    /**
     * 云贷每日借款手续费、还款营收结算定时
     */
    public void yunDaiRevenueSettle() {
        logger.info("=========【每日借款手续费、还款营收结算定时】开始======================");
        depFixedRevenueSettleService.revenueSettle();
        logger.info("=========【每日借款手续费、还款营收结算定时】结束======================");
    }

    /**
     * 云贷每日重复还款结算定时
     */
    public void repayRepeatExecute() {
        logger.info("=========【每日重复还款结算定时】开始======================");
        depFixedRevenueSettleService.repayRepeatSettle();
        logger.info("=========【每日重复还款结算定时】结束======================");
    }

    /**
     * 7贷营收结算及通知
     */
    public void sevenDaiRevenueSettle() {
        logger.info("=========【7贷每日营收结算及通知定时】开始======================");
        try {
            depFixedRevenueSettleService.sevenDaiRevenueSettle();
        } catch (Exception e) {
            logger.error("7贷营收结算及通知异常:{}", e.getMessage());
        }
        logger.info("=========【7贷每日营收结算及通知定时】结束======================");
    }

    /**
     * 7贷重复还款每日结算
     */
    public void sevenDaiRepayRepeat() {
        logger.info("=========【7贷重复还款每日结算定时】开始======================");
        try {
            depFixedRevenueSettleService.sevenRepayRepeatSettle();
        } catch (Exception e) {
            logger.error("7贷重复还款每日结算异常:{}", e.getMessage());
        }
        logger.info("=========【7贷重复还款每日结算定时】结束======================");
    }


}
