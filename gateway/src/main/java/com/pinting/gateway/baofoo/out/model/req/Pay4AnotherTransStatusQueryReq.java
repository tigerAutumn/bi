package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherTransStatusQueryReq extends BaoFooOutBaseReq{

    /**
     * 宝付批次号
     */
    private String trans_batchid;

    /**
     * 商户订单号
     */
    private String trans_no;

    public String getTrans_batchid() {
        return trans_batchid;
    }

    public void setTrans_batchid(String trans_batchid) {
        this.trans_batchid = trans_batchid;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }
}
