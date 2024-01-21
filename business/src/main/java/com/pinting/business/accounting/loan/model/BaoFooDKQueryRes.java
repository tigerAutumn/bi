package com.pinting.business.accounting.loan.model;

/**
 * 宝付代扣查询结果统一返回参数
 * @author bianyatian
 * @2018-4-1 下午1:55:55
 */
public class BaoFooDKQueryRes {

	/** 返回码 */
	private String resCode;
	/** 返回信息 */
	private String resMsg;
	
	private String trans_id;
	/**
	 * 成功金额
	 */
	private Double succ_amt;

	/**
	 * 宝付交易号
	 */
	private String trans_no;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public Double getSucc_amt() {
		return succ_amt;
	}

	public void setSucc_amt(Double succ_amt) {
		this.succ_amt = succ_amt;
	}

	public String getTrans_no() {
		return trans_no;
	}

	public void setTrans_no(String trans_no) {
		this.trans_no = trans_no;
	}
	
	
}
