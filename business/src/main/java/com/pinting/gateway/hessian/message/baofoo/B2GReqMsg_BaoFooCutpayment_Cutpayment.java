package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 宝付代扣添加字段
 * @author bianyatian
 * @2017-12-7 上午9:23:29
 */
public class B2GReqMsg_BaoFooCutpayment_Cutpayment extends ReqMsg {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 4578402026820213892L;

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
    private String txnAmt;
    
    /**
     * 流水号
     */
    private String trans_serial_no;

	/**
	 * 附加字段 （长度不可超过128位）
	 */
	private String additional_info;
	
	/**
	 * 商户号
	 */
	private String merchantNo;
	
	/**
	 * 主/辅助通道标记;1 主通道,2 次通道
	 */
	private Integer isMain;
	
    public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
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

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTrans_serial_no() {
		return trans_serial_no;
	}

	public void setTrans_serial_no(String trans_serial_no) {
		this.trans_serial_no = trans_serial_no;
	}

	public String getAdditional_info() {
		return additional_info;
	}

	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}
}
