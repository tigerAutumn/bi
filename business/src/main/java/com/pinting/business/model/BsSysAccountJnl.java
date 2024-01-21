package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsSysAccountJnl extends PageInfoObject {
    private Integer id;

    private Date transTime;

    private String transCode;

    private String transName;

    private Double transAmount;

    private Date sysTime;

    private Date channelTime;

    private String channelJnlNo;

    private Integer cdFlag1;

    private String subAccountCode1;

    private Double beforeBalance1;

    private Double afterBalance1;

    private Double beforeAvialableBalance1;

    private Double afterAvialableBalance1;

    private Double beforeFreezeBalance1;

    private Double afterFreezeBalance1;

    private Integer cdFlag2;

    private String subAccountCode2;

    private Double beforeBalance2;

    private Double afterBalance2;

    private Double beforeAvialableBalance2;

    private Double afterAvialableBalance2;

    private Double beforeFreezeBalance2;

    private Double afterFreezeBalance2;

    private Double fee;

    private Integer status;

    private String respCode;

    private String respMsg;

    private String note;

    private Integer opId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Date getSysTime() {
        return sysTime;
    }

    public void setSysTime(Date sysTime) {
        this.sysTime = sysTime;
    }

    public Date getChannelTime() {
        return channelTime;
    }

    public void setChannelTime(Date channelTime) {
        this.channelTime = channelTime;
    }

    public String getChannelJnlNo() {
        return channelJnlNo;
    }

    public void setChannelJnlNo(String channelJnlNo) {
        this.channelJnlNo = channelJnlNo;
    }

    public Integer getCdFlag1() {
        return cdFlag1;
    }

    public void setCdFlag1(Integer cdFlag1) {
        this.cdFlag1 = cdFlag1;
    }

    public String getSubAccountCode1() {
        return subAccountCode1;
    }

    public void setSubAccountCode1(String subAccountCode1) {
        this.subAccountCode1 = subAccountCode1;
    }

    public Double getBeforeBalance1() {
        return beforeBalance1;
    }

    public void setBeforeBalance1(Double beforeBalance1) {
        this.beforeBalance1 = beforeBalance1;
    }

    public Double getAfterBalance1() {
        return afterBalance1;
    }

    public void setAfterBalance1(Double afterBalance1) {
        this.afterBalance1 = afterBalance1;
    }

    public Double getBeforeAvialableBalance1() {
        return beforeAvialableBalance1;
    }

    public void setBeforeAvialableBalance1(Double beforeAvialableBalance1) {
        this.beforeAvialableBalance1 = beforeAvialableBalance1;
    }

    public Double getAfterAvialableBalance1() {
        return afterAvialableBalance1;
    }

    public void setAfterAvialableBalance1(Double afterAvialableBalance1) {
        this.afterAvialableBalance1 = afterAvialableBalance1;
    }

    public Double getBeforeFreezeBalance1() {
        return beforeFreezeBalance1;
    }

    public void setBeforeFreezeBalance1(Double beforeFreezeBalance1) {
        this.beforeFreezeBalance1 = beforeFreezeBalance1;
    }

    public Double getAfterFreezeBalance1() {
        return afterFreezeBalance1;
    }

    public void setAfterFreezeBalance1(Double afterFreezeBalance1) {
        this.afterFreezeBalance1 = afterFreezeBalance1;
    }

    public Integer getCdFlag2() {
        return cdFlag2;
    }

    public void setCdFlag2(Integer cdFlag2) {
        this.cdFlag2 = cdFlag2;
    }

    public String getSubAccountCode2() {
        return subAccountCode2;
    }

    public void setSubAccountCode2(String subAccountCode2) {
        this.subAccountCode2 = subAccountCode2;
    }

    public Double getBeforeBalance2() {
        return beforeBalance2;
    }

    public void setBeforeBalance2(Double beforeBalance2) {
        this.beforeBalance2 = beforeBalance2;
    }

    public Double getAfterBalance2() {
        return afterBalance2;
    }

    public void setAfterBalance2(Double afterBalance2) {
        this.afterBalance2 = afterBalance2;
    }

    public Double getBeforeAvialableBalance2() {
        return beforeAvialableBalance2;
    }

    public void setBeforeAvialableBalance2(Double beforeAvialableBalance2) {
        this.beforeAvialableBalance2 = beforeAvialableBalance2;
    }

    public Double getAfterAvialableBalance2() {
        return afterAvialableBalance2;
    }

    public void setAfterAvialableBalance2(Double afterAvialableBalance2) {
        this.afterAvialableBalance2 = afterAvialableBalance2;
    }

    public Double getBeforeFreezeBalance2() {
        return beforeFreezeBalance2;
    }

    public void setBeforeFreezeBalance2(Double beforeFreezeBalance2) {
        this.beforeFreezeBalance2 = beforeFreezeBalance2;
    }

    public Double getAfterFreezeBalance2() {
        return afterFreezeBalance2;
    }

    public void setAfterFreezeBalance2(Double afterFreezeBalance2) {
        this.afterFreezeBalance2 = afterFreezeBalance2;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }
}