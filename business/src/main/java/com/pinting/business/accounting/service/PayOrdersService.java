package com.pinting.business.accounting.service;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersJnl;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsUserTransDetail;
import com.pinting.business.model.vo.BsAccountJnlVO;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_BuyProductResult;
import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;
import com.pinting.gateway.hessian.message.dafy.model.ReceiveMoneyDetail;

/**
 * @Project: business
 * @Title: PayOrdersService.java
 * @author Zhou Changzai
 * @date 2015-2-28 上午11:26:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface PayOrdersService {
	/**
	 * 购买一个产品前置操作
	 * @param bankId 银行卡Id
	 * @param userId 用户编号
	 * @param amount 购买金额
	 * @param product 所购买的产品
	 * @return String 订单号
	 */
	public String preBuyProduct(int bankId, int userId, double amount, BsProduct product);
	
	/**
	 * 订单购买后，对方发起的回调
	 * 根据返回结果做如下处理：
	 * 1.更新订单信息表
	 * 2.更新结算户记录的金额 （同时记录一条结算户充值流水）
	 * 3.转账到子账户信息表（同时记录一条从结算户到子账户的转账信息流水）
	 * 4.更新产品表，产品购买人数+1
	 * @param req
	 * @return boolean
	 */
	@Deprecated
	public boolean finishBuyProduct(G2BReqMsg_DafyPayment_BuyProductResult req);
	
	/**
	 * 新增一条订单
	 * @param bsPayOrders 订单对象
	 * @return 成功返回true，否则返回false
	 */
	public boolean addPayOrder(BsPayOrders bsPayOrders);
	
	/**
	 * 根据订单号修改订单
	 * @param bsPayOrders 订单对象
	 * @return 成功返回true，否则返回false
	 */
	public boolean modifyPayOrderByOrderNo(BsPayOrders bsPayOrders);

	/**
	 * 根据渠道、产品代码、日期，查询该日期理财产品资金总额
	 * @param channel 渠道
	 * @param productCode 产品代码
	 * @param queryDate	查询日期
	 * @return 有购买记录则 返回资金汇总情况，无购买记录则返回金额为0的汇总情况
	 */
	public InvestmentAmounts queryInvestmentAmount(String channel, String productCode, Date queryDate);

	
	/**
	 * 根据订单号查询订单信息
	 * @param orderNo 订单号
	 * @return 成功返回订单信息，否则返回null
	 */
	public BsPayOrders findOrderByOrderNo(String orderNo);
	
	/**
	 * 根据订单号查询 某日期区间 订单信息
	 * @param orderNo 订单号
	 * @param beginDate
	 * @param endDate
	 * @return 成功返回订单信息，否则返回null
	 */
	public BsPayOrders findOrderByOrderNoAndDate(String orderNo, Date beginDate, Date endDate);
	
	/**
	 * 根据起始和截止日期查询 该区间内所有订单
	 * @param beginDate
	 * @param endDate
	 * @return 成功返回订单信息，否则返回null
	 */
	public List<BsPayOrders> findOrdersByDate(Date beginDate, Date endDate);
	
	/**
	 * 客户理财产品到期回款通知
	 * @param receiveMoneyDetail 回款结果通知
	 * @return 成功返回true，否则返回false
	 */
	public boolean receiveMoney(ReceiveMoneyDetail receiveMoneyDetail);

	/**
	 * 查询购买银行卡类型列表
	 * @return BsPayOrders 
	 */
	public List<BsPayOrders> findBuyBankTypeList();
	
	/**
	 * 订单明细流水查询
	 * @param userName
	 * @param mobile
	 * @param beginTime
	 * @param overTime
	 * @param status 订单状态
	 * @param startUpdateTime 订单更新时间
	 * @param endUpdateTime
	 * @param orderNo
	 * @param transType 交易类型
	 * @param pageNum
	 * @param numPerPage
	 * @param orderDirection
	 * @param orderField
	 * @return
	 */
	public List<BsPayOrdersVO> findOrderDetailList(String userName, String mobile,
												   Date beginTime, Date overTime, Integer status,
												   Date startUpdateTime, Date endUpdateTime,
												   String orderNo, String transType,
												   int pageNum, int numPerPage,
												   String orderDirection, String orderField);
	
	/**
	 * 订单明细流水记录统计
	 * @param userName
	 * @param mobile
	 * @param beginTime
	 * @param overTime
	 * @param status
	 * @param startUpdateTime
	 * @param endUpdateTime
	 * @param orderNo
	 * @param transType
	 * @return
	 */
	public int findCountOrderDetail(String userName, String mobile,
									Date beginTime, Date overTime, Integer status,
									Date startUpdateTime, Date endUpdateTime,
									String orderNo, String transType);
	/**
	 * 网银支付，更新订单流水表状态
	 * @param bsPayOrdersJnl
	 * @return 
	 */
	public int updateSelectivePayOrderJnl(BsPayOrdersJnl bsPayOrdersJnl);
	
	/**
	 * 网银支付，更新用户交易明细表状态
	 * @param bsPayOrdersJnl
	 * @return 
	 */
	public int updateSelectiveBsUserTransDetail(BsUserTransDetail detail);
	/**
	 * 网银支付，新增用户交易明细表
	 * @param bsPayOrdersJnl
	 * @return 
	 */
	public void insertSelectiveBsUserTransDetail(BsUserTransDetail detail);
	
	/**
	 * 用户充值记录统计
	 * @param mobile
	 * @param userName
	 * @param status 交易类型：处理中、成功、失败
	 * @param beginTime
	 * @param overTime
	 * @return
	 */
	public int userCountTopUp(String mobile, String userName,
							  Integer status, Date beginTime, Date overTime);
	
	/**
	 * 用户充值列表
	 * @param mobile
	 * @param userName
	 * @param status 交易类型：处理中、成功、失败
	 * @param beginTime
	 * @param overTime
	 * @param pageNum
	 * @param numPerPage
	 * @param orderDirection
	 * @param orderField
	 * @return
	 */
	public List<BsPayOrdersVO> findUserTopUp(String mobile, String userName,
											 Integer status, Date beginTime, Date overTime,
											 int pageNum, int numPerPage,
											 String orderDirection, String orderField);
	
	/**
	 * 用户充值金额合计
	 * @param mobile
	 * @param userName
	 * @param status 交易类型：处理中、成功、失败
	 * @param beginTime
	 * @param overTime
	 * @return
	 */
	public double userSumTopUp(String mobile, String userName,
							   Integer status, Date beginTime, Date overTime);

	/**
	 * 更新订单状态
	 * ！无影响行数即无记录或已最终状态时会throw异常！
	 * @param id       编号
	 * @param status       状态
	 * @param subAccountId 子账户编号
	 * @param returnCode   返回码
	 * @param returnMsg    返回信息
	 */
	public void modifyOrderStatus4Safe(Integer id, Integer status, Integer
			subAccountId, String returnCode, String returnMsg);

	/**
	 * 更新订单状态
	 * ！无影响行数即无记录或已最终状态时会throw异常！
	 * @param id       编号
	 * @param status       状态
	 * @param subAccountId 子账户编号
	 * @param returnCode   返回码
	 * @param returnMsg    返回信息
	 */
	public void modifyLnOrderStatus4Safe(Integer id, Integer status, Integer
			subAccountId, String returnCode, String returnMsg);
	
	/**
	 * 修改订单表支付渠道id
	 * 修改宝付扣款方式是否为协议支付
	 * @param id
	 * @param channelId
	 */
	public void updatePaymentChannelId(Integer id,Integer channelId, String isProtocolPay);
	
}
