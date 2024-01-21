package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.service.FinanceReceiveMoneyService;
import com.pinting.business.dao.BsLoanFinanceRepayMapper;
import com.pinting.business.model.BsLoanFinanceRepay;
import com.pinting.business.model.BsLoanFinanceRepayExample;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分期产品回款
 * Created by babyshark on 2016/9/2.
 */
@Deprecated
public class LnReceiveMoneyTask {
    private Logger log = LoggerFactory.getLogger(LnReceiveMoneyTask.class);
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private FinanceReceiveMoneyService financeReceiveMoneyService;
    @Autowired
    private UserService userService;
    @Autowired
    private BsLoanFinanceRepayMapper bsLoanFinanceRepayMapper;

    protected void execute() {
        log.info("=================【分期产品回款】定时任务开始=====================");
        try {
            //查询待回款列表
            BsLoanFinanceRepayExample loanFinanceRepayExample = new BsLoanFinanceRepayExample();
            List<String> statuses = new ArrayList<>(2);
            statuses.add(Constants.FINANCE_REPAY_SATAE_INIT);
            statuses.add(Constants.FINANCE_REPAY_SATAE_FAIL);
            //原没有回款执行时间按照原逻辑来回款
            loanFinanceRepayExample.createCriteria().andStatusIn(statuses).andPlanDateIsNull();
            List<BsLoanFinanceRepay> loanFinanceRepays = bsLoanFinanceRepayMapper.selectByExample(loanFinanceRepayExample);
            loanFinanceRepayExample = new BsLoanFinanceRepayExample();
            //新的有回款执行时间按照新逻辑来回款

            loanFinanceRepayExample.createCriteria().andStatusIn(statuses).andPlanDateLessThanOrEqualTo(DateUtil.getDateEnd(new Date()));
            loanFinanceRepays.addAll(bsLoanFinanceRepayMapper.selectByExample(loanFinanceRepayExample));

            if(CollectionUtils.isEmpty(loanFinanceRepays)){
                log.info("=================【分期产品回款】无待回款计划，不需发起回款=====================");
                return;
            }
            log.info("=================【分期产品回款】共["+loanFinanceRepays.size()+"]笔回款计划需回款，开始遍历执行=====================");
            //获取用户回款路径信息，进行回款
            for (BsLoanFinanceRepay loanFinanceRepay : loanFinanceRepays) {
                try {
                    log.info("=================【分期产品回款】编号["+loanFinanceRepay.getId()+"]执行开始=====================");
                    BsUser fnUser = userService.findUserById(loanFinanceRepay.getFnUserId());
                    Integer returnPath = fnUser.getReturnPath();
                    if(Constants.RETURN_PATH_BALANCE == returnPath){
                        financeReceiveMoneyService.receiveMoney2Balance(loanFinanceRepay);
                    }else if(Constants.RETURN_PATH_BANKCARD == returnPath){
                        financeReceiveMoneyService.receiveMoney2Card(loanFinanceRepay);
                    }
                    log.info("=================【分期产品回款】编号["+loanFinanceRepay.getId()+"]执行结束=====================");
                }catch (Exception e){
                    log.error("=================【分期产品回款】" +
                            "回款表编号["+loanFinanceRepay.getId()+"]执行回款失败=====================", e);
                    specialJnlService.warn4Fail(loanFinanceRepay.getTotal(),
                            "定时任务{分期产品回款}回款表编号["+loanFinanceRepay.getId()+"]执行回款失败，请检查",
                            null,"单笔分期产品回款失败",true);
                }
            }
        } catch (Exception e) {
            log.error("=================【分期产品回款】定时任务异常=====================", e);
            specialJnlService.warn4Fail(null,
                    "定时任务{分期产品回款}执行异常",
                    null,"分期产品回款",true);
        }
        log.info("=================【分期产品回款】定时任务结束=====================");
    }
}
