package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.model.LnCompensate;
import com.pinting.business.model.LnCompensateDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 代偿还款通知默认服务
 * Created by zousheng on 2018/6/19.
 */
@Service("compensateNotice4DefaultServiceImpl")
public class CompensateNotice4DefaultServiceImpl extends AbstractCompensateNoticeServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(CompensateNotice4DefaultServiceImpl.class);

    @Override
    public void lateRepayNotice(final LnCompensate lnCompensate, final List<LnCompensateDetail> detailList, final PartnerEnum partner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("=========================== 云贷代偿通知批量处理开始 ===============");
                try {
                    jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE_SECOND.getKey() + lnCompensate.getPartnerCode());

                    CompensateNotice4DefaultServiceImpl.super.lateRepayNotice(lnCompensate, detailList, partner);
                } finally {
                    jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE_SECOND.getKey() + lnCompensate.getPartnerCode());
                }
                logger.info("=========================== 云贷代偿通知批量处理结束 ===============");
            }

        }).start();
    }
}
