package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherOnlineTransResp {

    /**
     * 宝付订单号
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
     * 收款人宝付商户名
     */
    private String to_acc_name;

    /**
     * 收款人宝付注册帐号
     */
    private String to_acc_no;

    /**
     * 订单交易处理状态
     * 0：转账中；
     * 1：转账成功；
     * -1：转账失败；
     * 2：转账退款
     */
    private String state;

    /**
     * 摘要
     */
    private String trans_summary;

    /**
     * 备注（错误信息）
     */
    private String trans_remark;


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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTrans_summary() {
        return trans_summary;
    }

    public void setTrans_summary(String trans_summary) {
        this.trans_summary = trans_summary;
    }

    public String getTrans_remark() {
        return trans_remark;
    }

    public void setTrans_remark(String trans_remark) {
        this.trans_remark = trans_remark;
    }

}
