package com.pinting.business.service.manage;


/**
 * 
 * @author shh  2016/9/11
 *
 */
public interface BaofooBatchService {
	
	/**
     * 1、如果bindCardMobile为空，查询本地库中所有绑卡成功的银行卡信息，进行批量绑卡
     * 2、如果bindCardMobile不为空，根据手机号查询绑卡表，绑卡表中记录存在并且绑卡成功的记录，进行批量绑卡
     * @param bindCardMobile 批量绑卡手机号
     * @return
     */
	String batchBindCard(String bindCardMobile);

}
