package com.pinting.business.redis.sign.model;

/**
 * 签章服务redis对象
 */
public class SignRedisVO {

    private Integer signFileId; // 签章文件ID

    private String agreementPdfName; // 签章文件名称

    private Integer warnCount; // 告警次数

    private Integer signFailCount; // 签章失败次数

    public Integer getSignFileId() {
        return signFileId;
    }

    public void setSignFileId(Integer signFileId) {
        this.signFileId = signFileId;
    }

    public String getAgreementPdfName() {
        return agreementPdfName;
    }

    public void setAgreementPdfName(String agreementPdfName) {
        this.agreementPdfName = agreementPdfName;
    }

    public Integer getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(Integer warnCount) {
        this.warnCount = warnCount;
    }

    public Integer getSignFailCount() {
        return signFailCount;
    }

    public void setSignFailCount(Integer signFailCount) {
        this.signFailCount = signFailCount;
    }
}
