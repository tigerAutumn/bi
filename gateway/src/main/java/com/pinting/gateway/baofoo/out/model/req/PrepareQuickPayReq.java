package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/19.
 * 预支付交易
 */
public class PrepareQuickPayReq extends BaoFooOutBaseReq {

    /**
     * 商户订单号
     * 唯一订单号，8-20 位字母和 数字，同一天内不可重复； 如果商户开通“发送短信类交易”，该订单号从发送短信类交易到当前交易都有效
     */
    private String trans_id;

    /**
     * 绑定标识号
     */
    private String bind_id;

    /**
     * 交易金额
     * 单位：分 例：1 元则提交 100
     */
    private String txn_amt;

    /**
     * 用户ip地址 （用于风险控制参数）
     */
    private String  risk_content ;

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

    public String getRisk_content() {
        return risk_content;
    }

    public void setRisk_content(String risk_content) {
        this.risk_content = risk_content;
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
