package com.pinting.business.hessian.manage.message;

import java.util.Date;
import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsUserTagListQuery extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1883579638135805055L;
	private Integer id;
	private Integer status;
	private Integer sIsBindBank;
	private Integer sIsBindName;
	private String sNick;
	private String sEmail;
	private String sName;
	private String sReward;
	private String eReward;
	private String sIdCard;
	private String sBankCard;
	private Integer sAgent;
	
	private String sRecommend;
	private String eRecommend;
	private Date sregistTime;
	private Date eregistTime;
	private Date sFirstBuyTime;
	private Date eFirstBuyTime;
	//排序
	private String orderField;
	private String orderDirection;
	
	private Integer totalRows;
	
	private String sBalance;
	private String eBalance;
	private String sTotalPrincipal;
	private String eTotalPrincipal;
	private Integer bankStatus;
	private String bankName;
	private Integer userId;
	private String sSumBalance;
	private String eSumBalance;
	private String gender;
	private String age;
	
	private String agentIds;
	
	private String nonAgentId;
	
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
	private String sAge;
	private String eAge;
	/** 推荐人数  */
	private String sRecommendNum;
	private String eRecommendNum;
	
	/** 标签名字 */
	private String content;
	private List<Integer> tagIdList;
	private String noTagId;
	private String buyBankType;    //购买银行类别
	private Integer tagIdListSize;

	public List<Integer> getTagIdList() {
		return tagIdList;
	}

	public void setTagIdList(List<Integer> tagIdList) {
		this.tagIdList = tagIdList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	public Date getSregistTime() {
		return sregistTime;
	}

	public void setSregistTime(Date sregistTime) {
		this.sregistTime = sregistTime;
	}

	public Date getEregistTime() {
		return eregistTime;
	}

	public void setEregistTime(Date eregistTime) {
		this.eregistTime = eregistTime;
	}

	public String getsRecommend() {
		return sRecommend;
	}

	public void setsRecommend(String sRecommend) {
		this.sRecommend = sRecommend;
	}

	public String geteRecommend() {
		return eRecommend;
	}

	public void seteRecommend(String eRecommend) {
		this.eRecommend = eRecommend;
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

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
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
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	private String searchMobile;
	

	public String getSearchMobile() {
		return searchMobile;
	}

	public void setSearchMobile(String searchMobile) {
		this.searchMobile = searchMobile;
	}
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private Integer numPerPage = 20;
	/**
	 * 当前页码
	 */
	private Integer pageNum;

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getsIdCard() {
		return sIdCard;
	}

	public void setsIdCard(String sIdCard) {
		this.sIdCard = sIdCard;
	}

	public String getsBankCard() {
		return sBankCard;
	}

	public void setsBankCard(String sBankCard) {
		this.sBankCard = sBankCard;
	}

	public Integer getsAgent() {
		return sAgent;
	}

	public void setsAgent(Integer sAgent) {
		this.sAgent = sAgent;
	}

	public Date getsFirstBuyTime() {
		return sFirstBuyTime;
	}

	public void setsFirstBuyTime(Date sFirstBuyTime) {
		this.sFirstBuyTime = sFirstBuyTime;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Date geteFirstBuyTime() {
		return eFirstBuyTime;
	}

	public void seteFirstBuyTime(Date eFirstBuyTime) {
		this.eFirstBuyTime = eFirstBuyTime;
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

	public Integer getBankStatus() {
		return bankStatus;
	}

	public void setBankStatus(Integer bankStatus) {
		this.bankStatus = bankStatus;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public String geteRecommendNum() {
		return eRecommendNum;
	}

	public void seteRecommendNum(String eRecommendNum) {
		this.eRecommendNum = eRecommendNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
