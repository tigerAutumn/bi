package com.pinting.business.accounting.finance.service;

import java.util.List;

import com.pinting.business.model.vo.SysReturnManageResVO;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysReturnMoneyNotice;

/**
 * 系统接收达飞回款通知接口（一笔通知，多个产品回款明细）
 * @Project: business
 * @Title: SysReturnMoneyService.java
 * @author dingpf
 * @date 2015-11-18 下午2:06:14
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SysReturnMoneyService {

	/**
	 * 系统回款通知处理
	 * @param req
	 */
	void notifySysReturnMoney(G2BReqMsg_DafyPayment_SysReturnMoneyNotice req);
	
	/**
	 * 系统理财产品每月结息通知处理
	 * @param req
	 */
	void notifySysReturnInterest(G2BReqMsg_DafyPayment_SysReturnMoneyNotice req);
	
	
	/**
	 * 系统回款通知处理（本金回款）
	 * 针对云贷、七贷存量数据回款
	 * @param req
	 */
	void notifySysReturnPrincipalNew(G2BReqMsg_DafyPayment_SysReturnMoneyNotice req);
	
	/**
	 * 管理台存量回款提前赎回check
	 * @param sendBatchIdList 批次号列表
	 */
	String return4ManageCheck(List<String> sendBatchIdList);
	
	/**
	 * 管理台存量回款提前赎回check通过返回对象裂表
	 * @param sendBatchIdList
	 * @return
	 */
	SysReturnManageResVO return4ManageGetList(List<String> sendBatchIdList);
	
	/**
	 * 管理台存量回款提前赎回-执行赎回
	 * @param sendBatchIdList
	 * @return
	 */
	String doSysReturn(List<String> sendBatchIdList);
}
