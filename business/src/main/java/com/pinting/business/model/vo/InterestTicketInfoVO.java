package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by cyb on 2018/4/2.
 */
public class InterestTicketInfoVO {

    private Integer id; // 红包、加息券ID
    private Integer userId; // 用户ID
    private String serialName;  // 优惠券名称
    private Double full; // 满额
    private String termLimit;
    private String productLimit;
    private Double rate;    // 利率%
    private Date useTimeStart;
    private Date useTimeEnd;
    private Date useTime;  //加息券使用时间
    private String productName;  //产品名称
    private String status;

    private Integer term;
    private Double interest;
    private String isSupportIncrInterest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public Double getFull() {
        return full;
    }

    public void setFull(Double full) {
        this.full = full;
    }

    public String getTermLimit() {
        return termLimit;
    }

    public void setTermLimit(String termLimit) {
        this.termLimit = termLimit;
    }

    public String getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(String productLimit) {
        this.productLimit = productLimit;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getUseTimeStart() {
        return useTimeStart;
    }

    public void setUseTimeStart(Date useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public Date getUseTimeEnd() {
        return useTimeEnd;
    }

    public void setUseTimeEnd(Date useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

    public String getIsSupportIncrInterest() {
        return isSupportIncrInterest;
    }

    public void setIsSupportIncrInterest(String isSupportIncrInterest) {
        this.isSupportIncrInterest = isSupportIncrInterest;
    }
}
