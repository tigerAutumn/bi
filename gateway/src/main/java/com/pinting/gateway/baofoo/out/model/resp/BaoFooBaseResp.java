package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/18.
 */
public class BaoFooBaseResp {

    private String version;

    private String req_reserved;

    private String additional_info;

    private String resp_code;

    private String resp_msg;

    /**
     * 交易卡类型
     */
    private String pay_card_type;

    /**
     * 商户订单号
     */
    private String trans_id;

    /**
     * 商户流水号
     */
    private String trans_serial_no;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReq_reserved() {
        return req_reserved;
    }

    public void setReq_reserved(String req_reserved) {
        this.req_reserved = req_reserved;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    public String getResp_msg() {
        return resp_msg;
    }

    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }

    public String getPay_card_type() {
        return pay_card_type;
    }

    public void setPay_card_type(String pay_card_type) {
        this.pay_card_type = pay_card_type;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }
}
