package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherOnlineTransReq extends BaoFooOutBaseReq{

    /**
     * 商户订单号
     * 商户唯一流水号， 具体格式建议：商户编号+14 位日期+3 位随机数
     */
    private String trans_no;

    /**
     * 交易金额
     * 单位：元
     */
    private String trans_money;

    /**
     * 收款人宝付会员名
     */
    private String to_acc_name;

    /**
     * 收款人宝付注册帐号
     */
    private String to_acc_no;

    /**
     * 收款方会员号
     */
    private String to_member_id;

    /**
     * 摘要
     */
    private String trans_summary;

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

    public String getTo_member_id() {
        return to_member_id;
    }

    public void setTo_member_id(String to_member_id) {
        this.to_member_id = to_member_id;
    }

    public String getTrans_summary() {
        return trans_summary;
    }

    public void setTrans_summary(String trans_summary) {
        this.trans_summary = trans_summary;
    }
}
