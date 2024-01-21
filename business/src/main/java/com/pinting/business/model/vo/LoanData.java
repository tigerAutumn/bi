package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * 收益概况
 * Created by cyb on 2018/3/12.
 */
public class LoanData implements Serializable {
    private static final long serialVersionUID = 3116770114288143872L;
    // 自成立以来累计借贷金额（元） 同累计出借额
    private String totalLoanAmount;
    // 自成立以来累计借贷笔数;
    private Integer totalLoanNumber;
    // 当前待还借贷金额（元）;
    private String currentWaitRepayAmount;
    // 当前待还借贷笔数t;
    private Integer currentWaitRepayNumber;
    // 关联关系借款余额（元）;
    private String relationBorrowerAmount;
    // 关联关系借款余额笔数t;
    private Integer relationBorrowerNumber;

    public String getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(String totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public Integer getTotalLoanNumber() {
        return totalLoanNumber;
    }

    public void setTotalLoanNumber(Integer totalLoanNumber) {
        this.totalLoanNumber = totalLoanNumber;
    }

    public String getCurrentWaitRepayAmount() {
        return currentWaitRepayAmount;
    }

    public void setCurrentWaitRepayAmount(String currentWaitRepayAmount) {
        this.currentWaitRepayAmount = currentWaitRepayAmount;
    }

    public Integer getCurrentWaitRepayNumber() {
        return currentWaitRepayNumber;
    }

    public void setCurrentWaitRepayNumber(Integer currentWaitRepayNumber) {
        this.currentWaitRepayNumber = currentWaitRepayNumber;
    }

    public String getRelationBorrowerAmount() {
        return relationBorrowerAmount;
    }

    public void setRelationBorrowerAmount(String relationBorrowerAmount) {
        this.relationBorrowerAmount = relationBorrowerAmount;
    }

    public Integer getRelationBorrowerNumber() {
        return relationBorrowerNumber;
    }

    public void setRelationBorrowerNumber(Integer relationBorrowerNumber) {
        this.relationBorrowerNumber = relationBorrowerNumber;
    }
}
