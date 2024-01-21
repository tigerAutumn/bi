package com.pinting.business.service.common;

import java.util.Date;

/**
 * 短信平台发送和状态查询接口
 * @author bianyatian
 * @2017-6-19 下午2:15:55
 */
public interface SmsPlatformsDealService {
	

	/**
	 * 通知类
	 * 亿美短信发送
	 * @param mobile 手机号
	 * @param message 短信内容，不含签名
	 * @param addserial 签名序号：1-【币港湾】，2-【达飞云贷】，3-【赞分期】
	 * @param platformsId 对应平台id
	 * @return
	 */
	String emayNoticeSend(String mobile, String message, String addserial, Integer platformsId);
	
	
	/**
	 * 营销类短信
	 * 亿美短信发送
	 * @param mobile 手机号
	 * @param message 短信内容，不含签名
	 * @param platformsId 对应平台id
	 * @return
	 */
	String emayMarketingSend(String mobile, String message, Integer platformsId);
	
	/**
	 * 营销类短信
	 * 梦网短信发送
	 * @param mobile 手机号
	 * @param message 短信内容，不含签名
	 * @param platformsId 对应平台id
	 * @return
	 */
	String dreamMarketingSend(String mobile, String message, Integer platformsId);
	
	/**
	 * 亿美短信状态查询
	 */
	void emayCheck();
	
	/**
	 * 创蓝253 短信发送
	 * @param mobile
	 * @param message
	 * @param addserial
	 * @param platformsId
	 * @return
	 */
	String chuanglan253Send(String mobile, String message, String addserial, Integer platformsId);
	
	
	/**
	 * 创蓝253短信状态返回
	 * @param mobile
	 * @param msgid
	 * @param status
	 * @param time
	 */
	void chuanglan253Check(String mobile, String msgid, String status, Date time);

}
