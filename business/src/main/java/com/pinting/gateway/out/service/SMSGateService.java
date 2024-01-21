package com.pinting.gateway.out.service;
/**
 * @Project: business
 * @Title: SMSGateService.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:01
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SMSGateService {
	/**
	 * 根据手机发送验证码
	 * @param mobile 手机号
	 * @return 如果发送成功返回验证码，否则返回错误信息
	 */
	public String sendIdentify(String mobile)  ;
	
	/**
	 * 根据手机发送内容
	 * @param mobile 手机号
	 * @param message 手机消息
	 * @return 如果发送成功返回true，否则返回false
	 */
	public Boolean sendMessage(String mobile , String message);
	
}
