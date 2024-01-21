package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAppNotice_AppNoticeAdd extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -3944473771131305243L;
	
	private Integer releasePart;

    private String name;

    private String title;

    private String pushChar;

    private String pushAbstract;

    private Integer pushType;

    private Integer appPage;

    private String content;

	public Integer getReleasePart() {
		return releasePart;
	}

	public void setReleasePart(Integer releasePart) {
		this.releasePart = releasePart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPushChar() {
		return pushChar;
	}

	public void setPushChar(String pushChar) {
		this.pushChar = pushChar;
	}

	public String getPushAbstract() {
		return pushAbstract;
	}

	public void setPushAbstract(String pushAbstract) {
		this.pushAbstract = pushAbstract;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public Integer getAppPage() {
		return appPage;
	}

	public void setAppPage(Integer appPage) {
		this.appPage = appPage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
