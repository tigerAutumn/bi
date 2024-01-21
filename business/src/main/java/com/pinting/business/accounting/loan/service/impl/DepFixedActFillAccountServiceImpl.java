package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedActFillAccountService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.dao.BsSysSubAccountMapper;
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
 * Date:        2017/4/7
 * Description: 云贷补账记账服务
 */
@Service
public class DepFixedActFillAccountServiceImpl implements DepFixedActFillAccountService {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;

    @Override
    public void depFixedActFillFinishRecord(final String sysAccountCode, final Double amount, final PartnerEnum partnerEnum) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                //系统子账户表 THD_REVENUE_YUN 增加金额
                BsSysSubAccount bsSysSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(sysAccountCode);
                BsSysSubAccount bsSysSubAccountNew = new BsSysSubAccount();
                bsSysSubAccountNew.setId(bsSysSubAccount.getId());
                bsSysSubAccountNew.setBalance(MoneyUtil.add(bsSysSubAccount.getBalance(), amount).doubleValue());
                bsSysSubAccountNew.setAvailableBalance(MoneyUtil.add(bsSysSubAccount.getAvailableBalance(), amount).doubleValue());
                bsSysSubAccountNew.setCanWithdraw(MoneyUtil.add(bsSysSubAccount.getCanWithdraw(), amount).doubleValue());
                bsSysSubAccountNew.setLastTransDate(new Date());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(bsSysSubAccountNew);

                //新增系统记账流水表，SYS_THD_REVENUE_YUN 余额增加
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                sysAccountJnl.setTransTime(new Date());
                if(partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) {
                    sysAccountJnl.setTransCode(Constants.SYS_THD_REVENUE_7);
                } else {
                    sysAccountJnl.setTransCode(Constants.SYS_THD_REVENUE_YUN);
                }
                sysAccountJnl.setTransName(partnerEnum.getName() + "免息补账完成记账");
                sysAccountJnl.setTransAmount(amount);
                sysAccountJnl.setSysTime(new Date());
                sysAccountJnl.setChannelTime(new Date());
                sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                sysAccountJnl.setSubAccountCode2(bsSysSubAccount.getCode());
                sysAccountJnl.setBeforeBalance2(bsSysSubAccount.getBalance());
                sysAccountJnl.setAfterBalance2(bsSysSubAccountNew.getBalance());
                sysAccountJnl.setBeforeAvialableBalance2(bsSysSubAccount.getAvailableBalance());
                sysAccountJnl.setAfterAvialableBalance2(bsSysSubAccountNew.getAvailableBalance());
                sysAccountJnl.setBeforeFreezeBalance2(bsSysSubAccount.getFreezeBalance());
                sysAccountJnl.setAfterFreezeBalance2(bsSysSubAccount.getFreezeBalance());
                sysAccountJnl.setFee(0.0);
                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
            }
        });
    }

}
