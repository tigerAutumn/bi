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
 * 恒丰理财端相关交易拦截
 * 根据bs_sys_status表查询状态，实现拦截操作
 * @author bianyatian
 * @2017-5-18 下午2:32:42
 */
@Aspect
@Component
@Order(6)
public class StopHfTransaction4FinanceAspect {
	
	private Logger log = LoggerFactory.getLogger(StopHfTransaction4FinanceAspect.class);
	
	//币港湾绑卡预下单
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.preBindCard(..))")
	public void preBindCardPointcut(){}
	
	//币港湾绑卡
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.bindCard(..))")
	public void bindCardPointcut(){}
	
	//币港湾解绑卡
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.unBindCard(..))")
	public void unBindCardPointcut(){}
	
	//恒丰充值预下单
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserTopUpServiceImpl.hfPre(..))")
	public void topUpHfPrePointcut(){}
	
	//恒丰充值确认下单
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserTopUpServiceImpl.hfConfirm(..))")
	public void topUpHfConfirmPointcut(){}
	
	//恒丰网银充值
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserTopUpServiceImpl.hfEBank(..))")
	public void topUpHfEBankPointcut(){}
	
	//恒丰提现-前端直接提现
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceWithdrawServiceImpl.apply(..)) " +
			"|| execution(public * com.pinting.business.accounting.finance.service.impl.UserBalanceWithdrawServiceImpl.preWithdraw(..))")
	public void withdrawSitePointcut(){}

	//恒丰提现-审核通过提现
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceWithdrawServiceImpl.checkPass(..))")
	public void withdrawCheckPassPointcut(){}
	
	// 代偿人管理台提现
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceWithdrawServiceImpl.preCompensatorApply(..))")
	public void preCompensatorApplyPointcut(){}
	

	@Autowired
	private BsSysStatusMapper bsSysStatusMapper;
	
	
	@Before("preBindCardPointcut()")
	public void preBindCardBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	
	@Before("bindCardPointcut()")
	public void bindCardBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	@Before("unBindCardPointcut()")
	public void unBindCardBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	@Before("topUpHfPrePointcut()")
	public void topUpHfPreBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	
	@Before("topUpHfConfirmPointcut()")
	public void topUpHfConfirmBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	@Before("topUpHfEBankPointcut()")
	public void topUpHfEBankBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}

	
	@Before("withdrawSitePointcut()")
	public void withdrawSiteBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}

	@Before("withdrawCheckPassPointcut()")
	public void withdrawCheckPassBefore(JoinPoint call) {
		boolean doFlag = confirmTransaction();
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE);
		}
	}
	
	@Before("preCompensatorApplyPointcut()")
	public void preCompensatorApplyBefore(JoinPoint call) {
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

		//查询，挂起中，且交易类型为普通交易则返回false
		BsSysStatusExample example = new BsSysStatusExample();
		example.createCriteria().andStatusValueEqualTo(Constants.SYS_STATUS_STATUS_VALUE_HANG)
				.andTransTypeEqualTo(Constants.SYS_STATUS_TRANS_TYPE_TRANSACTION_FINANCE);
		List<BsSysStatus> list = bsSysStatusMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			return false;
		}
		return true;
	}
}
