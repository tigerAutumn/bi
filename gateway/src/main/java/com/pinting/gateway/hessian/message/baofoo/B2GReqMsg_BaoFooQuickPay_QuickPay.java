package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_QuickPay extends ReqMsg {

    /**
     * 商户订单号
     * 唯一订单号，8-20 位字母和数 字，同一天内不可重复； 该订单号从发送短信类交易到 当前交易都有效
     */
    private String transId;

    /**
     * 绑定标识号
     * (用于绑定关系的唯一标识)
     */
    private String bindId;

    /**
     * 交易金额 单位：分
     *
     */
    private String txnAmt;

    /**
     * 流水号
     */
    private String trans_serial_no;


    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }
}
