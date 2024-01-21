package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GResMsg_BaoFooQuickPay_BindCardConfirm extends ResMsg {


    /**
     * 商户订单号
     */
    private String trans_id;

    private String bind_id;


    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getBind_id() {
        return bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }
}
