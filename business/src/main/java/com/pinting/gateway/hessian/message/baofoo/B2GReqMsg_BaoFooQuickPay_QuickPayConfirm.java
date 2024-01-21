package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_QuickPayConfirm extends ReqMsg {

    /**
     * 订单号
     */
    private String trans_id;
    /**
     * 绑定标识号
     */
    private String bind_id;

    /**
     * 交易金额
     * 单位：分 例：1 元则提交 100
     */
    private String txn_amt;

    /**
     * 短信验证码
     */
    private String  sms_code;

    /**
     * 流水号
     */
    private String trans_serial_no;

    /**
     * 附加字段 （长度不可超过128位）
     */
    private String additional_info;


    public String getSms_code() {
        return sms_code;
    }

    public void setSms_code(String sms_code) {
        this.sms_code = sms_code;
    }

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

    public String getTxn_amt() {
        return txn_amt;
    }

    public void setTxn_amt(String txn_amt) {
        this.txn_amt = txn_amt;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }
}
