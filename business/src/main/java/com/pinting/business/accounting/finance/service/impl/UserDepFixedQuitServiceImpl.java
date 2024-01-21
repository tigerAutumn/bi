package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.finance.service.UserDepFixedQuitAccountService;
import com.pinting.business.accounting.finance.service.UserDepFixedQuitService;
import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_PlatformTransfer;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_PlatformTransfer;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 
 * @project business
 * @title UserDepFixedQuitServiceImpl.java
 * @author Dragon & cat
 * @date 2017-4-6
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 * 
 */
@Service
public class UserDepFixedQuitServiceImpl implements UserDepFixedQuitService {
	private final Logger logger = LoggerFactory.getLogger(UserDepFixedQuitServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private TransactionTemplate transactionTemplate;
	@Autowired
	private		BsSubAccountMapper  bsSubAccountMapper;	
	@Autowired
	private 	BsAccountMapper	bsAccountMapper;
	@Autowired
	private		BsUserMapper  bsUserMapper;
	@Autowired
	private		UserDepFixedQuitAccountService userDepFixedQuitAccountService;
	@Autowired
	private		HfbankTransportService hfbankTransportService;
	@Autowired
	private 	BsSysConfigMapper  bsSysConfigMapper;
	@Autowired
	private 	BsAccountJnlMapper  bsAccountJnlMapper;
	@Autowired
	private 	BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private 	BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
	@Autowired
	private 	UserReturnMoneyService userReturnMoneyService;
	@Autowired
	private 	BsProductMapper bsProductMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
    @Autowired
    private	BsDepositionReturnMapper bsDepositionReturnMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private BsDepositionQuitApplyMapper bsDepositionQuitApplyMapper;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
	
	@Override
	public void quit(final Integer subAccountId) {
		/**
		 * 前置判断是否存在ln_finance_repay_schedule表中状态为INIT 、REPAYING的数据，存在则告警，修改退出申请表,预计执行时间+1天，不继续其他操作
		 * 
		 * 1、判断是否需要补息   根据理财本金、计息天数、利率计算应收利息，
		 * 应补息金额 = 应收利息 - 已得利息 ，如果应补息金额>0 则需要进行补息操作 ，否则直接进行记账
		 * 
		 * 		需要补息
		 * 		1.1 检查平台转个人剩余额度
		 * 			平台转个人额度有限制 （  每月每个人10万 -->2017年6月28日 恒丰不是按照每人每月计算，按照平台每月10万计算）
		 * 			需要查配置表中月限额，然后统计平台在自然月内平台转个人已发生金额，计算剩余额度
		 * 
		 * 		
		 * 
		 * 		1.2 如果平台转个人剩余额度 < 应补息金额 -->奖励金补息
		 * 			用户站岗户变动金额= 平台转个人剩余额度
		 * 			平台自有子账户-
		 * 			投资人投资户+
		 * 			
		 * 			奖励金补息金额 = 应补息金额 - 平台转个人剩余额度
		 * 			用户奖励金户+
		 * 		1.3 如果平台转个人剩余额度 >= 应补息金额 -->直接补息
		 * 			用户站岗户变动金额=应补息金额
		 * 			平台自有子账户-
		 * 			投资人投资户+
		 * 
		 * 2、退出记账
		 * 		用户站岗户+（包含补息金额或剩余额度）
		 * 		自由子账户-
		 * 		系统回款户+
		 * 3、生成回款计划
		 */
		final BsSubAccount bsSubAccount = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
		final BsAccount bsAccount = bsAccountMapper.selectByPrimaryKey(bsSubAccount.getAccountId());
		final BsUser bsUser = bsUserMapper.selectByPrimaryKey(bsAccount.getUserId());
		BsDepositionQuitApplyExample quitApplyExample = new BsDepositionQuitApplyExample();
		quitApplyExample.createCriteria().andSubAccountIdEqualTo(subAccountId).andUserIdEqualTo(bsUser.getId());
		final List<BsDepositionQuitApply> quitApplies = bsDepositionQuitApplyMapper.selectByExample(quitApplyExample);
		
		Integer countNotRepaied = lnFinanceRepayScheduleMapper.countNotRepaied(subAccountId);
		if (countNotRepaied != null && countNotRepaied >0){
			logger.info("=================={存管系统固定期限产品退出服务}产品子账户编号"+subAccountId+"对应理财人还款计划表仍有数据初始或支付中=================");
			specialJnlService.warnDevelop4Fail(null, "退出失败", null, "产品子账户编号"+subAccountId+"对应理财人还款计划表仍有数据初始或支付中",true);
			//修改退出申请表,预计执行时间+1天
			BsDepositionQuitApply apply = new BsDepositionQuitApply();
			apply.setId(quitApplies.get(0).getId());
			apply.setPlanDate(DateUtil.addDays(quitApplies.get(0).getPlanDate(), 1));
			apply.setUpdateTime(new Date());
			apply.setStatus(Constants.DEP_QUIT_APPLY_PASS);
			bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(apply);
			
			throw new PTMessageException(PTMessageEnum.PRODUCT_EXIT_CECK_ERROR);
		}
		
		
		final Double totalPrincipal = bsSubAccountMapper.selectTotalPrincipal(bsSubAccount.getId());
		BsProduct bsProduct = bsProductMapper.selectByPrimaryKey(bsSubAccount.getProductId());
		
		
		
		BsHfbankUserExtExample userExtExample = new BsHfbankUserExtExample();
		userExtExample.createCriteria().andUserIdEqualTo(bsUser.getId());
		final List<BsHfbankUserExt> userExts = bsHfbankUserExtMapper.selectByExample(userExtExample);
		
		
		
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_QUIT.getKey());

			if(Constants.SUBACCOUNT_STATUS_FINANCING.equals(bsSubAccount.getStatus()) ){
				BsSubAccount subAccount = new BsSubAccount();
				subAccount.setId(bsSubAccount.getId());
				subAccount.setStatus(Constants.SUBACCOUNT_STATUS_SETTLEING);
				bsSubAccountMapper.updateByPrimaryKeySelective(subAccount);
			}

			/*1、判断是否需要补息   根据理财本金、计息天数、利率计算应收利息*/
			logger.info("========={存管系统固定期限产品退出服务}"+subAccountId+"开始计算补息金额=========");

			//校验是否已经做过退出操作
			BsDepositionReturnExample example = new BsDepositionReturnExample();
			example.createCriteria().andUserIdEqualTo(bsUser.getId()).andSubAccountIdEqualTo(subAccountId);
			List<BsDepositionReturn>  depositionReturnList = bsDepositionReturnMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(depositionReturnList)) {
				logger.info("========={存管系统固定期限产品退出服务}回款表已存在对应回款记录=========");
				throw new PTMessageException(PTMessageEnum.HF_QUIT_USER_RETURN_MONEY_EXIST);
			}

			if (CollectionUtils.isEmpty(userExts)) {
				logger.info("========={存管系统固定期限产品退出服务}查询不到用户恒丰存管注册信息=========");
				throw new PTMessageException(PTMessageEnum.USER_INFO_NOT_FOUND);
			}


			Double fillRateInterest = 0d;
			Integer investDays = 0;

			if (bsProduct.getTerm() < 0 ) {
				investDays = - bsProduct.getTerm() ;
			}else if (bsProduct.getTerm() == 12) {
				investDays = 365;
			}else {
				investDays = bsProduct.getTerm() * 30;
			}
			//investDays = DateUtil.getDiffeDay(new Date(), bsSubAccount.getInterestBeginDate());
			logger.info("========={存管系统固定期限产品退出服务}投资总金额:"+totalPrincipal+";投资计息天数："+investDays+"=========");

			Double totalInterest = CalculatorUtil.calculate("a*a*a/a", totalPrincipal,bsSubAccount.getProductRate(),investDays.doubleValue(),36500d);
					//MoneyUtil.defaultRound(totalPrincipal * bsSubAccount.getProductRate() * investDays /36500).doubleValue();

			Double earnedInterest = MoneyUtil.subtract(bsSubAccount.getBalance(), totalPrincipal).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


			logger.info("========={存管系统固定期限产品退出服务}用户应收总利息"+totalInterest+";用户已获得总利息"+earnedInterest+"=========");
			fillRateInterest = MoneyUtil.subtract(totalInterest, earnedInterest).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			logger.info("========={存管系统固定期限产品退出服务}应补息金额"+fillRateInterest+"=========");

			/*2、退出记账*/
			BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.DEP_PLATFORM_TRANSFER);
			Double userPlatTransAmount  = 0d;
			//统计平台当月平台转个人已使用额度    USER_DEP_FIXED_QUIT_FILL_INTEREST
			userPlatTransAmount =  bsAccountJnlMapper.selectUsedPlatTransAmount();
			logger.info("========={存管系统固定期限产品退出服务}平台转个人已使用额度"+userPlatTransAmount+"=========");
			Double userLeftPlatTransAmount = MoneyUtil.subtract(Double.parseDouble(bsSysConfig.getConfValue()), userPlatTransAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			logger.info("========={存管系统固定期限产品退出服务}平台转个人剩余额度"+userLeftPlatTransAmount+"=========");


			Double fillAmount = 0d; //平台转个人补息金额
			Double bonus = 0d; //奖励金补息部分
			Double overflowInterest = 0d; //溢出利息
			String trsOrderNo = Util.generateSysOrderNo("HTS");
			if (fillRateInterest > 0) {
				if (userLeftPlatTransAmount < fillRateInterest) {
					fillAmount = userLeftPlatTransAmount;
					bonus = MoneyUtil.subtract(fillRateInterest, userLeftPlatTransAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					logger.info("========={存管系统固定期限产品退出服务}平台转个人剩余额度小于需要补息的金额，需要平台转个人和奖励金共同进行补息。平台转个人补息金额："+fillAmount+";奖励金补息部分："+bonus+"=========");
				}else {
					fillAmount = fillRateInterest;
					logger.info("========={存管系统固定期限产品退出服务}平台转个人剩余额度大于或等于需要补息的金额，只用平台转个人进行补息，补息金额："+fillAmount+"=========");
				}
				
				//如果补息平台转个人部分为0（个人当月平台转个人额度全部用完，剩余额度为0 的情况，此时需要全部用奖励金进行补息）
				if (fillAmount>0) {
					logger.info("========={存管系统固定期限产品退出服务}订单号"+trsOrderNo+"=========");
					Date transTime = new Date();
					//订单表插入
					BsPayOrders order = new BsPayOrders();
					order.setOrderNo(trsOrderNo);
					order.setAmount(fillAmount);
					order.setUserId(bsUser.getId());
					order.setSubAccountId(bsSubAccount.getId());
					order.setStatus(Constants.ORDER_STATUS_CREATE);
					order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
					order.setTerminalType(null);
					order.setCreateTime(transTime);
					order.setUpdateTime(transTime);
					order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
					order.setTransType(Constants.TRANS_DEP_FILL_INTEREST);
					order.setMobile(bsUser.getMobile());
					order.setIdCard(bsUser.getIdCard());
					order.setUserName(bsUser.getUserName());
					order.setChannel(Constants.CHANNEL_HFBANK);
					order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
					bsPayOrdersMapper.insertSelective(order);
					//新增订单流水表
					BsPayOrdersJnl jnl = new BsPayOrdersJnl();
					jnl.setOrderId(order.getId());
					jnl.setOrderNo(order.getOrderNo());
					jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
					jnl.setTransAmount(order.getAmount());
					jnl.setSysTime(transTime);
					jnl.setUserId(bsUser.getId());
					jnl.setSubAccountId(bsSubAccount.getId());
					jnl.setCreateTime(transTime);
					bsPayOrdersJnlMapper.insertSelective(jnl);


					//调用恒丰存管接口    平台自有子账户-   用户投资户 +
					B2GReqMsg_HFBank_PlatformTransfer platformTransferReq = new B2GReqMsg_HFBank_PlatformTransfer();
					platformTransferReq.setOrder_no(trsOrderNo);
					platformTransferReq.setPartner_trans_date(order.getCreateTime());
					platformTransferReq.setPartner_trans_time(order.getCreateTime());
					platformTransferReq.setPlat_account("01");
					platformTransferReq.setAmount(String.valueOf(fillAmount));
					platformTransferReq.setPlatcust(userExts.get(0).getHfUserId());
					platformTransferReq.setCause_type(Constants.HF_CAUSE_TYPE_FEE);
					platformTransferReq.setRemark("手续费返还");
					B2GResMsg_HFBank_PlatformTransfer platformTransferRes = new B2GResMsg_HFBank_PlatformTransfer();
					try {
						platformTransferRes =  hfbankTransportService.platformTransfer(platformTransferReq);
					} catch (Exception e) {
						logger.info("========={存管系统固定期限产品退出服务}平台转个人补息失败:通讯失败=========");
						BsPayOrders ordersFail = new BsPayOrders();
						ordersFail.setId(order.getId());
						ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
						ordersFail.setReturnCode(platformTransferRes.getRecode());
						ordersFail.setReturnMsg(platformTransferRes.getRemsg());
						ordersFail.setUpdateTime(new Date());

						bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
						BsPayOrdersJnl jnlFail = new BsPayOrdersJnl();
						jnlFail.setOrderId(order.getId());
						jnlFail.setOrderNo(order.getOrderNo());
						jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
						jnlFail.setTransAmount(order.getAmount());
						jnlFail.setSysTime(new Date());
						jnlFail.setUserId(bsUser.getId());
						jnlFail.setSubAccountId(order.getSubAccountId());
						jnlFail.setCreateTime(new Date());
						bsPayOrdersJnlMapper.insertSelective(jnlFail);


						BsDepositionQuitApply quitApply = new BsDepositionQuitApply();
						quitApply.setId(quitApplies.get(0).getId());
						quitApply.setStatus(Constants.DEP_QUIT_APPLY_FAIL);
						quitApply.setOperateTime(new Date());
						quitApply.setUpdateTime(new Date());
						bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(quitApply);

						specialJnlService.warn4Fail(order.getAmount(), "退出补息平台转账个人订单["+trsOrderNo+"]失败", trsOrderNo, "退出补息平台转账个人", true);
						throw new PTMessageException(PTMessageEnum.DEP_PLATFORM_TRANS_ORDER_ERROR, platformTransferRes.getRemsg());
					}

					//成功
					if(ConstantUtil.BSRESCODE_SUCCESS.equals(platformTransferRes.getResCode()) && Constants.DEP_RECODE_SUCCESS.equals(platformTransferRes.getRecode())){
						logger.info("========={存管系统固定期限产品退出服务}平台转个人补息成功=========");
						BsPayOrders ordersSucc = new BsPayOrders();
						ordersSucc.setId(order.getId());
						ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
						ordersSucc.setUpdateTime(new Date());
						bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);

						BsPayOrdersJnl jnlSucc = new BsPayOrdersJnl();
						jnlSucc.setOrderId(order.getId());
						jnlSucc.setOrderNo(order.getOrderNo());
						jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
						jnlSucc.setTransAmount(order.getAmount());
						jnlSucc.setSysTime(new Date());
						jnlSucc.setUserId(bsUser.getId());
						jnlSucc.setSubAccountId(order.getSubAccountId());
						jnlSucc.setCreateTime(new Date());
						bsPayOrdersJnlMapper.insertSelective(jnlSucc);
					}
					//失败
					else{
						logger.info("========={存管系统固定期限产品退出服务}平台转个人补息失败=========");
						BsPayOrders ordersFail = new BsPayOrders();
						ordersFail.setId(order.getId());
						ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
						ordersFail.setReturnCode(platformTransferRes.getRecode());
						ordersFail.setReturnMsg(platformTransferRes.getRemsg());
						ordersFail.setUpdateTime(new Date());

						bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
						BsPayOrdersJnl jnlFail = new BsPayOrdersJnl();
						jnlFail.setOrderId(order.getId());
						jnlFail.setOrderNo(order.getOrderNo());
						jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
						jnlFail.setTransAmount(order.getAmount());
						jnlFail.setSysTime(new Date());
						jnlFail.setUserId(bsUser.getId());
						jnlFail.setSubAccountId(order.getSubAccountId());
						jnlFail.setCreateTime(new Date());
						bsPayOrdersJnlMapper.insertSelective(jnlFail);

						BsDepositionQuitApply quitApply = new BsDepositionQuitApply();
						quitApply.setId(quitApplies.get(0).getId());
						quitApply.setStatus(Constants.DEP_QUIT_APPLY_FAIL);
						quitApply.setOperateTime(new Date());
						quitApply.setUpdateTime(new Date());
						bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(quitApply);

						specialJnlService.warn4Fail(order.getAmount(), "退出补息平台转账个人订单["+trsOrderNo+"]失败", trsOrderNo, "退出补息平台转账个人", true);
						throw new PTMessageException(PTMessageEnum.DEP_PLATFORM_TRANS_ORDER_ERROR, platformTransferRes.getRemsg());
					}
				}else {
					logger.info("========={存管系统固定期限产品退出服务}平台转个人额度已用完，全部使用奖励金进行补息"+bonus+"=========");
				}
			}else {
				//利息溢出
				overflowInterest = -fillRateInterest;
			}

			final Double  fillAmountFin = fillAmount;
			final Double  totalInterestFin = totalInterest;
			final Double  overflowInterestFin = overflowInterest;
			final Double  bonusFin = bonus;

			try {
				transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {

						BaseAccount baseAccount = new BaseAccount();
						baseAccount.setPartner(SubAccountCode.getPartnerByAuthCode(bsSubAccount.getProductType()));

						//相关记账操作
						userDepFixedQuitAccountService.depQuitAccount(subAccountId, bsUser.getId(), fillAmountFin,
								totalPrincipal, totalInterestFin, overflowInterestFin ,baseAccount);

						/*3、生成回款计划 */
						logger.info("========={存管系统固定期限产品退出服务}回款计划生成=========");
						Double penalty = 0d;
						userReturnMoneyService.depGeneratePlans(subAccountId, bsAccount.getUserId(), totalPrincipal, totalInterestFin, penalty, bonusFin, overflowInterestFin);


					}
				});
			} catch (Exception e) {
				logger.info("========={存管系统固定期限产品退出服务}记账或者生成回款计划失败========="+e);
				specialJnlService.warn4Fail(fillAmount, "退出记账或者生成回款计划失败", trsOrderNo, "出记账或者生成回款计划失败",true);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return ;
		}finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_QUIT.getKey());
		}
		

	}

}
