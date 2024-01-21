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
 * @version $Id: RedPacketInfoVO.java, v 0.1 2016-3-1 下午1:07:34 HuanXiong Exp $
 */
public class RedPacketInfoVO implements Serializable {
    
    /**  */
    private static final long serialVersionUID = 3173118772503591641L;

    private Integer id;
    
    private Integer userId;
    
    private String serialName;
    
    private Double full;
    
    private Double subtract;
    
    private Date useTimeStart;
    
    private Date useTimeEnd;
    
    private String status;
    
    private String termLimit;
    
    private String termLimitMsg;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTermLimitMsg() {
        return termLimitMsg;
    }

    public void setTermLimitMsg(String termLimitMsg) {
        this.termLimitMsg = termLimitMsg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTermLimit() {
        return termLimit;
    }

    public void setTermLimit(String termLimit) {
        this.termLimit = termLimit;
    }
    
}
