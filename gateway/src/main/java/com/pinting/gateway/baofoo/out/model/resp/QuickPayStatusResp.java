package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/19.
 */
public class QuickPayStatusResp extends BaoFooBaseResp {


    /**
     * 商户订单号
     */
    private String trans_id;
    /**
     * 宝付交易号
     */
    private String trans_no;

    /**
     * 成功金额
     */
    private String succ_amt;

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getSucc_amt() {
        return succ_amt;
    }

    public void setSucc_amt(String succ_amt) {
        this.succ_amt = succ_amt;
    }

    @Override
    public String getTrans_id() {
        return trans_id;
    }

    @Override
    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }
}
