package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsUser;

public class BsUserAssetVO extends BsUser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1374876107565212952L;
	private Integer accountId;
	private Double totalBalance;
	private Double balance;
	private Integer bankcardCount;
	private Double sumBalance;
	private Double currentBalance;
	
	//银行卡信息
	private String cardNo;
	private Integer bankStatus;
	private Date bindTime;
	private String cardOwner;
	private String recommendNum;
	private String sEmail;
	private Integer sRecommend;
	private Integer eRecommend;
	private String sReward;
	private String eReward;
	private Date sRegisterTime;
	private Date eRegisterTime;
	private Integer sIsBindBank;
	private Integer sIsBindName;
	private String sNick;
	private String sName;
	private String bindFailReson;
	private String recommendName;
	private String sBankCard;
	
	private String agentName;
	private Integer bankCardNum;
	private String bankName;
	private Double totalPrincipal;
	
	private Date sFirstBuyTime;
	private Date eFirstBuyTime;
	/** 可用余额  账户余额 */
	private Double availableBalance;
	
	private Integer investmentTimes; // 投资次数
	private Double annualizedInvestment; // 年化投资额
	private Double stockAssets; // 存量资产
	private String age; // 年龄
	private String gender; // 性别
	private Date recentBuyTime; // 最近一次投资时间
	private Integer threeInvestmentTimes; // 近3个月投资次数
	
	private String recommendMobile; // 推荐人手机号码
	
    private List<Object> agentIds;
	
	private String nonAgentId;
	
	private String sBalance;
	private String eBalance;
	/** 累计投资本金*/
	private String sTotalPrincipal;
	private String eTotalPrincipal;
	private String sSumBalance;
	private String eSumBalance;
	/** 是否为常用卡 */
	private Integer isFirst;
	
	/** 账户余额 */
	private Double accountBalance;
	
	/** 投资次数起始值*/
	private String sInvestmentTimes;
	/** 投资次数结束值*/
	private String eInvestmentTimes;
	/** 累计投资收益起始值 */
	private String sTotalInterest;
	/** 累计投资收益结束值 */
	private String eTotalInterest;
	/** 当前投资本金 */
	private String sCurrentBalance;
	private String eCurrentBalance;
	/** 年龄 */
	private String sAge;
	private String eAge;
	/** 推荐人数  */
	private String sRecommendNum;
	private String eRecommendNum;
	/** 标签名字  */
	private String content;
	
	private List<Object> userIds;
	
	private Integer userId;
	
	private List<Object> tagIdList;
	
	private String noTagId;
	
	private String buyBankType;    //购买银行类别
	
	private Integer tagIdListSize;
	
	/** 投资开始时间 */
	private Date sInterestBeginDate;
	
	private Date eInterestBeginDate;
	
	/** 回款开始时间 */
	private Date sLastFinishInterestDate;
	
	private Date eLastFinishInterestDate;
	
	/** 回款金额 */
	private Double amountOfPayment;

	/**
	 * 销售人员
	 */
	private String saleName;

	/**
	 * 客户经理
	 */
	private String managerName;
	
	/** 预留手机号码 */
	private String reservedMobile;

	private Double realBalance;
	
	// 风险测评结果
	private String questionnaireResult;

	/* 注册端口 */
	private Integer regTerminal;

	/* 分销渠道 */
	private String distributionChannel;

	public Double getRealBalance() {
		return realBalance;
	}

	public void setRealBalance(Double realBalance) {
		this.realBalance = realBalance;
	}

	public List<Object> getTagIdList() {
		return tagIdList;
	}
	public void setTagIdList(List<Object> tagIdList) {
		this.tagIdList = tagIdList;
	}
	public String geteRecommendNum() {
		return eRecommendNum;
	}
	public void seteRecommendNum(String eRecommendNum) {
		this.eRecommendNum = eRecommendNum;
	}
	public String getRecommendName() {
		return recommendName;
	}
	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}
	public String getBindFailReson() {
		return bindFailReson;
	}
	public void setBindFailReson(String bindFailReson) {
		this.bindFailReson = bindFailReson;
	}
	//购买产品数量
	private Integer investNum;
	//达飞 实名绑卡状态
	private Integer dafyStatus;
	
	public Integer getInvestNum() {
		return investNum;
	}
	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}
	public Integer getDafyStatus() {
		return dafyStatus;
	}
	public void setDafyStatus(Integer dafyStatus) {
		this.dafyStatus = dafyStatus;
	}
	public Integer getsIsBindBank() {
		return sIsBindBank;
	}
	public void setsIsBindBank(Integer sIsBindBank) {
		this.sIsBindBank = sIsBindBank;
	}
	public Integer getsIsBindName() {
		return sIsBindName;
	}
	public void setsIsBindName(Integer sIsBindName) {
		this.sIsBindName = sIsBindName;
	}
	public String getsNick() {
		return sNick;
	}
	public void setsNick(String sNick) {
		this.sNick = sNick;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsEmail() {
		return sEmail;
	}
	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}
	public Integer getsRecommend() {
		return sRecommend;
	}
	public void setsRecommend(Integer sRecommend) {
		this.sRecommend = sRecommend;
	}
	public Integer geteRecommend() {
		return eRecommend;
	}
	public void seteRecommend(Integer eRecommend) {
		this.eRecommend = eRecommend;
	}
	public Date getsRegisterTime() {
		return sRegisterTime;
	}
	public void setsRegisterTime(Date sRegisterTime) {
		this.sRegisterTime = sRegisterTime;
	}
	public Date geteRegisterTime() {
		return eRegisterTime;
	}
	public void seteRegisterTime(Date eRegisterTime) {
		this.eRegisterTime = eRegisterTime;
	}

	public String getRecommendNum() {
		return recommendNum;
	}
	public void setRecommendNum(String recommendNum) {
		this.recommendNum = recommendNum;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Double getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getBankStatus() {
		return bankStatus;
	}
	public void setBankStatus(Integer bankStatus) {
		this.bankStatus = bankStatus;
	}
	public Date getBindTime() {
		return bindTime;
	}
	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}
	public String getCardOwner() {
		return cardOwner;
	}
	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Integer getBankCardNum() {
		return bankCardNum;
	}
	public void setBankCardNum(Integer bankCardNum) {
		this.bankCardNum = bankCardNum;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Double getTotalPrincipal() {
		return totalPrincipal;
	}
	public void setTotalPrincipal(Double totalPrincipal) {
		this.totalPrincipal = totalPrincipal;
	}
	public String getsBankCard() {
		return sBankCard;
	}
	public void setsBankCard(String sBankCard) {
		this.sBankCard = sBankCard;
	}
	public Integer getBankcardCount() {
		return bankcardCount;
	}
	public void setBankcardCount(Integer bankcardCount) {
		this.bankcardCount = bankcardCount;
	}
	public Date getsFirstBuyTime() {
		return sFirstBuyTime;
	}
	public void setsFirstBuyTime(Date sFirstBuyTime) {
		this.sFirstBuyTime = sFirstBuyTime;
	}
	public Date geteFirstBuyTime() {
		return eFirstBuyTime;
	}
	public void seteFirstBuyTime(Date eFirstBuyTime) {
		this.eFirstBuyTime = eFirstBuyTime;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Integer getInvestmentTimes() {
		return investmentTimes;
	}
	public void setInvestmentTimes(Integer investmentTimes) {
		this.investmentTimes = investmentTimes;
	}
	public Double getAnnualizedInvestment() {
		return annualizedInvestment;
	}
	public void setAnnualizedInvestment(Double annualizedInvestment) {
		this.annualizedInvestment = annualizedInvestment;
	}
	public Double getStockAssets() {
		return stockAssets;
	}
	public void setStockAssets(Double stockAssets) {
		this.stockAssets = stockAssets;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getRecentBuyTime() {
		return recentBuyTime;
	}
	public void setRecentBuyTime(Date recentBuyTime) {
		this.recentBuyTime = recentBuyTime;
	}
	public Integer getThreeInvestmentTimes() {
		return threeInvestmentTimes;
	}
	public void setThreeInvestmentTimes(Integer threeInvestmentTimes) {
		this.threeInvestmentTimes = threeInvestmentTimes;
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
	public String getRecommendMobile() {
		return recommendMobile;
	}
	public void setRecommendMobile(String recommendMobile) {
		this.recommendMobile = recommendMobile;
	}
	public Double getSumBalance() {
		return sumBalance;
	}
	public void setSumBalance(Double sumBalance) {
		this.sumBalance = sumBalance;
	}
	public Double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public String getsReward() {
		return sReward;
	}
	public void setsReward(String sReward) {
		this.sReward = sReward;
	}
	public String geteReward() {
		return eReward;
	}
	public void seteReward(String eReward) {
		this.eReward = eReward;
	}
	public String getsBalance() {
		return sBalance;
	}
	public void setsBalance(String sBalance) {
		this.sBalance = sBalance;
	}
	public String geteBalance() {
		return eBalance;
	}
	public void seteBalance(String eBalance) {
		this.eBalance = eBalance;
	}
	public String getsTotalPrincipal() {
		return sTotalPrincipal;
	}
	public void setsTotalPrincipal(String sTotalPrincipal) {
		this.sTotalPrincipal = sTotalPrincipal;
	}
	public String geteTotalPrincipal() {
		return eTotalPrincipal;
	}
	public void seteTotalPrincipal(String eTotalPrincipal) {
		this.eTotalPrincipal = eTotalPrincipal;
	}
	public String getsSumBalance() {
		return sSumBalance;
	}
	public void setsSumBalance(String sSumBalance) {
		this.sSumBalance = sSumBalance;
	}
	public String geteSumBalance() {
		return eSumBalance;
	}
	public void seteSumBalance(String eSumBalance) {
		this.eSumBalance = eSumBalance;
	}
	public Integer getIsFirst() {
		return isFirst;
	}
	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getsInvestmentTimes() {
		return sInvestmentTimes;
	}
	public void setsInvestmentTimes(String sInvestmentTimes) {
		this.sInvestmentTimes = sInvestmentTimes;
	}
	public String geteInvestmentTimes() {
		return eInvestmentTimes;
	}
	public void seteInvestmentTimes(String eInvestmentTimes) {
		this.eInvestmentTimes = eInvestmentTimes;
	}
	public String getsTotalInterest() {
		return sTotalInterest;
	}
	public void setsTotalInterest(String sTotalInterest) {
		this.sTotalInterest = sTotalInterest;
	}
	public String geteTotalInterest() {
		return eTotalInterest;
	}
	public void seteTotalInterest(String eTotalInterest) {
		this.eTotalInterest = eTotalInterest;
	}
	public String getsCurrentBalance() {
		return sCurrentBalance;
	}
	public void setsCurrentBalance(String sCurrentBalance) {
		this.sCurrentBalance = sCurrentBalance;
	}
	public String geteCurrentBalance() {
		return eCurrentBalance;
	}
	public void seteCurrentBalance(String eCurrentBalance) {
		this.eCurrentBalance = eCurrentBalance;
	}
	public String getsAge() {
		return sAge;
	}
	public void setsAge(String sAge) {
		this.sAge = sAge;
	}
	public String geteAge() {
		return eAge;
	}
	public void seteAge(String eAge) {
		this.eAge = eAge;
	}
	public String getsRecommendNum() {
		return sRecommendNum;
	}
	public void setsRecommendNum(String sRecommendNum) {
		this.sRecommendNum = sRecommendNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Object> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Object> userIds) {
		this.userIds = userIds;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNoTagId() {
		return noTagId;
	}
	public void setNoTagId(String noTagId) {
		this.noTagId = noTagId;
	}
	public String getBuyBankType() {
		return buyBankType;
	}
	public void setBuyBankType(String buyBankType) {
		this.buyBankType = buyBankType;
	}
	public Integer getTagIdListSize() {
		return tagIdListSize;
	}
	public void setTagIdListSize(Integer tagIdListSize) {
		this.tagIdListSize = tagIdListSize;
	}
	public Date getsInterestBeginDate() {
		return sInterestBeginDate;
	}
	public void setsInterestBeginDate(Date sInterestBeginDate) {
		this.sInterestBeginDate = sInterestBeginDate;
	}
	public Date geteInterestBeginDate() {
		return eInterestBeginDate;
	}
	public void seteInterestBeginDate(Date eInterestBeginDate) {
		this.eInterestBeginDate = eInterestBeginDate;
	}
	public Date getsLastFinishInterestDate() {
		return sLastFinishInterestDate;
	}
	public void setsLastFinishInterestDate(Date sLastFinishInterestDate) {
		this.sLastFinishInterestDate = sLastFinishInterestDate;
	}
	public Date geteLastFinishInterestDate() {
		return eLastFinishInterestDate;
	}
	public void seteLastFinishInterestDate(Date eLastFinishInterestDate) {
		this.eLastFinishInterestDate = eLastFinishInterestDate;
	}
	public Double getAmountOfPayment() {
		return amountOfPayment;
	}
	public void setAmountOfPayment(Double amountOfPayment) {
		this.amountOfPayment = amountOfPayment;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getReservedMobile() {
		return reservedMobile;
	}
	public void setReservedMobile(String reservedMobile) {
		this.reservedMobile = reservedMobile;
	}

	public String getQuestionnaireResult() {
		return questionnaireResult;
	}

	public void setQuestionnaireResult(String questionnaireResult) {
		this.questionnaireResult = questionnaireResult;
	}

	public Integer getRegTerminal() {
		return regTerminal;
	}

	public void setRegTerminal(Integer regTerminal) {
		this.regTerminal = regTerminal;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
}
