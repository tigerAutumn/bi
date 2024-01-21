package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 协议支付手续费明细VO
 *
 * @author shh
 * @date 2018/7/10 11:20
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class AgreementFeeDetailVO {

    /* 订单号 */
    private String orderNo;

    /* 订单金额 */
    private Double amount;

    /* 协议支付手续费 */
    private Double agreementFeeAmount;

    private Date updateTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAgreementFeeAmount() {
        return agreementFeeAmount;
    }

    public void setAgreementFeeAmount(Double agreementFeeAmount) {
        this.agreementFeeAmount = agreementFeeAmount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
