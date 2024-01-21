package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_Pay4Trans extends ReqMsg {

    /**
     * 商户订单号
     * 商户唯一流水号， 具体格式建议：商户编号+14 位日期+3 位随机数
     */
    private String trans_no;

    /**
     * 转账金额
     * 单位：元
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
     * 收款人银行名称
     */
    private String to_bank_name;

    /**
     * 收款人开户行省名
     * 对个人可不填写省；对公必须填写。
     */
    private String to_pro_name;

    /**
     * 收款人开户行市名
     * 对个人可不填写市；对公必须填写。
     */
    private String to_city_name;

    /**
     * 收款人开户行机构名
     * 对个人可不填写支行；对公必须填写。
     */
    private String to_acc_dept;

    /**
     * 银行卡身份证件号码
     */
    private String trans_card_id;

    /**
     * 银行卡预留手机号
     */
    private String trans_mobile;

    /**
     * 摘要
     */
    private String trans_summary;

    public String getTrans_card_id() {
        return trans_card_id;
    }

    public void setTrans_card_id(String trans_card_id) {
        this.trans_card_id = trans_card_id;
    }

    public String getTrans_mobile() {
        return trans_mobile;
    }

    public void setTrans_mobile(String trans_mobile) {
        this.trans_mobile = trans_mobile;
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

    public String getTo_bank_name() {
        return to_bank_name;
    }

    public void setTo_bank_name(String to_bank_name) {
        this.to_bank_name = to_bank_name;
    }

    public String getTo_pro_name() {
        return to_pro_name;
    }

    public void setTo_pro_name(String to_pro_name) {
        this.to_pro_name = to_pro_name;
    }

    public String getTo_city_name() {
        return to_city_name;
    }

    public void setTo_city_name(String to_city_name) {
        this.to_city_name = to_city_name;
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
