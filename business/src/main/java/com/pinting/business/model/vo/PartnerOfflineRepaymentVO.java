package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/11/2
 * Description: 资产方线下还款对账vo
 */
public class PartnerOfflineRepaymentVO {

    /* 合作方还款订单号 */
    private String partnerOrderNo;

    /* 资产方 */
    private String partnerCode;

    /* 申请时间 */
    private Date createTime;

    /* 还款总额 */
    private Double doneTotal;

    /* 还款状态 */
    private String status;
    
    // 支付订单号
    private String payOrderNo;
    
    public String getPartnerOrderNo() {
        return partnerOrderNo;
    }

    public void setPartnerOrderNo(String partnerOrderNo) {
        this.partnerOrderNo = partnerOrderNo;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getDoneTotal() {
        return doneTotal;
    }

    public void setDoneTotal(Double doneTotal) {
        this.doneTotal = doneTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
    
}
