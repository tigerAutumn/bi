package com.pinting.business.aspect;

import com.pinting.business.dao.BsSysStatusMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysStatus;
import com.pinting.business.model.BsSysStatusExample;
import com.pinting.business.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 恒丰借款端相关交易拦截
 * 根据bs_sys_status表查询状态，实现拦截操作
 * @author bianyatian
 * @2017-5-18 下午2:32:42
 */
@Aspect
@Component
@Order(7)
public class StopHfTransaction4LoanAspect {
	
	private Logger log = LoggerFactory.getLogger(StopHfTransaction4LoanAspect.class);

	
	//云贷借款
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedLoanPaymentServiceImpl.loanApply(..))")
	public void yunLoanApplyPointcut(){}
	
	//赞分期预绑卡
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.LoanCardOperateServiceImpl.preBindCard(..))")
	public void zanPreBindCardPointcut(){}
	
	//赞分期绑卡
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.LoanCardOperateServiceImpl.bindCardConfirm(..))")
	public void zanBindCardPointcut(){}
	
	//赞分期解绑
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.LoanCardOperateServiceImpl.unBindCard(..))")
	public void zanUnBindCardPointcut(){}

	//还款预下单
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.doPreRepay(..))")
	public void doPreRepayPointcut(){}
	//还款预下单重发短信
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.doPreRepayRepeat(..))")
	public void doPreRepayRepeatPointcut(){}
	//还款确认下单
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.doRepayConfirm(..))")
	public void doRepayConfirmPointcut(){}
	//云贷代扣还款申请
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.repayApply(..))")
	public void repayApplyPointcut(){}
	//七贷代扣还款申请
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.depSevenRepayApply(..))")
	public void depSevenRepayApplyPointcut(){}
	//代偿
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.lateRepayNotice(..))")
	public void lateRepayNoticePointcut(){}

	
	@Autowired
	private BsSysStatusMapper bsSysStatusMapper;

	
	@Before("yunLoanApplyPointcut()")
	public void yunLoanApplyBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	
	@Before("zanPreBindCardPointcut()")
	public void zanPreBindCardBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	@Before("zanBindCardPointcut()")
	public void zanBindCardBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	@Before("zanUnBindCardPointcut()")
	public void zanUnBindCardBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}

	@Before("doPreRepayPointcut()")
	public void doPreRepayBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	@Before("doPreRepayRepeatPointcut()")
	public void doPreRepayRepeatBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	@Before("doRepayConfirmPointcut()")
	public void doRepayConfirmBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	@Before("repayApplyPointcut()")
	public void repayApplyBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	@Before("depSevenRepayApplyPointcut()")
	public void depSevenRepayApplyBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	@Before("lateRepayNoticePointcut()")
	public void lateRepayNoticeBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}

	/**
	 * 确认交易是否可执行
	 * @return
	 */
	private boolean confirmTransaction() {
		//查询，挂起中，且交易类型为普通交易,返回false
		BsSysStatusExample example = new BsSysStatusExample();
		example.createCriteria().andStatusValueEqualTo(Constants.SYS_STATUS_STATUS_VALUE_HANG)
			.andTransTypeEqualTo(Constants.SYS_STATUS_TRANS_TYPE_TRANSACTION_LOAN);
		
		List<BsSysStatus> list = bsSysStatusMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			return false;
		}
		return true;
	}
}
