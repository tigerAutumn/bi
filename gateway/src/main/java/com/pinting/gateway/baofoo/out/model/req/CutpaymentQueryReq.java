package com.pinting.gateway.baofoo.out.model.req;

public class CutpaymentQueryReq extends BaoFooOutBaseReq {

	/**
     * 原商户订单号
     * 由宝付返回，用于在后续类交易中唯一标识一笔交易
     */
    private String orig_trans_id;

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

    /**
     * 流水号
     */
    private String trans_serial_no;
    
    public String getOrig_trans_id() {
        return orig_trans_id;
    }

    public void setOrig_trans_id(String orig_trans_id) {
        this.orig_trans_id = orig_trans_id;
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
