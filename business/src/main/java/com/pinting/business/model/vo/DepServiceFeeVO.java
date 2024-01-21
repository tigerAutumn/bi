package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 信息服务费查询
 * @author SHENGUOPING
 * @date  2017年10月20日 下午2:01:00
 */
public class DepServiceFeeVO extends PageInfoObject {

	private static final long serialVersionUID = -2417335692666523174L;
	
	private String productName;  //产品名称
	
	private String mobile;	//手机号
	
	private String userName;	//姓名
	
	private Integer userId;  //用户ID
	
	private String orderNo;		//订单号
	
	private Date openTime;	//购买日期
	
	private Integer productTerm;	//期限
	
	private Double productRate;		//产品利率
	
	private Double openBalance;	//购买金额
	
	private String bankName;	//提现银行		
	
	private Date withdrawTime;	//到期提现日
	
	private Integer accountStatus; //账户状态
		
	private String agentName;	//渠道来源
	
	private Integer revenueRate;	//结算比例
	
	private Double depServiceFee;	//信息服务费

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getProductTerm() {
		return productTerm;
	}

	public void setProductTerm(Integer productTerm) {
		this.productTerm = productTerm;
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getRevenueRate() {
		return revenueRate;
	}

	public void setRevenueRate(Integer revenueRate) {
		this.revenueRate = revenueRate;
	}

	public Double getDepServiceFee() {
		return depServiceFee;
	}

	public void setDepServiceFee(Double depServiceFee) {
		this.depServiceFee = depServiceFee;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Double getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(Double openBalance) {
		this.openBalance = openBalance;
	}
	
}
