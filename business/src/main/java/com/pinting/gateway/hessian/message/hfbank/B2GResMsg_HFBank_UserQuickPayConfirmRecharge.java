package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.DirectQuickConfirmData;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 快捷充值确认响应信息
 */
public class B2GResMsg_HFBank_UserQuickPayConfirmRecharge extends ResMsg {

    private static final long serialVersionUID = 2819467224917033011L;
    /* 返回业务数据 */
    private DirectQuickConfirmData data;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    public DirectQuickConfirmData getData() {
        return data;
    }

    public void setData(DirectQuickConfirmData data) {
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
