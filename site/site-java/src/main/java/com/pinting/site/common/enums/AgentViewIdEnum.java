package com.pinting.site.common.enums;

/**
 * 渠道终端枚举
 * 1、该枚举类中的渠道ID均没有PC端入口，只有H5页面；
 * 2、PC端打开这些渠道ID的链接均进入系统统一配置的错误页；
 * @author shh
 * @date 2018/7/17 15:50
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public enum AgentViewIdEnum {

	AGENT_VIEW_ID_QD("49", "七店"),
	AGENT_VIEW_ID_JT("51", "秦皇岛交通广播"),
	AGENT_VIEW_ID_XW("52", "秦皇岛新闻891"),
	AGENT_VIEW_ID_TV("53", "秦皇岛电视台今日报道"),
	AGENT_VIEW_ID_SJC("55", "1038私家车广播"),
	;

	/** code */
	private String code;

	/** description */
	private String description;

	private AgentViewIdEnum(String code, String description) {
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
