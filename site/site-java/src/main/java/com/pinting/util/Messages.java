package com.pinting.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Messages {
	/**
	 * 页面vm 常量翻译成文字消息   获得方法
	 * @param key 传入常量名， 如PRODUCT_TYPE_JSH
	 * @return
	 */
	public static String get(String key){
		Logger log = LoggerFactory.getLogger(Messages.class);
		String value = "";
		try {
			Class<Messages> clazz = Messages.class;
			Field field = clazz.getDeclaredField(key);
			value = String.valueOf(field.get(clazz));
		} catch (NoSuchFieldException e) {
			log.warn("can't find such field:" + key);
		} catch (Exception e) {
			log.warn("can't get such field:" + key);
		}
		
		return value;
	} 
	
	//是否绑定
	public static final String IS_BIND_1 = "已绑定";//是
	public static final String IS_BIND_2 = "未绑定";//否
	
	//是否认证
	public static final String IS_AUTH_1 = "已认证";//是
	public static final String IS_AUTH_2 = "未认证";//否
	
	//达飞 实名绑卡状态
	public static final String DAFY_STATUS_ = "未绑定";
	public static final String DAFY_STATUS_1 = "已绑定";
	public static final String DAFY_STATUS_2 = "禁用";
	public static final String DAFY_STATUS_3 = "绑定中";
	public static final String DAFY_STATUS_4 = "绑定失败";
	//银行卡状态 1-正常  2-禁用 3-绑定中 4.绑定失败
	public static final String BANK_STATUS_1 = "绑定成功";
	public static final String BANK_STATUS_2 = "禁用";
	public static final String BANK_STATUS_3 = "绑定中";
	public static final String BANK_STATUS_4 = "绑定失败";
	//产品类型
	public static final String PRODUCT_TYPE_JSH = "结算户";//
	public static final String PRODUCT_TYPE_DQLC = "定期理财";//
	
	
	//记账流水状态
	public static final String ACCOUNT_JNL_STATUS_1 = "交易成功";//记账流水状态 交易成功
	public static final String ACCOUNT_JNL_STATUS_2 = "交易失败";//记账流水状态 交易失败
	public static final String ACCOUNT_JNL_STATUS_3 = "系统确认中";//记账流水状态 系统确认中
	public static final String ACCOUNT_JNL_STATUS_4 = "银行处理中";//记账流水状态 银行处理中
	public static final String ACCOUNT_JNL_STATUS_5 = "通讯超时";//记账流水状态 超时
	public static final String ACCOUNT_JNL_STATUS_9 = "状态未知";//未知
	
	//银行名称
	public static final String BANK_NAME_1 = "工商银行";//
	public static final String BANK_NAME_2 = "农业银行";//
	public static final String BANK_NAME_3 = "建设银行";//
	public static final String BANK_NAME_4 = "光大银行";//
	

}
