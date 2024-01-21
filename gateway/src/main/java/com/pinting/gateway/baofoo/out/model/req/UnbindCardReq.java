package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/19.
 * 解绑卡请求
 */
public class UnbindCardReq extends BaoFooOutBaseReq {

    /**
     * 绑定标识号
     */
    private String bind_id;

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

    public String getBind_id() {
        return bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
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
