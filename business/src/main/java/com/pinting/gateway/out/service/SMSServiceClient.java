package com.pinting.gateway.out.service;

import java.util.List;

import com.pinting.gateway.smsEnums.TemplateKey;

public interface SMSServiceClient {
	
	/**
     * 发送模板短信
     * 
     * @param mobile 手机号
     * @param templateKey 模板短信内容
     * @param messages 模板短信参数
     * @return
     */
    public boolean sendTemplateMessage(String mobile, TemplateKey templateKey, String... messages);

    /**
     * 发送模板短信 ---云贷自主放款
     * 
     * @param mobile 手机号
     * @param templateKey 模板短信内容
     * @param addserial 扩展号，不同扩展号用于不同的短信签名，默认为1，币港湾。
     * @param messages 模板短信参数
     * @return
     */
    public boolean sendTemplateMessage4YundaiSelf(String mobile, String addserial, TemplateKey templateKey, String... messages);
    
    /**
     * 发送随机验证码
     * 
     * @param mobile 手机号
     * @return 发送成功返回token码，失败则返回send fail
     */
    public String sendTokenMessage(String mobile);

    /**
     * 发送指定验证码
     * 
     * @param mobile 手机号
     * @param token
     * @return 发送成功返回token码，失败则返回send fail
     */
    public String sendTokenMessage(String mobile, String token);

    /**
     * 发送随机验证码
     * 
     * @param mobile 手机号
     * @return 发送成功返回token码，失败则返回send fail
     */
    @Deprecated
    public String sendTokenSound(String mobile, String token);


    /**
     * 发送营销短信
     * @param mobiles
     * @param message
     * @return
     */
    public String sendMarketingMessage(List<String> mobiles, String message);
    
    /**
     * 发送云贷自主放款，还款预下单的验证码
     * @param mobile
     * @return
     */
    public String sendYundaiSelfRepayToken(String mobile);
    /**
     * 发送云贷自主放款，绑卡预下单的验证码
     * @param mobile
     * @return
     */
	public String sendYundaiSelfBindCardToken(String mobile);

    /**
     * 发送云贷自主放款，绑卡预下单的验证码
     * @param mobile    手机号
     * @param addserial 扩展号，不同扩展号用于不同的短信签名，默认为1，币港湾。
     * @return
     */
    public String sendYundaiSelfBindCardToken(String mobile, String addserial);

    /**
     * 发送模板短信 ---赞分期
     *
     * @param mobile 手机号
     * @param templateKey 模板短信内容
     * @param addserial 扩展号，不同扩展号用于不同的短信签名，默认为1，币港湾。
     * @param messages 模板短信参数
     * @return
     */
    public boolean sendTemplateMessage4Zan(String mobile, String addserial, TemplateKey templateKey, String... messages);

    /**
     * 发送赞分期还款预下单的验证码
     * @param mobile
     * @return
     */
    public String sendZanRepayToken(String mobile);

    /**
     * 发送赞分期绑卡预下单的验证码
     * @param mobile
     * @return
     */
    public String sendZanBindCardToken(String mobile);

    String sendYundaiSelfRepayRepeatToken(String mobile);
    
    /**
     * 发送还款预下单的验证码
     * @param mobile
     * @param partnerCode 
     * @return
     */
    String sendRepayRepeatToken(String mobile, String partnerCode);

    /**
     * 发送随机验证码 管理台云贷/赞时贷砍头息划转验证码
     *
     * @param mobile 手机号
     * @param amount 金额
     * @param userName 收款人
     * @param propertySymbol 资产方
     * @return 发送成功返回token码，失败则返回send fail
     */
    public String sendYunHeadFeeTokenMessage(String mobile, String propertySymbol, Double amount, String userName);

    /**
     * 发送指定验证码 管理台云贷/赞时贷砍头息划转验证码
     *
     * @param mobile 手机号
     * @param token
     * @param amount 金额
     * @param userName 收款人
     * @param propertySymbol 资产方
     * @return 发送成功返回token码，失败则返回send fail
     */
    public String sendYunHeadFeeTokenMessage(String mobile, String propertySymbol, String amount, String userName, String token);
    
}
