package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_OrderListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6987339564590137076L;

	private String orderNo;

	private Integer status;

	private Date beginTime;

	private Date overTime;
	
	private String begTime;
	
	private String ovTime;

	private int pageNum;
	
	/**
	 * 每页显示的记录数(默认为200条,可以通过set改变其数量)
	 */
	private int numPerPage = 200;
	// 排序
	private String orderField;
	private String orderDirection;
	
	/** 用户手机号 */
	private String userMobile;
	
	private String userName;
	
	private Integer accountType;
	
	private String transType;
	
	private String channelTransType;
	
	private String bankName;
	
	private String bankCardNo;
	
	private Double amount;
	
	private Integer terminalType;
	
	private Date updateTime; 
	
	/** 购买银行类别 */
	private String buyBankType;
	
	/** 渠道id */
	private Integer agentId;
	
	/** 预留手机号 */
	private String mobile;
	
	/** 身份证 */
	private String idCard;
	
	/** 订单渠道 */
	private String payChannel;

	private String returnMsg;
	
	private String agentIds;
	
	private String nonAgentId;
    
	private Integer userId;

	/**
	 * 终端列表
	 */
	private String sShowTerminal;
	
    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
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

	public int getPageNum() {
		if (pageNum < 1) {

			this.pageNum = 1;
		}

		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getChannelTransType() {
		return channelTransType;
	}

	public void setChannelTransType(String channelTransType) {
		this.channelTransType = channelTransType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBegTime() {
		return begTime;
	}

	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}

	public String getOvTime() {
		return ovTime;
	}

	public void setOvTime(String ovTime) {
		this.ovTime = ovTime;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getsShowTerminal() {
		return sShowTerminal;
	}

	public void setsShowTerminal(String sShowTerminal) {
		this.sShowTerminal = sShowTerminal;
	}
}
