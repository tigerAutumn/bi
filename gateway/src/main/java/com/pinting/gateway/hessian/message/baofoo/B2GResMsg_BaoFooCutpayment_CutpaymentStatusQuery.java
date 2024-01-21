package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2963191693905430961L;
	/**
     * 商户订单号
     */
	private String trans_id;
	/**
	 * 成功金额
	 */
	private String succ_amt;

	/**
	 * 宝付交易号
	 */
	private String trans_no;

	public String getSucc_amt() {
		return succ_amt;
	}

	public void setSucc_amt(String succ_amt) {
		this.succ_amt = succ_amt;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getTrans_no() {
		return trans_no;
	}

	public void setTrans_no(String trans_no) {
		this.trans_no = trans_no;
	}

}
