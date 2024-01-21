package com.pinting.business.hessian.manage.message;


import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsUserListQuery extends ReqMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -4025939148604502123L;
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
	
	//风险测评结果
	private String questionnaireResult;

	/* 注册终端 */
	private Integer regTerminal;
	 
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
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;

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
}
