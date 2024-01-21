package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 渠道用户投资查询（秦皇岛）
 * @author SHENGUOPING
 * @date  2018年7月18日 下午1:22:41
 */
public class ReqMsg_Agent_QhdBuyMessageListQuery extends ReqMsg {

	private static final long serialVersionUID = -6078324110796639555L;
	
	private String mobile;			//手机号码
	
	private String userName;		//用户名

	private Double beginBuyAmount;	//购买起始金额
	
	private Double endBuyAmount;	//购买结束金额
	
    private Date settleAccountsBeginTime;  //结算起始时间
	
	private Date settleAccountsEndTime;		//结算结束时间
	
	private Date buyBeginTime;		//购买起始时间
	
	private Date buyEndTime;		//购买结束时间
	
	private Integer term;				//产品期限
	
	private Integer accountStatus;		//账户状态
	
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

	/* 分销渠道 */
	private String distributionChannel;

	private String queryFlag;

	/* 购买终端 */
	private String terminalType;
		
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
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
		
}
