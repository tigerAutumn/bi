package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.service.impl.DepOfflineRepayServiceImpl;
import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsAccountMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnFinanceRepayScheduleMapper;
import com.pinting.business.model.BsAccount;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.vo.DailyDiffVO;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangbao on 2017/4/8.
 */
@Service
public class DailyDiffMoneyTask {

    private Logger log = LoggerFactory.getLogger(DepOfflineRepayServiceImpl.class);

    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;

    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;

    @Autowired
    private BsSubAccountMapper subAccountMapper;

    @Autowired
    private BsAccountJnlMapper accountJnlMapper;

    @Autowired
    private BsAccountMapper bsAccountMapper;

    /**
     * 每日补差额累加
     */
    public void execute(){
        log.info(">>>每日补差额累加 begin");
        //站岗户(open_balance)+红包户-日期小于今天的在投本金(已起息的债权本金)-回款日为D-1日的回款本金
        List<DailyDiffVO> dailyDiffs = bsSubAccountMapper.dailyDiffMoney();

        for(DailyDiffVO dailyDiffVO :dailyDiffs){
        	//补差本金
            log.info(">>>补差本金:"+ dailyDiffVO.getAmount() + "<<<");
            Double diffInterest = MoneyUtil.divide(MoneyUtil.multiply(dailyDiffVO.getAmount(),dailyDiffVO.getProductRate()).doubleValue(),36500d).doubleValue();

            //增加用户补差户补差额
            BsSubAccount subAccountLock = subAccountMapper.selectSubAccountByIdForLock(dailyDiffVO.getDiffAccountId());
            BsSubAccount readySubAccount = new BsSubAccount();
            readySubAccount.setId(subAccountLock.getId());
            readySubAccount.setBalance(MoneyUtil.add(subAccountLock.getBalance(), diffInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            readySubAccount.setAvailableBalance(MoneyUtil.add(subAccountLock.getAvailableBalance(), diffInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            readySubAccount.setCanWithdraw(MoneyUtil.add(subAccountLock.getCanWithdraw(), diffInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            subAccountMapper.updateByPrimaryKeySelective(readySubAccount);

            //新增一条补差流水
            BsAccountJnl accountJnl = new BsAccountJnl();
            accountJnl.setTransTime(new Date());
            accountJnl.setTransCode(Constants.USER_DIFF_MONEY_ADD);
            accountJnl.setTransName(dailyDiffVO.getAmount().compareTo(0d)<0?"补差额减少" : "补差额增加");
            accountJnl.setTransAmount(diffInterest);
            accountJnl.setSysTime(new Date());
            accountJnl.setCdFlag1(dailyDiffVO.getAmount().compareTo(0d)<0?Constants.CD_FLAG_C : Constants.CD_FLAG_D);
            BsAccount bsAccount = bsAccountMapper.selectByPrimaryKey(dailyDiffVO.getAccountId());
            accountJnl.setUserId1(bsAccount.getUserId());
            accountJnl.setAccountId1(subAccountLock.getAccountId());
            accountJnl.setSubAccountId1(subAccountLock.getId());
            accountJnl.setSubAccountCode1(subAccountLock.getCode());
            accountJnl.setBeforeBalance1(subAccountLock.getBalance());
            accountJnl.setAfterBalance1(readySubAccount.getBalance());
            accountJnl.setBeforeAvialableBalance1(subAccountLock.getAvailableBalance());
            accountJnl.setAfterAvialableBalance1(readySubAccount.getAvailableBalance());
            accountJnl.setBeforeFreezeBalance1(subAccountLock.getFreezeBalance());
            accountJnl.setAfterFreezeBalance1(subAccountLock.getFreezeBalance());
            accountJnl.setFee(0.0);
            accountJnlMapper.insertSelective(accountJnl);

        }
        log.info(">>>每日补差额累加 end");
    }

}
