package com.pinting.gateway.baofoo.out.model.req;

public class CutpaymentReq extends BaoFooOutBaseReq {
	
	/**
     * 商户订单号
     * 唯一订单号，8-20 位字母和数 字，该订单号需与预绑卡时发 送的商户订单号一致
     */
    private String trans_id;
    
    /**
     * 银行编码
     */
    private String pay_code;
    
    /**
     * 请求的银行卡号
     */
    private String acc_no;
    
    /**
     * 身份证类型
     */
    private String id_card_type;
    
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
     * 交易金额 单位：分
     *
     */
    private String txn_amt;
    
    /**
     * 流水号
     */
    private String trans_serial_no;
    
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

	private String pay_cm = "2";

	public String getPay_cm() {
		return pay_cm;
	}

	public void setPay_cm(String pay_cm) {
		this.pay_cm = pay_cm;
	}

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

	public String getTrans_serial_no() {
		return trans_serial_no;
	}

	public void setTrans_serial_no(String trans_serial_no) {
		this.trans_serial_no = trans_serial_no;
	}

	public String getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
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

	public String getId_card_type() {
		return id_card_type;
	}

	public void setId_card_type(String id_card_type) {
		this.id_card_type = id_card_type;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getTxn_amt() {
		return txn_amt;
	}

	public void setTxn_amt(String txn_amt) {
		this.txn_amt = txn_amt;
	}

}
