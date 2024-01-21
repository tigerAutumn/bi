/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author HuanXiong
 * @version $Id: BsRedPacketInfoVO.java, v 0.1 2016-2-29 下午6:04:37 HuanXiong Exp $
 */
public class BsRedPacketInfoVO implements Serializable {

    /**  */
    private static final long serialVersionUID = 7760633254534122997L;
    
    private Integer id; // 红包Info

    private String mobile;
    
    private String rpName;  // 红包名称
    
    private String serialNo;

    private String serialName;  
    
    private String distributeType;
    
    private Double amount;
    
    private Double full;    // 用于 使用条件
    
    private Double subtract;    // 用于 使用条件
    
    private Date useTimeStart;
    
    private Date useTimeEnd;
    
    private Date distributeTime;    // 发放日期（bs_red_packat_check的updateTime）
    
    private String status;
    
    private String agentName;
    
    private String triggerType;
    
    private Integer userId;
    
    private Integer investDays; //投资期限，天数
    
    private Double investAmount; //投资金额，包括红包金额
    
    private Date usedTime; //使用时间
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRpName() {
        return rpName;
    }

    public void setRpName(String rpName) {
        this.rpName = rpName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFull() {
        return full;
    }

    public void setFull(Double full) {
        this.full = full;
    }

    public Double getSubtract() {
        return subtract;
    }

    public void setSubtract(Double subtract) {
        this.subtract = subtract;
    }

    public Date getUseTimeStart() {
        return useTimeStart;
    }

    public void setUseTimeStart(Date useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public Date getUseTimeEnd() {
        return useTimeEnd;
    }

    public void setUseTimeEnd(Date useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getInvestDays() {
		return investDays;
	}

	public void setInvestDays(Integer investDays) {
		this.investDays = investDays;
	}

	public Double getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}
    
}
