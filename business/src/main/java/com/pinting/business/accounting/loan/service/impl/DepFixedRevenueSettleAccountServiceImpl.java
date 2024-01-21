package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleAccountService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysAccountJnl;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/4/8
 * Description: 营收结算记账服务（砍头息、还款营收、重复还款）
 */
@Service
public class DepFixedRevenueSettleAccountServiceImpl implements DepFixedRevenueSettleAccountService {


    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;


    @Override
    public void revenueSettleRecord(final String sysAccountCode, final Double settleAmount, final String revenueFlag) {
        // TODO 调用当前方法的 sysAccountCode 需要做check
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //云贷营收户加行级锁
                BsSysSubAccount sysSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(sysAccountCode);
                if(sysSubAccount == null){
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"营收结算记账时未找到系统户记录");
                }
                BsSysSubAccount sysSubAccountNew = new BsSysSubAccount();
                sysSubAccountNew.setId(sysSubAccount.getId());
                sysSubAccountNew.setBalance(MoneyUtil.subtract(sysSubAccount.getBalance(), settleAmount).doubleValue());
                sysSubAccountNew.setAvailableBalance(MoneyUtil.subtract(sysSubAccount.getAvailableBalance(), settleAmount).doubleValue());
                sysSubAccountNew.setCanWithdraw(MoneyUtil.subtract(sysSubAccount.getCanWithdraw(), settleAmount).doubleValue());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(sysSubAccountNew);

                //出账,系统账户流水记录
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                if(revenueFlag.equals(Constants.REVENUE_TYPE_REPEAT)){
                    sysAccountJnl.setTransCode(Constants.USER_REPAY_REPEAT_OUT);
                    sysAccountJnl.setTransName("重复还款出账");
                }else if(revenueFlag.equals(Constants.REVENUE_TYPE_LOAN_FEE)){
                    sysAccountJnl.setTransCode(Constants.USER_LOAN_FEE_OUT);
                    sysAccountJnl.setTransName("砍头息出账");
                }else if(revenueFlag.equals(Constants.REVENUE_TYPE_REPAY_REVENUE)){
                    sysAccountJnl.setTransCode(Constants.USER_REPAY_REVENUE_OUT);
                    sysAccountJnl.setTransName("还款营收出账");
                }
                sysAccountJnl.setTransTime(new Date());
                sysAccountJnl.setTransAmount(settleAmount);
                sysAccountJnl.setSysTime(new Date());
                sysAccountJnl.setChannelTime(new Date());
                sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                sysAccountJnl.setSubAccountCode1(sysSubAccount.getCode());
                sysAccountJnl.setBeforeBalance1(sysSubAccount.getBalance());
                sysAccountJnl.setAfterBalance1(sysSubAccountNew.getBalance());
                sysAccountJnl.setBeforeAvialableBalance1(sysSubAccount.getAvailableBalance());
                sysAccountJnl.setAfterAvialableBalance1(sysSubAccountNew.getAvailableBalance());
                sysAccountJnl.setBeforeFreezeBalance1(sysSubAccount.getFreezeBalance());
                sysAccountJnl.setAfterFreezeBalance1(sysSubAccount.getFreezeBalance());
                sysAccountJnl.setFee(0.0);
                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
            }
        });
    }

}
