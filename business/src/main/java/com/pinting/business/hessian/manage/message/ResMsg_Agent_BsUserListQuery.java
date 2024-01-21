package com.pinting.business.hessian.manage.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Agent_BsUserListQuery extends ResMsg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1424409062648467462L;
	/**
	 * 以下循环：
	 * id					用户Id
	 * nick					用户昵称
 	 * userName 		          用户名
	 * mobile				手机号
	 * idCard				身份证
	 * status				状态
	 * sumBalance           总资产
	 * canWithdraw			可提现金额
	 * currentInterest		投资收益
	 * currentBanlace		当前投资本金
	 * totalBonus			累计推荐奖励
	 * totalInterest		累计投资收益
	 * cardNo				绑定的银行卡号
	 * bankStatus			绑定银行卡号的状态 （1-正常  2-禁用 3-绑定中 4.绑定失败）
	 * bindTime 			绑卡时间
	 * cardOwner			银行卡所属人
	 */
	private List<HashMap<String, Object>> bsUserList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	private String search;
	private Integer sIsBindBank;
	private Integer sIsBindName;
	private String sNick;
	private String sEmail;
	private String sName;
	private String sReward;
	private String eReward;
	
	private String sRecommend;
	private String eRecommend;
	private Date sregistTime;
	private Date eregistTime;
	/* 系统用户的渠道编号 */
	private Integer mAgentId;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public List<HashMap<String, Object>> getBsUserList() {
		return bsUserList;
	}
	public void setBsUserList(List<HashMap<String, Object>> bsUserList) {
		this.bsUserList = bsUserList;
	}
	public Integer getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getmAgentId() {
		return mAgentId;
	}

	public void setmAgentId(Integer mAgentId) {
		this.mAgentId = mAgentId;
	}
}
