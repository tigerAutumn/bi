package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/11/7.
 * @title 借款用户管理vo
 */
public class LoanUserVO {

    /**
     * 借款用户id
     */
    private Integer userId;

    /**
     * 合作方编码
     */
    private String partnerCode;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 历史借款总数
     */
    private Double historyLoanAmount;

    /**
     * 应还笔数
     */
    private Integer noReturnNum;

    /**
     * 应还金额
     */
    private Double noReturnAmount;

    /**
     * 当前逾期金额
     */
    private Double lateNotAmount;

    /**
     * 历史逾期总额
     */
    private Double lateAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getHistoryLoanAmount() {
        return historyLoanAmount!=null?historyLoanAmount:0;
    }

    public void setHistoryLoanAmount(Double historyLoanAmount) {
        this.historyLoanAmount = historyLoanAmount;
    }

    public Integer getNoReturnNum() {
        return noReturnNum!=null?noReturnNum:0;
    }

    public void setNoReturnNum(Integer noReturnNum) {
        this.noReturnNum = noReturnNum;
    }

    public Double getNoReturnAmount() {
        return noReturnAmount!=null?noReturnAmount:0;
    }

    public void setNoReturnAmount(Double noReturnAmount) {
        this.noReturnAmount = noReturnAmount;
    }

    public Double getLateNotAmount() {
        return lateNotAmount!=null?lateNotAmount:0;
    }

    public void setLateNotAmount(Double lateNotAmount) {
        this.lateNotAmount = lateNotAmount;
    }

    public Double getLateAmount() {
        return lateAmount!=null?lateAmount:0;
    }

    public void setLateAmount(Double lateAmount) {
        this.lateAmount = lateAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
