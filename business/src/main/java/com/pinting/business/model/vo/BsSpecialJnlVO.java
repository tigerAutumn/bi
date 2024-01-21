package com.pinting.business.model.vo;

import java.util.List;

import com.pinting.business.model.BsSpecialJnl;

public class BsSpecialJnlVO extends BsSpecialJnl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 356501699910387215L;
	/**用户名称**/
	private String userName;
	/**用户手机号码**/
	private String mobile;
	
	private List<String> typeList;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<String> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}

}
