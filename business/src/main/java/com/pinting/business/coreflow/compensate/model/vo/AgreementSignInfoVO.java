package com.pinting.business.coreflow.compensate.model.vo;

public class AgreementSignInfoVO {
    private String agreementPdfName; // 协议PDF文件名

    private String agreementNo; // 协议编号

    private String agreementHtml; // 协议网络地址

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getAgreementHtml() {
        return agreementHtml;
    }

    public void setAgreementHtml(String agreementHtml) {
        this.agreementHtml = agreementHtml;
    }

    public String getAgreementPdfName() {
        return agreementPdfName;
    }

    public void setAgreementPdfName(String agreementPdfName) {
        this.agreementPdfName = agreementPdfName;
    }
}
