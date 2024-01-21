package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MWxMenu_MWxMenuAdd extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -8011285267939592723L;
	
	private String type;

    private String name;

    private String url;

    private Integer parentId;

    private Integer orderNum;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
