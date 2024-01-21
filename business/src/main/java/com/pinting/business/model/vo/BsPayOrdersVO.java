package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsPayOrders;

public class BsPayOrdersVO extends BsPayOrders {
    /**
     * 序列化编号
     */
    private static final long serialVersionUID = -2350192579091329668L;
    private String userName;
    private Date beginTime;
    private Date overTime;
    private Integer term;

    private Double baseRate;

    private String productName;
    /**
     * 银行卡
     **/
    private String bankCardNo;
    /**
     * 返回码
     **/
    private String returnCode;
    /**
     * 返回消息
     **/
    private String returnMsg;
    /**
     * 用户手机号码
     */
    private String userMobile;
    /**
     * 订单更新时间
     */
    private Date startUpdateTime;
    private Date endUpdateTime;
    private String transType;
    private Integer status;
    private Date updateTime;
    /**
     * 交易类型
     **/
    private List<String> transTypeList;

    private Integer orderId;
    private String transCode;
    private Double transAmount;
    private Date sysTime;
    private String subAccountCode;
    private Date channelTime;
    private String orderNo;
    private String channelTransType;
    private String channelJnlNo;
    private Integer userId;
    private Integer subAccountId;
    private String note;
    /**
     * 购买银行类别
     */
    private String buyBankType;
    /**
     * 渠道id
     */
    private Integer agentId;
    /**
     * 渠道名称
     */
    private String agentName;
    /**
     * 支付渠道
     */
    private String payChannel;

    private List<Object> agentIds;

    private String nonAgentId;

    /**
     * 邀请人
     */
    private String recommendName;

    /**
     * 销售人员
     */
    private String saleName;

    /**
     * 客户经理
     */
    private String managerName;

    /**
     * 终端列表
     */
    private List<Object> terminalTypes;
    
    /**
     * app版本
     */
    private String lastAppVersion;

    /**
     * app更新时间
     */
    private Date lastAppTime;

    public Date getSysTime() {
        return sysTime;
    }

    public void setSysTime(Date sysTime) {
        this.sysTime = sysTime;
    }

    private Date createTime;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
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

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Date getStartUpdateTime() {
        return startUpdateTime;
    }

    public void setStartUpdateTime(Date startUpdateTime) {
        this.startUpdateTime = startUpdateTime;
    }

    public Date getEndUpdateTime() {
        return endUpdateTime;
    }

    public void setEndUpdateTime(Date endUpdateTime) {
        this.endUpdateTime = endUpdateTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getTransTypeList() {
        return transTypeList;
    }

    public void setTransTypeList(List<String> transTypeList) {
        this.transTypeList = transTypeList;
    }

    public String getSubAccountCode() {
        return subAccountCode;
    }

    public void setSubAccountCode(String subAccountCode) {
        this.subAccountCode = subAccountCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getChannelTime() {
        return channelTime;
    }

    public void setChannelTime(Date channelTime) {
        this.channelTime = channelTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getChannelTransType() {
        return channelTransType;
    }

    public void setChannelTransType(String channelTransType) {
        this.channelTransType = channelTransType;
    }

    public String getChannelJnlNo() {
        return channelJnlNo;
    }

    public void setChannelJnlNo(String channelJnlNo) {
        this.channelJnlNo = channelJnlNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
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

    public List<Object> getTerminalTypes() {
        return terminalTypes;
    }

    public void setTerminalTypes(List<Object> terminalTypes) {
        this.terminalTypes = terminalTypes;
    }

	public String getLastAppVersion() {
		return lastAppVersion;
	}

	public void setLastAppVersion(String lastAppVersion) {
		this.lastAppVersion = lastAppVersion;
	}

	public Date getLastAppTime() {
		return lastAppTime;
	}

	public void setLastAppTime(Date lastAppTime) {
		this.lastAppTime = lastAppTime;
	}
}
