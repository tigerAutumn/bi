package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 网关充值请求信息
 */
public class GatewayRechargeRequestReqModel extends BaseReqModel {

    /* 电子账户 */
    private String platcust;
    /* 充值类型（1-用户充值） */
    private String type;
    /* 1-投资账户 2-融资账户 若为用户充值，则为必填项 */
    private String charge_type;
    /* 银行编码 */
    private String bankcode;
    /* 卡类型(借记卡) */
    private String card_type;
    /* 币种（默认CNY） */
    private String currency_code;
    /* 卡号 */
    private String card_no;
    /* 金额 */
    private String amt;
    /* 同步回调地址 */
    private String return_url;
    /* 异步通知地址 */
    private String notify_url;
    /* 支付通道 */
    private String pay_code;
    /* 备注 */
    private String remark;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
