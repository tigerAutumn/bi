package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.CustomerInfoSyncVO;
import com.pinting.business.model.vo.OrderInfoSyncVO;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_OrderInfoSync;
import com.pinting.gateway.hessian.message.qidian.model.Customers;
import com.pinting.gateway.hessian.message.qidian.model.OrderInfos;
import com.pinting.gateway.out.service.qidian.QiDianNoticeService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 七店信息同步通知定时
 * @project business
 * @title QiDianInfoSyncTask.java
 * @author Dragon & cat
 * @date 2018-3-22
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */

@Service
public class QiDianInfoSyncTask {
	private Logger log = LoggerFactory.getLogger(QiDianInfoSyncTask.class);
	
	
	@Autowired
	private 	BsUserMapper  bsUserMapper;
	@Autowired
	private 	QiDianNoticeService qiDianNoticeService;
	@Autowired
	private 	BsPayOrdersMapper  bsPayOrdersMapper;
	@Autowired
	private 	BsSysConfigService  bsSysConfigService;
	
	/**
	 * 任务执行
	 */
	public void execute() {
		/**
		 * 1、客户信息和订单信息统一使用一个时间
		 * 2、可能出现通知失败的情况，从数据库中配置一个开关 ，自定义设置时间进行通知
		 */
		Date startTime = new Date();
		Date queryStartTime = null;
		log.info("七店信息同步通知定时开始执行时间："+DateUtil.format(startTime));
		boolean customerSendFlag = false;
		boolean orderSendFlag = false;
		
		//判断数据库中开关状态
		//{"CUSTOMER_SYNC_SWITCH":"N","ORDER_SYNC_SWITCH":"N","DATA_SYNC_TIME":"2018-07-27 22:00:00","DATA_QUERY_START_TIME":"2018-07-24 22:00:00"}
		BsSysConfig config = bsSysConfigService.findKey(Constants.QIDIAN_DATA_SYNC_SWITCH);
		if (config != null) {
			JSONObject jsonObject = JSONObject.fromObject(config.getConfValue());
			String customerSyncSwitch =  jsonObject.getString("CUSTOMER_SYNC_SWITCH");
			String orderSyncSwitch =  jsonObject.getString("ORDER_SYNC_SWITCH");
			String dataSyncTime =  jsonObject.getString("DATA_SYNC_TIME");
			String dataQueryStartTime =  jsonObject.getString("DATA_QUERY_START_TIME");

			if (!StringUtil.isBlank(customerSyncSwitch) && "Y".equals(customerSyncSwitch)) {
				startTime = DateUtil.parseDateTime(dataSyncTime);
				queryStartTime = DateUtil.parseDateTime(dataQueryStartTime);
				//客户信息同步
				log.info("七店客户信息同步时间区间确定："+ queryStartTime  + "-" +  startTime);
				customerInfoSyncTask(startTime, queryStartTime);
				customerSendFlag = true;
			}
			if (!StringUtil.isBlank(orderSyncSwitch) && "Y".equals(orderSyncSwitch)) {
				startTime = DateUtil.parseDateTime(dataSyncTime);
				queryStartTime = DateUtil.parseDateTime(dataQueryStartTime);
				//订单信息同步
				log.info("七店订单信息同步时间区间确定："+ queryStartTime  + "-" +  startTime);
				orderInfoSyncTask(startTime, queryStartTime);
				orderSendFlag = true;
			}
		}

		if(!customerSendFlag){
			//客户信息同步
			customerInfoSyncTask(startTime, null);
		}
		if(!orderSendFlag){
			//订单信息同步
			orderInfoSyncTask(startTime, null);
		}
	}
	
	
	private void customerInfoSyncTask(Date startTime, Date queryStartTime) {
		log.info("==================日终【七店用户信息同步通知】开始====================");
		
		/**
		 * 1、根据传入时间（startTime）和场景类型参数查询开始时间之前两个小时的增量数据
		 * 
		 */
		List<CustomerInfoSyncVO> vo = new ArrayList<CustomerInfoSyncVO>();
		//1.客户注册；2.用户绑卡；3.用户登录
		Integer[]  types  = {1,2,3};
		for (Integer type : types) {
			
			if (type ==1 ) {
				vo = bsUserMapper.customerInfoSyncRegister(startTime, queryStartTime);
			}else if (type ==2) {
				vo = bsUserMapper.customerInfoSyncRealName(startTime, queryStartTime);
			}else if (type ==3) {
				vo = bsUserMapper.customerInfoSyncLogin(startTime, queryStartTime);
			}
			List<Customers>  customers = new ArrayList<Customers>();
			//调用通知
			for (CustomerInfoSyncVO  customerInfoSyncVO : vo) {
				Customers customer = new Customers();
				customer.setFranchiseeId(customerInfoSyncVO.getFranchiseeId());
				customer.setCustomerId(customerInfoSyncVO.getCustomerId());
				customer.setCustomerName(customerInfoSyncVO.getCustomerName());
				customer.setCustomerMobile(customerInfoSyncVO.getCustomerMobile());
				customer.setCustomerLevel(customerInfoSyncVO.getCustomerLevel());
				customer.setRealNameStatus(customerInfoSyncVO.getRealNameStatus());
				customer.setIdCardNo(customerInfoSyncVO.getIdCardNo());
				customer.setRemainInvest(customerInfoSyncVO.getRemainInvest());
				customer.setTotalInvest(customerInfoSyncVO.getTotalInvest());
				customer.setRegisterTime(customerInfoSyncVO.getRegisterTime());
				customer.setLoginTime(customerInfoSyncVO.getLoginTime());
				customers.add(customer);
			}
			
			B2GReqMsg_QiDianNotice_CustomerInfoSync req= new B2GReqMsg_QiDianNotice_CustomerInfoSync();
			req.setType(type);
			req.setCustomers(customers);
			qiDianNoticeService.customerInfoSync(req);
		}
		
		log.info("==================日终【七店用户信息同步通知】结束====================");
	}
	
	
	private void orderInfoSyncTask(Date startTime, Date queryStartTime) {
		log.info("==================日终【七店订单信息同步通知】开始====================");
		List<OrderInfoSyncVO> vo = new ArrayList<OrderInfoSyncVO>();
		//1.理财购买 2.到期回款
		Integer[]  types  = {1,2};
		for (Integer type : types) {
			if (type ==1 ) {
				vo = bsPayOrdersMapper.orderInfoSyncBuy(startTime, queryStartTime);
			}else if (type ==2 ) {
				vo = bsPayOrdersMapper.orderInfoSyncReturn(startTime, queryStartTime);
			}
			List<OrderInfos>  orderInfos = new ArrayList<OrderInfos>();
			//调用通知
			for (OrderInfoSyncVO  orderInfoSyncVO : vo) {
				OrderInfos orderInfo = new OrderInfos();
				orderInfo.setOrderNo(orderInfoSyncVO.getOrderNo());
				orderInfo.setCustomerId(orderInfoSyncVO.getCustomerId());
				orderInfo.setPartnerName(orderInfoSyncVO.getPartnerName());
				orderInfo.setProductId(orderInfoSyncVO.getProductId());
				orderInfo.setProductName(orderInfoSyncVO.getProductName());
				orderInfo.setProductTerm(orderInfoSyncVO.getProductTerm());
				orderInfo.setInvestStatus(orderInfoSyncVO.getInvestStatus());
				orderInfo.setInvestStatusDesc(orderInfoSyncVO.getInvestStatusDesc());
				orderInfo.setReturnType(orderInfoSyncVO.getReturnType());
				orderInfo.setBaseRate(orderInfoSyncVO.getBaseRate());
				orderInfo.setIsRaise(orderInfoSyncVO.getIsRaise());
				orderInfo.setRaiseRate(orderInfoSyncVO.getRaiseRate());
				orderInfo.setOpenBalance(orderInfoSyncVO.getOpenBalance());
				orderInfo.setBalance(orderInfoSyncVO.getBalance());
				orderInfo.setExpectYield(orderInfoSyncVO.getExpectYield());
				orderInfo.setOpenTime(orderInfoSyncVO.getOpenTime());
				orderInfo.setInterestBeginDate(orderInfoSyncVO.getInterestBeginDate());
				orderInfo.setUpdateTime(orderInfoSyncVO.getUpdateTime());
				orderInfo.setPlanDate(orderInfoSyncVO.getPlanDate());
				orderInfo.setYield(orderInfoSyncVO.getYield());
				orderInfo.setReturnDate(orderInfoSyncVO.getReturnDate());
				orderInfo.setIsDiscount(orderInfoSyncVO.getIsDiscount());
				orderInfo.setDiscountAmount(orderInfoSyncVO.getDiscountAmount());
				orderInfos.add(orderInfo);
			}
			
			B2GReqMsg_QiDianNotice_OrderInfoSync req= new B2GReqMsg_QiDianNotice_OrderInfoSync();
			req.setType(type);
			req.setOrderInfos(orderInfos);
			qiDianNoticeService.orderInfoSync(req);
		}
		
		log.info("==================日终【七店订单信息同步通知】结束====================");
	}
	


}
