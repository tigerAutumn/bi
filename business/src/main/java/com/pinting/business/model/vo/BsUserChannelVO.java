package com.pinting.business.model.vo;

import com.pinting.business.model.BsUserChannel;

public class BsUserChannelVO extends BsUserChannel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6263550729880687398L;
	
	/** 用户id */
	private Integer uid;
	
	/** 19付银行信息表 */
	private Integer payId;
	
	/** 用户名 */
	private String userName;
	
	/** 用户注册手机号 */
	private String mobile;
	
	/** 19付银行id */
	private Integer bankId;
	
	/** 19付银行编码 */
	private String pay19BankCode;
	
	/** 渠道类型 */
	private String channel;
	
	/** 渠道优先级 */
	private Integer channelPriority;
	
	/** 银行名称 */
	private String name;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
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

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getPay19BankCode() {
		return pay19BankCode;
	}

	public void setPay19BankCode(String pay19BankCode) {
		this.pay19BankCode = pay19BankCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getChannelPriority() {
		return channelPriority;
	}

	public void setChannelPriority(Integer channelPriority) {
		this.channelPriority = channelPriority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
