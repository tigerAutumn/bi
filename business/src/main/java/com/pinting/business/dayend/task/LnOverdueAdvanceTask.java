package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.model.FinanceRepayInfo;
import com.pinting.business.accounting.loan.service.FinanceReceiveMoneyService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.dao.BsHfbankUserExtMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnLoanRelationExample;
import com.pinting.business.model.vo.RepayScheduleVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 逾期垫付检查和执行
 * Created by babyshark on 2016/9/2.
 */
@Service
public class LnOverdueAdvanceTask {
    private Logger log = LoggerFactory.getLogger(LnOverdueAdvanceTask.class);
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private FinanceReceiveMoneyService financeReceiveMoneyService;
    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;

    public void execute() {
        log.info("=================【ZAN还款逾期垫付】定时任务开始=====================");
        overdueAdvance();
        
        log.info("=================【ZAN还款逾期垫付】定时任务结束=====================");
    }

    private void overdueAdvance() {
    	 //1、查询当前所有已逾期的还款计划
        List<RepayScheduleVO> repayScheduleVOs = financeReceiveMoneyService.queryOverdueRepaySchedules();
        if(CollectionUtils.isEmpty(repayScheduleVOs)){
            log.info("=================【ZAN还款逾期垫付】无逾期未还款的计划，不需逾期垫付=====================");
            return;
        }
        log.info("=================【ZAN还款逾期垫付】共["+repayScheduleVOs.size()+"]笔还款计划需逾期垫付，开始遍历执行=====================");
        //已生成的逾期垫付, true表示已生成，不需重复生成
        Map<String, Boolean> repayGeneratedMap = new HashMap<>();
        for (RepayScheduleVO repayScheduleVO : repayScheduleVOs) {
            try {
                Boolean isGenerated = repayGeneratedMap.get(repayScheduleVO.getPartnerRepayId());
                if (isGenerated != null && isGenerated) {
                    log.error("=================【ZAN还款逾期垫付】" +
                            "还款计划编号[" + repayScheduleVO.getId() + "]已逾期垫付，不需重复处理=====================");
                    continue;
                }
                Integer loanId = repayScheduleVO.getLoanId();
                //借款数据校验
                LnLoan loan = lnLoanMapper.selectByPrimaryKey(loanId);
                if (loan == null) {
                    log.info("=================【ZAN还款逾期垫付】借贷借款数据未获取=====================");
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                }
                //借贷关系列表数据校验
                LnLoanRelationExample relationExample = new LnLoanRelationExample();
                relationExample.createCriteria().andLoanIdEqualTo(loanId);
                List<LnLoanRelation> relationList = lnLoanRelationMapper.selectByExample(relationExample);
                if (CollectionUtils.isEmpty(relationList)) {
                    log.info("=================【ZAN还款逾期垫付】借贷关系列表为空=====================");
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                }
                //逾期还款系统分账 1、记录还款营收收入 2、生成存管账单 3、调用系统记账 4、ln_repay_schedule更新状态为已还款
                repayPaymentService.overdueSysSplit(loan, repayScheduleVO, relationList);
                repayGeneratedMap.put(repayScheduleVO.getPartnerRepayId(), true);
            }catch (Exception e){
                log.error("=================【ZAN还款逾期垫付】" +
                        "还款计划编号["+repayScheduleVO.getId()+"]逾期垫付处理失败=====================", e);
                specialJnlService.warn4Fail(repayScheduleVO.getPrincipal(),
                        "定时任务{ZAN还款逾期垫付}还款计划编号["+repayScheduleVO.getId()+"]逾期垫付处理失败，请检查",
                        null,"逾期垫付处理失败",true);
            }
        }
	}

	/**
     * 旧逾期垫付检查和执行
     */
	private void oldLnOverdueAdvance() {
		try {
            //1、查询当前所有已逾期的还款计划
            List<RepayScheduleVO> repayScheduleVOs = financeReceiveMoneyService.queryOverdueRepaySchedules();
            if(CollectionUtils.isEmpty(repayScheduleVOs)){
                log.info("=================【还款逾期垫付】无逾期未还款的计划，不需逾期垫付=====================");
                return;
            }
            log.info("=================【还款逾期垫付】共["+repayScheduleVOs.size()+"]笔还款计划需逾期垫付，开始遍历执行=====================");
            //2、生成逾期垫付回款计划
            //已生成的逾期垫付, true表示已生成，不需重复生成
            Map<String, Boolean> repayGeneratedMap = new HashMap<>();
            for (RepayScheduleVO repayScheduleVO : repayScheduleVOs) {
                try {
                    Boolean isGenerated = repayGeneratedMap.get(repayScheduleVO.getPartnerRepayId());
                    if(isGenerated != null && isGenerated){
                        log.error("=================【还款逾期垫付】" +
                                "还款计划编号["+repayScheduleVO.getId()+"]已生成逾期垫付回款计划，不需重复生成=====================");
                        continue;
                    }
                    FinanceRepayInfo financeRepayInfo = new FinanceRepayInfo();
                    financeRepayInfo.setLoanId(repayScheduleVO.getLoanId());
                    financeRepayInfo.setRepayScheduleId(repayScheduleVO.getId());
                    financeRepayInfo.setRepaySerial(repayScheduleVO.getSerialId());
                    financeReceiveMoneyService.generateOverdueRepayPlan(financeRepayInfo);
                    repayGeneratedMap.put(repayScheduleVO.getPartnerRepayId(), true);
                }catch (Exception e){
                    log.error("=================【还款逾期垫付】" +
                            "还款计划编号["+repayScheduleVO.getId()+"]生成逾期垫付回款计划失败=====================", e);
                    specialJnlService.warn4Fail(repayScheduleVO.getPrincipal(),
                            "定时任务{还款逾期垫付}还款计划编号["+repayScheduleVO.getId()+"]生成逾期垫付回款计划失败，请检查",
                            null,"生成逾期垫付回款计划失败",true);
                }
            }
        } catch (Exception e) {
            log.error("=================【还款逾期垫付】定时任务异常=====================", e);
            specialJnlService.warn4Fail(null,
                    "定时任务{还款逾期垫付}执行异常",
                    null,"还款逾期垫付",true);
        }
		
	}
}
