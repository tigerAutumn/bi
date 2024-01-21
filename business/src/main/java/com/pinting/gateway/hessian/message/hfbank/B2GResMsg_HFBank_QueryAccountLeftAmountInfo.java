package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 账户余额明细查询响应信息
 * Created by shh on 2017/4/3.
 */
public class B2GResMsg_HFBank_QueryAccountLeftAmountInfo extends ResMsg {

    private static final long serialVersionUID = -6720937429264120879L;

    /* 返回账户余额明细 */
    private String data;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
