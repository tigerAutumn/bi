package com.pinting.business.accounting.service.impl;

import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.accounting.service.RecordAccountingService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.business.model.vo.BsUserAssetVO;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_BuyProductResult;
import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;
import com.pinting.gateway.hessian.message.dafy.model.ReceiveMoneyDetail;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Project: business
 * @Title: PayOrdersServiceImpl.java
 * @author Zhou Changzai
 * @date 2015-2-28 上午11:41:40
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class PayOrdersServiceImpl implements PayOrdersService{
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private BsPayOrdersMapper payOrderMapper;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private BsSpecialJnlService specialJnlService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private RecordAccountingService recordService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private AccountHandleService accountHandleService;
	@Autowired
	private RecordAccountingService recordAccountingService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private BsUserTypeAuthMapper bsUserTypeAuthMapper;
	@Autowired
	private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
	@Autowired
	private BsUserTransDetailMapper bsUserTransDetailMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private SendWechatService sendWechatService;
	
	private Logger log = LoggerFactory.getLogger(PayOrdersServiceImpl.class);
	
	@Override
	@Transactional(noRollbackFor=PTMessageException.class)
	@Deprecated
	public synchronized boolean finishBuyProduct(G2BReqMsg_DafyPayment_BuyProductResult req) {
		BsPayOrders order = null;
		log.info("[PayOrdersServiceImpl实例：]" + this.toString() + ",thread:" + Thread.currentThread().getName());
		String orderNo = req.getOrderNo();
		BsPayOrdersExample example = new BsPayOrdersExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(example);
		order = orderList.get(0);
		
		if(order == null){//查无此订单
			throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
		}
		
		if(order.getStatus() == Constants.ORDER_STATUS_SUCCESS){//查到订单，并且已成功，此时是达飞发起重复调用，应予以拒绝
			throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
		}
		//达飞返回失败
		//更新订单信息表
		if(req.getResult() == G2BReqMsg_DafyPayment_BuyProductResult.BUY_RESULT_FAIL){
			order.setStatus(Constants.ORDER_STATUS_FAIL);
			order.setUpdateTime(req.getSuccessTime());
			payOrderMapper.updateByPrimaryKeySelective(order);
			return true;
		}
		
		
		//如果成功
		// 1.往结算户充值 （同时记录一条结算户充值流水）
		// 2.结算户转账到子账户（同时记录一条从结算户到子账户的转账信息流水）
		// 3.更新订单信息表
		// 4.更新产品起息日期（已达飞通知时间+1d为准）///Zhou Changzai 2015-11-13 15:00:00 以及last_finish_interest_date字段（考虑跨日问题）---
		// 5.更新产品表，产品购买人数+1
		//获取对应的订单

		//对比金额是否一致
		if(order.getAmount() != req.getAmount()){
			specialJnlService.addSpecialJnl("【购买结果通知】", "【订单金额和实际金额不一致，订单金额：" + order.getAmount() + "，实际金额：" + req.getAmount() + "】");
			throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_AMOUNT_NOT_SAME);
		}
		
		//1.往结算户充值
		BsAccountJnl jnl = new BsAccountJnl();
		jnl.setId(order.getStartJnlNo());
		jnl.setTransTime(new Date());
		jnl.setTransCode(Constants.USER_TOP_UP);
		jnl.setTransAmount(req.getAmount());
		jnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		jnl.setUserId1(order.getUserId());
		jnl.setRespCode(req.getResult() + "");
		recordService.postfRecordAccounting(jnl);
		log.debug("往结算户充值：" + jnl.toString());
		
		//2.结算户转账到子账户(购买)
		jnl = new BsAccountJnl();
		jnl.setTransTime(new Date());
		jnl.setTransCode(Constants.USER_CARD_BUY_PRODUCT);
		jnl.setTransAmount(req.getAmount());
		jnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		jnl.setUserId1(order.getUserId());
		jnl.setRespCode(req.getResult() + "");
		jnl.setSubAccountId2(order.getSubAccountId());
		recordService.postfRecordAccounting(jnl);
		log.debug("结算户转账到子账户(购买)：" + jnl.toString());
		
		//3.更新订单信息
		order.setStatus(Constants.ORDER_STATUS_SUCCESS);
		order.setBankName(req.getBankName());
		order.setMoneyType(req.getMoneyType());
		order.setUpdateTime(req.getSuccessTime());
		order.setEndJnlNo(jnl.getId());
		payOrderMapper.updateByPrimaryKeySelective(order);
		log.debug("更新订单信息：" + order.toString());
		
		//4.更新产品起息日期（已达飞通知时间+1d为准）
		BsSubAccount subAccount = subAccountService.findSubAccountById(order.getSubAccountId());
		BsProduct product = productService.findRegularById(subAccount.getProductId());
		
		BsSubAccount tempSubAccount = new BsSubAccount();
		tempSubAccount.setId(order.getSubAccountId());
		tempSubAccount.setInterestBeginDate(DateUtil.addDays(req.getSuccessTime(), 1));
		Date tomorrow = DateUtil.addDays(req.getSuccessTime(), 1);
		tempSubAccount.setLastFinishInterestDate(DateUtil.addDays(tomorrow, product.getTerm4Day()));//Zhou Changzai 2015-11-13 15:00:00 修复跨日问题
		subAccountService.modifySubAccountById(tempSubAccount);
		log.debug("更新产品起息日期：" + tempSubAccount.getInterestBeginDate());
		
		//5.更新产品表
		BsProduct productModify = new BsProduct();
		productModify.setId(product.getId());
		productModify.setInvestNum(product.getInvestNum() + 1);
		productModify.setCurrTotalAmount(MoneyUtil.add(product.getCurrTotalAmount(), order.getAmount()).doubleValue());
		productService.modifyProductById(productModify);
		log.debug("更新产品表：" + productModify.toString());
		
		//5.推荐奖励 处理（推荐人结算户增加奖励金）
		BsUser beRecommendUser = userService.findUserById(order.getUserId());
		if(beRecommendUser.getRecommendId() != null 
				&& !Constants.USER_TYPE_PROMOT.equals(beRecommendUser.getUserType())){
			BsUser recommendUser = userService.findUserById(beRecommendUser.getRecommendId());
			if(recommendUser != null){
				BsAccountJnl bonusJnl = new BsAccountJnl();
				bonusJnl.setTransTime(new Date());
				bonusJnl.setTransCode(Constants.USER_RECOMMEND_BONUS);
				Double pushMoney = 0d;
				//1个月理财与其他定期理财 推荐奖励比率不同
				if(Constants.PRODUCT_CODE_1_70.equals(product.getCode()) || 
						Constants.PRODUCT_CODE_1_120.equals(product.getCode()) ||
						Constants.PRODUCT_CODE_1_75.equals(product.getCode()) ||
						Constants.PRODUCT_CODE_1_132.equals(product.getCode()) ||
						Constants.PRODUCT_CODE_1_90.equals(product.getCode())||
						Constants.PRODUCT_CODE_1_100.equals(product.getCode())){
					BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.PUSH_MONEY_RATE_FOR_ONE_MONTH);
					Double pushMoneyRate = MoneyUtil.divide(Double.valueOf(bsSysConfig.getConfValue()), 100, 4).doubleValue();
					pushMoney = MoneyUtil.multiply(req.getAmount(), pushMoneyRate).doubleValue();
				}else{
					BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.PUSH_MONEY_RATE);
					Double pushMoneyRate = MoneyUtil.divide(Double.valueOf(bsSysConfig.getConfValue()), 100, 4).doubleValue();
					pushMoney = MoneyUtil.multiply(req.getAmount(), pushMoneyRate).doubleValue();
				}
				bonusJnl.setTransAmount(pushMoney);
				bonusJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
				bonusJnl.setUserId1(recommendUser.getId());
				bonusJnl.setUserId2(order.getUserId());
				recordService.postfRecordAccounting(bonusJnl);
				log.debug("往推荐人结算户增加推荐奖励金：" + bonusJnl.toString());
			}
		}
		return true;
	}
		
	
	@Override
	public boolean addPayOrder(BsPayOrders bsPayOrders) {
		return payOrderMapper.insertSelective(bsPayOrders) == 1;
	}
	@Override
	public boolean modifyPayOrderByOrderNo(BsPayOrders bsPayOrders) {
		BsPayOrdersExample example = new BsPayOrdersExample();
		example.createCriteria().andOrderNoEqualTo(bsPayOrders.getOrderNo());
		return payOrderMapper.updateByExampleSelective(bsPayOrders, example) == 1;
	}

	@Override
	@Transactional
	public String preBuyProduct(int bankId,int userId, double amount, BsProduct product) {
		/**
		 * 1.判断是否已经实名认证，绑定银行卡，是否由此产品的购买权限
		 * 2.判断购买次数是否充足，判断购买金额是否低于下限，高于上限,判断累计购买金额是否充足
		 * 3.插入产品子账户，当前状态为银行处理中
		 * 4.购买流水前置登记
		 * 5.新增订单
		 */
		
		BsUser user = userService.findUserById(userId);
		if(user.getIsBindName() == Constants.IS_BIND_NAME_NO){ //1.判断是否已经实名认证
			throw new PTMessageException(PTMessageEnum.NAME_NOT_BIND);
		}
		
		if(user.getIsBindName() == Constants.IS_BIND_BANK_NO){ //1.判断是会否已经绑定银行卡
			throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
		}
		
		//1、根据用户类型，判断用户是否有购买此产品的权限
		String userType = user.getUserType();
		BsUserTypeAuthExample example = new BsUserTypeAuthExample();
		example.createCriteria().andUserTypeEqualTo(userType).andProductIdEqualTo(product.getId());
		List<BsUserTypeAuth> userTypeAuths = bsUserTypeAuthMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(userTypeAuths)){
			throw new PTMessageException(PTMessageEnum.USER_NO_AUTH_FOR_PRODUCT);
		}

		//2.判断当前购买的次数
		int currBuyNum = userService.countBuyNum(userId);
		if(currBuyNum >= product.getMaxInvestTimes()){
			throw new PTMessageException(PTMessageEnum.REGULAR_BUY_NUM_REACHLIMIT);
		}
		
		//2.判断购买金额是否上限 或者是下限
		
		if(product.getMinInvestAmount() >0 && amount < product.getMinInvestAmount()){ //判断购买下限
			throw new PTMessageException(PTMessageEnum.REGULAR_AMOUNT_NOT_MATCH);
		}
		
		
		if(product.getMaxInvestAmount()>0 && amount > product.getMaxInvestAmount()){ //判断购买上限
			throw new PTMessageException(PTMessageEnum.REGULAR_AMOUNT_NOT_MATCH, product.getMaxInvestAmount() + "元");
		}
		
		//2.判断个人累计购买金额是否超过限额
		double surplusAmount = subAccountService.countProductAmountByProductId(product.getId(),userId);
		if(amount > surplusAmount){	//累计购买金额超出限额
			throw new PTMessageException(PTMessageEnum.PRODUCT_MAX_AMOUNT_PERSON_IS_OUT,surplusAmount + "元");
			
		}
		
		//判断产品规模是否超限
		if(MoneyUtil.add(amount, (product.getCurrTotalAmount() == null ? 0d : product.getCurrTotalAmount())).doubleValue() 
				> product.getMaxTotalAmount()){
			throw new PTMessageException(PTMessageEnum.PRODUCT_MAX_AMOUNT_IS_OUT, 
					String.valueOf(product.getMaxTotalAmount() - product.getCurrTotalAmount()));
		}
		
		//查询账户结算户
		BsSubAccount account = subAccountService.findJSHSubAccountByUserId(userId);
		
		//3.开始创建产品子账户表
		BsSubAccount subAccount = new BsSubAccount();
		subAccount.setAccountId(account.getAccountId()); //主账户Id号
		//子账户编码
		String code = accountHandleService.generateProductAccount(product.getId(), userId);
		subAccount.setCode(code);
		
		subAccount.setProductId(product.getId());
		subAccount.setProductCode(product.getCode());
		subAccount.setProductType(product.getType());
		subAccount.setProductName(product.getName());
		subAccount.setProductRate(product.getBaseRate());
		
		subAccount.setOpenBalance(0.0);
		subAccount.setBalance(0.0);
		subAccount.setAvailableBalance(0.0);
		subAccount.setFreezeBalance(0.0);
		subAccount.setCanWithdraw(0.0);
		subAccount.setExtraRate(0.0);
		subAccount.setAccumulationInerest(0.0);
		
		Date tomorrow = DateUtil.addDays(new Date(), 1);
		subAccount.setInterestBeginDate(tomorrow);
		subAccount.setLastFinishInterestDate(DateUtil.addDays(tomorrow, product.getTerm4Day()));
		subAccount.setOpenTime(new Date());
		subAccount.setStatus(Constants.SUBACCOUNT_STATUS_OPEN); //等待第三方支付确定，是否成功
		boolean isSuccess = subAccountService.addSubAccount(subAccount);
		if(!isSuccess){
			throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
		}
		
		int subAccountId = subAccount.getId();
		
		//4.产品购买及插入购买流水
		Date transTime = new Date();
		BsAccountJnl jnl = new BsAccountJnl();
		jnl.setTransTime(transTime);
		jnl.setTransCode(Constants.USER_CARD_BUY_PRODUCT);
		jnl.setTransAmount(amount);
		jnl.setUserId1(userId);
		jnl.setSubAccountCode1(code);
		Integer jnlId = recordAccountingService.preRecordAccounting(jnl);

		//5.新增订单记录(订单表中的子账户为产品子账户)
		String orderNo = Util.generateOrderNo4Dafy(String.valueOf(subAccountId));
		BsPayOrders order = new BsPayOrders();
		order.setAmount(amount);
		order.setChannel(Constants.ORDER_CHANNEL_DAFY);
		order.setCreateTime(transTime);
		order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
		order.setOrderNo(orderNo);
		order.setStatus(Constants.ORDER_STATUS_CREATE);
		order.setSubAccountId(subAccountId);
		order.setType(Constants.ORDER_TYPE_BUY);
		order.setUserId(userId);
		order.setStartJnlNo(jnlId);
		
		if(bankId != -1){ //pc端传入-1
			BsBank bsBank = bankCardService.findBankById(bankId);
			//插入购买银行卡名字
			if(bsBank != null){
				order.setBankName(bsBank.getName());
			}
			order.setTerminalType(Constants.TERMINAL_TYPE_MOBILE);
		}else{
			order.setTerminalType(Constants.TERMINAL_TYPE_PC);
		}
		addPayOrder(order);
		
		return order.getOrderNo();
	}

	@Override
	public InvestmentAmounts queryInvestmentAmount(String channel,
			String productCode, Date queryDate) {
		String beginDate = DateUtil.formatYYYYMMDD(queryDate);
		String endDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(queryDate, 1));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channel", channel);
		paramMap.put("productCode", productCode);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		InvestmentAmounts investmentAmount = payOrderMapper.selectInvestmentAmount(paramMap);
		//查回为null表示 无金额发生，故此处返回对象金额需要赋0
		if(investmentAmount == null){
			investmentAmount = new InvestmentAmounts();
			investmentAmount.setProductCode(productCode);
			investmentAmount.setAmount(0d);
		}
		return investmentAmount;
	}
	

	@Override
	public BsPayOrders findOrderByOrderNo(String orderNo) {
		BsPayOrdersExample example = new BsPayOrdersExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		List<BsPayOrders> list = payOrderMapper.selectByExample(example);
		return (list!=null && list.size() == 1) ? list.get(0) : null;
	}
	
	@Override
	public BsPayOrders findOrderByOrderNoAndDate(String orderNo, Date beginDate, Date endDate) {
		BsPayOrdersExample example = new BsPayOrdersExample();
		example.createCriteria().andOrderNoEqualTo(orderNo).andCreateTimeBetween(beginDate, endDate);
		List<BsPayOrders> list = payOrderMapper.selectByExample(example);
		return (list!=null && list.size() == 1) ? list.get(0) : null;
	}

	@Override
	public List<BsPayOrders> findOrdersByDate(Date beginDate, Date endDate) {
		BsPayOrdersExample example = new BsPayOrdersExample();
		example.createCriteria().andCreateTimeBetween(beginDate, endDate);
		List<BsPayOrders> list = payOrderMapper.selectByExample(example);
		return (list!=null && list.size() > 0) ? list : null;
		
	}

	@Override
	@Transactional
	public boolean receiveMoney(ReceiveMoneyDetail receiveMoneyDetail) {
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_OLD_RECEIVEMONEY.getKey());
			//回款通知状态为结算成功时，产品子账户 状态改为已结算，进行记账
			if(Constants.RECEIVE_MONEY_SUCCESS == receiveMoneyDetail.getStatus()){
				//产品子账户更新
				BsPayOrders order = findOrderByOrderNo(receiveMoneyDetail.getOrderNo());
				if(order != null && order.getAmount() == receiveMoneyDetail.getAmountBj()){
					BsSubAccount productSubAccount = subAccountService.findSubAccountById(order.getSubAccountId());
					if(productSubAccount == null || productSubAccount.getStatus() == Constants.SUBACCOUNT_STATUS_SETTLE){
						log.warn("===============>订单号【" + receiveMoneyDetail.getOrderNo() + "】回款结果重复通知，不进行处理！");
						return false;
					}
					//回款记账
					//用户内部产品子账户转账到JSH记账
					BsAccountJnl jnl = new BsAccountJnl();
					jnl.setTransTime(new Date());
					jnl.setTransCode(Constants.USER_RETURN_2_BALANCE);
					jnl.setTransAmount(receiveMoneyDetail.getAmountBj()+receiveMoneyDetail.getAmountLx());//本息
					jnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
					jnl.setUserId1(order.getUserId());
					jnl.setSubAccountId2(order.getSubAccountId());
//					recordService.postfRecordAccounting(jnl);//废弃通过交易码找实现类方式，把实现迁移到本类 zhouchangzai 201511281150
					receiveMoneyReturn2Balance(jnl);
					//提现（还款）到银行卡
					jnl = new BsAccountJnl();
					jnl.setTransTime(new Date());
					jnl.setTransCode(Constants.USER_RETURN_2_BANK_CARD);
					jnl.setTransAmount(receiveMoneyDetail.getAmountBj()+receiveMoneyDetail.getAmountLx());//本息
					jnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
					jnl.setUserId1(order.getUserId());
					jnl.setSubAccountCode2(receiveMoneyDetail.getBankNo());
//					recordService.postfRecordAccounting(jnl);//废弃通过交易码找实现类方式，把实现迁移到本类 zhouchangzai 201511281150
					receiveMoneyBalance2Card(jnl);
					
					// 用户表 当前投资收益字段 减去 利息金额
					if (receiveMoneyDetail.getAmountLx() > 0) {
						BsUserAssetVO userVO = userService.findUserAssetByUserId(order.getUserId());
						if(userVO.getInvestNum() == 0){
							BsUser tempUser = new BsUser();
							tempUser.setId(order.getUserId());
							tempUser.setCurrentInterest(0d);
							userService.updateBsUser(tempUser);
						}else if(userVO.getInvestNum() > 0) {
							userService.modifyCurrInterestByIdAndIncrement(
									order.getUserId(),
									-receiveMoneyDetail.getAmountLx());
						}
					}
					//如果回款成功，记录用户交易明细表20151127 12:00 zhouchangzai start
					//新增用户交易明细表
					Date now = new Date();
					BsUserTransDetail detail = new BsUserTransDetail();
					detail.setUserId(order.getUserId());
					detail.setCardNo(receiveMoneyDetail.getBankNo());
					detail.setTransType(Constants.Trans_TYPE_RETURN);
					detail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
					detail.setTransDesc("到期回款");
					detail.setOrderNo(order.getOrderNo());
					detail.setReturnCode(null);
					detail.setReturnMsg(null);
					detail.setAmount(-order.getAmount());
					detail.setCreateTime(now);
					detail.setUpdateTime(now);
					bsUserTransDetailMapper.insertSelective(detail);
					//end
					
					// 短信通知客户回款成功
					BsUser user = userService.findUserById(order.getUserId());
					Double totalAmount = MoneyUtil.add(receiveMoneyDetail.getAmountBj(), receiveMoneyDetail.getAmountLx()).doubleValue();
					/*String message = "您有一笔投资已回款，总额：￥" + totalAmount + "元，其中本金：￥"
							+ receiveMoneyDetail.getAmountBj() + "元，利息：￥"
							+ receiveMoneyDetail.getAmountLx()
							+ "元，如有疑问请拨打400-806-1230。";
					smsService.sendMessage(user.getMobile(), message);*/
					String bankCardNo = order.getBankCardNo();
					if(StringUtil.isEmpty(bankCardNo)){
						BsBankCard card = bankCardService.findFirstBankCardByUserId(order.getUserId());
						bankCardNo = card!=null?card.getCardNo().substring(card.getCardNo().length()-4,card.getCardNo().length()):"";
					}else{
						bankCardNo = bankCardNo.substring(bankCardNo.length()-4,bankCardNo.length());
					}
					smsServiceClient.sendTemplateMessage(user.getMobile(), TemplateKey.RETURN_SUCCESS2CARD, bankCardNo, 
							totalAmount.toString(), String.valueOf(receiveMoneyDetail.getAmountBj()), String.valueOf(receiveMoneyDetail.getAmountLx())); 
					//发送微信模板消息
					sendWechatService.paymentMgs2Card(order.getUserId(),"",totalAmount.toString(), String.valueOf(receiveMoneyDetail.getAmountBj()),
							String.valueOf(receiveMoneyDetail.getAmountLx()), bankCardNo);
				}else{
					log.error("===============>订单号【" + receiveMoneyDetail.getOrderNo() + "】数据不符，不进行处理！");
					specialJnlService.addSpecialJnl("【回款结果通知】", "订单号【" + receiveMoneyDetail.getOrderNo() + "】达飞通知的数据不符，请向达飞核实该条信息！");
					return false;
				}
			}else{
				log.warn("===============>订单号【" + receiveMoneyDetail.getOrderNo() + "】回款状态为失败，不进行处理！");
				return true;
			}
			return true;
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_OLD_RECEIVEMONEY.getKey());
		}
		
		
	}

	@Transactional(rollbackFor=Exception.class)
	private void receiveMoneyReturn2Balance(BsAccountJnl accountJnl)  {
		log.info("======交易【用户内部产品子账户转账到JSH记账】：执行开始======");
		
		log.info("======交易【用户内部产品子账户转账到JSH记账】：结算户余额增加======");
		BsSubAccount userSubAccount = bsSubAccountMapper.selectJSHSubAccountByUserId(accountJnl.getUserId1());
		//为防止并发问题， 结算户余额修改 这里作增量修改
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(userSubAccount.getId());
		userTmpSubAccount.setBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setAvailableBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(accountJnl.getTransAmount());
		bsSubAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		
		log.info("======交易【用户内部产品子账户转账到JSH记账】：产品设置为已结算======");
		//产品设置为已结算
		BsSubAccount productAccount = new BsSubAccount();
		productAccount.setId(accountJnl.getSubAccountId2());
		productAccount.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
		BsSubAccountExample subAccountExample = new BsSubAccountExample();
		subAccountExample.createCriteria().andIdEqualTo(accountJnl.getSubAccountId2());
		bsSubAccountMapper.updateByExampleSelective(productAccount, subAccountExample);
		
		log.info("======交易【用户内部产品子账户转账到JSH记账】：流水记账======");
		//记流水表
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_TRANSFER);
		bsAccountJnl.setTransName("回款");//用户内部产品子账户转账到JSH
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);//用户结算户 金额增加
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(userSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId1(userSubAccount.getId());
		bsAccountJnl.setSubAccountCode1(userSubAccount.getCode());
		bsAccountJnl.setBeforeBalance1(userSubAccount.getBalance());
		bsAccountJnl.setBeforeAvialableBalance1(userSubAccount.getAvailableBalance());
		bsAccountJnl.setBeforeFreezeBalance1(userSubAccount.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(MoneyUtil.add(userSubAccount.getBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(userSubAccount.getAvailableBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterFreezeBalance1(userSubAccount.getFreezeBalance());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
		bsAccountJnl.setAccountId2(userSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId2(accountJnl.getSubAccountId2());
		bsAccountJnl.setSubAccountCode2(accountJnl.getSubAccountCode2());
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(accountJnl.getStatus());
		log.debug("======交易【用户内部产品子账户转账到JSH记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);
		log.info("======交易【用户内部产品子账户转账到JSH记账】：执行结束======");
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void receiveMoneyBalance2Card(BsAccountJnl accountJnl)  {
		
		log.info("======交易【结算户回款提现到银行卡记账】：开始执行======");
		log.info("======交易【结算户回款提现到银行卡记账】：结算户金额减少======");
		//结算户金额减少
		BsSubAccount jshSubAccount = subAccountService.findJSHSubAccountByUserId(accountJnl.getUserId1());
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(jshSubAccount.getId());
		userTmpSubAccount.setBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setAvailableBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(-accountJnl.getTransAmount());
		bsSubAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		log.info("======交易【结算户回款提现到银行卡记账】：流水记账开始======");
		//记录流水
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_WITHDRAW);
		bsAccountJnl.setTransName("回款提现");//结算户提现到银行卡
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(jshSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId1(jshSubAccount.getId());
		bsAccountJnl.setSubAccountCode1(jshSubAccount.getCode());
		bsAccountJnl.setBeforeBalance1(jshSubAccount.getBalance());
		bsAccountJnl.setBeforeAvialableBalance1(jshSubAccount.getAvailableBalance() );
		bsAccountJnl.setBeforeFreezeBalance1(jshSubAccount.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(MoneyUtil.subtract(jshSubAccount.getBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jshSubAccount.getAvailableBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterFreezeBalance1(jshSubAccount.getFreezeBalance());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
		bsAccountJnl.setUserId2(accountJnl.getUserId1());
		bsAccountJnl.setSubAccountCode2(accountJnl.getSubAccountCode2());
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		log.debug("======交易【结算户回款提现到银行卡记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);		
		accountJnl.setId(bsAccountJnl.getId());
		log.info("======交易【结算户回款提现到银行卡记账】：执行结束======");
		
	}

	@Override
	public List<BsPayOrders> findBuyBankTypeList() {
		return payOrderMapper.findBuyBankTypeList();
	}


	@Override
	public List<BsPayOrdersVO> findOrderDetailList(String userName,
			String mobile, Date beginTime, Date overTime, Integer status,
			Date startUpdateTime, Date endUpdateTime, String orderNo,
			String transType, int pageNum, int numPerPage,
			String orderDirection, String orderField) {
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		if(userName != null && !"".equals(userName)) {
			bsPayOrdersVO.setUserName(userName);
		}
		if(mobile != null && !"".equals(mobile)) {
			bsPayOrdersVO.setMobile(mobile);
		}
		if(transType != null && !"".equals(transType)) {
			bsPayOrdersVO.setTransType(transType);
		}
		if(beginTime != null) {
			bsPayOrdersVO.setBeginTime(beginTime);
		}
		if(overTime != null) {
			bsPayOrdersVO.setOverTime(overTime);
		}
		if(status != null && !"".equals(status)) {
			bsPayOrdersVO.setStatus(status);
		}
		if(startUpdateTime != null) {
			bsPayOrdersVO.setStartUpdateTime(startUpdateTime);
		}
		if(endUpdateTime != null) {
			bsPayOrdersVO.setEndUpdateTime(endUpdateTime);
		}
		if(orderNo != null && !"".equals(orderNo)) {
			bsPayOrdersVO.setOrderNo(orderNo);
		}
		if(transType != null && !"".equals(transType)) {
			bsPayOrdersVO.setTransType(transType);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			bsPayOrdersVO.setOrderDirection(orderDirection);
			bsPayOrdersVO.setOrderField(orderField);
		}
		bsPayOrdersVO.setPageNum(pageNum);
		bsPayOrdersVO.setNumPerPage(numPerPage);
		return bsPayOrdersJnlMapper.selectOrderDetailList(bsPayOrdersVO);
	}


	@Override
	public int findCountOrderDetail(String userName, String mobile,
			Date beginTime, Date overTime, Integer status, Date startUpdateTime,
			Date endUpdateTime, String orderNo, String transType) {
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		if(userName != null && !"".equals(userName)) {
			bsPayOrdersVO.setUserName(userName);
		}
		if(mobile != null && !"".equals(mobile)) {
			bsPayOrdersVO.setMobile(mobile);
		}
		if(transType != null && !"".equals(transType)) {
			bsPayOrdersVO.setTransType(transType);
		}
		if(beginTime != null) {
			bsPayOrdersVO.setBeginTime(beginTime);
		}
		if(overTime != null) {
			bsPayOrdersVO.setOverTime(overTime);
		}
		if(status != null && !"".equals(status)) {
			bsPayOrdersVO.setStatus(status);
		}
		if(startUpdateTime != null) {
			bsPayOrdersVO.setStartUpdateTime(startUpdateTime);
		}
		if(endUpdateTime != null) {
			bsPayOrdersVO.setEndUpdateTime(endUpdateTime);
		}
		if(orderNo != null && !"".equals(orderNo)) {
			bsPayOrdersVO.setOrderNo(orderNo);
		}
		if(transType != null && !"".equals(transType)) {
			bsPayOrdersVO.setTransType(transType);
		}
		return bsPayOrdersJnlMapper.selectCountOrderDetail(bsPayOrdersVO);
	}


	@Override
	public int updateSelectivePayOrderJnl(BsPayOrdersJnl bsPayOrdersJnl) {
		return  bsPayOrdersJnlMapper.updateByPrimaryKeySelective(bsPayOrdersJnl);
		
	}


	@Override
	public int updateSelectiveBsUserTransDetail(BsUserTransDetail detail) {
		return  bsUserTransDetailMapper.updateByPrimaryKeySelective(detail);
	}


	@Override
	public void insertSelectiveBsUserTransDetail(BsUserTransDetail detail) {
		bsUserTransDetailMapper.insertSelective(detail);
	}


	@Override
	public int userCountTopUp(String mobile, String userName, Integer status,
			Date beginTime, Date overTime) {
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		bsPayOrdersVO.setMobile(mobile);
		bsPayOrdersVO.setUserName(userName);
		bsPayOrdersVO.setStatus(status);
		bsPayOrdersVO.setBeginTime(beginTime);
		bsPayOrdersVO.setOverTime(overTime);
		return payOrderMapper.selectUserCountTopUp(bsPayOrdersVO);
	}


	@Override
	public List<BsPayOrdersVO> findUserTopUp(String mobile, String userName,
			Integer status, Date beginTime, Date overTime, int pageNum,
			int numPerPage, String orderDirection, String orderField) {
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		bsPayOrdersVO.setMobile(mobile);
		bsPayOrdersVO.setUserName(userName);
		bsPayOrdersVO.setStatus(status);
		bsPayOrdersVO.setBeginTime(beginTime);
		bsPayOrdersVO.setOverTime(overTime);
		bsPayOrdersVO.setPageNum(pageNum);
		bsPayOrdersVO.setNumPerPage(numPerPage);
		bsPayOrdersVO.setOrderDirection(orderDirection);
		bsPayOrdersVO.setOrderField(orderField);
		return payOrderMapper.selectUserTopUpList(bsPayOrdersVO);
	}


	@Override
	public double userSumTopUp(String mobile, String userName, Integer status,
			Date beginTime, Date overTime) {
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		bsPayOrdersVO.setMobile(mobile);
		bsPayOrdersVO.setUserName(userName);
		bsPayOrdersVO.setStatus(status);
		bsPayOrdersVO.setBeginTime(beginTime);
		bsPayOrdersVO.setOverTime(overTime);
		return payOrderMapper.selectUserSumTopUp(bsPayOrdersVO);
	}

	/**
	 * 更新订单状态
	 * ！无影响行数即无记录或已最终状态时会throw异常！
	 * @param id       编号
	 * @param status       状态
	 * @param subAccountId 子账户编号
	 * @param returnCode   返回码
	 * @param returnMsg    返回信息
	 */
	@Override
	public void modifyOrderStatus4Safe(Integer id, Integer status, Integer subAccountId, String returnCode, String returnMsg) {
		BsPayOrders tempReguOrder = new BsPayOrders();
		if(subAccountId != null)
			tempReguOrder.setSubAccountId(subAccountId);
		if(StringUtil.isNotEmpty(returnCode))
			tempReguOrder.setReturnCode(returnCode);
		if(StringUtil.isNotEmpty(returnMsg))
			tempReguOrder.setReturnMsg(returnMsg);
		tempReguOrder.setUpdateTime(new Date());
		tempReguOrder.setStatus(status);
		BsPayOrdersExample ordersExample = new BsPayOrdersExample();
		List<Integer> statuses = new ArrayList<Integer>(2);
		statuses.add(Constants.ORDER_STATUS_SUCCESS);
		statuses.add(Constants.ORDER_STATUS_FAIL);
		ordersExample.createCriteria().andIdEqualTo(id).andStatusNotIn(statuses);
		int row = payOrderMapper.updateByExampleSelective(tempReguOrder, ordersExample);
		if(row == 0){
			throw new PTMessageException(PTMessageEnum.ORDER_STATUS_UPDATE_ERROR, "订单["+id+"]未找到或已为最终结果");
		}
	}

	/**
	 * 更新订单状态
	 * ！无影响行数即无记录或已最终状态时会throw异常！
	 *
	 * @param id           编号
	 * @param status       状态
	 * @param subAccountId 子账户编号
	 * @param returnCode   返回码
	 * @param returnMsg    返回信息
	 */
	@Override
	public void modifyLnOrderStatus4Safe(Integer id, Integer status, Integer subAccountId, String returnCode, String returnMsg) {
		LnPayOrders tempReguOrder = new LnPayOrders();
		if(subAccountId != null)
			tempReguOrder.setSubAccountId(subAccountId);
		if(StringUtil.isNotEmpty(returnCode))
			tempReguOrder.setReturnCode(returnCode);
		if(StringUtil.isNotEmpty(returnMsg))
			tempReguOrder.setReturnMsg(returnMsg);
		tempReguOrder.setUpdateTime(new Date());
		tempReguOrder.setStatus(status);
		LnPayOrdersExample ordersExample = new LnPayOrdersExample();
		List<Integer> statuses = new ArrayList<Integer>(2);
		statuses.add(Constants.ORDER_STATUS_SUCCESS);
		statuses.add(Constants.ORDER_STATUS_FAIL);
		ordersExample.createCriteria().andIdEqualTo(id).andStatusNotIn(statuses);
		int row = lnPayOrdersMapper.updateByExampleSelective(tempReguOrder, ordersExample);
		if(row == 0){
			throw new PTMessageException(PTMessageEnum.ORDER_STATUS_UPDATE_ERROR, "订单["+id+"]未找到或已为最终结果");
		}
	}


	@Override
	public void updatePaymentChannelId(Integer id, Integer channelId, String isProtocolPay) {
		LnPayOrders tempReguOrder = new LnPayOrders();
		tempReguOrder.setId(id);
		tempReguOrder.setPaymentChannelId(channelId);
		tempReguOrder.setIsProtocolPay(isProtocolPay);
		tempReguOrder.setUpdateTime(new Date());
		lnPayOrdersMapper.updateByPrimaryKeySelective(tempReguOrder);
	}

}
