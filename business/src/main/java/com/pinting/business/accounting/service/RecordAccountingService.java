package com.pinting.business.accounting.service;

import java.util.Date;
import java.util.Map;

import com.pinting.business.model.BsAccountJnl;

/**
 * 记账接口（待定）
 * @Project: business
 * @Title: RecordAccountingService.java
 * @author dingpf
 * @date 2015-1-21 下午1:01:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface RecordAccountingService {
	/**
	 * 前置记账接口
	 * @param transTime			交易时间			必输
	 * @param transCode			交易码			必输		根据交易码进行对应的流水记账
	 * @param transAmount		交易金额			必输
	 * @param userId1			用户编号			必输		根据此用户编号查询结算户
	 * @param userId2			相关账户的用户编号	可选		可选，若传入，则根据该编号进行相关结算户查询
	 * @param subAccountCode2	相关账户号		可选		
	 * @param bankCard			相关卡号			可选		可选，若有外部卡号，则需传入
	 * @return 返回流水号jnlId
	 * @
	 */
	public Integer preRecordAccounting(BsAccountJnl accountJnl) ;
	
	/**
	 * 后置记账状态修改，账户余额修改
	 * @param jnlId				原记账流水号		必输		原流水号+原交易时间 唯一确定进行记账状态修改
	 * @param transTime			原记账交易时间		必输		原流水号+原交易时间 唯一确定进行记账状态修改
	 * @param transCode			交易码			必输		根据交易码进行对应的记账修改
	 * @param transAmount		交易金额			必输
	 * @param status			状态				必输		流水最终状态
	 * @param userId1			用户编号			必输		根据此用户编号查询结算户
	 * @param respCode			支付通道返回的响应码	必输		
	 * @param respMsg			支付通道返回的响应消息	可选		若有则请传入
	 * @
	 */
	public void postfRecordAccounting(BsAccountJnl accountJnl) ;

}
