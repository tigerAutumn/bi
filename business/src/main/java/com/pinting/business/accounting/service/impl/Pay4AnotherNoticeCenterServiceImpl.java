package com.pinting.business.accounting.service.impl;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.accounting.service.Pay4AnotherNoticeCenterService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 
 * @Project: business
 * @Title: Pay4AnotherNoticeCenterServiceImpl.java
 * @author Zhou Changzai
 * @date 2015-11-25上午10:37:01
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class Pay4AnotherNoticeCenterServiceImpl implements
		Pay4AnotherNoticeCenterService {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

	@Autowired
	private PayOrdersService payOrderService;
	@Autowired
	private UserBalanceWithdrawService userBalanceWithdrawService;
	@Autowired
	private UserReturnMoneyService userReceiveMoneyNotice;

	@Override
	public void dispatcher(
			G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice req,
			G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice res) {
		//1.查询订单信息表
		//2.根据订单交易类型，判断具体业务
		BsPayOrders order = payOrderService.findOrderByOrderNo(req.getMxOrderId());
		if(order == null){//订单不存在
			throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
		}
		
		String transTpye = order.getTransType();
		switch (transTpye) {
		case Constants.TRANS_BALANCE_WITHDRAW://用户提现
			try {
				jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
				DFResultInfo resultInfo = new DFResultInfo();
				resultInfo.setAmount(req.getAmount());
				resultInfo.setFinishTime(req.getFinishTime());
				resultInfo.setMxOrderId(req.getMxOrderId());
				resultInfo.setOrderStatus(req.getOrderStatus());
				resultInfo.setRetCode(req.getRetCode());
				resultInfo.setErrorMsg(req.getErrorMsg());
				resultInfo.setSysOrderId(req.getSysOrderId());
				userBalanceWithdrawService.notify(resultInfo);
			}finally {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
			}
			break;
		case Constants.TRANS_RETURN_2_USER_BANK_CARD://用户回款
			try {
				jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
				DFResultInfo resultReq = new DFResultInfo();
				resultReq.setAmount(req.getAmount());
				resultReq.setFinishTime(new Date());//宝付代付接口同步成功时 无完成时间，此处只能用本地服务时间
				resultReq.setMxOrderId(req.getMxOrderId());
				resultReq.setOrderStatus(req.getOrderStatus());
				resultReq.setRetCode(req.getRetCode());
				resultReq.setErrorMsg(req.getErrorMsg());
				userReceiveMoneyNotice.notifyReturn2CardResult(resultReq);
			}finally {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
			}
			break;

		default:
			break;
		}
	}

}


