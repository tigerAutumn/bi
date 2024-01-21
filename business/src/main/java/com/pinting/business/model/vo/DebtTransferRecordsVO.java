package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 存管港湾产品-借款协议附件 债权转让记录VO
 * Created by shh on 2017/1/9 21:35.
 */
public class DebtTransferRecordsVO {

    /* 债权转让人 */
    private String outUserMobile;

    /* 债权受让人 */
    private String inUserMobile;

    /* 债权转让金额 本金+利息 */
    private Double amount;

    /* 债权转让时间 */
    private Date createTime;

    public String getOutUserMobile() {
        return outUserMobile;
    }

    public void setOutUserMobile(String outUserMobile) {
        this.outUserMobile = outUserMobile;
    }

    public String getInUserMobile() {
        return inUserMobile;
    }

    public void setInUserMobile(String inUserMobile) {
        this.inUserMobile = inUserMobile;
    }

    public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
