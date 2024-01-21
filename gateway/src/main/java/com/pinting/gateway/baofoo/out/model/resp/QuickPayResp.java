package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/19.
 */
public class QuickPayResp extends BaoFooBaseResp {

    /**
     * 宝付业务流水号
     */
    private String business_no;

    /**
     * 成功金额
     */
    private String succ_amt;


    /**
     * 宝付交易号
     */
    private String trans_no;

    public String getBusiness_no() {
        return business_no;
    }

    public void setBusiness_no(String business_no) {
        this.business_no = business_no;
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
