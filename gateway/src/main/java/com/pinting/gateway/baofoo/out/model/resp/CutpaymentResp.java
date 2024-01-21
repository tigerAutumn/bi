package com.pinting.gateway.baofoo.out.model.resp;

public class CutpaymentResp extends BaoFooBaseResp {

	/**
     * 商户订单号
     */
    private String trans_id;

    /**
     * 成功金额
     */
    private String succ_amt;


    /**
     * 宝付交易号
     */
    private String trans_no;

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getSucc_amt() {
        return succ_amt;
    }

    public void setSucc_amt(String succ_amt) {
        this.succ_amt = succ_amt;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }
}
