package com.pinting.business.model.vo;

import com.pinting.business.model.BsUser;

public class UserInfoXiCaiVO extends BsUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7297137198238908415L;
	
	/** QQ */
	private String qq;
	
	/** ip地址 */
	private String ip;
	
	/** 投资总金额 */
	private Double totalmoney;

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Double getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}
	

}
