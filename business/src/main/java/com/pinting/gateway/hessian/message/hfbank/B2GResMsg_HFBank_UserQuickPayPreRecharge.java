package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.DirectQuickRequestData;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 快捷充值响应信息
 */
public class B2GResMsg_HFBank_UserQuickPayPreRecharge extends ResMsg {

    private static final long serialVersionUID = -3370879231299538285L;

    /* 返回业务数据 */
    private DirectQuickRequestData data;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    public DirectQuickRequestData getData() {
        return data;
    }

    public void setData(DirectQuickRequestData data) {
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
