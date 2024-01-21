package com.pinting.business.enums;

import com.pinting.core.util.GlobEnvUtil;

/**
 * 微信通知模板枚举
 * @project business
 * @author bianyatian
 * @2018-8-6 下午4:58:07
 */
public enum WeChatTemplateEnum {
	
	
	
	BONUS_ARRIVE("bonus_arrive","奖励金到账通知","{\"topcolor\":\"#FF0000\",\"data\":{\"income_time\":{\"color\":\"#173177\",\"value\":\"bgw_income_time\"},\"income_amount\":{\"color\":\"#173177\",\"value\":\"bgw_income_amount元\"},\"remark\":{\"color\":\"#173177\",\"value\":\"备注：如有疑问请拨打400-806-1230。\"},\"first\":{\"color\":\"#173177\",\"value\":\"尊敬的客户，您有一笔奖励金已到账。\"}},\"template_id\":\""+GlobEnvUtil.get("wechat.template.bonusArrive")+"\",\"touser\":\"bgw_open_id\",\"url\":\""+GlobEnvUtil.get("wechat.server.web") + "/micro2.0/assets/assets\""+"}"),
	
	
	;
	
	private WeChatTemplateEnum(String noticeCode, String desc, String noticeTemp) {
		this.noticeCode = noticeCode;
		this.desc = desc;
		this.noticeTemp = noticeTemp;
	}
	private String noticeCode; //通知编码
	private String desc;//描述
	private String noticeTemp;//通知模板(模板中的可变参数用map传入，其中key与此模板中对应变量的字符串对应)
	
	
	public String getNoticeCode() {
		return noticeCode;
	}
	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNoticeTemp() {
		return noticeTemp;
	}
	public void setNoticeTemp(String noticeTemp) {
		this.noticeTemp = noticeTemp;
	}
	
	public static WeChatTemplateEnum getWeChatNoticeEnum(String noticeCode){
		for (WeChatTemplateEnum weChatNoticeEnum : values()) {
			if(weChatNoticeEnum.getNoticeCode().equals(noticeCode)){
				return weChatNoticeEnum;
			}
		}
		return null;
	}
	

}
