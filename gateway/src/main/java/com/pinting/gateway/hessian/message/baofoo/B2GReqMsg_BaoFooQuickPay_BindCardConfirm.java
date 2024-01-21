package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_BindCardConfirm extends ReqMsg {

    /**
     * 商户订单号
     * 唯一订单号，8-20 位字母和数 字，该订单号需与预绑卡时发 送的商户订单号一致
     */
    private String trans_id;

    /**
     * 短信验证码
     */
    private String  sms_code;

    /**
     * 请求绑定的银行卡号
     */
    private String acc_no;

    /**
     * 身份证号
     */
    private String id_card;

    /**
     * 持卡人姓名
     */
    private String id_holder;

    /**
     * 银行卡绑定手机号
     */
    private String mobile;


    /**
     * 银行编码
     */
    private String pay_code;

    /**
     * 流水号
     */
    private String trans_serial_no;

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getSms_code() {
        return sms_code;
    }

    public void setSms_code(String sms_code) {
        this.sms_code = sms_code;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_holder() {
        return id_holder;
    }

    public void setId_holder(String id_holder) {
        this.id_holder = id_holder;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }
}
