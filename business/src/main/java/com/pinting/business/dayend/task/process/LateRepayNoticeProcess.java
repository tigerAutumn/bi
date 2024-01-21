package com.pinting.business.dayend.task.process;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.model.LnCompensate;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 云贷代偿通知请求调用的线程
 *
 * @author zhangpeng
 * @2018-03-12
 */

public class LateRepayNoticeProcess implements Runnable {
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger logger = LoggerFactory.getLogger(LateRepayNoticeProcess.class);

    private DepFixedRepayPaymentServiceImpl depFixedRepayPaymentService;

    private LnCompensate lnCompensate;
    private List<LnCompensateDetail> detailList;

    public void setDepFixedRepayPaymentService(DepFixedRepayPaymentServiceImpl depFixedRepayPaymentService) {
        this.depFixedRepayPaymentService = depFixedRepayPaymentService;
    }

    public void setLnCompensate(LnCompensate lnCompensate) {
        this.lnCompensate = lnCompensate;
    }

    public void setDetailList(List<LnCompensateDetail> detailList) {
        this.detailList = detailList;
    }

    @Override
    public void run() {
        //线程开始
        logger.info("=========================== 云贷代偿通知请求线程开始 ===============");
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE_SECOND.getKey() + lnCompensate.getPartnerCode());
            PartnerEnum partner = PartnerEnum.getEnumByCode(lnCompensate.getPartnerCode());

            depFixedRepayPaymentService.lateRepayNotice(lnCompensate, detailList, partner);
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE_SECOND.getKey() + lnCompensate.getPartnerCode());
        }
        //线程结束
        logger.info("=========================== 云贷代偿通知请求线程结束 ===============");
    }
}
