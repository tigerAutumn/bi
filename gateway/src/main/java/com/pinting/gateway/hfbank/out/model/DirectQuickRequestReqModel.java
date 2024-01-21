package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 快捷充值预下单请求信息
 */
public class DirectQuickRequestReqModel extends BaseReqModel {

    /* 用户编号 */
    private String platcust;
    /* 姓名 */
    private String name;
    /* 银行卡号 */
    private String card_no;
    /* 卡类型(借记卡) */
    private String card_type;
    /* 币种（默认CNY） */
    private String currency_code;
    /* 证件类型 */
    private String id_type;
    /* 证件号 */
    private String id_code;
    /* 银行预留手机号 */
    private String mobile;
    /* 电子邮箱 */
    private String email;
    /* 充值金额 */
    private String amt;
    /* 支付通道 */
    private String pay_code;
    /* 投融资账户类型，1-投资账户  2-融资账户 */
    private String account_type;
    /* 异步通知地址 */
    private String notify_url;
    /* 备注 */
    private String remark;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
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

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
