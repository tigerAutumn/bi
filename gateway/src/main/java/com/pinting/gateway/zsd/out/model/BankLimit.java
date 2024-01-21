package com.pinting.gateway.zsd.out.model;


import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Map;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 推送银行卡限额请求信息 对象
 */
public class BankLimit implements Serializable {

    /* 支付类型 */
    @NotEmpty(message = "支付类型")
    private String payType;

    private Map<String,Limit> bankLimits;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Map<String, Limit> getBankLimits() {
        return bankLimits;
    }

    public void setBankLimits(Map<String, Limit> bankLimits) {
        this.bankLimits = bankLimits;
    }
}
