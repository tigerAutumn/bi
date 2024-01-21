package com.pinting.business.accounting.service;

import com.pinting.business.model.BsPayOrders;
import com.pinting.gateway.hessian.message.pay19.model.AccountDetail;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_QueryOrderResult;

public interface CheckAccountService {

	/**
     * 处理中 转 成功或失败（业务）
     * @param order 订单对象（订单号、订单金额、状态、子账户编号）
     * @param detail 对账记录对象（三方支付订单号、支付完成时间、三方交易类型、支付金额）
     * @param status 转成功：SUCCESS 或 转失败：FAIL
     * @param note 备注
     */
	public void checkAccount4Paying(BsPayOrders order, AccountDetail detail, String status, String note);
	
	/**
     * 处理中 转 成功或失败（业务）
     * @param order 订单对象（订单号、订单金额、状态、子账户编号）
     * @param detail 对账记录对象（三方支付订单号、支付完成时间、三方交易类型、支付金额）
     * @param status 转成功：SUCCESS 或 转失败：FAIL
     * @param note 备注
     * @param channel 渠道
     * @param partnerCode 资产方
     */
	public void checkAccountHf4Paying(BsPayOrders order, AccountDetail detail, String status, String note,
			String channel, String partnerCode, String transTerminal);
	
	/**
     * 处理中 转 成功或失败（业务）
     * @param order 订单对象（订单号、订单金额、状态、子账户编号）
     * @param detail 对账记录对象（三方支付订单号、支付完成时间、三方交易类型、支付金额）
     * @param status 转成功：SUCCESS 或 转失败：FAIL
     * @param note 备注
     */
	public void checkAccount4Paying(BsPayOrders order, AccountDetail detail, String status, String note, String merchantNo, String hostSysStatus);
	
	/**
	 * 融宝单笔对账
	 * @param order
	 */
	public void checkReapalSingle(BsPayOrders order);
	
	/**
	 * 融宝快捷支付 处理中 转 成功或失败（业务）
     * @param order 订单对象
     * @param result 订单结果对象
     * @param status 转成功：SUCCESS 或 转失败：FAIL
     * @param note 备注
	 */
	public void reapalQuickPayCheckAccount4Paying(BsPayOrders order, 
			B2GResMsg_ReapalQuickPay_QueryOrderResult result, String status, String note);
	
	/**
     * 对账不一致记录
     * 
     * @param orderNo 订单号
     * @param sysAmount 本地订单金额
     * @param doneAmount 支付平台订单金额
     * @param result 对账结果
     * @param info 对账描述
     * @param sysStatus 本地订单状态
     * @param doneStatus 三方订单状态
     * @param subAccountId 子账户编号
     * @param smsKeywords 短信关键词，用于发送短信时传的参数
     */
	public void checkAccountIsUnMatch(String orderNo, Double sysAmount, Double doneAmount,
            String result, String info, String sysStatus,
            String doneStatus, Integer subAccountId, String smsKeywords);
	
	/**
     * 对账一致记录
     * 
     * @param orderNo 订单号
     * @param sysAmount 本地订单金额
     * @param doneAmount 支付平台订单金额
     * @param result 对账结果
     * @param info 对账描述
     * @param subAccountId 子账户编号
     */
	public void checkAccountIsMatch(String orderNo, Double sysAmount, Double doneAmount,
            String result, String info, Integer subAccountId);
	
	/**
     * 对账不一致记录
     * 
     * @param orderNo 订单号
     * @param sysAmount 本地订单金额
     * @param doneAmount 支付平台订单金额
     * @param result 对账结果
     * @param info 对账描述
     * @param sysStatus 本地订单状态
     * @param doneStatus 三方订单状态
     * @param subAccountId 子账户编号
     * @param smsKeywords 短信关键词，用于发送短信时传的参数
     * @param transType 交易类型
     * @param transTerminal 交易终端： 理财，借款等
     */
	public void checkAccountIsUnMatch(String orderNo, Double sysAmount, Double doneAmount,
            String result, String info, String sysStatus,
            String doneStatus, Integer subAccountId, String smsKeywords, 
            String transType, String transTerminal);
	
	/**
     * 对账不一致记录
     * 
     * @param orderNo 订单号
     * @param sysAmount 本地订单金额
     * @param doneAmount 支付平台订单金额
     * @param result 对账结果
     * @param info 对账描述
     * @param sysStatus 本地订单状态
     * @param doneStatus 三方订单状态
     * @param subAccountId 子账户编号
     * @param smsKeywords 短信关键词，用于发送短信时传的参数
     * @param transType 交易类型
     * @param transTerminal 交易终端： 理财，借款等
     * @param merchantNo 商户号
     * @param partnerCode 资产合作方
     * @param bfOrderNo 宝付订单号
     * @param hostSysStatus 宝付订单状态
     */
	public void checkAccountIsUnMatch(String orderNo, Double sysAmount, Double doneAmount,
            String result, String info, String sysStatus, String doneStatus, Integer subAccountId, String smsKeywords, 
            String transType, String transTerminal, String merchantNo, String partnerCode, String bfOrderNo, String hostSysStatus, String specialJnlInfo);
	
	/**
     * 恒丰对账不一致记录
     * 
     * @param orderNo 订单号
     * @param sysAmount 本地订单金额
     * @param doneAmount 支付平台订单金额
     * @param result 对账结果
     * @param info 对账描述
     * @param sysStatus 本地订单状态
     * @param doneStatus 三方订单状态
     * @param subAccountId 子账户编号
     * @param smsKeywords 短信关键词，用于发送短信时传的参数
     * @param transType 交易类型
     * @param transTerminal 交易终端： 理财，借款等
     * @param channel 通道：hfbank恒丰银行，baofoo宝付
     * @param partnerCode 资产合作方
     * @param bfOrderNo 恒丰订单号
     * @param hostSysStatus 恒丰订单状态
     */
	public void checkHfbankAccountIsUnMatch(String orderNo, Double sysAmount, Double doneAmount,
            String result, String info, String sysStatus, String doneStatus, Integer subAccountId, String smsKeywords, 
            String transType, String transTerminal, String channel, String partnerCode, String bfOrderNo, String hostSysStatus, String specialJnlInfo);
	
}
