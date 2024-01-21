package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Statistics_SelectUserInvestmentPaybackList extends ReqMsg {
	
	private static final long serialVersionUID = -154903504178003765L;
	
	private Date settleAccountsBeginTime; // 结算起始起始时间

	private Date settleAccountsEndTime; // 结算到期结束时间

	private Date beginTime; // 购买起始时间

	private Date endTime; // 购买结束时间

	private Double beginAmount; // 购买起始金额

	private Double endAmount; // 购买结束金额
	
	private Double beginSettlementAmount; //起始结算金额
	
	private Double endSettlementAmount;	//结束结算金额
	
	private String productCode;    //产品代码
	
	private String bindBankCard; // 回款银行卡号
	
	private String bindBankName; //回款银行卡类型

	private Integer accountStatus; // 账户状态

	private String userName; // 用户名

	private String idCard; // 身份证号

	private String mobile; // 手机号码
	
	private String orderNo; // 订单号
	
	private String agentName; //渠道来源
	
	private int numPerPage = 20; // 每页显示的记录数(默认为20条,可以通过set改变其数量)
	
	private int pageNum; // 当前页码
	
	// 排序
	private String orderField;
	
	private String orderDirection;
	
	private String buyBankCard; // 购买银行卡号
	
	private Integer term; // 产品期限
	
	private Double settlementAmount; //结算金额
	
	private Integer orderStatus; //订单状态
	
	private Integer agentId;  //渠道编号
	
	private String rate;
	
	private String productId;    //产品Id
	
	private String buyBankType;    //购买银行类别
	
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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(Double beginAmount) {
		this.beginAmount = beginAmount;
	}

	public Double getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(Double endAmount) {
		this.endAmount = endAmount;
	}

	public Double getBeginSettlementAmount() {
		return beginSettlementAmount;
	}

	public void setBeginSettlementAmount(Double beginSettlementAmount) {
		this.beginSettlementAmount = beginSettlementAmount;
	}

	public Double getEndSettlementAmount() {
		return endSettlementAmount;
	}

	public void setEndSettlementAmount(Double endSettlementAmount) {
		this.endSettlementAmount = endSettlementAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBindBankCard() {
		return bindBankCard;
	}

	public void setBindBankCard(String bindBankCard) {
		this.bindBankCard = bindBankCard;
	}

	public String getBindBankName() {
		return bindBankName;
	}

	public void setBindBankName(String bindBankName) {
		this.bindBankName = bindBankName;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
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

	public String getBuyBankCard() {
		return buyBankCard;
	}

	public void setBuyBankCard(String buyBankCard) {
		this.buyBankCard = buyBankCard;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBuyBankType() {
		return buyBankType;
	}

	public void setBuyBankType(String buyBankType) {
		this.buyBankType = buyBankType;
	}
	
	

}
