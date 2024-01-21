package com.pinting.business.dayend.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.model.FinanceRepayCalVO;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnLoanRelationExample;
import com.pinting.business.model.vo.LnLoanRepayScheduleVO;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;

/**
 * 赞分期提前赎回恒丰线下还款定时
 * 在宝付账户资金充足的情况下，对赞分期理财人进行提前还款，生成对应的理财人还款计划和恒丰还款计划表
 * @project business
 * @author bianyatian
 * @2018-3-12 上午11:03:56
 */
@Service
public class ZanCalledAwayRepayTask {
	private Logger log = LoggerFactory.getLogger(ZanCalledAwayRepayTask.class);
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private LnLoanRelationMapper relationMapper;
	@Autowired
	private RepayPaymentService repayPaymentService;
	
	public void execute(){
		log.info("===========【 赞分期提前赎回】定时开始=================");
		/**
		 * 1）查询赞分期账单计划还款时间在某时间之后的状态为未还（INIT），
		 * 	根据partnerRepayId left join LnDepositionRepaySchedule表查询无线下还款记录的账单信息，根据借款id和期数排序
		 * 2）循环账单信息调用 RepayPaymentServiceImpl.do4FinanceRepay计算理财人应得本息及恒丰系统币港湾营收金额，
		 * 	修改回款计划表，并统计债权协议利率之和（包括本金），即获得恒丰线下还款金额
		 * 3）根据线下还款金额，生成LnDepositionRepaySchedule及明细
		 * 4）系统记账：
		 */
		Date finishDate = DateUtil.parseDate("2018-03-15"); //赞分期提前赎回日期
        BsSysConfig config = bsSysConfigService.findKey( Constants.ZAN_CALLED_AWAY_DATE );
        if(config != null){
        	finishDate = DateUtil.parseDate(config.getConfValue());
        }
        //查询计划还款时间在该日期之后的账单
		List<LnLoanRepayScheduleVO>  repayScheduleList = lnLoanMapper.selectZanNotRepayReturn(finishDate);
		if(CollectionUtils.isNotEmpty(repayScheduleList)){
			for (LnLoanRepayScheduleVO lnLoanRepaySchedule : repayScheduleList) {
				//查询借贷关系表
                LnLoanRelationExample relationExample = new LnLoanRelationExample();
                relationExample.createCriteria().andLoanIdEqualTo(lnLoanRepaySchedule.getId()).andLnUserIdEqualTo(lnLoanRepaySchedule.getLnUserId());
                List<LnLoanRelation> relationList = relationMapper.selectByExample(relationExample);
                LnLoan lnLoan = new LnLoan();
                lnLoan.setId(lnLoanRepaySchedule.getId());
                lnLoan.setLnUserId(lnLoanRepaySchedule.getLnUserId());
                lnLoan.setPeriod(lnLoanRepaySchedule.getPeriod());
                lnLoan.setChargeRuleId(lnLoanRepaySchedule.getChargeRuleId());
                
                repayPaymentService.normalRepaySysSplit4ZANHF(lnLoan, lnLoanRepaySchedule, relationList);
			}
		}
		log.info("===========【 赞分期提前赎回】定时结束=================");
	}
}
