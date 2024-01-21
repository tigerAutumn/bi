package com.pinting.site.common.enums;

public enum CookieEnums {
	
	_SITE("_site"),
	_SITE_USER_ID("_userId"),
	_SITE_USER_NAME("_userName"),
	_SITE_USER_TYPE("_userType"),
	_SITE_MOBILE_CODE("_userMobileCode"),
	_SITE_MOBILE_NUM("_userMobile"),
	_SITE_EMAIL_ADDRESS("_userEmail"),
	_SITE_EMAIL_CODE("_userEmailCode"),
	_SITE_FOOTER_INDEX("_footerIndex"),
	_SITE_RECOMMEND_ID("_recommendId"),
	_SITE_AGENT_ID("_agentId"),
	_SITE_REAL_AGENT_ID("_realAgentId"),
	_SITE_OPEN_ID("_openId"),
	_SITE_BS_ACTIVITY_USER("_bsActivityUser"),
	_SITE_BS_QIANBAO_USER("_qianbaoUser"),
	_SITE_WX_NICK("_wxNick"),
	_SITE_WX_HEAD_IMG_URL("_wxHeadImgUrl"),
	_CODE("_code"),
	_CODE_IMG("_codeImg"),//图形验证码
	_ACTIVITY_LUCKY_DRAW("_activityLuckyDraw"),
	_ACTIVITY("_activity"),
	_ACTIVITY_USER_ID("_activityUserId"),//活动用户id
	_ACTIVITY_AGENT_ID("_activityAgentId"),//活动渠道id
	_ADS_EFFECT_UTM_RESOURCE("_adsEffectUtmResource"),
	_ADS_EFFECT_UTM_MEDIUM("_adsEffectUtmMedium"),
	_ADS_EFFECT_UTM_TERM("_adsEffectUtmTerm"),
	_ADS_EFFECT_UTM_CONTENT("_adsEffectUtmContent"),
	_ADS_EFFECT_UTM_CAMPAIGN("_adsEffectUtmCampaign"),
	_ADS("_ads"),
	_CSAI("_csai"),
	_CSAI_AGENT_CSAI("_csai"),
	//业务相关
	_SITE_BIZ("_site_biz"),
	//跳转连接
	_BIZ_REDIRECT_URL("_redirectUrl"),
	//token
	_BIZ_TOKEN("_token"),
	_BIZ_PRE_TOKEN("_pre_token"),
	_BIZ_MOBILE_TOKEN("_mobile_token"),
	_SITE_SESSION("_site_session"),
	
	// 显示终端
	_VIEW("_view"),
	//柯桥日报等标识
	_VIEW_AGENT_FLAG("_agentFlag"),

	// 验证码
	_SITE_CODE("_site_code"),
	_SITE_178_BACK_URL("_site_178_back_url"),

	_SITE_CODE_TS("_site_code_ts"),
	
	//用户IP
	_USER("_user"),
	_USER_IP("_userIP"),
	;

	private String name;
	
	CookieEnums(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}