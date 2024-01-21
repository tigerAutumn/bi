package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery extends ReqMsg {

    /**
     * 原商户订单号
     * 由宝付返回，用于在后续类交易中唯一标识一笔交易
     */
    private String orig_trans_id;

    /**
     * 流水号
     */
    private String trans_serial_no;

    public String getOrig_trans_id() {
        return orig_trans_id;
    }

    public void setOrig_trans_id(String orig_trans_id) {
        this.orig_trans_id = orig_trans_id;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }
}
