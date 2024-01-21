package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

public class ReqMsg_Statistics_BuyMessageDepListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -154903504178003765L;
	
	private Date investBeginTime;  //投资到期起始时间
	
	private Date investEndTime;		//投资到期结束时间
	
	private Date buyBeginTime;		//购买起始时间
	
	private Date buyEndTime;		//购买结束时间
	
	private String beginBuyAmount;	//购买起始金额
	
	private String endBuyAmount;	//购买结束金额
	
	private Integer term;				//产品期限
	
	private String productCode;    //产品代码
	
	private String buyBankCard;		//购买银行卡号
	
	private String bindBankCard;	//绑定银行卡号
	
	private Integer accountStatus;		//账户状态
	
	private String userName;		//用户名
	
	private String idCard;			//身份证号
	
	private String mobile;			//手机号码
	
	private String orderNo;			//订单号
	
	private String agentName;			//渠道名称
	
	private String buyBankType;    //购买银行类别
	
	private Integer agentId;       //渠道id
	
	private Double rate;          // 利率
	
	private String agentIds;
	
	private String nonAgentId;
	
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
	
    private Double startRate;     // 开始利率
	
	private Double endRate;       // 结束利率
	
	private String propertySymbol;  //资金接收方标识
		
	// 投资购买查询（存管后）新增
	private String investBuyStartTime;	//购买开始时间
	
	private String investBuyEndTime;		//购买结束时间
	
	private String investFinishStartTime;  //投资到期起始时间
	
	private String investFinishEndTime;		//投资到期结束时间
	
	private String partnerCode;  // 资产方
	
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

	public String getBeginBuyAmount() {
		return beginBuyAmount;
	}

	public void setBeginBuyAmount(String beginBuyAmount) {
		this.beginBuyAmount = beginBuyAmount;
	}

	public String getEndBuyAmount() {
		return endBuyAmount;
	}

	public void setEndBuyAmount(String endBuyAmount) {
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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getBuyBankType() {
		return buyBankType;
	}

	public void setBuyBankType(String buyBankType) {
		this.buyBankType = buyBankType;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getStartRate() {
		return startRate;
	}

	public void setStartRate(Double startRate) {
		this.startRate = startRate;
	}

	public Double getEndRate() {
		return endRate;
	}

	public void setEndRate(Double endRate) {
		this.endRate = endRate;
	}

	public String getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public String getInvestBuyStartTime() {
		return investBuyStartTime;
	}

	public void setInvestBuyStartTime(String investBuyStartTime) {
		this.investBuyStartTime = investBuyStartTime;
	}

	public String getInvestBuyEndTime() {
		return investBuyEndTime;
	}

	public void setInvestBuyEndTime(String investBuyEndTime) {
		this.investBuyEndTime = investBuyEndTime;
	}

	public String getInvestFinishStartTime() {
		return investFinishStartTime;
	}

	public void setInvestFinishStartTime(String investFinishStartTime) {
		this.investFinishStartTime = investFinishStartTime;
	}

	public String getInvestFinishEndTime() {
		return investFinishEndTime;
	}

	public void setInvestFinishEndTime(String investFinishEndTime) {
		this.investFinishEndTime = investFinishEndTime;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
}
