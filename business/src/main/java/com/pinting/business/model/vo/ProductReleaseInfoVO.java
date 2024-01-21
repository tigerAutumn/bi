/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import java.util.List;

import com.pinting.business.model.BsProduct;

/**
 * 理财计划发布管理的查询所需VO
 * @author HuanXiong
 * @version $Id: ProductReleaseInfoVO.java, v 0.1 2016-4-21 上午10:49:47 HuanXiong Exp $
 */
public class ProductReleaseInfoVO extends BsProduct {
    
    /**  */
    private static final long serialVersionUID = 1053218500306973163L;

    private Integer productId;  // 产品ID
    
    private String serialName;  // 计划系列
    
    private String checkUserName;   // 审核人名字
    
    private String distributeUserName; // 发布人名字

    private String remindTagContent; // 提醒标签内容

    private String interestRatesTagContent; //加息标签内容

    // ================================ 查询相关条件 ================================
    private String distributeStartTime; // 发布开始时间
    
    private String distributeEndTime;   // 发布结束时间
    
    private String startTimeBegin;   //  开始时间
    
    private String startTimeEnd;    // 开始时间
    
    private String endTimeBegin; // 结束时间
    
    private String endTimeEnd;  // 结束时间
    
    private String sShowTerminal;   // 显示终端
    
    private List<Object>  showTerminalList;// 显示终端
    
    private String pActivityType;

    private String beginInterestDays; // 起息时间类型
    
    // ================================ 查询相关条件 ================================
    
    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getDistributeUserName() {
        return distributeUserName;
    }

    public void setDistributeUserName(String distributeUserName) {
        this.distributeUserName = distributeUserName;
    }

    public String getRemindTagContent() {
        return remindTagContent;
    }

    public void setRemindTagContent(String remindTagContent) {
        this.remindTagContent = remindTagContent;
    }

    public String getInterestRatesTagContent() {
        return interestRatesTagContent;
    }

    public void setInterestRatesTagContent(String interestRatesTagContent) {
        this.interestRatesTagContent = interestRatesTagContent;
    }

    public String getDistributeStartTime() {
        return distributeStartTime;
    }

    public void setDistributeStartTime(String distributeStartTime) {
        this.distributeStartTime = distributeStartTime;
    }

    public String getDistributeEndTime() {
        return distributeEndTime;
    }

    public void setDistributeEndTime(String distributeEndTime) {
        this.distributeEndTime = distributeEndTime;
    }

    public String getStartTimeBegin() {
        return startTimeBegin;
    }

    public void setStartTimeBegin(String startTimeBegin) {
        this.startTimeBegin = startTimeBegin;
    }

    public String getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(String startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public String getEndTimeBegin() {
        return endTimeBegin;
    }

    public void setEndTimeBegin(String endTimeBegin) {
        this.endTimeBegin = endTimeBegin;
    }

    public String getEndTimeEnd() {
        return endTimeEnd;
    }

    public void setEndTimeEnd(String endTimeEnd) {
        this.endTimeEnd = endTimeEnd;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getsShowTerminal() {
        return sShowTerminal;
    }

    public void setsShowTerminal(String sShowTerminal) {
        this.sShowTerminal = sShowTerminal;
    }

    public List<Object> getShowTerminalList() {
        return showTerminalList;
    }

    public void setShowTerminalList(List<Object> showTerminalList) {
        this.showTerminalList = showTerminalList;
    }

	public String getpActivityType() {
		return pActivityType;
	}

	public void setpActivityType(String pActivityType) {
		this.pActivityType = pActivityType;
	}

    public String getBeginInterestDays() {
        return beginInterestDays;
    }

    public void setBeginInterestDays(String beginInterestDays) {
        this.beginInterestDays = beginInterestDays;
    }
}
