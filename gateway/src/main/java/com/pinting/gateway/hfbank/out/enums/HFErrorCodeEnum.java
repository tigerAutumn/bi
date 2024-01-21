package com.pinting.gateway.hfbank.out.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @project gateway
 * @title HFCode2BGWCodeEnum.java
 * @author YED
 * @date 2017-5-29
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰返回的错误信息预处理
 */
public enum HFErrorCodeEnum {
	//恒丰银行错误码
	HF_45000("45000",	"开通失败,用户已在平台开通"),
	//宝付错误码
	PROCESS_CODE_BF00100("BF00100", "支付平台异常"),
	PROCESS_CODE_BF00112("BF00112", "系统繁忙，请稍后再试"),
	PROCESS_CODE_BF00113("BF00113", "交易结果未知，请稍后查询"),
	PROCESS_CODE_BF00115("BF00115", "交易处理中，请稍后查询"),
	PROCESS_CODE_BF00144("BF00144", "该交易有风险,订单处理中"),
	PROCESS_CODE_BF00202("BF00202", "交易超时，请稍后查询"),
	
	SUCCESS_CODE_BF00101("BF00101", "持卡人信息有误"),                                           
	SUCCESS_CODE_BF00102("BF00102", "银行卡已过有效期，请联系发卡行"),                           
	SUCCESS_CODE_BF00103("BF00103", "账户余额不足"),                                             
	SUCCESS_CODE_BF00104("BF00104", "交易金额超限"),                                             
	SUCCESS_CODE_BF00105("BF00105", "短信验证码错误"),                                           
	SUCCESS_CODE_BF00106("BF00106", "短信验证码失效"),                                           
	SUCCESS_CODE_BF00107("BF00107", "当前银行卡不支持该业务"),                     
	SUCCESS_CODE_BF00108("BF00108", "交易失败，请联系发卡行"),                                   
	SUCCESS_CODE_BF00109("BF00109", "交易金额低于限额"),                                         
	SUCCESS_CODE_BF00110("BF00110", "该卡暂不支持此交易"),                                       
	SUCCESS_CODE_BF00111("BF00111", "交易失败"),                                                 
	SUCCESS_CODE_BF00116("BF00116", "该终端号不存在"),                                           
	SUCCESS_CODE_BF00118("BF00118", "报文中密文解析失败"),                                       
	SUCCESS_CODE_BF00120("BF00120", "报文交易要素缺失"),                                         
	SUCCESS_CODE_BF00121("BF00121", "报文交易要素格式错误"),                                     
	SUCCESS_CODE_BF00122("BF00122", "卡号和支付通道不匹配"),                                     
	SUCCESS_CODE_BF00123("BF00123", "商户不存在或状态不正常，请联系宝付"),                       
	SUCCESS_CODE_BF00124("BF00124", "商户与终端号不匹配"),                                       
	SUCCESS_CODE_BF00125("BF00125", "商户该终端下未开通此类型交易"),                             
	SUCCESS_CODE_BF00126("BF00126", "该笔订单已存在"),                                           
	SUCCESS_CODE_BF00127("BF00127", "不支持该支付通道的交易"),                                   
	SUCCESS_CODE_BF00128("BF00128", "该笔订单不存在"),                                           
	SUCCESS_CODE_BF00129("BF00129", "密文和明文中参数【%s】不一致,请确认是否被篡改！"),          
	SUCCESS_CODE_BF00130("BF00130", "请确认是否发送短信,当前交易必须通过短信验证！"),            
	SUCCESS_CODE_BF00131("BF00131", "当前交易信息与短信交易信息不一致,请核对信息"),              
	SUCCESS_CODE_BF00132("BF00132", "短信验证超时，请稍后再试"),                                 
	SUCCESS_CODE_BF00133("BF00133", "短信验证失败"),                                             
	SUCCESS_CODE_BF00134("BF00134", "绑定关系不存在"),                                           
	SUCCESS_CODE_BF00135("BF00135", "交易金额不正确"),                                           
	SUCCESS_CODE_BF00136("BF00136", "订单创建失败"),                                             
	SUCCESS_CODE_BF00140("BF00140", "该卡已被注销"),                                             
	SUCCESS_CODE_BF00141("BF00141", "该卡已挂失"),                                               
	SUCCESS_CODE_BF00146("BF00146", "订单金额超过单笔限额"),                                     
	SUCCESS_CODE_BF00147("BF00147", "该银行卡不支持此交易"),                                     
	SUCCESS_CODE_BF00177("BF00177", "非法的交易"),                                               
	SUCCESS_CODE_BF00180("BF00180", "获取短信验证码失败"),                                       
	SUCCESS_CODE_BF00182("BF00182", "您输入的银行卡号有误，请重新输入"),                         
	SUCCESS_CODE_BF00187("BF00187", "暂不支持信用卡的绑定"),                                     
	SUCCESS_CODE_BF00188("BF00188", "绑卡失败"),                                                 
	SUCCESS_CODE_BF00190("BF00190", "商户流水号不能重复"),                                       
	SUCCESS_CODE_BF00199("BF00199", "订单日期格式不正确"),                                       
	SUCCESS_CODE_BF00200("BF00200", "发送短信和支付时商户订单号不一致"),                         
	SUCCESS_CODE_BF00201("BF00201", "发送短信和支付交易时金额不相等"),                           
	SUCCESS_CODE_BF00203("BF00203", "退款交易已受理"),                                           
	SUCCESS_CODE_BF00204("BF00204", "确认绑卡时与预绑卡时的商户订单号不一致"),                   
	SUCCESS_CODE_BF00232("BF00232", "银行卡未开通认证支付"),                                     
	SUCCESS_CODE_BF00233("BF00233", "密码输入次数超限，请联系发卡行"),                           
	SUCCESS_CODE_BF00234("BF00234", "单日交易金额超限"),                                         
	SUCCESS_CODE_BF00235("BF00235", "单笔交易金额超限"),                                         
	SUCCESS_CODE_BF00236("BF00236", "卡号无效，请确认后输入"),                                   
	SUCCESS_CODE_BF00237("BF00237", "该卡已冻结，请联系发卡行"),                                 
	SUCCESS_CODE_BF00249("BF00249", "订单已过期，请使用新的订单号发起交易"),                     
	SUCCESS_CODE_BF00251("BF00251", "订单未支付"),                                               
	SUCCESS_CODE_BF00253("BF00253", "交易拒绝"),                                                 
	SUCCESS_CODE_BF00255("BF00255", "发送短信验证码失败"),                                       
	SUCCESS_CODE_BF00256("BF00256", "请重新获取验证码"),                                         
	SUCCESS_CODE_BF00258("BF00258", "手机号码校验失败"),                                         
	SUCCESS_CODE_BF00260("BF00260", "短信验证码已过期，请重新发送"),                             
	SUCCESS_CODE_BF00261("BF00261", "短信验证码错误次数超限，请重新获取"),                       
	SUCCESS_CODE_BF00262("BF00262", "交易金额与扣款成功金额不一致，请联系宝付"),                 
	SUCCESS_CODE_BF00311("BF00311", "卡类型和biz_type值不匹配"),                                 
	SUCCESS_CODE_BF00312("BF00312", "卡号校验失败"),                                             
	SUCCESS_CODE_BF00313("BF00313", "商户未开通此产品"),                                         
	SUCCESS_CODE_BF00315("BF00315", "手机号码为空，请重新输入"),                                 
	SUCCESS_CODE_BF00316("BF00316", "ip未绑定，请联系宝付"),                                     
	SUCCESS_CODE_BF00317("BF00317", "短信验证码已失效，请重新获取"),                             
	SUCCESS_CODE_BF00321("BF00321", "身份证号不合法"),                                           
	SUCCESS_CODE_BF00322("BF00322", "卡类型和卡号不匹配"),                                       
	SUCCESS_CODE_BF00323("BF00323", "商户未开通交易模版"),                                       
	SUCCESS_CODE_BF00324("BF00324", "暂不支持此银行卡支付，请更换其他银行卡或咨询商户客服"),     
	SUCCESS_CODE_BF00325("BF00325", "非常抱歉！目前该银行正在维护中，请更换其他银行卡支付"),     
	SUCCESS_CODE_BF00327("BF00327", "请联系银行核实您的卡状态是否正常"),                         
	SUCCESS_CODE_BF00331("BF00331", "卡号校验失败"),                                             
	SUCCESS_CODE_BF00332("BF00332", "交易失败，请重新支付"),                                     
	SUCCESS_CODE_BF00333("BF00333", "该卡有风险，发卡行限制交易"),                               
	SUCCESS_CODE_BF00341("BF00341", "该卡有风险，请持卡人联系银联客服[95516]"),                  
	SUCCESS_CODE_BF00342("BF00342", "单卡单日余额不足次数超限"),                                 
	SUCCESS_CODE_BF00343("BF00343", "验证失败（手机号有误）"),                                   
	SUCCESS_CODE_BF00344("BF00344", "验证失败（卡号有误）"),                                     
	SUCCESS_CODE_BF00345("BF00345", "验证失败（姓名有误）"),                                     
	SUCCESS_CODE_BF00346("BF00346", "验证失败（身份证号有误）"),                                 
	SUCCESS_CODE_BF00347("BF00347", "交易次数频繁，请稍后重试"),                                 
	SUCCESS_CODE_BF00350("BF00350", "该卡当日失败次数已超过3次，请次日再试！"),                  
	SUCCESS_CODE_BF00351("BF00351", "该卡当日交易笔数超过限制，请次日再试！"),                   
	SUCCESS_CODE_BF00353("BF00353", "未设置手机号码，请联系发卡行确认"),                         
	SUCCESS_CODE_BF00373("BF00373", "请求处理中，请勿重复提交");                                 
	
	public static Map<String,String> hfErrorCodeMap=new HashMap<>();
	public static Map<String,String> hfProcessCodeMap=new HashMap<>();
	static {
		//宝付处理中的代码
		hfProcessCodeMap.put("BF00100", 	"支付平台异常");
		hfProcessCodeMap.put("BF00112", 	"系统繁忙,请稍后再试");
		hfProcessCodeMap.put("BF00113", 	"交易结果未知,请稍后查询");
		hfProcessCodeMap.put("BF00115", 	"交易处理中,请稍后查询");
		hfProcessCodeMap.put("BF00144", 	"该交易有风险,订单处理中");
		hfProcessCodeMap.put("BF00202", 	"交易超时,请稍后查询");
		
		hfErrorCodeMap.put("45000",	  "注册失败,用户已在平台注册");
		hfErrorCodeMap.put("BF00101","持卡人信息有误");
		hfErrorCodeMap.put("BF00102","银行卡已过有效期，请联系发卡行");
		hfErrorCodeMap.put("BF00103","账户余额不足");
		hfErrorCodeMap.put("BF00104","交易金额超限");
		hfErrorCodeMap.put("BF00105","短信验证码错误");
		hfErrorCodeMap.put("BF00106","短信验证码失效");
		hfErrorCodeMap.put("BF00107","当前银行卡不支持该业务，请联系发卡行");
		hfErrorCodeMap.put("BF00108","交易失败，请联系发卡行");
		hfErrorCodeMap.put("BF00109","交易金额低于限额");
		hfErrorCodeMap.put("BF00110","该卡暂不支持此交易");
		hfErrorCodeMap.put("BF00111","交易失败");
		hfErrorCodeMap.put("BF00116","该终端号不存在");
		hfErrorCodeMap.put("BF00118","报文中密文解析失败");
		hfErrorCodeMap.put("BF00120","报文交易要素缺失");
		hfErrorCodeMap.put("BF00121","报文交易要素格式错误");
		hfErrorCodeMap.put("BF00122","卡号和支付通道不匹配");
		hfErrorCodeMap.put("BF00123","商户不存在或状态不正常，请联系宝付");
		hfErrorCodeMap.put("BF00124","商户与终端号不匹配");
		hfErrorCodeMap.put("BF00125","商户该终端下未开通此类型交易");
		hfErrorCodeMap.put("BF00126","该笔订单已存在");
		hfErrorCodeMap.put("BF00127","不支持该支付通道的交易");
		hfErrorCodeMap.put("BF00128","该笔订单不存在");
		hfErrorCodeMap.put("BF00129","密文和明文中参数【%s】不一致;请确认是否被篡改！");
		hfErrorCodeMap.put("BF00130","请确认是否发送短信;当前交易必须通过短信验证！");
		hfErrorCodeMap.put("BF00131","当前交易信息与短信交易信息不一致;请核对信息");
		hfErrorCodeMap.put("BF00132","短信验证超时，请稍后再试");
		hfErrorCodeMap.put("BF00133","短信验证失败");
		hfErrorCodeMap.put("BF00134","绑定关系不存在");
		hfErrorCodeMap.put("BF00135","交易金额不正确");
		hfErrorCodeMap.put("BF00136","订单创建失败");
		hfErrorCodeMap.put("BF00140","该卡已被注销");
		hfErrorCodeMap.put("BF00141","该卡已挂失");
		hfErrorCodeMap.put("BF00146","订单金额超过单笔限额");
		hfErrorCodeMap.put("BF00147","该银行卡不支持此交易");
		hfErrorCodeMap.put("BF00177","非法的交易");
		hfErrorCodeMap.put("BF00180","获取短信验证码失败");
		hfErrorCodeMap.put("BF00182","您输入的银行卡号有误，请重新输入");
		hfErrorCodeMap.put("BF00187","暂不支持信用卡的绑定");
		hfErrorCodeMap.put("BF00188","绑卡失败");
		hfErrorCodeMap.put("BF00190","商户流水号不能重复");
		hfErrorCodeMap.put("BF00199","订单日期格式不正确");
		hfErrorCodeMap.put("BF00200","发送短信和支付时商户订单号不一致");
		hfErrorCodeMap.put("BF00201","发送短信和支付交易时金额不相等");
		hfErrorCodeMap.put("BF00203","退款交易已受理");
		hfErrorCodeMap.put("BF00204","确认绑卡时与预绑卡时的商户订单号不一致");
		hfErrorCodeMap.put("BF00232","银行卡未开通认证支付");
		hfErrorCodeMap.put("BF00233","密码输入次数超限，请联系发卡行");
		hfErrorCodeMap.put("BF00234","单日交易金额超限");
		hfErrorCodeMap.put("BF00235","单笔交易金额超限");
		hfErrorCodeMap.put("BF00236","卡号无效，请确认后输入");
		hfErrorCodeMap.put("BF00237","该卡已冻结，请联系发卡行");
		hfErrorCodeMap.put("BF00249","订单已过期，请使用新的订单号发起交易");
		hfErrorCodeMap.put("BF00251","订单未支付");
		hfErrorCodeMap.put("BF00253","交易拒绝");
		hfErrorCodeMap.put("BF00255","发送短信验证码失败");
		hfErrorCodeMap.put("BF00256","请重新获取验证码");
		hfErrorCodeMap.put("BF00258","手机号码校验失败");
		hfErrorCodeMap.put("BF00260","短信验证码已过期，请重新发送");
		hfErrorCodeMap.put("BF00261","短信验证码错误次数超限，请重新获取");
		hfErrorCodeMap.put("BF00262","交易金额与扣款成功金额不一致，请联系宝付");
		hfErrorCodeMap.put("BF00311","卡类型和biz_type值不匹配");
		hfErrorCodeMap.put("BF00312","卡号校验失败");
		hfErrorCodeMap.put("BF00313","商户未开通此产品");
		hfErrorCodeMap.put("BF00315","手机号码为空，请重新输入");
		hfErrorCodeMap.put("BF00316","ip未绑定，请联系宝付");
		hfErrorCodeMap.put("BF00317","短信验证码已失效，请重新获取");
		hfErrorCodeMap.put("BF00321","身份证号不合法");
		hfErrorCodeMap.put("BF00322","卡类型和卡号不匹配");
		hfErrorCodeMap.put("BF00323","商户未开通交易模版");
		hfErrorCodeMap.put("BF00324","暂不支持此银行卡支付，请更换其他银行卡或咨询商户客服");
		hfErrorCodeMap.put("BF00325","非常抱歉！目前该银行正在维护中，请更换其他银行卡支付");
		hfErrorCodeMap.put("BF00327","请联系银行核实您的卡状态是否正常");
		hfErrorCodeMap.put("BF00331","卡号校验失败");
		hfErrorCodeMap.put("BF00332","交易失败，请重新支付");
		hfErrorCodeMap.put("BF00333","该卡有风险，发卡行限制交易");
		hfErrorCodeMap.put("BF00341","该卡有风险，请持卡人联系银联客服[95516]");
		hfErrorCodeMap.put("BF00342","单卡单日余额不足次数超限");
		hfErrorCodeMap.put("BF00343","验证失败（手机号有误）");
		hfErrorCodeMap.put("BF00344","验证失败（卡号有误）");
		hfErrorCodeMap.put("BF00345","验证失败（姓名有误）");
		hfErrorCodeMap.put("BF00346","验证失败（身份证号有误）");
		hfErrorCodeMap.put("BF00347","交易次数频繁，请稍后重试");
		hfErrorCodeMap.put("BF00350","该卡当日失败次数已超过3次，请次日再试！");
		hfErrorCodeMap.put("BF00351","该卡当日交易笔数超过限制，请次日再试！");
		hfErrorCodeMap.put("BF00353","未设置手机号码，请联系发卡行确认");
		hfErrorCodeMap.put("BF00373","请求处理中，请勿重复提交");
	}
    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    HFErrorCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
