package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Agent_BuyMessageListQuery extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6077497964942552048L;

    private Date investBeginTime;  //投资到期起始时间
	
	private Date investEndTime;		//投资到期结束时间
	
	private Date buyBeginTime;		//购买起始时间
	
	private Date buyEndTime;		//购买结束时间
	
	private Double beginBuyAmount;	//购买起始金额
	
	private Double endBuyAmount;	//购买结束金额
	
	private Integer term;				//产品期限
	
	private String productCode;    //产品代码
	
	private String buyBankCard;		//购买银行卡号
	
	private String bindBankCard;	//绑定银行卡号
	
	private Integer accountStatus;		//账户状态
	
	private String userName;		//用户名
	
	private String idCard;			//身份证号
	
	private String mobile;			//手机号码
	private String orderNo;			//订单号
	
	private Integer mUserId;
	
	private String productName;//产品名称
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;
	//排序
	private String orderField;
	private String orderDirection;
	
	private Date interestBeginTime; // 起息开始时间
	private Date interestEndTime; // 起息结束时间

	/* 分销渠道 */
	private String distributionChannel;

	private String queryDateFlag;

	/* 购买终端 */
	private Integer terminalType;

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Date getInvestBeginTime() {
		return investBeginTime;
	}

	public void setInvestBeginTime(Date investBeginTime) {
		this.investBeginTime = investBeginTime;
	}

	public Date getInvestEndTime() {
		return investEndTime;
	}

	public void setInvestEndTime(Date investEndTime) {
		this.investEndTime = investEndTime;
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

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getBuyBankCard() {
		return buyBankCard;
	}

	public void setBuyBankCard(String buyBankCard) {
		this.buyBankCard = buyBankCard;
	}

	public String getBindBankCard() {
		return bindBankCard;
	}

	public void setBindBankCard(String bindBankCard) {
		this.bindBankCard = bindBankCard;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Date getInterestBeginTime() {
		return interestBeginTime;
	}

	public void setInterestBeginTime(Date interestBeginTime) {
		this.interestBeginTime = interestBeginTime;
	}

	public Date getInterestEndTime() {
		return interestEndTime;
	}

	public void setInterestEndTime(Date interestEndTime) {
		this.interestEndTime = interestEndTime;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getQueryDateFlag() {
		return queryDateFlag;
	}

	public void setQueryDateFlag(String queryDateFlag) {
		this.queryDateFlag = queryDateFlag;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
