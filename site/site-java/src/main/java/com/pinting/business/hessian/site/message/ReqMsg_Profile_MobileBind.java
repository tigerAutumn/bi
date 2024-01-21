package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_Profile_MobileBind.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:41:10
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_MobileBind extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5599313865929538672L;
	private String oldMobile;//类型编号	必填
	private String newMobile;//类型编号	必填
	private String oldMobileCode;//类型编号	必填
	private String newMobileCode;//类型编号	必填
	public String getOldMobile() {
		return oldMobile;
	}
	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}
	public String getNewMobile() {
		return newMobile;
	}
	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}
	public String getOldMobileCode() {
		return oldMobileCode;
	}
	public void setOldMobileCode(String oldMobileCode) {
		this.oldMobileCode = oldMobileCode;
	}
	public String getNewMobileCode() {
		return newMobileCode;
	}
	public void setNewMobileCode(String newMobileCode) {
		this.newMobileCode = newMobileCode;
	}
	
}
