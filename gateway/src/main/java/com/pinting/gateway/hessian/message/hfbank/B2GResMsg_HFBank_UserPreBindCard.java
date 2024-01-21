package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 短验绑卡申请响应信息
 */
public class B2GResMsg_HFBank_UserPreBindCard extends ResMsg {

    private static final long serialVersionUID = 3628342773413101381L;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

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
