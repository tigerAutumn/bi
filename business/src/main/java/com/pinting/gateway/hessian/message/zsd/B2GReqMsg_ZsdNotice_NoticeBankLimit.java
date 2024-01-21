package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.zsd.model.BankLimit;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/8/31
 * Description: 推送银行卡限额 请求信息
 */
public class B2GReqMsg_ZsdNotice_NoticeBankLimit extends ReqMsg {

    private static final long serialVersionUID = 5913844282412280868L;

    /* 各银行限额 */
    private List<BankLimit> limits;

    public List<BankLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<BankLimit> limits) {
        this.limits = limits;
    }
}
