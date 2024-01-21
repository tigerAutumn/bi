package com.pinting.business.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.service.ProductPlanStatutsChangeService;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.accounting.service.impl.process.OrderNoticeProcess;
import com.pinting.business.dao.BsFileSealRelationMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.enums.*;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.*;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.manage.ProductReleaseService;
import com.pinting.business.service.site.ActivityLuckyDrawService;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_OrderNotice;
import com.pinting.gateway.out.service.App178TransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 产品购买结果拦截
 * 1、根据理财计划是否满额，修改理财计划状态为结束
 * 2、活动奖励机会发放
 * @Project: business
 * @Title: ProductBuyAspect.java
 * @author dingpf
 * @date 2016-4-22 上午10:39:01
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
@Aspect
@Component
@Order(3)
public class ProductBuyResultAspect {

	private Logger log = LoggerFactory.getLogger(ProductBuyResultAspect.class);

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notify(..))")
	public void pay19Pointcut(){}*/
	
	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.channelNotify(..))")
	public void reapalPointcut(){}*/
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyFixed(..))")
	public void balanceBuyPointcut(){}
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyStage(..))")
	public void balanceAuthPointcut(){}
	
	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notifyEBank(..))")
	public void eBankPointcut(){}*/
	
	@Autowired
	private ProductPlanStatutsChangeService productPlanStatutsChangeService;
	@Autowired
	private ProductReleaseService productReleaseService;
	@Autowired
	private ActivityLuckyDrawService activityLuckyDrawService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private UserService userService;
	@Autowired
	private SignSealService signSealService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private App178TransportService app178TransportService;
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private BsFileSealRelationMapper bsFileSealRelationMapper;
	@Autowired
	private BsUserSealFileMapper bsUserSealFileMapper;
	
	/*@AfterReturning("pay19Pointcut()")
	public void pay19After(JoinPoint call) {
		G2BResMsg_Pay19QuickPay_PayResultNotice res = (G2BResMsg_Pay19QuickPay_PayResultNotice)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		if(extendProMap != null){
			//后置拦截公共处理
			afterCommonDeal(extendProMap);
		}
		res.setExtendMap(extendProMap);
	}*/
	
	/*@AfterReturning("reapalPointcut()")
	public void reapalAfter(JoinPoint call) {
		G2BResMsg_ReapalQuickPay_ReapalNotify res = (G2BResMsg_ReapalQuickPay_ReapalNotify)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		if(extendProMap != null){
			//后置拦截公共处理
			afterCommonDeal(extendProMap);
		}
		res.setExtendMap(extendProMap);
	}*/

	@Before("balanceBuyPointcut()")
	public void balanceBuyBefore(JoinPoint call) {
		// 超级理财人无需购买产品
		superFinanceUserNotNeesBuy(call);
		
		//特定产品，老用户（已有起息中的购买，赞分期产品已借出）校验
		oldUserCheck(call);
		

	}

	@Before("balanceAuthPointcut()")
	public void balanceAuthPointcut(JoinPoint call) {
		// 超级理财人无需购买产品
		superFinanceUserNotNeesBuy(call);
	}

	@AfterReturning("balanceBuyPointcut()")
	public void balanceBuyAfter(JoinPoint call) {
		ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		if(extendProMap != null){
			//普通产品签章
			signSeal(extendProMap);
			//后置拦截公共处理
			afterCommonDeal(extendProMap);
			
			//钱报用户购买产品（普通产品+钱报产品）订单通知
			orderNoticeApp178(extendProMap);
			
			//remove
			extendProMap.remove("product");
			extendProMap.remove("userId");
			extendProMap.remove("investAmount");
		}
		res.setExtendMap(extendProMap);
	}
	@AfterReturning("balanceAuthPointcut()")
	public void balanceAuthAfter(JoinPoint call) {
		ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		if(extendProMap != null){
			//计划产品签章
			signSealAuth(extendProMap);
			
			//后置拦截公共处理
			afterCommonDeal(extendProMap);
			
			//remove
			extendProMap.remove("product");
			extendProMap.remove("userId");
			extendProMap.remove("investAmount");
		}
		res.setExtendMap(extendProMap);
	}
	
	/*@AfterReturning("eBankPointcut()")
	public void eBankAfter(JoinPoint call) {
		G2BResMsg_Pay19NewCounter_NewCounterResultNotice res = (G2BResMsg_Pay19NewCounter_NewCounterResultNotice)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		if(extendProMap != null){
			//后置拦截公共处理
			afterCommonDeal(extendProMap);
		}
		res.setExtendMap(extendProMap);
	}*/
	
	/**
	 * 后置拦截公共处理
	 * @param extendProMap
	 */
	private void afterCommonDeal(HashMap<String, Object> extendProMap) {
		//只针对购买
		Object extendPro = extendProMap.get("product");
		if(extendPro != null){
			BsProduct product = (BsProduct) extendPro;
			//理财满额处理
			productFinishDeal(product);
			//理财奖励机会发放
			Object userId = extendProMap.get("userId");
			Object subAccountId = extendProMap.get("subAccountId");
			Object investAmount = extendProMap.get("investAmount");
			Object showTerminal = product.getShowTerminal();
			if(userId != null && investAmount != null && StringUtil.isNotEmpty(product.getName())){
				// 双十一活动
//				activityLuckyDrawService.addActivityLuckyDrawDouble11((Integer) userId, (Double) investAmount, (Integer)subAccountId);

				// 2017年元宵活动-元宵喜乐会
//				boolean grantBonus = true;
//				if(showTerminal instanceof String) {
//					String showTerminalStr = (String)showTerminal;
//					String[] showArray = showTerminalStr.split(",");
//					for (String show: showArray) {
//						if(StringUtil.isNotBlank(show) && !Constants.PRODUCT_SHOW_TERMINAL_H5.equals(show) && !Constants.PRODUCT_SHOW_TERMINAL_PC.equals(show) && !Constants.PRODUCT_SHOW_TERMINAL_APP.equals(show)) {
//							grantBonus = false;
//							break;
//						}
//					}
//				} else {
//					grantBonus = false;
//				}
//				if(grantBonus) {
//					activityService.userBuyGrantBonus((Integer) userId, (Double) investAmount, (Integer) subAccountId);
//				} else {
//					log.info("用户 {} 购买了非币港湾理财平台产品 {} 元，无元宵活动奖励金，子账户编号：{}", userId, investAmount, subAccountId);
//				}

				// 2017年周年庆活动
//				activityService.anniversaryUnlockGoldIngot((Integer) userId);
			}
			
			//618活动
			/*if(userId != null && investAmount != null && StringUtil.isNotEmpty(product.getName())){
				activityLuckyDrawService.addActivityLuckyDraw((Integer) userId, (Double) investAmount, product.getName());
			}
			//欧洲杯新手专享投资成功发送短信
			if(userId != null && StringUtil.isNotBlank(product.getName()) && investAmount != null && 
					product.getName().indexOf("欧洲杯新手专享")>=0 && (Double) investAmount == 100000){
				BsUser user = userService.findUserById((Integer) userId);
				if(StringUtils.isNotBlank(user.getMobile())){
					smsServiceClient.sendTemplateMessage(user.getMobile(), 
							TemplateKey.BUY_PRODUCT_SUCCESS2GIFT,"苹果 6s plus 64G");
				}
			}*/
			
		}
	}
	

	/**
	 * 判断是否满额，并对于进行结束理财计划处理
	 * @param product
	 */
	private void productFinishDeal(BsProduct product){
		Double leftAmount = MoneyUtil.subtract(product.getMaxTotalAmount(), product.getCurrTotalAmount()).doubleValue();
		//剩余金额小于起投金额，则认为满额，理财计划结束
		if(MoneyUtil.subtract(leftAmount, product.getMinInvestAmount()).doubleValue() < 0){
			productReleaseService.finishProduct(product.getId(), null);
			productPlanStatutsChangeService.scheduleRegistDelete4AmountFull(product);
		}
	}
	
	//普通产品协议签章
	private void signSeal(HashMap<String, Object> extendProMap){
		Object userId = extendProMap.get("userId");
		Object subAccountId = extendProMap.get("subAccountId");
		Object investAmount = extendProMap.get("investAmount");
		
		if(userId != null && subAccountId != null && investAmount != null) {
	        //协议签章
			BsUser user = userService.findUserById((Integer)userId);
			String orderNo = String.valueOf(subAccountId);

	        // 签章异步处理，保存表信息：Bs_User_Seal_File,Bs_User_Sign_Seal,Bs_File_Seal_Relation
			BsUserSignSeal userSealReq = new BsUserSignSeal();
			userSealReq.setIdCard(user.getIdCard());
			userSealReq.setUserName(user.getUserName());
			userSealReq.setUserId(user.getId());
			BsUserSignSeal signSeal = signSealService.findUserSignSeal(userSealReq);

			// 用户没有签章信息,新增签章信息
			if (signSeal == null) {
				signSeal = userSealReq;
				signSealService.addUserSeal(signSeal);
			}

			String buyHtml = GlobEnvUtil.get("cfca.seal.buy.pdfSrcHtml") + "?investId="
					+ orderNo;

			// 新增用户签章文件记录表
			BsUserSealFile sealFile = new BsUserSealFile();
			sealFile.setAgreementNo(orderNo);
			sealFile.setSrcAddress(buyHtml);
			sealFile.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
			sealFile.setSealType(SealBusiness.BUY.getCode());
			sealFile.setUserId(signSeal.getUserId());
			sealFile.setUserSrc(SealPdfPathEnum.BIGANGWAN.getCode());
			sealFile.setRelativeInfo(orderNo);
			sealFile.setCreateTime(new Date());
			bsUserSealFileMapper.insertSelective(sealFile);

			// 新增签章文件与客户签章关系表
			BsFileSealRelation sealRelation = new BsFileSealRelation();
			sealRelation.setSealFileId(sealFile.getId());
			sealRelation.setCreateTime(new Date());
			sealRelation.setUpdateTime(new Date());

			String propertySymbol = signSealService.checkPropertySymbol(Integer.valueOf(orderNo));
			// 客户签章
			if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
					Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(propertySymbol) ||
					Constants.PRODUCT_PROPERTY_SYMBOL_FREE.equals(propertySymbol)) {
				sealRelation.setKeywords("甲方（委托方）：" + signSeal.getUserName());
			} else {
				sealRelation.setKeywords("甲方：" + signSeal.getUserName());
			}
			sealRelation.setContentType(SealType.PERSON.name());
			sealRelation.setUserSealId(signSeal.getId());
			bsFileSealRelationMapper.insertSelective(sealRelation);

			// 币港湾签章
			if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol)) {
				sealRelation.setKeywords("乙方（受托方）：杭州币港湾科技有限公司");
			} else if (Constants.PROPERTY_SYMBOL_ZSD.equals(propertySymbol)) {
				sealRelation.setKeywords("乙方：杭州币港湾科技有限公司");
			} else if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(propertySymbol)) {
				sealRelation.setKeywords("乙方（受托方）：杭州币港湾科技有限公司");
			} else if(PartnerEnum.FREE.getCode().equals(propertySymbol)) {
				sealRelation.setKeywords("乙方（受托方）：杭州币港湾科技有限公司");
			}else {
				sealRelation.setKeywords("丙方：杭州币港湾科技有限公司");
			}
			sealRelation.setContentType(SealType.COMPANY.name());
			sealRelation.setUserSealId(SealPdfPathEnum.BIGANGWAN.getSealId()); // 填写币港湾签章ID
			bsFileSealRelationMapper.insertSelective(sealRelation);

			log.info(">>>签章入redis走定时-file_id:" + sealRelation.getSealFileId());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("file_id", sealRelation.getSealFileId());
			jsClientDaoSupport.rpush(Constants.SIGN_FILE_QUEUE_KEY, JSON.toJSONString(jsonObject));
		}
	}
	
	
	//计划产品协议签章
	private void signSealAuth(HashMap<String, Object> extendProMap) {
		Object userId = extendProMap.get("userId");
		Object subAccountId = extendProMap.get("subAccountId");
		Object investAmount = extendProMap.get("investAmount");
		
		if(userId != null && subAccountId != null && investAmount != null) {
	        //协议签章
			BsUser user = userService.findUserById((Integer)userId);
			String orderNo = String.valueOf(subAccountId);

			// 签章异步处理，保存表信息：Bs_User_Seal_File,Bs_User_Sign_Seal,Bs_File_Seal_Relation
			BsUserSignSeal userSealReq = new BsUserSignSeal();
			userSealReq.setIdCard(user.getIdCard());
			userSealReq.setUserName(user.getUserName());
			userSealReq.setUserId(user.getId());
			BsUserSignSeal signSeal = signSealService.findUserSignSeal(userSealReq);

			// 用户没有签章信息,新增签章信息
			if (signSeal == null) {
				signSeal = userSealReq;
				signSealService.addUserSeal(signSeal);
			}

			String buyHtml = GlobEnvUtil.get("cfca.seal.buy.pdfSrcHtml") + "?investId="
					+ orderNo;

			// 新增用户签章文件记录表
			BsUserSealFile sealFile = new BsUserSealFile();
			sealFile.setAgreementNo(orderNo);
			sealFile.setSrcAddress(buyHtml);
			sealFile.setSealStatus(SealStatus.FILE_CREATE.getCode());
			sealFile.setSealType(SealBusiness.BUY.getCode());
			sealFile.setUserId(signSeal.getUserId());
			sealFile.setUserSrc(SealPdfPathEnum.BIGANGWAN.getCode());
			sealFile.setRelativeInfo(orderNo);
			bsUserSealFileMapper.insertSelective(sealFile);

			// 新增签章文件与客户签章关系表
			BsFileSealRelation sealRelation = new BsFileSealRelation();
			sealRelation.setSealFileId(sealFile.getId());
			sealRelation.setCreateTime(new Date());
			sealRelation.setUpdateTime(new Date());

			// 客户签章
			sealRelation.setKeywords("乙方：" + signSeal.getUserName());
			sealRelation.setContentType(SealType.PERSON.name());
			sealRelation.setUserSealId(signSeal.getId());
			bsFileSealRelationMapper.insertSelective(sealRelation);

			// 币港湾签章
			sealRelation.setKeywords("甲方：杭州币港湾科技有限公司");
			sealRelation.setContentType(SealType.COMPANY.name());
			sealRelation.setUserSealId(SealPdfPathEnum.BIGANGWAN.getSealId()); // 填写币港湾签章ID
			bsFileSealRelationMapper.insertSelective(sealRelation);

			log.info(">>>签章入redis走定时-file_id:" + sealRelation.getSealFileId());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("file_id", sealRelation.getSealFileId());
			jsClientDaoSupport.rpush(Constants.SIGN_FILE_QUEUE_KEY, JSON.toJSONString(jsonObject));
		}
	}

	/**
	 * 超级理财人无需进行购买
	 * @param call
     */
	private void superFinanceUserNotNeesBuy(JoinPoint call) {
		Object[] objs = call.getArgs();
		for (Object obj : objs) {
			if(obj instanceof ReqMsg_RegularBuy_BalanceBuy) {
				ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy) obj;
				if(this.isSupperUserZan(req.getUserId()) ||
						this.isSuperUserZSD(req.getUserId()) ||
						this.isSuperUser7DAI(req.getUserId()) ||
						this.isSupperUserYunSelf(req.getUserId()) ||
						this.isSuperUserFree(req.getUserId())) {
					throw new PTMessageException(PTMessageEnum.SUPER_FINANCE_USER_NOT_NEED_BUY);
				}
			}
		}
	}
	
	//特定产品，老用户（已有起息中的购买，赞分期产品已借出）校验
	private void oldUserCheck(JoinPoint call) {
		Object[] objs = call.getArgs();
		for (Object obj : objs) {
			if(obj instanceof ReqMsg_RegularBuy_BalanceBuy) {
				ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy) obj;
				BsProduct product = productService.findRegularById(req.getProductId());
				if(Constants.ACTIVITY_SPRING_PRODUCT_NAME.equals(product.getName())){
					boolean isOldFlag = userService.isOldUser(req.getUserId());
					if(!isOldFlag){
						throw new PTMessageException(PTMessageEnum.CHECK_OLD_USER_ERROR);
					}
				}
			}
		}
	}
	
	
	
	//钱报用户购买产品（普通产品+钱报产品）订单通知
	private void orderNoticeApp178(HashMap<String, Object> extendProMap) {
		
		try {
			Object userId = extendProMap.get("userId");
			Object subAccountId = extendProMap.get("subAccountId");
			Object investAmount = extendProMap.get("investAmount");
			Object subChannel = extendProMap.get("channel");
			
			if(userId != null && subAccountId != null ){
				BsUser user = userService.findUserById((Integer)userId);
				if (user == null ) {
					throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
				}
				
				if (Constants.AGENT_QIANBAO_ID == user.getAgentId()) {
					BsSubAccount bsSubAccount = subAccountMapper.selectByPrimaryKey((Integer)subAccountId);
					if (bsSubAccount == null) {
						throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT);
					}
					BsProduct product = productService.findRegularById(bsSubAccount.getProductId());
					if (product == null ) {
						throw new PTMessageException(PTMessageEnum.PRODUCT_INFO_NOT_FOUND_ERROR);
					}
					BsPayOrdersExample callBackExample = new BsPayOrdersExample();
					callBackExample.createCriteria().andSubAccountIdEqualTo(bsSubAccount.getId()).andTransTypeEqualTo(Constants.USER_BALANCE_BUY_PRODUCT).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
					List<BsPayOrders> orders = bsPayOrdersMapper.selectByExample(callBackExample);
					if (CollectionUtils.isEmpty(orders)) {
						throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
					}
					log.info("=============【钱报用户购买产品订单通知】用户"+user.getId()+"购买产品"+product.getId()+"===============");
					
					OrderNoticeProcess process = new OrderNoticeProcess();
					process.setApp178TransportService(app178TransportService);
					
					B2GReqMsg_APP178_OrderNotice orderNoticeReq = new B2GReqMsg_APP178_OrderNotice();
					orderNoticeReq.setDelegate_code(orders.get(0).getOrderNo());//订单编号,全局唯一（如果不存在则新增，存在则做更新处理）
					orderNoticeReq.setProduct_code(String.valueOf(product.getId()));//产品编码
					orderNoticeReq.setProduct_name(product.getName());//产品名称
					orderNoticeReq.setUser_account(user.getMobile()); //会员账号
					orderNoticeReq.setOrder_balance(MoneyUtil.multiply(bsSubAccount.getOpenBalance(), 100).longValue()); //订单金额（金额单位：分）
					orderNoticeReq.setDelegate_type("applyTrade"); //订单类型 认购（applyTrade）
					orderNoticeReq.setDelegate_status("delegating"); //订单状态：委托中(delegating)，成功(success)，失败(failed)
					orderNoticeReq.setOrder_time(orders.get(0).getCreateTime());//下单时间 yyyyMMddHHmmss			
					orderNoticeReq.setSubChannel((Integer)subChannel+"");
					process.setOrderNotice(orderNoticeReq);
					new Thread(process).start();
				}
			}
		} catch (Exception e) {
			log.error("=============【钱报用户购买产品订单通知】失败："+e.getMessage());
		}

	}


	/**
	 * 赞分期VIP用户
	 * @param userId
	 * @return
	 */
	private boolean isSupperUserZan(Integer userId) {
		BsSysConfig supers = bsSysConfigService.findKey(Constants.SUPER_FINANCE_USER_ID);
		return containUserId(userId, supers);
	}

	/**
	 * 云贷自主放款VIP用户
	 * @param userId
	 * @return
	 */
	private boolean isSupperUserYunSelf(Integer userId) {
		BsSysConfig supers = bsSysConfigService.findKey(VIPId4PartnerEnum.YUN_DAI_SELF.getVipIdKey());
		return containUserId(userId, supers);

	}

	/**
	 * 赞时贷VIP用户
	 * @param userId
	 * @return
	 */
	private boolean isSuperUserZSD(Integer userId) {
		BsSysConfig supers = bsSysConfigService.findKey(VIPId4PartnerEnum.ZSD.getVipIdKey());
		return containUserId(userId, supers);
	}

	/**
	 * 7贷VIP用户
	 * @param userId
	 * @return
	 */
	private boolean isSuperUser7DAI(Integer userId) {
		BsSysConfig supers = bsSysConfigService.findKey(VIPId4PartnerEnum.SEVEN_DAI_SELF.getVipIdKey());
		return containUserId(userId, supers);
	}

	/**
	 * 自由计划VIP用户
	 * @param userId
	 * @return
	 */
	private boolean isSuperUserFree(Integer userId) {
		BsSysConfig supers = bsSysConfigService.findKey(VIPId4PartnerEnum.FREE.getVipIdKey());
		return containUserId(userId, supers);
	}

	/**
	 * 配置中是否包含此userId
	 * @param userId
	 * @param supers
	 * @return
	 */
	public boolean containUserId(Integer userId, BsSysConfig supers) {
		boolean isSuperUser = false;
		List<Integer> superUserIds = new ArrayList<>();
		if (supers != null) {
			String[] userStr = supers.getConfValue().split(",");
			for (String string : userStr) {
				superUserIds.add(Integer.parseInt(string));
			}
		}
		if(!org.springframework.util.CollectionUtils.isEmpty(superUserIds)) {
			for (Integer superUserId : superUserIds) {
				if(superUserId.equals(userId)) {
					isSuperUser = true;
					break;
				}
			}
		}
		return isSuperUser;
	}
}
