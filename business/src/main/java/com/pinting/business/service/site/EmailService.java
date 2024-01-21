package com.pinting.business.service.site;

import com.pinting.core.exception.PTException;

/**
 * @Project: business
 * @Title: EmailService.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:48:26
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface EmailService {
	/**
	 * 根据Email地址发送验证码
	 * @param email Email地址
	 * @return 如果发送成功返回发送的验证码，否则返回发送失败信息
	 */
	public String sendIdentify(String email);
	
	/**
	 * 根据Email地址和用户输入验证码验证验证码是否正确
	 * @param email 发送验证码的Email地址
	 * @param emailCode 用户输入的验证码
	 * @return 如果验证成功返回true，否则返回false
	 * @ 返回邮件发送相应异常
	 */
	public boolean validateIdentity(String email,String emailCode) ;
	/**
	 * 根据Email地址和用户输入验证码验证验证码是否正确
	 * @param email 发送验证码的Email地址
	 * @param emailCode 用户输入的验证码
	 * @param isDelete 是否删除验证码
	 * @return 如果验证成功返回true，否则返回false
	 * @ 返回邮件发送相应异常
	 */
	public boolean validateIdentity(String email,String emailCode,Boolean isDelete) ;
}
