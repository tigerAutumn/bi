package com.pinting.gateway.in.facade;

import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.business.accounting.finance.service.SysReturnMoneyService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.enums.ThirdSysChannelEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Project: business
 * @Title: DafyPaymentFacade.java
 * @author Zhou Changzai
 * @date 2015-2-28 上午11:10:20
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("DafyPayment")
public class DafyPaymentFacade {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private PayOrdersService orderService;
	@Autowired
	private SysProductBuyService sysProductBuyService;
	@Autowired
	private SysReturnMoneyService sysReturnMoneyService;

	private Logger log = LoggerFactory.getLogger(DafyPaymentFacade.class);

	/**
	 * 购买结果反馈 根据返回结果做如下处理： 1.更新订单表 2.更新子账户产品表
	 * 
	 * @param req
	 * @param res
	 */
	@Deprecated
	public void buyProductResult(G2BReqMsg_DafyPayment_BuyProductResult req,
			G2BResMsg_DafyPayment_BuyProductResult res) {
		int result = req.getResult();
		log.info("====================>Business平台已收到购买通知结果："
				+ (result == G2BReqMsg_DafyPayment_BuyProductResult.BUY_RESULT_SUCCESS ? "成功" : "失败"));

		orderService.finishBuyProduct(req);
	}

	public void sysBuyProductNotice(
			G2BReqMsg_DafyPayment_SysBuyProductNotice req,
			G2BResMsg_DafyPayment_SysBuyProductNotice res) {
		log.info("====================>Business平台已收到系统购买结果通知：" + req);
		sysProductBuyService.notifyBuyProductResult(req);

	}

	public void sysReturnMoneyNotice(
			G2BReqMsg_DafyPayment_SysReturnMoneyNotice req,
			G2BResMsg_DafyPayment_SysReturnMoneyNotice res) {
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DAFYPAYMENT_FACADE.getKey());
			//渠道来源不能为空，且应在枚举内
			if(StringUtil.isEmpty(req.getChannel()) || ThirdSysChannelEnum.getEnumByCode(req.getChannel()) == null){
				throw new PTMessageException(PTMessageEnum.RETURN_CHANNEL_ERROR);
			}
			log.info("====================>Business平台已收到["+ req.getChannel() +"]系统回款通知：" + req);
			//判断通知类型 
			//1、本金不为0 ，最后次回款
			double productAmount = req.getProductAmount();
			if(productAmount > 0){
				//sysReturnMoneyService.notifySysReturnMoney(req);
				sysReturnMoneyService.notifySysReturnPrincipalNew(req);
			}
			//2、本金为0，结息回款
			else{
				sysReturnMoneyService.notifySysReturnInterest(req);
			}
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DAFYPAYMENT_FACADE.getKey());
		}
	}
}
