package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量提现请求信息明细数据
 */
public class BatchWithdrawExtDataReqModel implements Serializable {

    private static final long serialVersionUID = 5226033253536953805L;
    /* 明细编号 */
    private String detail_no;
    /* 用户编号 */
    private String platcust;
    /* 提现金额（包含手续费） */
    private String amt;
    /* 是否垫资:(1-不垫资， 2-垫资)；默认不垫付。 */
    private String is_advance;
    /* 支付通道 */
    private String pay_code;
    /* 手续费金额（小于提现金额） */
    private String fee_amt;
    /* 提现类型，默认平台提现，0：用户提现，  */
    private String type;
    /* 个人提现必填，提现的账户类型：0投资账户1融资账户 */
    private String withdraw_type;
    /* 银行编号，对公必填 */
    private String bank_id;
    /* 异步通知地址 */
    private String notify_url;
    /* 备注 */
    private String remark;
    /* 公私标志 0-个人 1-公司； 默认个人 */
    private String client_property;
    /* 城市编码（富友必填） */
    private String city_code;
    /* 交易类型（代表 pay行内，payReal 代表跨行） */
    private String tran_type;
    /* 银行行号（行内支付需要填）*/
    private String bank_code;
    /* 银行名称（行内支付需要填） */
    private String bank_name;

    public String getTran_type() {
        return tran_type;
    }

    public void setTran_type(String tran_type) {
        this.tran_type = tran_type;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getIs_advance() {
        return is_advance;
    }

    public void setIs_advance(String is_advance) {
        this.is_advance = is_advance;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(String withdraw_type) {
        this.withdraw_type = withdraw_type;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
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

	public String getClient_property() {
		return client_property;
	}

	public void setClient_property(String client_property) {
		this.client_property = client_property;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getFee_amt() {
		return fee_amt;
	}

	public void setFee_amt(String fee_amt) {
		this.fee_amt = fee_amt;
	}
}