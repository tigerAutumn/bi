package com.pinting.business.accounting.loan.model;

/**
 * 宝付代扣统一返回参数
 * @author bianyatian
 * @2018-4-1 上午10:36:38
 */
public class BaoFooDKRes {

	/** 返回码 */
	private String resCode;
	/** 返回信息 */
	private String resMsg;
	/** 成功金额*/
	private String succ_amt;
	
	/**
     * 钱包转账订单号
     */
    private String pay4OnlineOrderNo;
	
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
	public String getSucc_amt() {
		return succ_amt;
	}
	public void setSucc_amt(String succ_amt) {
		this.succ_amt = succ_amt;
	}
	public String getPay4OnlineOrderNo() {
		return pay4OnlineOrderNo;
	}
	public void setPay4OnlineOrderNo(String pay4OnlineOrderNo) {
		this.pay4OnlineOrderNo = pay4OnlineOrderNo;
	}
	
}
