package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.QueryOrderData;

/**
 * 订单状态查询响应信息
 * Created by shh on 2017/4/3.
 */
public class B2GResMsg_HFBank_QueryOrder extends ResMsg {

    private static final long serialVersionUID = 4240026629753149167L;

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
