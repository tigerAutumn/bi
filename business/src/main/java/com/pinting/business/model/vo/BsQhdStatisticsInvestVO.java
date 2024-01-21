package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsUser;

/**
 * 渠道用户投资查询（秦皇岛）
 * @author SHENGUOPING
 * @date  2018年7月18日 下午2:35:04
 */
public class BsQhdStatisticsInvestVO extends BsUser {

	private static final long serialVersionUID = -3147052696371390916L;

	/**
	 * 手机号前三位
	 */
	private String preMobile;

	/**
	 * 手机号后四位
	 */
	private String afterMobile;
	
	private String mobile;			//手机号码
	
	private String userName;		//用户名

	private Double beginBuyAmount;	//购买起始金额
	
	private Double endBuyAmount;	//购买结束金额
	
	private Date settleAccountsBeginTime; //结算起始时间
	
	private Date settleAccountsEndTime;  //结算结束时间
	
	private Date buyBeginTime;		//购买起始时间
	
	private Date buyEndTime;		//购买结束时间
	
	private Integer term;				//产品期限
	
	private Integer accountStatus;		//账户状态
	
	private Integer mUserId;
	
	private String productName;//产品名称
	
	private String distributionChannel;

	private String terminalType; // 购买终端
	
	private Double rate;		//产品利率
	
	private Double openBalance; //投资金额
	
	private String bankName;	//银行名 
	
	private Date openTime;

	private Date lastFinishInterestDate;  // 结算日期
	
	private Integer agentId;
	
	private String agentName;
	
	public String getPreMobile() {
		return preMobile;
	}

	public void setPreMobile(String preMobile) {
		this.preMobile = preMobile;
	}

	public String getAfterMobile() {
		return afterMobile;
	}

	public void setAfterMobile(String afterMobile) {
		this.afterMobile = afterMobile;
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

	public Double getBeginBuyAmount() {
		return beginBuyAmount;
	}

	public void setBeginBuyAmount(Double beginBuyAmount) {
		this.beginBuyAmount = beginBuyAmount;
	}

	public Double getEndBuyAmount() {
		return endBuyAmount;
	}

	public void setEndBuyAmount(Double endBuyAmount) {
		this.endBuyAmount = endBuyAmount;
	}

	public Date getSettleAccountsBeginTime() {
		return settleAccountsBeginTime;
	}

	public void setSettleAccountsBeginTime(Date settleAccountsBeginTime) {
		this.settleAccountsBeginTime = settleAccountsBeginTime;
	}

	public Date getSettleAccountsEndTime() {
		return settleAccountsEndTime;
	}

	public void setSettleAccountsEndTime(Date settleAccountsEndTime) {
		this.settleAccountsEndTime = settleAccountsEndTime;
	}

	public Date getBuyBeginTime() {
		return buyBeginTime;
	}

	public void setBuyBeginTime(Date buyBeginTime) {
		this.buyBeginTime = buyBeginTime;
	}

	public Date getBuyEndTime() {
		return buyEndTime;
	}

	public void setBuyEndTime(Date buyEndTime) {
		this.buyEndTime = buyEndTime;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Integer getmUserId() {
		return mUserId;
	}

	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(Double openBalance) {
		this.openBalance = openBalance;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getLastFinishInterestDate() {
		return lastFinishInterestDate;
	}

	public void setLastFinishInterestDate(Date lastFinishInterestDate) {
		this.lastFinishInterestDate = lastFinishInterestDate;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
}
