package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsCashSchedule30 extends PageInfoObject {
    private Integer id;

    private Date cashDate;

    private Double cashBaseAmount;

    private Double bashInterestAmount;

    private Double cashBonusAmount;

    private Date createTime;
    
    private Double yunDaiAmount; //云贷应付金额
    
    private Double qiDaiAmount; //7贷应付金额

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCashDate() {
        return cashDate;
    }

    public void setCashDate(Date cashDate) {
        this.cashDate = cashDate;
    }

    public Double getCashBaseAmount() {
        return cashBaseAmount;
    }

    public void setCashBaseAmount(Double cashBaseAmount) {
        this.cashBaseAmount = cashBaseAmount;
    }

    public Double getBashInterestAmount() {
        return bashInterestAmount;
    }

    public void setBashInterestAmount(Double bashInterestAmount) {
        this.bashInterestAmount = bashInterestAmount;
    }

    public Double getCashBonusAmount() {
        return cashBonusAmount;
    }

    public void setCashBonusAmount(Double cashBonusAmount) {
        this.cashBonusAmount = cashBonusAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Double getYunDaiAmount() {
		return yunDaiAmount;
	}

	public void setYunDaiAmount(Double yunDaiAmount) {
		this.yunDaiAmount = yunDaiAmount;
	}

	public Double getQiDaiAmount() {
		return qiDaiAmount;
	}

	public void setQiDaiAmount(Double qiDaiAmount) {
		this.qiDaiAmount = qiDaiAmount;
	}
}