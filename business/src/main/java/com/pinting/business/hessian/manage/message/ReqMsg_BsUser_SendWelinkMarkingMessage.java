package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.util.StringUtil;

public class ReqMsg_BsUser_SendWelinkMarkingMessage extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3961102306438163425L;

	private Integer id;

	private String mobile;

	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		if (StringUtil.isNotBlank(mobile)) {
			if (mobile.substring(mobile.length() - 1).equals(",")) {
				mobile = mobile.substring(0, mobile.length() - 1);
			}
			mobile = mobile.replaceAll("\r\n", "");// 去掉换行
		}
		return mobile != null ? mobile.trim() : mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
