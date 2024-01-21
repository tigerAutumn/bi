package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.GetAccountInfoData;

/**
 * 标的账户余额查询响应信息
 * Created by shh on 2017/4/3.
 */
public class B2GResMsg_HFBank_QueryAccountInfo extends ResMsg {

    private static final long serialVersionUID = 5479473670108251461L;

    private GetAccountInfoData data;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    public GetAccountInfoData getData() {
        return data;
    }

    public void setData(GetAccountInfoData data) {
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
