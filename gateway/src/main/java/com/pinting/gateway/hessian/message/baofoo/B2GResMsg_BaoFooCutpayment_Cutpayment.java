package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_BaoFooCutpayment_Cutpayment extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3062874045363942994L;

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
    
    /**
     * 钱报转账订单号
     */
    private String pay4OnlineOrderNo;

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getSucc_amt() {
        return succ_amt;
    }

    public void setSucc_amt(String succ_amt) {
        this.succ_amt = succ_amt;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

	public String getPay4OnlineOrderNo() {
		return pay4OnlineOrderNo;
	}

	public void setPay4OnlineOrderNo(String pay4OnlineOrderNo) {
		this.pay4OnlineOrderNo = pay4OnlineOrderNo;
	}
}
