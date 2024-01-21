package com.pinting.business.model.vo;

/**
 * Created by zhangbao on 2017/11/10.
 */
public class HeadFeeCollectPayVO {

    private Integer rowno;
    private String lnUserName;
    private String mobile;
    private String type;
    private Double loanAmount;
    private Double collectAmount;
    private Double payAmount;
    private String time;

    private Double collectTotalAmount;
    private Double payTotalAmount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(Double collectAmount) {
        this.collectAmount = collectAmount;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLnUserName() {
        return lnUserName;
    }

    public void setLnUserName(String lnUserName) {
        this.lnUserName = lnUserName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getRowno() {
        return rowno;
    }

    public void setRowno(Integer rowno) {
        this.rowno = rowno;
    }

    public Double getCollectTotalAmount() {
        return collectTotalAmount;
    }

    public void setCollectTotalAmount(Double collectTotalAmount) {
        this.collectTotalAmount = collectTotalAmount;
    }

    public Double getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(Double payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }
}
