package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 活跃用户记录添加入参
 * @author bianyatian
 * @2016-6-2 下午1:45:22
 */
public class ReqMsg_ActiveUserRecord_AddRecord extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7949397982826492002L;

	/*用户id*/
	private Integer userId;
	/*终端类型*/
    private String terminalType;
    /*点击链接*/
    private String srcUrl;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}


	
}
