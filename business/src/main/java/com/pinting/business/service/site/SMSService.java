package com.pinting.business.service.site;

/**
 * @Project: business
 * @Title: SMSService.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:48:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SMSService {
	
	/**
	 * 根据手机号码发送验证码
	 * @param mobile 手机号码
	 * @return 如果发送成功返回发送的验证码，否则返回发送失败信息
	 */
	public String sendIdentify(String mobile) ;
	/**
	 * 根据手机号码发送信息
	 * @param mobile 手机号码
	 * @param message 手机号码
	 * @return 如果发送成功返回true，否则返回false
	 */
	public Boolean sendMessage(String mobile,String message) ;
	
	/**
	 * 根据手机号码和用户输入验证码验证验证码是否正确
	 * @param mobile 发送验证码的手机号码
	 * @param mobileCode 用户输入的验证码
	 * @return 如果验证成功返回true，否则返回false
	 * @ 返回手机发送相应异常
	 */
	public boolean validateIdentity(String mobile,String mobileCode) ;

	/**
	 * 根据手机号码和用户输入验证码验证验证码是否正确（自定义验证码到期时间）
	 * @param mobile		发送验证码的手机号码
	 * @param mobileCode	用户输入的验证码
	 * @param expireSecond	验证码到期秒数
     * @return	如果验证成功返回true，否则返回false
	 * @ 返回手机发送相应异常
     */
	public boolean validateIdentityExpire(String mobile,String mobileCode, int expireSecond) ;

	/**
	 * 根据手机号码和用户输入验证码验证验证码是否正确
	 * @param mobile 发送验证码的手机号码
	 * @param mobileCode 用户输入的验证码
	 * @param isDelete 是否删除验证码
	 * @return 如果验证成功返回true，否则返回false
	 * @ 返回手机发送相应异常
	 */
	public boolean validateIdentity(String mobile,String mobileCode,Boolean isDelete) ;
	/**
	 * 根据手机号码发送信息，支持多个手机号发送
	 * @param mobiles 多个手机号，以逗号隔开，如"13888888888,13999999999"
	 * @param message 发送的信息
	 * @return 如果发送成功返回空字符串，否则返回发送失败的手机号，多个手机号时以逗号隔开
	 */
	public String sendMobiles(String mobiles, String message);
	
	/**
	 * 紧急情况，短信通知系统管理员
	 * @param message 消息
	 * @param isTsak true-定时任务异常警告，false-一般警告
	 * @return 如果发送成功返回空字符串，否则返回发送失败的手机号，多个手机号时以逗号隔开
	 */
	
	public String sendSysManagerMobiles(String message, boolean isTsak);

	/**
	 * 紧急情况，短信通知产品运营
	 * @param message 消息
	 * @param isTsak true-定时任务异常警告，false-一般警告
	 * @return 如果发送成功返回空字符串，否则返回发送失败的手机号，多个手机号时以逗号隔开
	 */

	public String sendProductOperatorMobiles(String message, boolean isTsak);

	/**
	 * 短信通知财务
	 * @param message 消息
	 * @param isTsak true-定时任务异常警告，false-一般警告
	 * @return 如果发送成功返回空字符串，否则返回发送失败的手机号，多个手机号时以逗号隔开
	 */

	public String sendFinanceMobiles(String message, boolean isTsak);

	/**
	 * 告警短信通知指定级别负责人
	 * @param message 消息
	 * @param isTsak true-定时任务异常警告，false-一般警告
	 * @param warnMobiles 告警级别常量KEY值
	 * @return 如果发送成功返回空字符串，否则返回发送失败的手机号，多个手机号时以逗号隔开
	 */
	public String sendWarnMobiles(String message,boolean isTsak, String...warnMobiles);

	/**
	 * 获取手机发送短信后间隔的时间
	 * @param mobile 手机号码
	 * @return 如果bs_auth中不存在此手机发送记录或者发送时间超过60秒则返回-1  否则返回正常间隔
	 */
	public Integer interval(String mobile);
	

	/**
	 * 云贷自主放款，还款预下单，根据手机号码发送验证码
	 * @param mobile 手机号码
	 * @return 如果发送成功返回发送的验证码，
	 */
	public String sendRepayPreToken(String mobile);
	
	/**
	 * 云贷自主放款，绑卡预下单，根据手机号码发送验证码
	 * @param mobile
	 * @return
	 */
	public String sendBindCardPreToken(String mobile);

	/**
	 * 云贷自主放款，绑卡预下单，根据手机号码发送验证码
	 * @param mobile	手机号
	 * @param addserial	扩展号，不同扩展号用于不同的短信签名，默认为1，币港湾。
     * @return
     */
	public String sendBindCardPreToken(String mobile, String addserial);

	/**
	 * 赞分期还款预下单，根据手机号码发送验证码
	 * @param mobile 手机号码
	 * @return 如果发送成功返回发送的验证码，
	 */
	public String sendZanRepayPreToken(String mobile);

	/**
	 * 赞分期绑卡预下单，根据手机号码发送验证码
	 * @param mobile
	 * @return
	 */
	public String sendZanBindCardPreToken(String mobile);
	
	/**
	 * 还款预下单，根据手机号码发送验证码
	 * @param mobile 手机号码
	 * @param partnerCode 合作方
	 * @return 如果发送成功返回发送的验证码，
	 */
	public String sendRepayPreToken(String mobile, String partnerCode);

	/**
	 * 根据手机号码发送验证码 管理台云贷/赞时贷砍头息划转验证码
	 * @param mobile 手机号码
	 * @param amount 金额
	 * @param userName 收款人
	 * @param propertySymbol 资产方
	 * @return 如果发送成功返回发送的验证码，否则返回发送失败信息
	 */
	public String sendYunHeadFeeIdentify(String mobile, String propertySymbol, Double amount, String userName) ;
	
}
