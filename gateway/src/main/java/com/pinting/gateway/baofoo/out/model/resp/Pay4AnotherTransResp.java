package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class  Pay4AnotherTransResp {

    /**
     * 宝付订单号返回唯一
     */
    private String trans_orderid;

    /**
     * 宝付批次号
     */
    private String trans_batchid;

    /**
     * 商户订单号
     */
    private String trans_no;

    /**
     * 转账金额
     */
    private String trans_money;

    /**
     * 收款人姓名
     */
    private String to_acc_name;

    /**
     * 收款人银行帐号
     */
    private String to_acc_no;

    /**
     * 收款人开户行机构名
     * 返回格式：省|市|支行
     */
    private String to_acc_dept;

    /**
     * 摘要
     */
    private String trans_summary;

    public String getTrans_orderid() {
        return trans_orderid;
    }

    public void setTrans_orderid(String trans_orderid) {
        this.trans_orderid = trans_orderid;
    }

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

    public String getTrans_money() {
        return trans_money;
    }

    public void setTrans_money(String trans_money) {
        this.trans_money = trans_money;
    }

    public String getTo_acc_name() {
        return to_acc_name;
    }

    public void setTo_acc_name(String to_acc_name) {
        this.to_acc_name = to_acc_name;
    }

    public String getTo_acc_no() {
        return to_acc_no;
    }

    public void setTo_acc_no(String to_acc_no) {
        this.to_acc_no = to_acc_no;
    }

    public String getTo_acc_dept() {
        return to_acc_dept;
    }

    public void setTo_acc_dept(String to_acc_dept) {
        this.to_acc_dept = to_acc_dept;
    }

    public String getTrans_summary() {
        return trans_summary;
    }

    public void setTrans_summary(String trans_summary) {
        this.trans_summary = trans_summary;
    }
}
