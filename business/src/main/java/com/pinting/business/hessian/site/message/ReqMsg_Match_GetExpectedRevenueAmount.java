package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 币港湾赞分期产品自动出借计划协议 等额本息计算期末预期收益数额  入参
 * Created by shh on 2016/9/29 12:08.
 */
public class ReqMsg_Match_GetExpectedRevenueAmount extends ReqMsg {

    private static final long serialVersionUID = -2015985098505571772L;

    /* 子账户ID */
    private Integer accountId;

    /* 产品ID */
    private Integer productId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
