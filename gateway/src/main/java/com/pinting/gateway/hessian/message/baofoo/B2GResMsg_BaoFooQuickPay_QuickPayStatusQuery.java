package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery extends ResMsg {

    private String trans_id ;
    /**
     * 成功金额
     */
    private String succ_amt;

    /**
     * 宝付交易号
     */
    private String trans_no;

    public String getSucc_amt() {
        return succ_amt;
    }

    public void setSucc_amt(String succ_amt) {
        this.succ_amt = succ_amt;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }
}
