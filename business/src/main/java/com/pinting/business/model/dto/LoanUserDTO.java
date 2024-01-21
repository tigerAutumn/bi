package com.pinting.business.model.dto;

import com.pinting.business.model.vo.PageInfoObject;

import java.util.Date;

/**
 * @title 管理台借款用户管理筛选条件
 * Created by 剑钊 on 2016/11/7.
 */
public class LoanUserDTO extends PageInfoObject {

    private String userId;


    private Long userid;

    /**
     * 注册时间起始
     */
    private Date createTimeStart;

    /**
     * 注册时间截至
     */
    private Date createTimeEnd;

    /**
     * 未还金额起始值
     */
    private String noReturnStart;

    /**
     * 未还金额起始值
     */
    private Double sNoReturn;

    /**
     * 未还金额截至值
     */
    private String noReturnEnd;

    /**
     * 未还金额截至值
     */
    private Double eNoReturn;

    /**
     * 当前逾期金额起始值
     */
    private String breakStart;

    /**
     * 当前逾期金额起始值
     */
    private Double sBreak;

    /**
     * 当前逾期金额截至值
     */
    private String breakEnd;

    /**
     * 当前逾期金额截至值
     */
    private Double eBreak;

    /**
     * 合作方
     */
    private String partner;

    /**
     * 历史借款金额起始值
     */
    private String historyStart;

    /**
     * 历史借款金额起始值
     */
    private Double sHistory;

    /**
     * 历史借款金额截至值
     */
    private String historyEnd;

    /**
     * 历史借款金额截至值
     */
    private Double eHistory;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getNoReturnStart() {
        return noReturnStart;
    }

    public void setNoReturnStart(String noReturnStart) {
        this.noReturnStart = noReturnStart;
    }

    public String getNoReturnEnd() {
        return noReturnEnd;
    }

    public void setNoReturnEnd(String noReturnEnd) {
        this.noReturnEnd = noReturnEnd;
    }

    public String getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(String breakStart) {
        this.breakStart = breakStart;
    }

    public String getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(String breakEnd) {
        this.breakEnd = breakEnd;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getHistoryStart() {
        return historyStart;
    }

    public void setHistoryStart(String historyStart) {
        this.historyStart = historyStart;
    }

    public String getHistoryEnd() {
        return historyEnd;
    }

    public void setHistoryEnd(String historyEnd) {
        this.historyEnd = historyEnd;
    }

    public Double getsNoReturn() {
        return sNoReturn;
    }

    public void setsNoReturn(Double sNoReturn) {
        this.sNoReturn = sNoReturn;
    }

    public Double geteNoReturn() {
        return eNoReturn;
    }

    public void seteNoReturn(Double eNoReturn) {
        this.eNoReturn = eNoReturn;
    }

    public Double getsBreak() {
        return sBreak;
    }

    public void setsBreak(Double sBreak) {
        this.sBreak = sBreak;
    }

    public Double geteBreak() {
        return eBreak;
    }

    public void seteBreak(Double eBreak) {
        this.eBreak = eBreak;
    }

    public Double getsHistory() {
        return sHistory;
    }

    public void setsHistory(Double sHistory) {
        this.sHistory = sHistory;
    }

    public Double geteHistory() {
        return eHistory;
    }

    public void seteHistory(Double eHistory) {
        this.eHistory = eHistory;
    }
}
