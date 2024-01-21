package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7955302060525085863L;

	/**
     * 原商户订单号
     * 由宝付返回，用于在后续类交易中唯一标识一笔交易
     */
    private String orig_trans_id;

    /**
     * 流水号
     */
    private String trans_serial_no;

    /**
     * 商户号
     */
    private String merchantNo;

    public String getOrig_trans_id() {
        return orig_trans_id;
    }

    public void setOrig_trans_id(String orig_trans_id) {
        this.orig_trans_id = orig_trans_id;
    }

    public String getTrans_serial_no() {
        return trans_serial_no;
    }

    public void setTrans_serial_no(String trans_serial_no) {
        this.trans_serial_no = trans_serial_no;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
