package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_UnBindCard extends ReqMsg {


    private String bind_id;

    /**
     * 流水号
     */
    private String trans_serial_no;


    public String getBind_id() {
        return bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }
}
