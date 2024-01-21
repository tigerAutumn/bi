package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 商品兑换
 */
public class ResMsg_MallExchangeFlow_ExchangeCommodity extends ResMsg {

    private static final long serialVersionUID = 1L;

    private Boolean exchangeResult; // 兑换结果：true 兑换成功，false 兑换失败

    public Boolean getExchangeResult() {
        return exchangeResult;
    }

    public void setExchangeResult(Boolean exchangeResult) {
        this.exchangeResult = exchangeResult;
    }
}
