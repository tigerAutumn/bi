package com.pinting.business.service.common;

/**
 * 银行相关的公共服务
 * @author bianyatian
 * @2016-9-21 下午7:46:34
 */
public interface BankService {
	
	/**
	 * 校验银行交易是否能通过
	 * @param bankChannel 银行渠道（Constants.CHANNEL_PAY19/Constants.CHANNEL_REAPAL/Constants.CHANNEL_BAOFOO）
	 * @param bankCode 银行编码 ICBC
	 * @param transChannel 交易类型（Constants.PAY_TYPE_QUICK/Constants.PAY_TYPE_PAYMENT）
	 * @param amount 交易金额
	 * @return 1.如果可以做，则返回null2.如果不能做，返回原因
	 */
	String checkTranAvailable(String bankChannel, String bankCode, Integer transChannel, Double amount);

}
