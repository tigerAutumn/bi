package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsUser;

public class BsStatisticsVO  extends BsUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3532281620358004884L;

	private Double beginAmount;  //起始金额
	
	private Double endAmount;	//结束金额
	
	private Date beginTime;		//起始时间
	
	private Date endTime;		//结束时间
	
	private Integer term;		//产品利率
	
	private String buyBankCard;	//购买银行卡号
	
	private String buyBankName;	//购买银行名
	
	private Double balance;		//购买金额
	
	private String bindBankCard;//绑定银行卡卡号
	
	private String bindBankName;//绑定银行卡名
	
	private Date openTime;		//开户时间
	
	private Date settleAccountsBeginTime; //结算起始时间
	private Date settleAccountsEndTime;  //结算结束时间
	
	private Date InvestEndTime;	//投资结束时间
	
	private Integer accountStatus;//账户状态

	private Double rate;		//产品利率
	
	private String productName; //产品名称
	
	private Integer subAccountId; //子账户表Id号
	
	private Double accmulationInerest; //累计利息

	private String orderNo; //订单
	
	private String productCode; //产品代码
	
	private Integer productId; //产品Id号
	
	private String agentName; //渠道来源名称
	
	private String buyBankType;    //购买银行类别
	
	private Double settlementAmount; //结算金额
	
	private Double beginSettlementAmount; //起始结算金额
	
	private Double endSettlementAmount;	//结束结算金额
	
	private String returnCode; //返回码
	
	private String returnMsg; //返回值信息
	
	private Integer accountType; //账号类型
	
	private Integer orderStatus; //订单状态
	
	private String bankName;	//银行名
	
	private Double startRate;  //开始利率
	
	private Double endRate;    //结束利率
	
	private Integer recommendId;//推荐人编号
	
	private Integer agentId;//渠道编号
	
	private Double bonus;//首月奖励金
	
	private List<Object> agentIds;//渠道编号
	
	private String nonAgentId;//非渠道编号
	
	private Date interestBeginTime; // 起息开始时间
	private Date interestEndTime; // 起息结束时间

	/**
	 * 手机号前三位
	 */
	private String preMobile;

	/**
	 * 手机号后四位
	 */
	private String afterMobile;
	
	
	private String propertySymbol;  //资金接收方标识
	
	private String propertyName; // 资产合作方名称

	private String distributionChannel;

	private Date loanTime; //借款成功时间
	
	private Double openBalance;//开户金额openBalance

	private Integer terminalType; // 购买终端

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public Double getAccmulationInerest() {
		return accmulationInerest;
	}

	public void setAccmulationInerest(Double accmulationInerest) {
		this.accmulationInerest = accmulationInerest;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
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

	public String getBuyBankName() {
		return buyBankName;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public void setBuyBankName(String buyBankName) {
		this.buyBankName = buyBankName;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
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

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getInvestEndTime() {
		return InvestEndTime;
	}

	public void setInvestEndTime(Date investEndTime) {
		InvestEndTime = investEndTime;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBuyBankType() {
		return buyBankType;
	}

	public void setBuyBankType(String buyBankType) {
		this.buyBankType = buyBankType;
	}
	
	public Double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
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

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public List<Object> getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(List<Object> agentIds) {
		this.agentIds = agentIds;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
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

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Double getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(Double openBalance) {
		this.openBalance = openBalance;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
