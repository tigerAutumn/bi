package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * 
 * @project gateway
 * @title BatchExtRepayResSuccessData.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，借款人批量还款返回--成功明细
 */
public class BatchExtRepayResSuccessData  implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6325937237045117523L;
	/**明细编号*/
	private 	String 		detail_no;
	/**交易金额*/
	private 	String 		trans_amt;
	public String getDetail_no() {
		return detail_no;
	}
	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}
	public String getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}
	
	
}
