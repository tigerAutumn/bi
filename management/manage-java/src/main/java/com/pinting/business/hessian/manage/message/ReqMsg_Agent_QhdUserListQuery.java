package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年7月18日 上午9:40:25
 */
public class ReqMsg_Agent_QhdUserListQuery extends ReqMsg {

	private static final long serialVersionUID = -3884648521532258809L;

	/** 当前管理员id*/
	private Integer mUserId;
	
	private String mUserName;

	private Integer mAgentId;
	
	private String mobile;
	
	private String userName;
	
	private Date startRegisterTime;
	
	private Date endRegisterTime;

	
	/* 分销渠道 */
	private String distributionChannel;

	/* 注册时终端 */
	private Integer regTerminal;
	
	private Integer status;
	
	private Integer totalRows;

	private String queryFlag;
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;
	
	public Integer getmUserId() {
		return mUserId;
	}
	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public Integer getmAgentId() {
		return mAgentId;
	}
	public void setmAgentId(Integer mAgentId) {
		this.mAgentId = mAgentId;
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

	public Date getStartRegisterTime() {
		return startRegisterTime;
	}
	public void setStartRegisterTime(Date startRegisterTime) {
		this.startRegisterTime = startRegisterTime;
	}
	public Date getEndRegisterTime() {
		return endRegisterTime;
	}
	public void setEndRegisterTime(Date endRegisterTime) {
		this.endRegisterTime = endRegisterTime;
	}
	
	public String getDistributionChannel() {
		return distributionChannel;
	}
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	public Integer getRegTerminal() {
		return regTerminal;
	}
	public void setRegTerminal(Integer regTerminal) {
		this.regTerminal = regTerminal;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
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
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	
}
