package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/8/5.
 */
public class SendSmsReq extends BaoFooOutBaseReq {

    /**
     * 商户订单号
     * 唯一订单号，8-20 位字母和数 字，同一天内不可重复； 该订单号从发送短信类交易到 当前交易都有效
     */
    private String trans_id;

    /**
     * 请求绑定的银行卡号
     * (绑卡时必填)
     */
    private String acc_no;

    /**
     * 银行卡绑定手机号
     * (绑定关系类交易必填，支付类交易非必填)
     */
    private String mobile;

    /**
     * 绑定标识号
     * (用于绑定关系的唯一标识)
     */
    private String bind_id;

    /**
     * 交易金额 单位：分
     * （非必填）
     */
    private String txn_amt;

    /**
     * 下一步进行的 交易子类
     */
    private String next_txn_sub_type;

    private String trade_date;

    /**
     * 附加字段
     */
    private String additional_info;

    /**
     * 请求保留域
     */
    private String req_reserved;

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

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBind_id() {
        return bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }

    public String getTxn_amt() {
        return txn_amt;
    }

    public void setTxn_amt(String txn_amt) {
        this.txn_amt = txn_amt;
    }

    public String getNext_txn_sub_type() {
        return next_txn_sub_type;
    }

    public void setNext_txn_sub_type(String next_txn_sub_type) {
        this.next_txn_sub_type = next_txn_sub_type;
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

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }
}
