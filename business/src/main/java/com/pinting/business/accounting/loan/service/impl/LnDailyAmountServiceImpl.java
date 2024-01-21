package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.service.LnDailyAmountService;
import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

/**
 * 每日额度管理
 */
@Service
public class LnDailyAmountServiceImpl implements LnDailyAmountService {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnDailyAmountMapper lnDailyAmountMapper;

    public LnDailyAmount getLnDailyAmount4Avaliable (String partnerCode) {
        //查询运营最近一次有效的额度配置
        LnDailyAmountExample lnDailyAmountExample = new LnDailyAmountExample();
        lnDailyAmountExample.createCriteria().andPartnerCodeEqualTo(partnerCode).andStatusEqualTo(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE).andUseDateEqualTo(new Date());
        lnDailyAmountExample.setOrderByClause("update_time desc");
        List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(lnDailyAmountExample);
        if (CollectionUtils.isNotEmpty(lnDailyAmountList)) {
            return lnDailyAmountList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void chargeLoannDailyAmountFreeze(final Double loanAmount, final Integer lnDailyAmountId) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // 资产方可放款总额度 - 借款金额
                LnDailyAmount lnDailyAmountForLock = lnDailyAmountMapper.selectLnDailyAmountForIdLock(lnDailyAmountId);
                // 要补充事务减少剩余可用额度（云贷/七贷限额控制）
                if (lnDailyAmountForLock.getTermxLeftAmount().compareTo(loanAmount) < 0) {
                    throw new PTMessageException(PTMessageEnum.ACCOUNT_QUOTA_NOT_ENOUGH);
                }
                LnDailyAmount lnDailyAmountTemp = new LnDailyAmount();
                lnDailyAmountTemp.setId(lnDailyAmountForLock.getId());
                lnDailyAmountTemp.setTermxLeftAmount(MoneyUtil.subtract(lnDailyAmountForLock.getTermxLeftAmount(), loanAmount).doubleValue());
                lnDailyAmountMapper.updateByPrimaryKeySelective(lnDailyAmountTemp);
            }
        });

    }

    @Override
    public void chargeLoannDailyAmountUnFreeze(final Double loanAmount, final Integer lnDailyAmountId) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // 债权匹配失败资产方可放款总额度 + 借款金额
                LnDailyAmount lnDailyAmountForLock = lnDailyAmountMapper.selectLnDailyAmountForIdLock(lnDailyAmountId);
                LnDailyAmount lnDailyAmountTemp = new LnDailyAmount();
                lnDailyAmountTemp.setId(lnDailyAmountForLock.getId());
                lnDailyAmountTemp.setTermxLeftAmount(MoneyUtil.add(lnDailyAmountForLock.getTermxLeftAmount(), loanAmount).doubleValue());
                lnDailyAmountMapper.updateByPrimaryKeySelective(lnDailyAmountTemp);
            }
        });
    }
}
