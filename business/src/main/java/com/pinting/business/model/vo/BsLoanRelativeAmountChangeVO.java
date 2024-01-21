package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsLoanRelativeAmountChange;

/**
 * 
 * @author HuanXiong
 * @version 2016-4-29 上午10:47:46
 */
public class BsLoanRelativeAmountChangeVO extends BsLoanRelativeAmountChange {

    private String borrowerCustomerId;

    private String borrowerIdCard;

    private String borrowerName;

    private String productName;

    private Double borrowAmount;

    private Double initAmount;

    private String borrowId;

    private String borrowTransNo;

    private Date time;

    private String isRepay;

    private Date repayTime;

    public String getBorrowerCustomerId() {
        return borrowerCustomerId;
    }

    public void setBorrowerCustomerId(String borrowerCustomerId) {
        this.borrowerCustomerId = borrowerCustomerId;
    }

    public String getBorrowerIdCard() {
        return borrowerIdCard;
    }

    public void setBorrowerIdCard(String borrowerIdCard) {
        this.borrowerIdCard = borrowerIdCard;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(Double borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public Double getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount = initAmount;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowTransNo() {
        return borrowTransNo;
    }

    public void setBorrowTransNo(String borrowTransNo) {
        this.borrowTransNo = borrowTransNo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIsRepay() {
        return isRepay;
    }

    public void setIsRepay(String isRepay) {
        this.isRepay = isRepay;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }
}
