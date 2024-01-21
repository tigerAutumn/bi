package com.pinting.gateway.out.service;

/**
 * @Project: business
 * @Title: EmailGateService.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:52
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface EmailGateService {
	/**
	 * 给某个邮箱发送验证码，并返回4位验证码
	 * @param email 收件人地址
	 * @return 生成的验证码, 如果失败，返回null
	 */
	public String sendIdentify(String email);
}
