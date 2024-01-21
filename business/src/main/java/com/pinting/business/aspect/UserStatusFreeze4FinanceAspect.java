package com.pinting.business.aspect;

import java.util.List;

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

import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.BsUserStatus;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_EBankBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserExample;

/**
 * 用户状态冻结，提现、充值、解绑卡、购买拦截
 * @author SHENGUOPING
 * @date  2018年8月1日 下午3:41:57
 */
@Aspect
@Component
@Order(9)
public class UserStatusFreeze4FinanceAspect {
	
	private Logger log = LoggerFactory.getLogger(StopHfTransaction4FinanceAspect.class);

	@Autowired
	private BsUserMapper bsUserMapper;
	
	//币港湾登录
	@Pointcut("execution(public * com.pinting.business.service.site.impl.UserServiceImpl.isValidMobileOrNick(..))")
	public void userLoginPointcut(){}
		
	//币港湾绑卡预下单
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.preBindCard(..))")
	public void preBindCardPointcut(){}
	
	//币港湾绑卡
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.bindCard(..))")
	public void bindCardPointcut(){}
	
	//币港湾解绑卡
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.unBindCard(..))")
	public void unBindCardPointcut(){}
	
	//币港湾解绑卡，前置校验
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.unBindCheck(..))")
	public void unBindCheckPointcut(){}
	
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

	// 奖励金提现
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBonusWithdrawServiceImpl.userBonusWithdraw(..))")
	public void userBonusWithdrawPointcut(){}
	
	//恒丰提现-审核通过提现
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceWithdrawServiceImpl.checkPass(..))")
	public void withdrawCheckPassPointcut(){}

	// 固定期限产品购买 
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyFixed(..))")
	public void balanceBuyPointcut(){}
	
	// 分期产品购买
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyStage(..))")
	public void balanceBuyAUTHPointcut(){}
	
	@Before("userLoginPointcut()")
	public void userLoginBefore(JoinPoint call) {
		String mobileOrNick = (String)call.getArgs()[0];
		boolean doFlag = confirmTransaction4Login(mobileOrNick);
		if (!doFlag) {
			throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("preBindCardPointcut()")
	public void preBindCardBefore(JoinPoint call) {
		String userId = (String)call.getArgs()[5];
		boolean doFlag = confirmTransaction(Integer.parseInt(userId));
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("bindCardPointcut()")
	public void bindCardBefore(JoinPoint call) {
		String userId = (String)call.getArgs()[2];
		boolean doFlag = confirmTransaction(Integer.parseInt(userId));
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("unBindCardPointcut()")
	public void unBindCardBefore(JoinPoint call) {
		Integer userId = (Integer)call.getArgs()[0];
		boolean doFlag = confirmTransaction(userId);
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("unBindCheckPointcut()")
	public void unBindCheckBefore(JoinPoint call) {
		Integer userId = (Integer)call.getArgs()[0];
		boolean doFlag = confirmTransaction(userId);
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("topUpHfPrePointcut()")
	public void topUpHfPreBefore(JoinPoint call) {
		ReqMsg_RegularBuy_Order req = (ReqMsg_RegularBuy_Order)call.getArgs()[0];
		boolean doFlag = confirmTransaction(req.getUserId());
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("topUpHfConfirmPointcut()")
	public void topUpHfConfirmBefore(JoinPoint call) {
		ReqMsg_RegularBuy_Order req = (ReqMsg_RegularBuy_Order)call.getArgs()[0];
		boolean doFlag = confirmTransaction(req.getUserId());
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("topUpHfEBankPointcut()")
	public void topUpHfEBankBefore(JoinPoint call) {
		ReqMsg_RegularBuy_EBankBuy req = (ReqMsg_RegularBuy_EBankBuy)call.getArgs()[0];
		boolean doFlag = confirmTransaction(req.getUserId());
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}

	@Before("withdrawSitePointcut()")
	public void withdrawSiteBefore(JoinPoint call) {
		ReqMsg_UserBalance_Withdraw req = (ReqMsg_UserBalance_Withdraw)call.getArgs()[0];
		boolean doFlag = confirmTransaction(req.getUserId());
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}

	@Before("userBonusWithdrawPointcut()")
	public void userBonusWithdrawBefore(JoinPoint call) {
		Integer userId = (Integer)call.getArgs()[0];
		boolean doFlag = confirmTransaction(userId);
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}	
	
	@Before("withdrawCheckPassPointcut()")
	public void withdrawCheckPassBefore(JoinPoint call) {
		ReqMsg_UserBalance_Withdraw req = (ReqMsg_UserBalance_Withdraw)call.getArgs()[0];
		boolean doFlag = confirmTransaction(req.getUserId());
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("balanceBuyPointcut()")
	public void balanceBuyBefore(JoinPoint call) {
		ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy)call.getArgs()[0];
		boolean doFlag = confirmTransaction(req.getUserId());
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	@Before("balanceBuyAUTHPointcut()")
	public void balanceBuyAUTHBefore(JoinPoint call) {
		ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy)call.getArgs()[0];
		boolean doFlag = confirmTransaction(req.getUserId());
		if(!doFlag){
			 throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
		}
	}
	
	/**
	 * 确认交易是否可执行
	 * @return
	 */
	private boolean confirmTransaction(Integer userId) {

		//对应用户状态冻结中则返回false
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIdEqualTo(userId).andStatusEqualTo(BsUserStatus.USER_STATUS_FREEZE.getIntValue());
		List<BsUser> list = bsUserMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 确认登录操作是否可执行
	 * @param mobileOrNick
	 * @return
	 */
	private boolean confirmTransaction4Login(String mobileOrNick) {

		//对应用户状态冻结中则返回false
		BsUserExample mobileExample = new BsUserExample();
		mobileExample.createCriteria().andMobileEqualTo(mobileOrNick).andStatusEqualTo(BsUserStatus.USER_STATUS_FREEZE.getIntValue());
		BsUserExample nickExample = new BsUserExample();
		nickExample.createCriteria().andNickEqualTo(mobileOrNick).andStatusEqualTo(BsUserStatus.USER_STATUS_FREEZE.getIntValue());
		List<BsUser> mobileList = bsUserMapper.selectByExample(mobileExample);
		List<BsUser> nickList = bsUserMapper.selectByExample(nickExample);
		if (CollectionUtils.isNotEmpty(mobileList) || CollectionUtils.isNotEmpty(nickList)) {
			return false;
		}
		return true;
	}
	
}
