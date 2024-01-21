package com.pinting.business.model;

import java.util.Date;

public class LnCompensateAgreementAddress {
    private Integer id; // 代偿协议下载地址表ID

    private String partnerCode; // YUN_DAI_SELF 云贷

    private String orderNo; // 代偿单号

    private String partnerLoanId; // 合作方借款编号

    private String agreementType; // 协议类型\r\n            债权转让通知书 DEBT_TRANS_NOTICES\r\n            代偿收款确认函 COLLECTION_CONFIRM\r\n            债权转让协议 DEBT_TRANSFER

    private String downloadUrl; // 下载地址

    private Integer downloadNum; // 下载次数

    private String isOpen; // OPEN：开放下载\r\n            CLOSE：关闭下载

    private String informStatus; // 代偿协议完成后通知合作方的状态 INIT 未通知 SUCCESS 通知成功 FAIL 通知失败

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getInformStatus() {
        return informStatus;
    }

    public void setInformStatus(String informStatus) {
        this.informStatus = informStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}