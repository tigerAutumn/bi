package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 代偿人信息
 * @project business
 * @title BsUserCompensateVO.java
 * @author Dragon & cat
 * @date 2018-3-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class BsUserCompensateVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3824714506753847334L;
	/*用户ID*/
	private Integer id;
	/*用户姓名*/
	private String userName;
	/*用户手机号码*/
	private String mobile;
	/*用户身份证号码*/
	private String idCard;
	/*恒丰用户ID*/
	private String hfUserId;
	/*分隔时间*/
	private Date separateDate;

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getHfUserId() {
		return hfUserId;
	}

	public void setHfUserId(String hfUserId) {
		this.hfUserId = hfUserId;
	}

	public Date getSeparateDate() {
		return separateDate;
	}

	public void setSeparateDate(Date separateDate) {
		this.separateDate = separateDate;
	}
	
}
