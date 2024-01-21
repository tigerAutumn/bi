package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.QueryOrderData;

/**
 * 充值订单状态查询响应信息
 */
public class B2GResMsg_HFBank_GetFundOrderInfo extends ResMsg {


    private static final long serialVersionUID = 3332896314461393494L;
    /* 处理成功时，返回业务数据 */
    private QueryOrderData data;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    public QueryOrderData getData() {
        return data;
    }

    public void setData(QueryOrderData data) {
        this.data = data;
    }

    public String getRecode() {
        return recode;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public String getRemsg() {
        return remsg;
    }

    public void setRemsg(String remsg) {
        this.remsg = remsg;
    }
}
