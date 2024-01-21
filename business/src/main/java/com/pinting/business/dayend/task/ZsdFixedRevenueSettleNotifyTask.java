package com.pinting.business.dayend.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleService;

/**
 * 每日营销结算通知定时-赞时贷
 * @author SHENGUOPING
 * @date  2017年9月5日 下午8:51:41
 */
@Service
public class ZsdFixedRevenueSettleNotifyTask {
    
	private final Logger logger = LoggerFactory.getLogger(ZsdFixedRevenueSettleNotifyTask.class);
	
	@Autowired
    private DepFixedRevenueSettleService depFixedRevenueSettleService;
	
	 /**
     * 任务执行
     */
    public void execute() {
    	// 每日借款手续费、还款营收结算
    	try {			
    		zsdRevenueSettleExecute();
		} catch (Exception e) {
			logger.error("每日借款手续费、还款营收结算异常:{}", e);
		}
        // 每日重复还款结算
        try {
        	zsdRepayRepeatExecute();
		} catch (Exception e) {
			logger.error("每日重复还款结算异常:{}", e);
		}
    }

    /**
     * 每日借款手续费、还款营收结算定时
     */
    public void zsdRevenueSettleExecute() {
        logger.info("=========【每日借款手续费、还款营收结算定时】开始======================");
        depFixedRevenueSettleService.zsdRevenueSettle();
        logger.info("=========【每日借款手续费、还款营收结算定时】结束======================");
    }

    /**
     * 每日重复还款结算定时
     */
    public void zsdRepayRepeatExecute() {
        logger.info("=========【每日重复还款结算定时】开始======================");
        depFixedRevenueSettleService.zsdRepayRepeatSettle();
        logger.info("=========【每日重复还款结算定时】结束======================");
    }
	
}
