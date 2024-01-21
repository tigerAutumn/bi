package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/18.
 * 预绑卡请求对象
 */
public class PrepareBindCardReq extends BaoFooOutBaseReq{

    /**
     * 商户订单号
     * 唯一订单号，8-20 位字母和数 字，同一天内不可重复； 该订单号从发送短信类交易到 当前交易都有效
     */
    private String trans_id;

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
     * 卡有效期
     * 格式为yyMM
     */
    private String vaild_date;

    /**
     * 卡安全码
     * 银行卡背后最后三位数字
     */
    private String vaild_no;

    /**
     * 银行编码
     */
    private String pay_code;

    /**
     *  订单日期
     *  格式：yyyyMMddHHmmss
     */
    private String trade_date;

    /**
     * 附加字段 （长度不可超过128位）
     */
    private String additional_info;

    /**
     *  请求方保留域
     */
    private String  req_reserved;


    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
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

    public String getVaild_date() {
        return vaild_date;
    }

    public void setVaild_date(String vaild_date) {
        this.vaild_date = vaild_date;
    }

    public String getVaild_no() {
        return vaild_no;
    }

    public void setVaild_no(String vaild_no) {
        this.vaild_no = vaild_no;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getReq_reserved() {
        return req_reserved;
    }

    public void setReq_reserved(String req_reserved) {
        this.req_reserved = req_reserved;
    }
}
